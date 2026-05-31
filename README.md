# Pesawaran Food - Platform Pesan Antar Makanan UMKM

![Pesawaran Food](docs/logo.png)

**Pesawaran Food** adalah aplikasi mobile Android profesional yang dirancang untuk mendukung UMKM (Usaha Mikro, Kecil, dan Menengah) di Kabupaten Pesawaran, Provinsi Lampung dengan menyediakan platform pesan antar makanan dan minuman yang mudah, aman, dan terpercaya.

## 🎯 Visi
"Pesan Makanan UMKM Pesawaran Lebih Mudah"

## 📋 Fitur Utama

### 👤 4 Role Pengguna
- **Admin**: Manajemen pengguna, UMKM, produk, pesanan, laporan
- **Pelanggan**: Memesan makanan, pembayaran, tracking pesanan
- **Mitra UMKM**: Dashboard penjualan, manajemen produk, kelola pesanan
- **Kurir**: Menerima pesanan, tracking lokasi realtime, bukti pengantaran

### 🛍️ Fitur Pelanggan
- Splash Screen & Onboarding 3 slide
- Autentikasi (Email, Nomor HP, Google)
- Browse UMKM terdekat dengan Google Maps
- Pencarian & filter produk realtime
- Detail produk dengan rating & ulasan
- Keranjang belanja dan checkout
- Multiple payment methods (COD, QRIS, Transfer Bank)
- Order tracking realtime
- Chat WhatsApp ke UMKM
- Daftar favorit
- Sistem poin loyalitas
- Riwayat pesanan

### 🏪 Fitur Mitra UMKM
- Dashboard dengan grafik penjualan
- Manajemen produk (tambah, edit, hapus)
- Upload foto produk ke Firebase Storage
- Kelola stok dan harga
- Terima/tolak pesanan
- Update status pesanan
- Laporan omzet harian & bulanan

### 🚚 Fitur Kurir
- Dashboard pesanan tersedia
- Accept/decline order
- Real-time location tracking
- Google Maps navigation
- Update status pengantaran
- Upload bukti pengantaran (foto)
- Riwayat pengantaran
- Tracking pendapatan

### 👨‍💼 Fitur Admin
- Manajemen pengguna & akun
- Verifikasi UMKM baru
- Manajemen kategori makanan
- Manajemen banner promo
- Blokir/unblokir akun
- Laporan transaksi & statistik
- Manajemen kupon & diskon

## 🛠️ Tech Stack

### Frontend
- **Kotlin** - Modern Android development
- **Android Studio** 2024.1+
- **Material Design 3** - Modern UI/UX
- **ViewBinding** - Type-safe view binding
- **Navigation Component** - Fragment navigation
- **Coroutines** - Asynchronous programming

### Architecture
- **MVVM** (Model-View-ViewModel)
- **Repository Pattern** - Data abstraction
- **Clean Architecture** - Layered approach
- **Dependency Injection (Koin)** - Loose coupling

### Backend Services
- **Firebase Authentication** - Email, Phone, Google Sign-in
- **Cloud Firestore** - Real-time database
- **Firebase Storage** - Image storage
- **Firebase Cloud Messaging** - Push notifications
- **Firebase Crashlytics** - Error monitoring

### APIs & Location
- **Google Maps SDK** - Location display & navigation
- **Google Location Services** - GPS tracking
- **Google Places API** - Location search
- **Retrofit** - REST API client

### UI Components
- **RecyclerView** - List views
- **CardView** - Card layouts
- **Shimmer** - Loading animation
- **SwipeRefreshLayout** - Pull-to-refresh
- **Bottom Navigation** - Navigation bar
- **Material Components** - MD3 widgets

### Additional Libraries
- **Glide** - Image loading & caching
- **GSON** - JSON serialization
- **OkHttp** - HTTP client
- **Timber** - Logging
- **DataStore** - Preferences storage
- **Room** - Local database (optional)

## 📱 Kompatibilitas
- **Minimum SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 14+ (API 34+)
- **Java Version**: 17
- **Kotlin**: 1.9.20+

## 📁 Struktur Proyek

```
pesawaran-food/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/pesawaran/food/
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/              # Data models
│   │   │   │   │   ├── repository/         # Repository layer
│   │   │   │   │   └── remote/             # Remote data sources
│   │   │   │   ├── domain/
│   │   │   │   │   └── usecase/            # Use cases
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── splash/
│   │   │   │   │   │   ├── onboarding/
│   │   │   │   │   │   ├── auth/
│   │   │   │   │   │   ├── customer/
│   │   │   │   │   │   ├── umkm/
│   │   │   │   │   │   ├── courier/
│   │   │   │   │   │   ├── admin/
│   │   │   │   │   │   └── common/
│   │   │   │   │   ├── viewmodel/          # ViewModels
│   │   │   │   │   └── adapter/            # RecyclerView adapters
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Constants.kt
│   │   │   │   │   ├── Extensions.kt
│   │   │   │   │   ├── FirebaseUtils.kt
│   │   │   │   │   ├── LocationUtils.kt
│   │   │   │   │   └── ValidationUtils.kt
│   │   │   │   ├── di/                     # Dependency injection
│   │   │   │   ├── service/
│   │   │   │   │   ├── LocationService.kt
│   │   │   │   │   └── PushNotificationService.kt
│   │   │   │   └── App.kt                  # Application class
│   │   │   ├── res/
│   │   │   │   ├── layout/                 # XML layouts
│   │   │   │   ├── drawable/               # Drawables & vectors
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   ├── values-night/           # Dark mode
│   │   │   │   └── mipmap/                 # App icons
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── README.md
├── .gitignore
└── docs/
    ├── INSTALLATION.md
    ├── FIREBASE_SETUP.md
    ├── ARCHITECTURE.md
    └── API_DOCUMENTATION.md
```

## 🚀 Instalasi & Setup

### 1. Clone Repository
```bash
git clone https://github.com/andribang85/pesawaran-food.git
cd pesawaran-food
```

### 2. Setup Firebase
1. Buat project baru di [Firebase Console](https://console.firebase.google.com)
2. Download `google-services.json`
3. Tempatkan di folder `app/`
4. Setup Firebase Rules (lihat `docs/FIREBASE_SETUP.md`)

### 3. Setup Google Maps API
1. Aktifkan Google Maps SDK di Google Cloud Console
2. Buat API Key
3. Masukkan ke `local.properties`:
```properties
GOOGLE_MAPS_API_KEY=your_api_key_here
```

### 4. Buka di Android Studio
1. File → Open → Select folder `pesawaran-food`
2. Tunggu Gradle sync
3. Run aplikasi atau build APK

### 5. Build & Run
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run di emulator/device
./gradlew installDebug
```

## 📊 Database Schema (Firestore)

### Collections
- `users` - User profiles & authentication
- `umkm` - UMKM merchant information
- `products` - Food & beverage products
- `categories` - Product categories
- `orders` - Customer orders
- `order_items` - Items in orders
- `couriers` - Courier information
- `addresses` - Delivery addresses
- `reviews` - Product reviews & ratings
- `banners` - Promotional banners
- `notifications` - Push notifications
- `transactions` - Payment transactions
- `settings` - App configuration

Tampilan lengkap struktur data ada di `docs/DATABASE_SCHEMA.md`

## 🔒 Keamanan

### Firebase Security Rules
- Role-based access control
- User data privacy
- Merchant data protection
- Admin area protection

Lihat `firestore.rules` untuk detail lengkap.

### Best Practices
- Environment variables untuk sensitive data
- Encryption untuk data sensitif
- Secure API communication
- Input validation & sanitization

## 📝 API Endpoints

Aplikasi menggunakan Firestore sebagai backend utama. Untuk integrasi REST API eksternal:

- Payment Gateway Integration
- SMS Gateway untuk OTP
- Email Service untuk notifikasi

See `docs/API_DOCUMENTATION.md` untuk detail.

## 🎨 UI/UX Design

### Color Palette
- **Primary**: Green #16A34A
- **Secondary**: White #FFFFFF
- **Accent**: Orange #F97316
- **Surface**: Light Gray #F5F5F5
- **Error**: Red #DC2626

### Navigation
- Bottom Navigation Bar dengan 5 menu
- Drawer Navigation untuk Admin
- Modal Bottom Sheet untuk detail
- Fragment-based navigation

### Responsive Design
- Adaptive layouts untuk berbagai screen sizes
- Landscape orientation support
- Tablet optimization
- Dark mode support

## 📞 Support & Contribution

### Melaporkan Bug
Gunakan GitHub Issues dengan template yang tersedia.

### Feature Request
Buat discussion atau issue dengan label `enhancement`.

### Contributing
1. Fork repository
2. Buat branch feature (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buka Pull Request

## 📄 Lisensi

Proyek ini dilindungi hak cipta dan dirancang khusus untuk UMKM Kabupaten Pesawaran.

## 👨‍💻 Developer

**Andri Bang** - Lead Developer  
github: [@andribang85](https://github.com/andribang85)

## 🙏 Terima Kasih

Terima kasih kepada semua UMKM di Kabupaten Pesawaran yang telah mendukung aplikasi ini.

---

**Last Updated**: 31 Mei 2026  
**Version**: 1.0.0  
**Status**: Development
