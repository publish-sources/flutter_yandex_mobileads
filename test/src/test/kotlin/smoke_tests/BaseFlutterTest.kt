package smoke_tests

import com.yandex.plugin_tests_support.BaseTest
import com.yandex.plugin_tests_support.PluginConfig

const val appPackage = "com.yandex.mobile.ads.flutter.example"
const val activityName = "${appPackage}.MainActivity"
const val bundleId = "com.yandex.mobile.ads.yandexMobileadsExample"
val appPath = System.getenv("APP_PATH")

open class BaseFlutterTest: BaseTest() {
    override val config: PluginConfig
        get() = PluginConfig(
            appPackage,
            activityName,
            bundleId,
            iosAppPath = appPath,
            androidAppPath = appPath
        )
}
