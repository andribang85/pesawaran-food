package com.pesawaran.food.utils

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import kotlin.math.*

object LocationUtils {
    private const val EARTH_RADIUS_METERS = 6371000.0

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * asin(sqrt(a))
        return EARTH_RADIUS_METERS * c / 1000 // Return in kilometers
    }

    fun calculateDistance(geoPoint1: GeoPoint, geoPoint2: GeoPoint): Double {
        return calculateDistance(
            geoPoint1.latitude,
            geoPoint1.longitude,
            geoPoint2.latitude,
            geoPoint2.longitude
        )
    }

    fun calculateDistance(latLng1: LatLng, latLng2: LatLng): Double {
        return calculateDistance(
            latLng1.latitude,
            latLng1.longitude,
            latLng2.latitude,
            latLng2.longitude
        )
    }

    fun estimateDeliveryTime(distanceKm: Double): Int {
        // Assuming average delivery speed of 25 km/h
        val avgSpeed = 25.0
        val timeMinutes = (distanceKm / avgSpeed * 60).toInt()
        // Add 5-10 minutes buffer for preparation
        return timeMinutes + 7
    }

    fun calculateBearing(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLon = Math.toRadians(lon2 - lon1)
        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)

        val y = sin(dLon) * cos(lat2Rad)
        val x = cos(lat1Rad) * sin(lat2Rad) -
                sin(lat1Rad) * cos(lat2Rad) * cos(dLon)

        val bearing = Math.toDegrees(atan2(y, x))
        return (bearing + 360) % 360
    }

    fun isLocationWithinRadius(centerLat: Double, centerLon: Double, radiusKm: Double,
                                testLat: Double, testLon: Double): Boolean {
        val distance = calculateDistance(centerLat, centerLon, testLat, testLon)
        return distance <= radiusKm
    }

    fun geoPointToLatLng(geoPoint: GeoPoint): LatLng {
        return LatLng(geoPoint.latitude, geoPoint.longitude)
    }

    fun latLngToGeoPoint(latLng: LatLng): GeoPoint {
        return GeoPoint(latLng.latitude, latLng.longitude)
    }

    fun getFormattedCoordinates(latitude: Double, longitude: Double): String {
        val latDir = if (latitude >= 0) "N" else "S"
        val lonDir = if (longitude >= 0) "E" else "W"
        return String.format("%f%s, %f%s", abs(latitude), latDir, abs(longitude), lonDir)
    }
}

object GeoUtils {
    fun createGeoPoint(latitude: Double, longitude: Double): GeoPoint {
        return GeoPoint(latitude, longitude)
    }

    fun createGeoPointFromLatLng(latLng: LatLng): GeoPoint {
        return GeoPoint(latLng.latitude, latLng.longitude)
    }

    fun getGeoPointFromLocation(geoPoint: GeoPoint): Pair<Double, Double> {
        return Pair(geoPoint.latitude, geoPoint.longitude)
    }
}

object MapsUtils {
    const val DEFAULT_ZOOM_LEVEL = 15f
    const val NEIGHBORHOOD_ZOOM_LEVEL = 17f
    const val CITY_ZOOM_LEVEL = 12f
    const val COUNTRY_ZOOM_LEVEL = 6f

    fun getZoomLevel(distanceKm: Double): Float {
        return when {
            distanceKm < 0.1 -> 20f
            distanceKm < 0.5 -> 18f
            distanceKm < 1 -> 16f
            distanceKm < 5 -> 14f
            distanceKm < 10 -> 12f
            distanceKm < 50 -> 10f
            else -> 8f
        }
    }

    fun createMapMarkerTitle(name: String, distance: Double): String {
        return "$name (${LocationUtils.calculateDistance(0.0, 0.0, distance, 0.0)}km)"
    }
}