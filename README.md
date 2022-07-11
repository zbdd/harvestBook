# HarvestBook
A simple notepad app that stores your data locally in an encrypted database.


### Editing a Note
<picture>
<img src="https://i.imgur.com/ISrVVPI.png" />
</picture>

Features
------
 - Written in [Kotlin](https://kotlinlang.org/)
 - The [Jetpack Compose](https://developer.android.com/jetpack/compose) toolkit used to rapidly develop the native UI
 - Persistence library provided by [Android Jetpack Room](https://developer.android.com/training/data-storage/room)
 - Encryption provided by [SQLCipher for Android with Room](https://github.com/sqlcipher/android-database-sqlcipher#using-sqlcipher-for-android-with-room)
 - Dependency injection provided by [Android Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
 - Utilising GitHub Actions to run Unit Tests on push [(example)](https://github.com/zbdd/harvestBook/tree/master/.github/workflows)

`All dependencies are provided in the build.gradle (app) file`


### List View
<picture>
<img src="https://i.imgur.com/F9BV7dS.png" />
</picture>

Functionality
-------
 - Create/Read/Update/Delete a Note to a local database
 - A Note consists of the following editable fields:
    - Title
    - Content
    - Last Updated
- Top row buttons that can be pressed to sort based on title or last updated

Structure
--------
MVVM pattern based upon Androids [recommended architecture (simplified)](https://developer.android.com/topic/architecture#recommended-app-arch)


### Architecture Diagram
<picture>
<img src="https://i.imgur.com/Wsstrmo.png" />
</picture>

Outstanding work
---------
 - More unit tests
