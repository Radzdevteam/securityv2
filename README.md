# Radz App Security


## How to Include
### Step 1. Add the repository to your project settings.gradle:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
   ```

### Step 2. Add the dependency
[![](https://jitpack.io/v/Radzdevteam/Securityv2.svg)](https://jitpack.io/#Radzdevteam/Securityv2)
```groovy
dependencies {
     implementation("com.github.Radzdevteam:Securityv2:1.0")
}

   ```

## Usage

In your `MainActivity`, add the following code:
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Security check
        SecurityChecker.validate(
            this,
            "69:24:26:8D:77:DA:70:BD:33:C1:39:F5:D0:34:5D:A4:55:EE:9C:61",
            "securityv2",
            "com.radzdev.securityv2"
        )
    }
}
   ```
