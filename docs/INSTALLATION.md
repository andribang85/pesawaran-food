# Panduan Instalasi Pesawaran Food

## Prerequisites

### Hardware Requirements
- **RAM**: Minimal 8GB (recommended 16GB)
- **Storage**: Minimal 20GB free space
- **Processor**: Intel i5/i7 atau setara

### Software Requirements
- **Android Studio**: 2024.1 atau terbaru
- **JDK**: Version 17 atau terbaru
- **Gradle**: 8.2.0 atau terbaru
- **Kotlin**: 1.9.20 atau terbaru
- **Git**: Versi terbaru

## Langkah 1: Setup Development Environment

### 1.1 Install Android Studio
1. Download dari [developer.android.com](https://developer.android.com/studio)
2. Install sesuai OS Anda (Windows, macOS, Linux)
3. Jalankan Android Studio
4. Pilih "Standard Install"
5. Tunggu download SDK, NDK, dan build tools selesai

### 1.2 Install JDK 17
```bash
# Windows
Download dari https://adoptium.net/

# macOS
brew install openjdk@17

# Linux
sudo apt-get install openjdk-17-jdk
```

### 1.3 Setup Environment Variables
```bash
# Linux/macOS (tambahkan ke ~/.bashrc atau ~/.zshrc)
export JAVA_HOME=/path/to/jdk17
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$JAVA_HOME/bin:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH

# Windows (System Properties → Environment Variables)
JAVA_HOME=C:\Program Files\Java\jdk-17
ANDROID_HOME=%USERPROFILE%\AppData\Local\Android\Sdk
PATH=%JAVA_HOME%\bin;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools;%PATH%
```

## Langkah 2: Clone & Setup Proyek

### 2.1 Clone Repository
```bash
cd ~/Projects
git clone https://github.com/andribang85/pesawaran-food.git
cd pesawaran-food
```

### 2.2 Verify Directory Structure
```bash
tree -L 2
# atau
find . -maxdepth 2 -type d
```

## Langkah 3: Setup Firebase

### 3.1 Buat Firebase Project
1. Kunjungi [Firebase Console](https://console.firebase.google.com)
2. Klik "Add project"
3. Nama project: `Pesawaran Food`
4. Ikuti wizard setup
5. Pilih lokasi: `asia-southeast1` (Jakarta)

### 3.2 Setup Android App di Firebase
1. Di Firebase Console, klik "Add app" → Android
2. Package Name: `com.pesawaran.food`
3. Debug SHA-1:
   ```bash
   cd ~/.android
   # Atau generate dengan:
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   ```
4. Download `google-services.json`
5. Copy ke `app/google-services.json`

### 3.3 Setup Firebase Services

#### Authentication
1. Firebase Console → Authentication
2. Enable providers:
   - Email/Password
   - Phone Number (dengan Twilio/Firebase SMS)
   - Google Sign-In

#### Firestore Database
1. Firebase Console → Firestore Database
2. Create Database
3. Mulai di "Test Mode" (untuk development)
4. Lokasi: `asia-southeast1`

#### Cloud Storage
1. Firebase Console → Storage
2. Create bucket
3. Nama: `pesawaran-food.appspot.com`

#### Cloud Messaging
1. Firebase Console → Cloud Messaging
2. Generate Server Key (untuk backend)

### 3.4 Setup Firestore Rules
```bash
# Buka file
cat firestore.rules

# Copy dan paste ke Firebase Console → Firestore → Rules
# Deploy rules
```

## Langkah 4: Setup Google Maps

### 4.1 Create Google Cloud Project
1. Kunjungi [Google Cloud Console](https://console.cloud.google.com)
2. Create new project
3. Enable APIs:
   - Maps SDK for Android
   - Places API
   - Geocoding API
   - Directions API

### 4.2 Create API Key
1. Google Cloud Console → APIs & Services → Credentials
2. Create Credentials → API Key
3. Restrict key ke Android apps
4. Add package name: `com.pesawaran.food`
5. Add SHA-1 fingerprint (debug key)

### 4.3 Add API Key to Project
```bash
# Edit app/build.gradle.kts
buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"YOUR_API_KEY_HERE\"")

# Atau setup di local.properties
echo 'GOOGLE_MAPS_API_KEY=your_api_key_here' >> local.properties
```

## Langkah 5: Open di Android Studio

### 5.1 Open Project
1. Android Studio → File → Open
2. Select folder `pesawaran-food`
3. Tunggu indexing selesai
4. Tunggu Gradle sync selesai

### 5.2 Fix Common Issues

**Issue: Gradle sync failed**
```bash
# Solution 1: Invalidate caches
File → Invalidate Caches → Invalidate and Restart

# Solution 2: Delete build folder
rm -rf .gradle build app/build

# Solution 3: Update Gradle
./gradlew wrapper --gradle-version 8.2.0
```

**Issue: Build tools not found**
```bash
# Android Studio → SDK Manager
# Install missing build tools (API 34)
```

## Langkah 6: Setup Emulator atau Physical Device

### 6.1 Setup Emulator
1. Android Studio → AVD Manager
2. Create Virtual Device
3. Select device: "Pixel 4" atau "Pixel 5"
4. Select OS: "Android 14" (API 34)
5. Allocate RAM: 4GB
6. Enable GPU
7. Finish & Start

### 6.2 Setup Physical Device
1. Connect device via USB
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging
3. Authorize connection
4. Check connection:
   ```bash
   adb devices
   ```

## Langkah 7: Build & Run

### 7.1 Debug Build
```bash
# Via Android Studio
# Run → Run 'app'
# Atau tekan Shift + F10

# Via Terminal
./gradlew installDebug
```

### 7.2 Release Build (dengan keystore)
```bash
# Generate keystore (hanya sekali)
keytool -genkey -v -keystore ~/keystore/pesawaran-food-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias pesawaran_food_key

# Setup di local.properties
echo 'KEYSTORE_FILE=~/keystore/pesawaran-food-key.jks' >> local.properties
echo 'KEYSTORE_PASSWORD=your_password' >> local.properties
echo 'KEY_ALIAS=pesawaran_food_key' >> local.properties
echo 'KEY_PASSWORD=your_password' >> local.properties

# Build release APK
./gradlew assembleRelease

# APK akan berada di:
# app/build/outputs/apk/release/app-release.apk
```

### 7.3 Build Bundle (untuk Play Store)
```bash
./gradlew bundleRelease

# Output:
# app/build/outputs/bundle/release/app-release.aab
```

## Langkah 8: Testing

### 8.1 Unit Tests
```bash
./gradlew test
```

### 8.2 Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### 8.3 Manual Testing
1. Jalankan aplikasi
2. Test splash screen & onboarding
3. Test login dengan berbagai metode
4. Test setiap role (Customer, UMKM, Courier, Admin)
5. Test integrasi Firebase
6. Test Google Maps
7. Test push notifications

## Troubleshooting

### Gradle Issues
```bash
# Clean dan rebuild
./gradlew clean
./gradlew build

# Update dependencies
./gradlew dependencies --refresh-dependencies
```

### Firebase Connection Issues
```bash
# Verify google-services.json
ls -la app/google-services.json

# Check internet connection
ping firebase.google.com
```

### Google Maps Issues
```bash
# Verify API key di build.gradle.kts
grepGOOGLE_MAPS_API_KEY app/build.gradle.kts

# Check Google Cloud Console untuk billing
```

### Device Issues
```bash
# List connected devices
adb devices

# Clear app data
adb shell pm clear com.pesawaran.food

# View logs
adb logcat -s PesawaranFood
```

## Dokumentasi Lanjutan

- [Firebase Setup Guide](./FIREBASE_SETUP.md)
- [Architecture Documentation](./ARCHITECTURE.md)
- [API Documentation](./API_DOCUMENTATION.md)
- [Database Schema](./DATABASE_SCHEMA.md)

## Support

Jika mengalami masalah:
1. Cek Gradle logs
2. Cek Android Studio Logcat
3. Buat issue di GitHub
4. Hubungi tim development

---
**Dokumentasi ini terakhir diperbarui: 31 Mei 2026**
