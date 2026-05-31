package com.pesawaran.food.utils

import android.util.Log
import timber.log.Timber

object LogUtils {
    fun d(tag: String, message: String) {
        Timber.d("[$tag] $message")
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Timber.e(throwable, "[$tag] $message")
    }

    fun i(tag: String, message: String) {
        Timber.i("[$tag] $message")
    }

    fun w(tag: String, message: String) {
        Timber.w("[$tag] $message")
    }
}

object ValidationUtils {
    fun validateEmail(email: String): Pair<Boolean, String> {
        return when {
            email.isEmpty() -> Pair(false, "Email tidak boleh kosong")
            !email.isValidEmail() -> Pair(false, "Format email tidak valid")
            else -> Pair(true, "")
        }
    }

    fun validatePhone(phone: String): Pair<Boolean, String> {
        return when {
            phone.isEmpty() -> Pair(false, "Nomor HP tidak boleh kosong")
            !phone.isValidPhone() -> Pair(false, "Format nomor HP tidak valid")
            else -> Pair(true, "")
        }
    }

    fun validatePassword(password: String): Pair<Boolean, String> {
        return when {
            password.isEmpty() -> Pair(false, "Kata sandi tidak boleh kosong")
            password.length < 6 -> Pair(false, "Kata sandi minimal 6 karakter")
            else -> Pair(true, "")
        }
    }

    fun validatePasswordMatch(password: String, confirmPassword: String): Pair<Boolean, String> {
        return if (password == confirmPassword) {
            Pair(true, "")
        } else {
            Pair(false, "Kata sandi tidak cocok")
        }
    }

    fun validateFullName(fullName: String): Pair<Boolean, String> {
        return when {
            fullName.isEmpty() -> Pair(false, "Nama lengkap tidak boleh kosong")
            fullName.length < 3 -> Pair(false, "Nama lengkap minimal 3 karakter")
            else -> Pair(true, "")
        }
    }

    fun validateProductName(name: String): Pair<Boolean, String> {
        return when {
            name.isEmpty() -> Pair(false, "Nama produk tidak boleh kosong")
            name.length < 3 -> Pair(false, "Nama produk minimal 3 karakter")
            name.length > 100 -> Pair(false, "Nama produk maksimal 100 karakter")
            else -> Pair(true, "")
        }
    }

    fun validateProductPrice(price: Double): Pair<Boolean, String> {
        return when {
            price <= 0 -> Pair(false, "Harga harus lebih dari 0")
            else -> Pair(true, "")
        }
    }

    fun validateProductStock(stock: Int): Pair<Boolean, String> {
        return when {
            stock < 0 -> Pair(false, "Stok tidak boleh negatif")
            else -> Pair(true, "")
        }
    }

    fun validateCartNotEmpty(cartSize: Int): Pair<Boolean, String> {
        return if (cartSize > 0) {
            Pair(true, "")
        } else {
            Pair(false, "Keranjang belanja kosong")
        }
    }

    fun validateDeliveryAddress(address: String, city: String, province: String, postalCode: String): Pair<Boolean, String> {
        return when {
            address.isEmpty() -> Pair(false, "Alamat tidak boleh kosong")
            city.isEmpty() -> Pair(false, "Kota tidak boleh kosong")
            province.isEmpty() -> Pair(false, "Provinsi tidak boleh kosong")
            postalCode.isEmpty() -> Pair(false, "Kode pos tidak boleh kosong")
            postalCode.length != 5 -> Pair(false, "Kode pos harus 5 digit")
            else -> Pair(true, "")
        }
    }
}

object FormatterUtils {
    fun formatPhoneNumber(phone: String): String {
        val cleaned = phone.replace(Regex("[^0-9]"), "")
        return when {
            cleaned.startsWith("62") -> "+$cleaned"
            cleaned.startsWith("0") -> "+62${cleaned.substring(1)}"
            else -> "+62$cleaned"
        }
    }

    fun formatCurrencyInput(amount: Long): String {
        val format = java.text.DecimalFormat("#,##0")
        return format.format(amount)
    }

    fun formatPercentage(value: Double): String {
        return String.format("%.0f%%", value)
    }

    fun formatDistance(distanceKm: Double): String {
        return when {
            distanceKm < 1 -> String.format("%.0f m", distanceKm * 1000)
            else -> String.format("%.1f km", distanceKm)
        }
    }

    fun formatDeliveryTime(minutes: Int): String {
        return when {
            minutes < 60 -> "$minutes menit"
            else -> {
                val hours = minutes / 60
                val mins = minutes % 60
                if (mins > 0) "$hours jam $mins menit" else "$hours jam"
            }
        }
    }
}

object ImageUtils {
    fun isValidImageSize(fileSizeInBytes: Long): Boolean {
        return fileSizeInBytes <= Constants.MAX_IMAGE_SIZE
    }

    fun isValidImageFile(fileName: String): Boolean {
        val extension = fileName.substringAfterLast(".", "").lowercase()
        return extension in listOf("jpg", "jpeg", "png", "webp")
    }
}

object FileUtils {
    fun isValidFile(fileName: String): Boolean {
        val extension = fileName.substringAfterLast(".", "").lowercase()
        return extension in listOf(
            "jpg", "jpeg", "png", "webp", "pdf", "doc", "docx", "xls", "xlsx"
        )
    }

    fun getFileExtension(fileName: String): String {
        return fileName.substringAfterLast(".", "")
    }

    fun getFileSize(fileSizeInBytes: Long): String {
        return when {
            fileSizeInBytes <= 0 -> "0 B"
            fileSizeInBytes < 1024 -> "$fileSizeInBytes B"
            fileSizeInBytes < 1024 * 1024 -> String.format("%.2f KB", fileSizeInBytes / (1024.0))
            fileSizeInBytes < 1024 * 1024 * 1024 -> String.format("%.2f MB", fileSizeInBytes / (1024.0 * 1024.0))
            else -> String.format("%.2f GB", fileSizeInBytes / (1024.0 * 1024.0 * 1024.0))
        }
    }
}