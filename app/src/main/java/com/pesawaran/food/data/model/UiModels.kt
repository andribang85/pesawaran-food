package com.pesawaran.food.data.model

import java.io.Serializable

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null,
    val success: Boolean = false
) : Serializable {
    fun isSuccess() = !isLoading && error == null && data != null
    fun isError() = error != null
    fun isIdle() = !isLoading && error == null && data == null
}

data class PaginatedResult<T>(
    val items: List<T> = emptyList(),
    val hasMoreItems: Boolean = false,
    val nextPageToken: String = ""
)

data class ErrorResult(
    val code: Int = 0,
    val message: String = "",
    val details: String = ""
)

enum class UserRole {
    CUSTOMER,
    UMKM,
    COURIER,
    ADMIN,
    UNKNOWN
}

enum class OrderStatusEnum {
    PENDING,
    CONFIRMED,
    PREPARING,
    READY,
    ON_WAY,
    DELIVERED,
    CANCELLED
}

enum class PaymentMethodEnum {
    COD,
    QRIS,
    BANK_TRANSFER
}

enum class CourierStatusEnum {
    AVAILABLE,
    BUSY,
    OFFLINE
}

data class FormValidationResult(
    val isValid: Boolean = true,
    val errorMessage: String = ""
)