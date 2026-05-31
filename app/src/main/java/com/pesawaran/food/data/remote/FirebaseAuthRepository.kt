package com.pesawaran.food.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.pesawaran.food.data.model.*
import com.pesawaran.food.utils.Constants
import com.pesawaran.food.utils.LogUtils
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseAuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseAuthRepository"

    suspend fun registerWithEmail(
        email: String,
        password: String,
        fullName: String,
        phone: String,
        role: String
    ): Result<User> = try {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw Exception("User ID is null")

        val user = User(
            userId = userId,
            email = email,
            phone = phone,
            fullName = fullName,
            role = role,
            isVerified = false,
            createdAt = Date(),
            updatedAt = Date()
        )

        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .set(user)
            .await()

        LogUtils.d(TAG, "User registered successfully: $userId")
        Result.success(user)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Register error", e)
        Result.failure(e)
    }

    suspend fun loginWithEmail(email: String, password: String): Result<User> = try {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw Exception("User ID is null")

        val userDoc = firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .get()
            .await()

        val user = userDoc.toObject(User::class.java) ?: throw Exception("User not found")

        if (!user.isActive) {
            throw Exception("User account is not active")
        }

        LogUtils.d(TAG, "User logged in successfully: $userId")
        Result.success(user)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Login error", e)
        Result.failure(e)
    }

    suspend fun loginWithGoogle(idToken: String): Result<User> = try {
        val authResult = auth.signInWithCredential(
            com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        ).await()

        val firebaseUser = authResult.user ?: throw Exception("Firebase user is null")
        val userId = firebaseUser.uid

        val userDoc = firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .get()
            .await()

        val user = if (userDoc.exists()) {
            userDoc.toObject(User::class.java) ?: throw Exception("User not found")
        } else {
            // Create new user from Google account
            val newUser = User(
                userId = userId,
                email = firebaseUser.email ?: "",
                fullName = firebaseUser.displayName ?: "",
                profilePhoto = firebaseUser.photoUrl?.toString() ?: "",
                role = Constants.ROLE_CUSTOMER,
                isVerified = true,
                createdAt = Date(),
                updatedAt = Date()
            )
            firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
                .document(userId)
                .set(newUser)
                .await()
            newUser
        }

        LogUtils.d(TAG, "User logged in with Google: $userId")
        Result.success(user)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Google login error", e)
        Result.failure(e)
    }

    fun logout() {
        auth.signOut()
        LogUtils.d(TAG, "User logged out")
    }

    fun getCurrentUser(): User? {
        return null // Will be fetched from Firestore in repository
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun resetPassword(email: String): Result<Unit> = try {
        auth.sendPasswordResetEmail(email).await()
        LogUtils.d(TAG, "Password reset email sent to: $email")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Reset password error", e)
        Result.failure(e)
    }

    suspend fun updatePassword(newPassword: String): Result<Unit> = try {
        auth.currentUser?.updatePassword(newPassword)?.await()
        LogUtils.d(TAG, "Password updated")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update password error", e)
        Result.failure(e)
    }

    suspend fun updateUserProfile(displayName: String, photoUrl: String): Result<Unit> = try {
        val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .setPhotoUri(android.net.Uri.parse(photoUrl))
            .build()

        auth.currentUser?.updateProfile(profileUpdates)?.await()
        LogUtils.d(TAG, "User profile updated")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update profile error", e)
        Result.failure(e)
    }

    suspend fun sendEmailVerification(): Result<Unit> = try {
        auth.currentUser?.sendEmailVerification()?.await()
        LogUtils.d(TAG, "Verification email sent")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Send verification email error", e)
        Result.failure(e)
    }
}

class FirebaseUserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseUserRepository"

    suspend fun getUserById(userId: String): Result<User> = try {
        val user = firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java) ?: throw Exception("User not found")

        LogUtils.d(TAG, "User fetched: $userId")
        Result.success(user)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get user error", e)
        Result.failure(e)
    }

    suspend fun updateUser(user: User): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(user.userId)
            .set(user)
            .await()

        LogUtils.d(TAG, "User updated: ${user.userId}")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update user error", e)
        Result.failure(e)
    }

    suspend fun updateUserLocation(userId: String, latitude: Double, longitude: Double): Result<Unit> = try {
        val geoPoint = com.google.firebase.firestore.GeoPoint(latitude, longitude)
        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .update("location", geoPoint)
            .await()

        LogUtils.d(TAG, "User location updated: $userId")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update location error", e)
        Result.failure(e)
    }

    suspend fun updateDeviceToken(userId: String, deviceToken: String): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .update("deviceToken", deviceToken)
            .await()

        LogUtils.d(TAG, "Device token updated: $userId")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update device token error", e)
        Result.failure(e)
    }

    suspend fun deleteUser(userId: String): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(userId)
            .delete()
            .await()

        LogUtils.d(TAG, "User deleted: $userId")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Delete user error", e)
        Result.failure(e)
    }
}