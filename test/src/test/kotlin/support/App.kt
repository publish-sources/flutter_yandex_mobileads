package support

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver

fun BaseTest.terminateApp() {
    platformDependant(ios = {
        (driver as? IOSDriver)?.terminateApp(bundleId)
    }, android = {
        (driver as? AndroidDriver)?.terminateApp(appPackage)
    })
}

fun BaseTest.launchApp() {
    platformDependant(ios = {
        (driver as? IOSDriver)?.activateApp(bundleId)
    }, android = {
        (driver as? AndroidDriver)?.activateApp(appPackage)
    })
}
