package com.pesawaran.food.data.model

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class User(
    val userId: String = "",
    val email: String = "",
    val phone: String = "",
    val fullName: String = "",
    val profilePhoto: String = "",
    val role: String = "", // "customer", "umkm", "courier", "admin"
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val isActive: Boolean = true,
    val isVerified: Boolean = false,
    val verificationDate: Date? = null,
    val lastLogin: Date? = null,
    val deviceToken: String = "",
    val location: GeoPoint? = null,
    val address: Address? = null
)

data class Address(
    val street: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = ""
)

data class UMKM(
    val umkmId: String = "",
    val ownerId: String = "",
    val businessName: String = "",
    val businessCategory: String = "",
    val description: String = "",
    val logo: String = "",
    val bannerImage: String = "",
    val location: GeoPoint? = null,
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val whatsapp: String = "",
    val operatingHours: OperatingHours? = null,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val totalOrders: Int = 0,
    val isVerified: Boolean = false,
    val isActive: Boolean = true,
    val verificationDate: Date? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val bankDetails: BankDetails? = null,
    val documents: UMKMDocuments? = null
)

data class OperatingHours(
    val openTime: String = "",
    val closeTime: String = "",
    val isOpen24Hours: Boolean = false
)

data class BankDetails(
    val bankName: String = "",
    val accountName: String = "",
    val accountNumber: String = ""
)

data class UMKMDocuments(
    val ktp: String = "",
    val npwp: String = "",
    val businessLicense: String = ""
)

data class Product(
    val productId: String = "",
    val umkmId: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val originalPrice: Double = 0.0,
    val discount: Int = 0,
    val stock: Int = 0,
    val images: List<String> = emptyList(),
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val preparationTime: Int = 0, // minutes
    val isAvailable: Boolean = true,
    val isPopular: Boolean = false,
    val spicy: Int = 0, // 0-5
    val servingSize: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

data class Category(
    val categoryId: String = "",
    val name: String = "",
    val icon: String = "",
    val description: String = "",
    val isActive: Boolean = true,
    val order: Int = 0,
    @ServerTimestamp
    val createdAt: Date? = null
)

data class CartItem(
    val productId: String = "",
    val product: Product? = null,
    val quantity: Int = 1,
    val notes: String = ""
) {
    fun getSubtotal(): Double = (product?.price ?: 0.0) * quantity
}

data class Order(
    val orderId: String = "",
    val customerId: String = "",
    val umkmId: String = "",
    val courierId: String = "",
    val status: String = "pending", // pending, confirmed, preparing, ready, on_way, delivered, cancelled
    val subtotal: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val discount: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val paymentMethod: String = "", // cod, qris, bank_transfer
    val paymentStatus: String = "pending", // pending, paid, failed
    val deliveryAddress: DeliveryAddress? = null,
    val notes: String = "",
    val estimatedDeliveryTime: Date? = null,
    val actualDeliveryTime: Date? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val items: List<OrderItem> = emptyList()
)

data class DeliveryAddress(
    val street: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = "",
    val location: GeoPoint? = null,
    val notes: String = ""
)

data class OrderItem(
    val itemId: String = "",
    val orderId: String = "",
    val productId: String = "",
    val productName: String = "",
    val price: Double = 0.0,
    val quantity: Int = 1,
    val subtotal: Double = 0.0,
    val notes: String = "",
    @ServerTimestamp
    val createdAt: Date? = null
)

data class Courier(
    val courierId: String = "",
    val userId: String = "",
    val status: String = "available", // available, busy, offline
    val currentLocation: GeoPoint? = null,
    val currentOrder: String = "",
    val totalDeliveries: Int = 0,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val totalEarnings: Double = 0.0,
    val todayEarnings: Double = 0.0,
    val verificationStatus: String = "pending", // pending, verified, rejected
    val documents: CourierDocuments? = null,
    val vehicle: Vehicle? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val lastUpdatedLocation: Date? = null
)

data class CourierDocuments(
    val ktp: String = "",
    val driverLicense: String = "",
    val vehiclePhoto: String = ""
)

data class Vehicle(
    val type: String = "", // motorcycle, car
    val plateNumber: String = "",
    val brand: String = ""
)

data class Review(
    val reviewId: String = "",
    val productId: String = "",
    val umkmId: String = "",
    val customerId: String = "",
    val orderId: String = "",
    val rating: Int = 0, // 1-5
    val comment: String = "",
    val photos: List<String> = emptyList(),
    val helpful: Int = 0,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

data class Banner(
    val bannerId: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val targetUrl: String = "",
    val isActive: Boolean = true,
    val startDate: Date? = null,
    val endDate: Date? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
    val order: Int = 0
)

data class Notification(
    val notificationId: String = "",
    val userId: String = "",
    val title: String = "",
    val body: String = "",
    val type: String = "", // order, promotion, system
    val data: Map<String, String> = emptyMap(),
    val isRead: Boolean = false,
    @ServerTimestamp
    val createdAt: Date? = null
)

data class Transaction(
    val transactionId: String = "",
    val orderId: String = "",
    val customerId: String = "",
    val umkmId: String = "",
    val amount: Double = 0.0,
    val paymentMethod: String = "",
    val status: String = "pending", // pending, success, failed
    val reference: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

data class DeliveryAddress(
    val addressId: String = "",
    val userId: String = "",
    val label: String = "", // Home, Office, Other
    val street: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = "",
    val location: GeoPoint? = null,
    val notes: String = "",
    val isDefault: Boolean = false,
    @ServerTimestamp
    val createdAt: Date? = null
)

data class AppSettings(
    val settingsId: String = "app_settings",
    val appVersion: String = "",
    val minAppVersion: String = "",
    val maintenanceMode: Boolean = false,
    val defaultDeliveryFee: Double = 0.0,
    val maxDeliveryDistance: Int = 0,
    val deliveryEstimateMultiplier: Double = 1.0,
    val supportPhone: String = "",
    val supportEmail: String = "",
    val privacyPolicyUrl: String = "",
    val termsConditionsUrl: String = "",
    val aboutUrl: String = "",
    @ServerTimestamp
    val lastUpdated: Date? = null
)

// DTO for API Response
data class ApiResponse<T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null,
    val code: Int = 0
)