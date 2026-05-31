package com.pesawaran.food.data.repository

import com.pesawaran.food.data.model.*
import com.pesawaran.food.data.remote.FirebaseAuthRepository
import com.pesawaran.food.data.remote.FirebaseProductRepository
import com.pesawaran.food.data.remote.FirebaseUMKMRepository
import com.pesawaran.food.data.remote.FirebaseOrderRepository
import com.pesawaran.food.data.remote.FirebaseUserRepository

class AuthRepository(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseUserRepository: FirebaseUserRepository
) {
    suspend fun registerWithEmail(
        email: String,
        password: String,
        fullName: String,
        phone: String,
        role: String
    ) = firebaseAuthRepository.registerWithEmail(email, password, fullName, phone, role)

    suspend fun loginWithEmail(email: String, password: String) =
        firebaseAuthRepository.loginWithEmail(email, password)

    suspend fun loginWithGoogle(idToken: String) =
        firebaseAuthRepository.loginWithGoogle(idToken)

    fun logout() = firebaseAuthRepository.logout()

    fun isUserLoggedIn() = firebaseAuthRepository.isUserLoggedIn()

    fun getCurrentUserId() = firebaseAuthRepository.getCurrentUserId()

    suspend fun resetPassword(email: String) =
        firebaseAuthRepository.resetPassword(email)

    suspend fun updatePassword(newPassword: String) =
        firebaseAuthRepository.updatePassword(newPassword)

    suspend fun updateUserProfile(displayName: String, photoUrl: String) =
        firebaseAuthRepository.updateUserProfile(displayName, photoUrl)

    suspend fun sendEmailVerification() =
        firebaseAuthRepository.sendEmailVerification()

    suspend fun getUserById(userId: String) =
        firebaseUserRepository.getUserById(userId)

    suspend fun updateUser(user: User) =
        firebaseUserRepository.updateUser(user)

    suspend fun updateUserLocation(userId: String, latitude: Double, longitude: Double) =
        firebaseUserRepository.updateUserLocation(userId, latitude, longitude)

    suspend fun updateDeviceToken(userId: String, deviceToken: String) =
        firebaseUserRepository.updateDeviceToken(userId, deviceToken)
}

class ProductRepository(
    private val firebaseProductRepository: FirebaseProductRepository
) {
    suspend fun getProducts(umkmId: String? = null, category: String? = null, limit: Int = 20) =
        firebaseProductRepository.getProducts(umkmId, category, limit)

    suspend fun getProductById(productId: String) =
        firebaseProductRepository.getProductById(productId)

    suspend fun searchProducts(query: String, limit: Int = 20) =
        firebaseProductRepository.searchProducts(query, limit)

    suspend fun getPopularProducts(limit: Int = 10) =
        firebaseProductRepository.getPopularProducts(limit)

    suspend fun addProduct(product: Product) =
        firebaseProductRepository.addProduct(product)

    suspend fun updateProduct(productId: String, product: Product) =
        firebaseProductRepository.updateProduct(productId, product)

    suspend fun deleteProduct(productId: String) =
        firebaseProductRepository.deleteProduct(productId)
}

class UMKMRepository(
    private val firebaseUMKMRepository: FirebaseUMKMRepository
) {
    suspend fun getUMKMById(umkmId: String) =
        firebaseUMKMRepository.getUMKMById(umkmId)

    suspend fun getUMKMsByOwner(ownerId: String) =
        firebaseUMKMRepository.getUMKMsByOwner(ownerId)

    suspend fun createUMKM(umkm: UMKM) =
        firebaseUMKMRepository.createUMKM(umkm)

    suspend fun updateUMKM(umkmId: String, umkm: UMKM) =
        firebaseUMKMRepository.updateUMKM(umkmId, umkm)
}

class OrderRepository(
    private val firebaseOrderRepository: FirebaseOrderRepository
) {
    suspend fun createOrder(order: Order) =
        firebaseOrderRepository.createOrder(order)

    suspend fun getOrderById(orderId: String) =
        firebaseOrderRepository.getOrderById(orderId)

    suspend fun getCustomerOrders(customerId: String) =
        firebaseOrderRepository.getCustomerOrders(customerId)

    suspend fun getUMKMOrders(umkmId: String) =
        firebaseOrderRepository.getUMKMOrders(umkmId)

    suspend fun updateOrderStatus(orderId: String, status: String) =
        firebaseOrderRepository.updateOrderStatus(orderId, status)
}