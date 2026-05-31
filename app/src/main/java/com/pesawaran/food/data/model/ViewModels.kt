package com.pesawaran.food.data.model

import com.google.firebase.firestore.GeoPoint

data class SearchFilter(
    val query: String = "",
    val category: String = "",
    val minPrice: Double = 0.0,
    val maxPrice: Double = Double.MAX_VALUE,
    val sortBy: String = "popularity", // popularity, rating, price_asc, price_desc
    val maxDistance: Int = 10, // km
    val userLocation: GeoPoint? = null
)

data class LocationInfo(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val city: String = "",
    val province: String = ""
)

data class NearbyStore(
    val store: UMKM,
    val distance: Double, // km
    val estimatedDeliveryTime: Int // minutes
)

data class OrderStatus(
    val status: String = "",
    val timestamp: Long = 0,
    val description: String = ""
)

data class DeliveryStatus(
    val orderId: String = "",
    val courierId: String = "",
    val currentLocation: LocationInfo? = null,
    val estimatedArrival: Long = 0,
    val isOnWay: Boolean = false
)

data class PaymentInfo(
    val paymentMethod: String = "",
    val amount: Double = 0.0,
    val status: String = "",
    val reference: String = "",
    val timestamp: Long = 0
)

data class RatingInfo(
    val rating: Int = 0,
    val totalRatings: Int = 0,
    val averageRating: Double = 0.0,
    val ratingDistribution: Map<Int, Int> = emptyMap()
)

data class PromotionCode(
    val code: String = "",
    val discount: Double = 0.0,
    val discountType: String = "", // percentage, fixed
    val minOrderAmount: Double = 0.0,
    val maxDiscount: Double = 0.0,
    val usageLimit: Int = 0,
    val usageCount: Int = 0,
    val isActive: Boolean = true,
    val expiryDate: Long = 0
)

data class LoyaltyPoints(
    val userId: String = "",
    val points: Int = 0,
    val tier: String = "", // bronze, silver, gold, platinum
    val totalSpent: Double = 0.0,
    val lastUpdated: Long = 0
)

data class DashboardStats(
    val totalOrders: Int = 0,
    val totalRevenue: Double = 0.0,
    val activeProducts: Int = 0,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val monthlyRevenue: List<DailyRevenue> = emptyList()
)

data class DailyRevenue(
    val date: String = "",
    val revenue: Double = 0.0,
    val orderCount: Int = 0
)

data class CourierDashboard(
    val availableOrders: Int = 0,
    val activeDeliveries: Int = 0,
    val totalEarnings: Double = 0.0,
    val todayEarnings: Double = 0.0,
    val completedDeliveries: Int = 0,
    val rating: Double = 0.0
)

data class AdminDashboard(
    val totalUsers: Int = 0,
    val totalUMKM: Int = 0,
    val totalCouriers: Int = 0,
    val totalOrders: Int = 0,
    val totalRevenue: Double = 0.0,
    val pendingVerifications: Int = 0,
    val activeUsers: Int = 0
)