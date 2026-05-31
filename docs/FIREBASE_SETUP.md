# Firebase Setup Guide - Pesawaran Food

## 1. Firebase Project Creation

### Step 1: Create Firebase Project
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Click "Add project"
3. Project name: `Pesawaran Food`
4. Accept terms & enable Google Analytics
5. Select region: `asia-southeast1` (Singapore)
6. Create project

### Step 2: Register Android App
1. Click "Add app" → Android
2. Fill in details:
   - Package name: `com.pesawaran.food`
   - App nickname: `Pesawaran Food"`
   - Debug SHA-1: (get from step below)
3. Download `google-services.json`
4. Copy to `app/google-services.json`
5. Next → Install plugins (already done in build.gradle)
6. Next → Skip to console

### Step 3: Get Debug SHA-1
```bash
# Windows
keytool -list -v -keystore %USERPROFILE%\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android

# macOS/Linux
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

# Output akan menampilkan SHA1 fingerprint
```

## 2. Firebase Authentication Setup

### Enable Email/Password Authentication
1. Firebase Console → Authentication → Sign-in method
2. Enable "Email/Password"
3. Allow passwordless sign-in (optional)
4. Save

### Enable Phone Number Authentication
1. Firebase Console → Authentication → Sign-in method
2. Enable "Phone"
3. Add testing phone numbers (untuk development)
4. Save

### Enable Google Sign-In
1. Firebase Console → Authentication → Sign-in method
2. Enable "Google"
3. Select default project support email
4. Save
5. Verify Google API setup di Google Cloud Console

## 3. Cloud Firestore Setup

### Create Firestore Database
1. Firebase Console → Firestore Database
2. Click "Create database"
3. Select region: `asia-southeast1`
4. Start in "Test mode" (untuk development)
5. Create

### Create Collections

Jalankan perintah di Firebase Console → Firestore → Data:

#### Collection: users
```
{
  "userId": string (auto-generated),
  "email": string,
  "phone": string,
  "fullName": string,
  "profilePhoto": string (URL),
  "role": string ("customer", "umkm", "courier", "admin"),
  "createdAt": timestamp,
  "updatedAt": timestamp,
  "isActive": boolean,
  "isVerified": boolean,
  "verificationDate": timestamp,
  "lastLogin": timestamp,
  "deviceToken": string,
  "location": GeoPoint,
  "address": {
    "street": string,
    "city": string,
    "province": string,
    "postalCode": string
  }
}
```

#### Collection: umkm
```
{
  "umkmId": string (auto-generated),
  "ownerId": string (reference to users),
  "businessName": string,
  "businessCategory": string,
  "description": string,
  "logo": string (URL),
  "bannerImage": string (URL),
  "location": GeoPoint,
  "address": string,
  "phone": string,
  "email": string,
  "whatsapp": string,
  "operatingHours": {
    "openTime": string (HH:mm),
    "closeTime": string (HH:mm),
    "isOpen24Hours": boolean
  },
  "rating": number,
  "reviewCount": number,
  "totalOrders": number,
  "isVerified": boolean,
  "isActive": boolean,
  "verificationDate": timestamp,
  "createdAt": timestamp,
  "updatedAt": timestamp,
  "bankDetails": {
    "bankName": string,
    "accountName": string,
    "accountNumber": string
  },
  "documents": {
    "ktp": string (URL),
    "npwp": string (URL),
    "businessLicense": string (URL)
  }
}
```

#### Collection: products
```
{
  "productId": string (auto-generated),
  "umkmId": string (reference to umkm),
  "name": string,
  "description": string,
  "category": string,
  "price": number,
  "originalPrice": number,
  "discount": number,
  "stock": number,
  "images": [string] (URLs),
  "rating": number,
  "reviewCount": number,
  "preparationTime": number (minutes),
  "isAvailable": boolean,
  "isPopular": boolean,
  "spicy": number (1-5, 0 for not spicy),
  "servingSize": string,
  "createdAt": timestamp,
  "updatedAt": timestamp
}
```

#### Collection: categories
```
{
  "categoryId": string (auto-generated),
  "name": string,
  "icon": string (URL or emoji),
  "description": string,
  "isActive": boolean,
  "order": number,
  "createdAt": timestamp
}
```

#### Collection: orders
```
{
  "orderId": string (auto-generated),
  "customerId": string (reference to users),
  "umkmId": string (reference to umkm),
  "courierId": string (reference to users),
  "status": string ("pending", "confirmed", "preparing", "ready", "on_way", "delivered", "cancelled"),
  "subtotal": number,
  "deliveryFee": number,
  "discount": number,
  "tax": number,
  "total": number,
  "paymentMethod": string ("cod", "qris", "bank_transfer"),
  "paymentStatus": string ("pending", "paid", "failed"),
  "deliveryAddress": {
    "street": string,
    "city": string,
    "province": string,
    "postalCode": string,
    "location": GeoPoint,
    "notes": string
  },
  "notes": string,
  "estimatedDeliveryTime": timestamp,
  "actualDeliveryTime": timestamp,
  "createdAt": timestamp,
  "updatedAt": timestamp,
  "items": [reference to order_items]
}
```

#### Collection: order_items
```
{
  "itemId": string (auto-generated),
  "orderId": string (reference to orders),
  "productId": string (reference to products),
  "productName": string,
  "price": number,
  "quantity": number,
  "subtotal": number,
  "notes": string,
  "createdAt": timestamp
}
```

#### Collection: couriers
```
{
  "courierId": string (same as userId),
  "userId": string (reference to users),
  "status": string ("available", "busy", "offline"),
  "currentLocation": GeoPoint,
  "currentOrder": string (reference to orders),
  "totalDeliveries": number,
  "rating": number,
  "reviewCount": number,
  "totalEarnings": number,
  "todayEarnings": number,
  "verificationStatus": string ("pending", "verified", "rejected"),
  "documents": {
    "ktp": string (URL),
    "driverLicense": string (URL),
    "vehiclePhoto": string (URL)
  },
  "vehicle": {
    "type": string ("motorcycle", "car"),
    "plateNumber": string,
    "brand": string
  },
  "createdAt": timestamp,
  "updatedAt": timestamp,
  "lastUpdatedLocation": timestamp
}
```

#### Collection: reviews
```
{
  "reviewId": string (auto-generated),
  "productId": string (reference to products),
  "umkmId": string (reference to umkm),
  "customerId": string (reference to users),
  "orderId": string (reference to orders),
  "rating": number (1-5),
  "comment": string,
  "photos": [string] (URLs),
  "helpful": number,
  "createdAt": timestamp,
  "updatedAt": timestamp
}
```

#### Collection: banners
```
{
  "bannerId": string (auto-generated),
  "title": string,
  "description": string,
  "image": string (URL),
  "targetUrl": string,
  "isActive": boolean,
  "startDate": timestamp,
  "endDate": timestamp,
  "createdAt": timestamp,
  "order": number
}
```

#### Collection: notifications
```
{
  "notificationId": string (auto-generated),
  "userId": string (reference to users),
  "title": string,
  "body": string,
  "type": string ("order", "promotion", "system"),
  "data": {
    "orderId": string,
    "action": string
  },
  "isRead": boolean,
  "createdAt": timestamp
}
```

#### Collection: transactions
```
{
  "transactionId": string (auto-generated),
  "orderId": string (reference to orders),
  "customerId": string (reference to users),
  "umkmId": string (reference to umkm),
  "amount": number,
  "paymentMethod": string,
  "status": string ("pending", "success", "failed"),
  "reference": string,
  "createdAt": timestamp,
  "updatedAt": timestamp
}
```

#### Collection: addresses
```
{
  "addressId": string (auto-generated),
  "userId": string (reference to users),
  "label": string ("Home", "Office", "Other"),
  "street": string,
  "city": string,
  "province": string,
  "postalCode": string,
  "location": GeoPoint,
  "notes": string,
  "isDefault": boolean,
  "createdAt": timestamp
}
```

#### Collection: settings
```
{
  "settingsId": string ("app_settings"),
  "appVersion": string,
  "minAppVersion": string,
  "maintenanceMode": boolean,
  "defaultDeliveryFee": number,
  "maxDeliveryDistance": number,
  "deliveryEstimateMultiplier": number,
  "supportPhone": string,
  "supportEmail": string,
  "privacyPolicyUrl": string,
  "termsConditionsUrl": string,
  "aboutUrl": string,
  "lastUpdated": timestamp
}
```

## 4. Firebase Storage Setup

### Create Storage Bucket
1. Firebase Console → Storage
2. Click "Get started"
3. Select region: `asia-southeast1`
4. Set security rules (see below)
5. Done

### Storage Structure
```
gs://pesawaran-food.appspot.com/
├── users/
│   └── {userId}/profile.jpg
├── umkm/
│   ├── {umkmId}/logo.jpg
│   ├── {umkmId}/banner.jpg
│   └── {umkmId}/documents/
├── products/
│   └── {productId}/
│       ├── image1.jpg
│       ├── image2.jpg
│       └── ...
├── couriers/
│   ├── {courierId}/profile.jpg
│   └── {courierId}/documents/
├── orders/
│   └── {orderId}/
│       ├── proof1.jpg
│       └── proof2.jpg
└── reviews/
    └── {reviewId}/
        └── photo1.jpg
```

### Firebase Storage Rules
```
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    // Allow read for authenticated users
    match /public/{allPaths=**} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == request.resource.metadata.userId;
    }

    // User profile pictures
    match /users/{userId}/profile.jpg {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }

    // UMKM data
    match /umkm/{umkmId}/{allPaths=**} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.customClaims.role == 'umkm';
    }

    // Product images
    match /products/{productId}/{allPaths=**} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.customClaims.role == 'umkm';
    }

    // Order proofs
    match /orders/{orderId}/{allPaths=**} {
      allow read: if request.auth != null;
      allow write: if request.auth != null;
    }
  }
}
```

## 5. Cloud Messaging Setup

### Generate Server Key
1. Firebase Console → Project Settings → Cloud Messaging
2. Copy "Server API Key" (jika ada)
3. Copy "Sender ID"
4. Simpan di safe location

### Setup Notification Topics
- `orders` - Order notifications
- `promotions` - Promotional messages
- `system` - System updates
- `umkm_{umkmId}` - Specific UMKM notifications
- `courier_{courierId}` - Specific courier notifications

## 6. Firestore Security Rules

Copy file `firestore.rules` ke Firebase Console → Firestore → Rules:

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Helper functions
    function isAuthenticated() {
      return request.auth != null;
    }

    function isAdmin() {
      return isAuthenticated() && request.auth.customClaims.role == 'admin';
    }

    function isUMKM() {
      return isAuthenticated() && request.auth.customClaims.role == 'umkm';
    }

    function isCourier() {
      return isAuthenticated() && request.auth.customClaims.role == 'courier';
    }

    function isCustomer() {
      return isAuthenticated() && request.auth.customClaims.role == 'customer';
    }

    function isOwner(userId) {
      return isAuthenticated() && request.auth.uid == userId;
    }

    // Users collection
    match /users/{document=**} {
      allow read: if isAuthenticated();
      allow create: if isAuthenticated();
      allow update: if isOwner(document) || isAdmin();
      allow delete: if isAdmin();
    }

    // UMKM collection
    match /umkm/{document=**} {
      allow read: if isAuthenticated();
      allow create: if isUMKM();
      allow update: if isOwner(resource.data.ownerId) || isAdmin();
      allow delete: if isAdmin();
    }

    // Products collection
    match /products/{document=**} {
      allow read: if isAuthenticated();
      allow create: if isUMKM();
      allow update: if isUMKM() && request.auth.uid == resource.data.ownerId || isAdmin();
      allow delete: if isAdmin();
    }

    // Categories collection
    match /categories/{document=**} {
      allow read: if isAuthenticated();
      allow write: if isAdmin();
    }

    // Orders collection
    match /orders/{document=**} {
      allow read: if isAuthenticated() && (
        request.auth.uid == resource.data.customerId ||
        request.auth.uid == resource.data.umkmId ||
        request.auth.uid == resource.data.courierId ||
        isAdmin()
      );
      allow create: if isCustomer();
      allow update: if isOwner(resource.data.customerId) || isOwner(resource.data.umkmId) || isOwner(resource.data.courierId) || isAdmin();
    }

    // Notifications collection
    match /notifications/{document=**} {
      allow read: if isOwner(resource.data.userId);
      allow create: if isAuthenticated();
      allow update: if isOwner(resource.data.userId);
    }

    // Other collections - flexible for development
    match /couriers/{document=**} {
      allow read: if isAuthenticated();
      allow write: if isCourier() || isAdmin();
    }

    match /reviews/{document=**} {
      allow read: if isAuthenticated();
      allow create: if isCustomer();
      allow update: if isOwner(resource.data.customerId) || isAdmin();
    }

    match /transactions/{document=**} {
      allow read: if isAuthenticated() && (
        request.auth.uid == resource.data.customerId ||
        request.auth.uid == resource.data.umkmId ||
        isAdmin()
      );
      allow create: if isAuthenticated();
    }
  }
}
```

## 7. Deploy Rules

### Via Firebase Console
1. Go to Firestore → Rules
2. Copy rules dari file `firestore.rules`
3. Click Publish

### Via Firebase CLI
```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login
firebase login

# Deploy
firebase deploy --only firestore:rules
```

## 8. Testing Firebase Connection

Test dengan menjalankan aplikasi:

```kotlin
// MainActivity.kt
FirebaseFirestore.getInstance()
    .collection("settings")
    .document("app_settings")
    .get()
    .addOnSuccessListener { doc ->
        Log.d("Firebase", "Connected: ${doc.data}")
    }
    .addOnFailureListener { e ->
        Log.e("Firebase", "Error", e)
    }
```

## 9. Development Mode Settings

### Initial Data Setup
1. Buat admin account
2. Buat test UMKM
3. Buat test products
4. Buat test customers
5. Buat test couriers

### Enable Debug Mode
- Firebase Console → Project Settings → Debug Token
- Copy token untuk testing

## Troubleshooting

### Connection Issues
```
- Check internet connection
- Verify google-services.json placement
- Check Firebase project ID matches
```

### Authentication Issues
```
- Enable sign-in methods
- Check email verification
- Verify phone number format
```

### Database Issues
```
- Check Firestore Rules
- Verify collection names
- Check data structure
```

---
**Last Updated: 31 Mei 2026**
