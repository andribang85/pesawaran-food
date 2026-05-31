package com.pesawaran.food.utils

object Constants {
    // API & Backend
    const val FIRESTORE_USERS_COLLECTION = "users"
    const val FIRESTORE_UMKM_COLLECTION = "umkm"
    const val FIRESTORE_PRODUCTS_COLLECTION = "products"
    const val FIRESTORE_CATEGORIES_COLLECTION = "categories"
    const val FIRESTORE_ORDERS_COLLECTION = "orders"
    const val FIRESTORE_ORDER_ITEMS_COLLECTION = "order_items"
    const val FIRESTORE_COURIERS_COLLECTION = "couriers"
    const val FIRESTORE_ADDRESSES_COLLECTION = "addresses"
    const val FIRESTORE_REVIEWS_COLLECTION = "reviews"
    const val FIRESTORE_BANNERS_COLLECTION = "banners"
    const val FIRESTORE_NOTIFICATIONS_COLLECTION = "notifications"
    const val FIRESTORE_TRANSACTIONS_COLLECTION = "transactions"
    const val FIRESTORE_SETTINGS_COLLECTION = "settings"

    // Storage Paths
    const val STORAGE_USERS_PATH = "users"
    const val STORAGE_UMKM_PATH = "umkm"
    const val STORAGE_PRODUCTS_PATH = "products"
    const val STORAGE_COURIERS_PATH = "couriers"
    const val STORAGE_ORDERS_PATH = "orders"
    const val STORAGE_REVIEWS_PATH = "reviews"

    // User Roles
    const val ROLE_CUSTOMER = "customer"
    const val ROLE_UMKM = "umkm"
    const val ROLE_COURIER = "courier"
    const val ROLE_ADMIN = "admin"

    // Order Status
    const val ORDER_STATUS_PENDING = "pending"
    const val ORDER_STATUS_CONFIRMED = "confirmed"
    const val ORDER_STATUS_PREPARING = "preparing"
    const val ORDER_STATUS_READY = "ready"
    const val ORDER_STATUS_ON_WAY = "on_way"
    const val ORDER_STATUS_DELIVERED = "delivered"
    const val ORDER_STATUS_CANCELLED = "cancelled"

    // Payment Methods
    const val PAYMENT_METHOD_COD = "cod"
    const val PAYMENT_METHOD_QRIS = "qris"
    const val PAYMENT_METHOD_BANK_TRANSFER = "bank_transfer"

    // Payment Status
    const val PAYMENT_STATUS_PENDING = "pending"
    const val PAYMENT_STATUS_PAID = "paid"
    const val PAYMENT_STATUS_FAILED = "failed"

    // Courier Status
    const val COURIER_STATUS_AVAILABLE = "available"
    const val COURIER_STATUS_BUSY = "busy"
    const val COURIER_STATUS_OFFLINE = "offline"

    // Notification Types
    const val NOTIFICATION_TYPE_ORDER = "order"
    const val NOTIFICATION_TYPE_PROMOTION = "promotion"
    const val NOTIFICATION_TYPE_SYSTEM = "system"

    // FCM Topics
    const val FCM_TOPIC_ORDERS = "orders"
    const val FCM_TOPIC_PROMOTIONS = "promotions"
    const val FCM_TOPIC_SYSTEM = "system"

    // Preferences
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_ROLE = "user_role"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    const val PREF_IS_ONBOARDING_COMPLETED = "is_onboarding_completed"
    const val PREF_DARK_MODE = "dark_mode"
    const val PREF_LANGUAGE = "language"
    const val PREF_DEVICE_TOKEN = "device_token"
    const val PREF_LAST_LOCATION = "last_location"

    // Default Values
    const val DEFAULT_DELIVERY_FEE = 5000.0
    const val DEFAULT_MAX_DELIVERY_DISTANCE = 10 // km
    const val DEFAULT_DELIVERY_TIME_MULTIPLIER = 1.0

    // Limits
    const val MAX_IMAGE_SIZE = 5242880L // 5MB
    const val MAX_FILE_SIZE = 10485760L // 10MB
    const val MAX_PRODUCT_IMAGES = 5
    const val PAGE_SIZE = 20
    const val LOCATION_UPDATE_INTERVAL = 5000L // 5 seconds
    const val LOCATION_FASTEST_UPDATE_INTERVAL = 2000L // 2 seconds

    // Time
    const val NETWORK_TIMEOUT = 30000L // 30 seconds
    const val SPLASH_SCREEN_DURATION = 2000L // 2 seconds

    // Deep Links
    const val DEEP_LINK_ORDER = "pesawaran://order"
    const val DEEP_LINK_PRODUCT = "pesawaran://product"
    const val DEEP_LINK_UMKM = "pesawaran://umkm"

    // Regex Patterns
    const val REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$"
    const val REGEX_PHONE = "^(\\+62|0)[0-9]{9,12}$"
    const val REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"

    // HTTP Headers
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_USER_AGENT = "User-Agent"

    // Response Codes
    const val HTTP_OK = 200
    const val HTTP_CREATED = 201
    const val HTTP_BAD_REQUEST = 400
    const val HTTP_UNAUTHORIZED = 401
    const val HTTP_FORBIDDEN = 403
    const val HTTP_NOT_FOUND = 404
    const val HTTP_SERVER_ERROR = 500

    // Features Toggle
    const val FEATURE_PAYMENT_QRIS = true
    const val FEATURE_PAYMENT_BANK_TRANSFER = true
    const val FEATURE_CHAT_INTEGRATION = true
    const val FEATURE_LOYALTY_POINTS = true
    const val FEATURE_COUPON_CODE = true
}

object AppConfig {
    const val APP_VERSION = "1.0.0"
    const val MIN_APP_VERSION = "1.0.0"
    const val API_BASE_URL = "https://api.pesawaran-food.com/"
    const val MAPS_ZOOM_LEVEL = 15f
    const val LOCATION_ACCURACY_THRESHOLD = 10f // meters
}

object Urls {
    const val PRIVACY_POLICY = "https://pesawaran-food.com/privacy"
    const val TERMS_CONDITIONS = "https://pesawaran-food.com/terms"
    const val ABOUT = "https://pesawaran-food.com/about"
    const val SUPPORT = "https://pesawaran-food.com/support"
    const val FAQ = "https://pesawaran-food.com/faq"
}