package smoke_tests

import PluginConfig
import keys.CommonKeys

const val appPackage = "com.yandex.mobile.ads.flutter.example"
const val activityName = "${appPackage}.MainActivity"
const val bundleId = "com.yandex.mobile.ads.yandexMobileadsExample"

open class BaseFlutterTest: BaseTest() {
    override val config: PluginConfig
        get() = PluginConfig(appPackage, activityName, bundleId, CommonKeys.logText)
}
