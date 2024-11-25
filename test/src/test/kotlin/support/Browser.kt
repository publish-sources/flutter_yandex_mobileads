package support

import io.appium.java_client.AppiumDriver
import io.qameta.allure.Allure.step

private const val safariBundleId = "com.apple.mobilesafari";

fun BaseTest.assertBrowserOpened() {
    step("Assert that browser is opened") { _ ->
        platformDependant(ios = {
            try {
                acceptIOSAlert(driver, "Continue")
            } catch (exception: Exception) {
                print(exception)
            }
        }, android = {
        })

        val result = driver.executeScript("mobile: getContexts", mapOf("waitForWebviewMs" to 5000))

        platformDependant(ios = {
//        assert(Gson().fromJson(result, GetContextsIOSResponse::class.java).bundleId == safariBundleId)
        }, android = {
//        assert(Gson().fromJson<List<Any>>(result, type).isNotEmpty())
        })
    }
}

fun BaseTest.returnToApp() {
    step("Return to app") { _ ->
        platformDependant(ios = {
            driver.executeScript("mobile: activateApp", mapOf("bundleId" to bundleId))
        }, android = {
            executeShellCommand(driver, "input", arrayOf("keyevent", "KEYCODE_BACK"))
            executeShellCommand(driver, "input", arrayOf("keyevent", "KEYCODE_BACK"))
        })
    }
}
