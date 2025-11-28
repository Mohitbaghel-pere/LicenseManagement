# License Management - Android Frontend

Android Kotlin application for the License Management System.

## Features

- **Authentication**: Customer and Admin login/signup
- **Subscription Management**: View, request, and deactivate subscriptions
- **Dashboard**: Admin dashboard with statistics
- **Customer Management**: Admin can manage customers
- **Subscription Pack Management**: Admin can manage subscription packs

## Project Structure

```
app/
├── src/main/
│   ├── java/com/licensemanagement/app/
│   │   ├── data/
│   │   │   ├── api/          # Retrofit API service
│   │   │   ├── model/        # Data models
│   │   │   └── repository/   # Repository layer
│   │   ├── ui/
│   │   │   ├── auth/         # Login/Signup activities
│   │   │   ├── main/         # Main activity
│   │   │   ├── fragments/    # UI fragments
│   │   │   └── components/   # Reusable components
│   │   ├── util/             # Utilities (PreferencesManager)
│   │   └── viewmodel/        # ViewModels
│   └── res/
│       ├── layout/           # XML layouts
│       ├── menu/            # Navigation menus
│       └── navigation/      # Navigation graph
```

## Setup

1. **Update API Base URL**: 
   - For Android Emulator: `http://10.0.2.2:8080/` (already configured)
   - For Physical Device: Update `ApiClient.kt` with your computer's IP address

2. **Build the project**:
   ```bash
   ./gradlew build
   ```

3. **Run the app**:
   ```bash
   ./gradlew installDebug
   ```

## Dependencies

- Retrofit 2.9.0 - HTTP client
- OkHttp 4.12.0 - HTTP client
- Gson - JSON serialization
- Material Design Components
- Navigation Component
- ViewModel & LiveData
- Coroutines

## API Integration

The app uses Retrofit for API calls. All API endpoints are defined in `ApiService.kt` and match the backend OpenAPI specification.

### Authentication Flow

1. User logs in via `LoginActivity`
2. Token is stored in `SharedPreferences` via `PreferencesManager`
3. Token is automatically added to API requests via Bearer header

### SDK Integration

For SDK authentication, use the `sdkLogin` endpoint which returns an API key. Store the API key and use it with `X-API-Key` header for SDK endpoints.

## Configuration

Update the base URL in `ApiClient.kt`:
```kotlin
private const val BASE_URL = "http://YOUR_SERVER_IP:8080/"
```

For local development with emulator, use:
```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/"
```
