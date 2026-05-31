package com.pesawaran.food.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pesawaran.food.data.model.*
import com.pesawaran.food.utils.Constants
import com.pesawaran.food.utils.LogUtils
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseProductRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseProductRepository"

    suspend fun getProducts(umkmId: String? = null, category: String? = null, limit: Int = Constants.PAGE_SIZE): Result<List<Product>> = try {
        var query: Query = firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION)

        if (!umkmId.isNullOrEmpty()) {
            query = query.whereEqualTo("umkmId", umkmId)
        }

        if (!category.isNullOrEmpty()) {
            query = query.whereEqualTo("category", category)
        }

        query = query.whereEqualTo("isAvailable", true)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(limit.toLong())

        val products = query.get().await().toObjects(Product::class.java)
        LogUtils.d(TAG, "Products fetched: ${products.size}")
        Result.success(products)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get products error", e)
        Result.failure(e)
    }

    suspend fun getProductById(productId: String): Result<Product> = try {
        val product = firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION)
            .document(productId)
            .get()
            .await()
            .toObject(Product::class.java) ?: throw Exception("Product not found")

        LogUtils.d(TAG, "Product fetched: $productId")
        Result.success(product)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get product error", e)
        Result.failure(e)
    }

    suspend fun searchProducts(query: String, limit: Int = Constants.PAGE_SIZE): Result<List<Product>> = try {
        val products = firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION)
            .whereEqualTo("isAvailable", true)
            .limit(limit.toLong())
            .get()
            .await()
            .toObjects(Product::class.java)
            .filter { it.name.contains(query, ignoreCase = true) }

        LogUtils.d(TAG, "Products searched: ${products.size}")
        Result.success(products)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Search products error", e)
        Result.failure(e)
    }

    suspend fun getPopularProducts(limit: Int = 10): Result<List<Product>> = try {
        val products = firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION)
            .whereEqualTo("isPopular", true)
            .whereEqualTo("isAvailable", true)
            .orderBy("rating", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .get()
            .await()
            .toObjects(Product::class.java)

        LogUtils.d(TAG, "Popular products fetched: ${products.size}")
        Result.success(products)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get popular products error", e)
        Result.failure(e)
    }

    suspend fun addProduct(product: Product): Result<String> = try {
        val docRef = firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION).add(product).await()
        LogUtils.d(TAG, "Product added: ${docRef.id}")
        Result.success(docRef.id)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Add product error", e)
        Result.failure(e)
    }

    suspend fun updateProduct(productId: String, product: Product): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION)
            .document(productId)
            .set(product)
            .await()

        LogUtils.d(TAG, "Product updated: $productId")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update product error", e)
        Result.failure(e)
    }

    suspend fun deleteProduct(productId: String): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_PRODUCTS_COLLECTION)
            .document(productId)
            .delete()
            .await()

        LogUtils.d(TAG, "Product deleted: $productId")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Delete product error", e)
        Result.failure(e)
    }
}

class FirebaseUMKMRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseUMKMRepository"

    suspend fun getUMKMById(umkmId: String): Result<UMKM> = try {
        val umkm = firestore.collection(Constants.FIRESTORE_UMKM_COLLECTION)
            .document(umkmId)
            .get()
            .await()
            .toObject(UMKM::class.java) ?: throw Exception("UMKM not found")

        LogUtils.d(TAG, "UMKM fetched: $umkmId")
        Result.success(umkm)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get UMKM error", e)
        Result.failure(e)
    }

    suspend fun getUMKMsByOwner(ownerId: String): Result<List<UMKM>> = try {
        val umkms = firestore.collection(Constants.FIRESTORE_UMKM_COLLECTION)
            .whereEqualTo("ownerId", ownerId)
            .get()
            .await()
            .toObjects(UMKM::class.java)

        LogUtils.d(TAG, "UMKMs fetched: ${umkms.size}")
        Result.success(umkms)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get UMKMs error", e)
        Result.failure(e)
    }

    suspend fun createUMKM(umkm: UMKM): Result<String> = try {
        val docRef = firestore.collection(Constants.FIRESTORE_UMKM_COLLECTION).add(umkm).await()
        LogUtils.d(TAG, "UMKM created: ${docRef.id}")
        Result.success(docRef.id)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Create UMKM error", e)
        Result.failure(e)
    }

    suspend fun updateUMKM(umkmId: String, umkm: UMKM): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_UMKM_COLLECTION)
            .document(umkmId)
            .set(umkm)
            .await()

        LogUtils.d(TAG, "UMKM updated: $umkmId")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update UMKM error", e)
        Result.failure(e)
    }
}

class FirebaseOrderRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "FirebaseOrderRepository"

    suspend fun createOrder(order: Order): Result<String> = try {
        val docRef = firestore.collection(Constants.FIRESTORE_ORDERS_COLLECTION).add(order).await()
        LogUtils.d(TAG, "Order created: ${docRef.id}")
        Result.success(docRef.id)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Create order error", e)
        Result.failure(e)
    }

    suspend fun getOrderById(orderId: String): Result<Order> = try {
        val order = firestore.collection(Constants.FIRESTORE_ORDERS_COLLECTION)
            .document(orderId)
            .get()
            .await()
            .toObject(Order::class.java) ?: throw Exception("Order not found")

        LogUtils.d(TAG, "Order fetched: $orderId")
        Result.success(order)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get order error", e)
        Result.failure(e)
    }

    suspend fun getCustomerOrders(customerId: String): Result<List<Order>> = try {
        val orders = firestore.collection(Constants.FIRESTORE_ORDERS_COLLECTION)
            .whereEqualTo("customerId", customerId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(Order::class.java)

        LogUtils.d(TAG, "Customer orders fetched: ${orders.size}")
        Result.success(orders)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get customer orders error", e)
        Result.failure(e)
    }

    suspend fun getUMKMOrders(umkmId: String): Result<List<Order>> = try {
        val orders = firestore.collection(Constants.FIRESTORE_ORDERS_COLLECTION)
            .whereEqualTo("umkmId", umkmId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(Order::class.java)

        LogUtils.d(TAG, "UMKM orders fetched: ${orders.size}")
        Result.success(orders)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Get UMKM orders error", e)
        Result.failure(e)
    }

    suspend fun updateOrderStatus(orderId: String, status: String): Result<Unit> = try {
        firestore.collection(Constants.FIRESTORE_ORDERS_COLLECTION)
            .document(orderId)
            .update("status", status, "updatedAt", Date())
            .await()

        LogUtils.d(TAG, "Order status updated: $orderId -> $status")
        Result.success(Unit)
    } catch (e: Exception) {
        LogUtils.e(TAG, "Update order status error", e)
        Result.failure(e)
    }
}