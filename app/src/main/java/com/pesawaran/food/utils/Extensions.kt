package com.pesawaran.food.utils

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.pesawaran.food.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

// String Extensions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    val regex = Regex(Constants.REGEX_PHONE)
    return regex.matches(this)
}

fun String.isValidPassword(): Boolean {
    val regex = Regex(Constants.REGEX_PASSWORD)
    return regex.matches(this)
}

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }
}

fun String.truncate(length: Int): String {
    return if (this.length > length) this.substring(0, length) + "..." else this
}

// Number Extensions
fun Double.formatCurrency(): String {
    val format = DecimalFormat("#,##0")
    return "Rp${format.format(this)}"
}

fun Double.formatPrice(): String {
    val format = DecimalFormat("#,##0.00")
    return format.format(this)
}

fun Int.formatCount(): String {
    return when {
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000.0)
        this >= 1_000 -> String.format("%.1fK", this / 1_000.0)
        else -> this.toString()
    }
}

fun Double.toStars(): String {
    return String.format("%.1f", this)
}

// Date Extensions
fun Date?.formatDate(): String {
    if (this == null) return ""
    val format = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    return format.format(this)
}

fun Date?.formatDateTime(): String {
    if (this == null) return ""
    val format = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID"))
    return format.format(this)
}

fun Date?.formatTime(): String {
    if (this == null) return ""
    val format = SimpleDateFormat("HH:mm", Locale("id", "ID"))
    return format.format(this)
}

fun Date?.getRelativeTime(): String {
    if (this == null) return ""
    val now = Date()
    val diff = now.time - this.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "Baru saja"
        minutes < 60 -> "$minutes menit yang lalu"
        hours < 24 -> "$hours jam yang lalu"
        days < 7 -> "$days hari yang lalu"
        else -> this.formatDate()
    }
}

// List Extensions
fun <T> List<T>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()

fun <T> List<T>.getOrNull(index: Int): T? {
    return if (index in 0 until size) this[index] else null
}

// Context Extensions
fun Context.getColorCompat(colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

fun Context.getDimensionPixelSize(dimenResId: Int): Int {
    return resources.getDimensionPixelSize(dimenResId)
}

fun Context.getDimension(dimenResId: Int): Float {
    return resources.getDimension(dimenResId)
}

fun Context.dpToPx(dp: Float): Int {
    return (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Context.pxToDp(px: Int): Float {
    return px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

// Boolean Extensions
fun Boolean?.orFalse(): Boolean = this ?: false

fun Boolean?.orTrue(): Boolean = this ?: true

// Collection Extensions
inline fun <T> List<T>.findByProperty(property: (T) -> Boolean): T? {
    return firstOrNull(property)
}

inline fun <T> List<T>.filterByProperty(property: (T) -> Boolean): List<T> {
    return filter(property)
}

// Null Coalescing
inline fun <T> T?.ifNull(block: () -> T): T {
    return this ?: block()
}

inline fun <T> T?.ifNotNull(block: (T) -> Unit) {
    if (this != null) block(this)
}

// Default Value Extensions
fun String?.orEmpty(): String = this ?: ""

fun String?.orDefault(default: String): String = this ?: default

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

// Boolean Operators
inline fun <T> T?.takeIfNotNull(block: (T) -> Boolean): T? {
    return if (this != null && block(this)) this else null
}

inline fun <T> T?.takeIfNull(): T? {
    return if (this == null) null else this
}

// Run Blocks
inline fun <T> T.runIfNotNull(block: T.() -> Unit): T {
    this?.block()
    return this
}

inline fun <T> T.runIf(condition: Boolean, block: T.() -> Unit): T {
    if (condition) this.block()
    return this
}