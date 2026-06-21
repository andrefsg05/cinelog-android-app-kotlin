# CineLog

CineLog is a native Android movie discovery app built as an academic project. It consumes the TMDb API and presents a clean Jetpack Compose interface for browsing movies, opening detailed information, saving titles to a watchlist, rating them, and getting a random movie suggestion.

The app UI is currently written in Portuguese and requests Portuguese (`pt-PT`) movie data from TMDb.

## Description and Key Features

- Home feed with "Now Playing", "Popular", and "Top Rated" movie sections.
- Movie search by title, with quick genre filters for action, comedy, drama, science fiction, horror, romance, and animation.
- Toggleable search results layout between list and grid views.
- Movie detail screen with backdrop, poster, title, tagline, release year, runtime, genres, TMDb rating, and synopsis.
- In-app watchlist with add/remove actions from the detail and watchlist screens.
- Personal 5-star rating component for movies.
- Random movie picker based on the popular movies list.
- Loading, empty, and error states across the main screens.
- Poster/backdrop image loading with placeholders and fallback images.

Note: the watchlist and user ratings are kept in memory for the current app session; they are not persisted to local storage.

## Tech Stack

- Kotlin
- Android SDK, min SDK 24, target/compile SDK 35
- Jetpack Compose
- Material 3
- Navigation Compose
- AndroidX ViewModel and Kotlin StateFlow
- Retrofit and OkHttp
- Kotlinx Serialization
- Coil for image loading
- Gradle Kotlin DSL, Gradle Wrapper, and Version Catalog
- TMDb API

## File Structure and Organization

```text
.
|-- README.md
|-- settings.gradle.kts
|-- build.gradle.kts
|-- gradle.properties
|-- gradlew / gradlew.bat
|-- gradle/
|   |-- libs.versions.toml
|   `-- wrapper/
`-- app/
    |-- build.gradle.kts
    |-- proguard-rules.pro
    `-- src/
        |-- main/
        |   |-- AndroidManifest.xml
        |   |-- java/com/example/sma/
        |   `-- res/
        `-- test/
```

Main project files:

- `settings.gradle.kts`: defines the Gradle project name and includes the `:app` module.
- `build.gradle.kts`: root Gradle plugin configuration.
- `gradle.properties`: project-wide Gradle and AndroidX settings.
- `gradle/libs.versions.toml`: centralized dependency and plugin versions.
- `gradlew` / `gradlew.bat`: Gradle wrapper scripts.
- `app/build.gradle.kts`: Android app configuration, Compose setup, dependencies, and TMDb `BuildConfig` fields.
- `app/proguard-rules.pro`: release/proguard customization placeholder.
- `app/src/main/AndroidManifest.xml`: declares the app, launcher activity, application class, and internet permission.

Main Kotlin files:

- `MainActivity.kt`: app entry point; applies the Compose theme and starts `CineLogApp`.
- `CineLogApplication.kt`: initializes the application-wide dependency container.
- `CineLogApp.kt`: top-level Compose scaffold and bottom navigation.
- `data/AppContainer.kt`: lightweight manual dependency injection container.
- `data/network/TmdbApiService.kt`: Retrofit API interface for TMDb endpoints.
- `data/repository/MovieRepository.kt`: repository that fetches movies and manages in-memory watchlist/ratings.
- `data/model/Movie.kt`: serializable TMDb models and movie formatting helpers.
- `ui/AppViewModelProvider.kt`: ViewModel factory wired to the shared repository.
- `ui/navigation/Screen.kt`: route definitions.
- `ui/navigation/CineLogNavGraph.kt`: navigation graph for home, search, random, watchlist, and detail screens.
- `ui/screens/home/`: home feed UI and state management.
- `ui/screens/search/`: movie search, genre filters, and list/grid result views.
- `ui/screens/detail/`: movie detail UI, watchlist action, and user rating handling.
- `ui/screens/random/`: random movie suggestion screen.
- `ui/screens/watchlist/`: saved movies list and removal flow.
- `ui/components/MovieCard.kt`: reusable movie card and list item components.
- `ui/components/RatingBar.kt`: reusable star rating component.
- `ui/theme/`: Compose color palette, typography, and Material theme.

Resources:

- `res/values/strings.xml`: app text resources.
- `res/values/themes.xml` and `res/values-night/themes.xml`: Android theme bridge for the Compose activity.
- `res/drawable/`: placeholder and broken-image drawables.
- `res/mipmap-*`: launcher icons.
- `res/xml/`: Android backup and data extraction rules.

## Running the Project

Requirements:

- Android Studio with Android SDK support
- JDK 11 or newer
- A TMDb API key

1. Open the project in Android Studio.
2. Create or update the root `local.properties` file:

```properties
sdk.dir=/path/to/your/android/sdk
TMDB_API_KEY=your_tmdb_api_key
TMDB_BASE_URL=https://api.themoviedb.org/3/
```

Android Studio usually manages `sdk.dir` automatically. The TMDb base URL must include the trailing slash.

3. Sync Gradle.
4. Run the `app` configuration on an emulator or physical Android device.

From the terminal, you can also build the debug APK with:

```bash
./gradlew assembleDebug
```

And run the local unit tests with:

```bash
./gradlew test
```

## Authors

André Gonçalves nº58392
André Zhan nº58762
