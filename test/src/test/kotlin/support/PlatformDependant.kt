package support

private val defaultPlatform: String = "android"
val platform = System.getenv("PLATFORM") ?: defaultPlatform

public fun<R> platformDependant(ios: () -> R, android: () -> R): R {
    when (platform) {
        "ios" -> return ios()
        "android" -> return android()
        else -> throw Exception("Unknown platform")
    }
}
