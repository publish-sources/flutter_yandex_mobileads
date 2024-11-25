package support

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.ios.IOSDriver
import io.qameta.allure.Allure.step
import keys.CommonKeys
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.time.TimeSource
import kotlin.time.toKotlinDuration

fun getElementContentDescription(driver: AppiumDriver, id: String): String {
    return platformDependant(ios = {
        return@platformDependant driver.findElement(By.id(id)).text
    }, android = {
        return@platformDependant driver.executeScript(
            "mobile: uiautomator",
            mapOf("strategy" to "res", "locator" to id, "action" to "getContentDescription")
        ).toString()
    })
}

fun BaseTest.assertLogsCallback(expectedCallback: String) {
    step("Check that logs have callback: $expectedCallback") { _ ->
        val logs = getElementContentDescription(driver, id = CommonKeys.logText)
        assert(logs.contains(expectedCallback), {
            "$expectedCallback not found"
        })
    }
}

fun BaseTest.waitLogsCallback(expectedCallback: String, timeout: Duration = Duration.ofSeconds(10)) {
    step("Wait for logs callback: $expectedCallback") { _ ->
        val timeSource = TimeSource.Monotonic
        val start = timeSource.markNow()
        while (timeSource.markNow().minus(start) < timeout.toKotlinDuration()) {
            val logs = getElementContentDescription(driver, id = CommonKeys.logText)
            if (logs.contains(expectedCallback)) {
                return@step
            }
        }

        throw AssertionError("$expectedCallback not found")
    }
}

fun BaseTest.enterText(id: String, text: String) {
    val wait = WebDriverWait(driver, Duration.ofSeconds(20))

    platformDependant(ios = {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
        val textField = driver.findElement(By.id(id))
        textField.clear()
        textField.sendKeys(text)
        try {
            (driver as? IOSDriver)?.hideKeyboard()
        } catch (exception: Exception) {
            print(exception)
        }
    }, android = {
        waitAndClick(id)
        driver.executeScript(
            "mobile: uiautomator",
            mapOf("strategy" to "res", "locator" to id, "action" to "clear")
        )
        for (char in text) {
            var key = when (char) {
                '-' -> AndroidKey.MINUS.toString()
                else -> char.toString().uppercase()
            }

            if (char.isDigit()) {
                key = "DIGIT_$key"
            }

            (driver as? AndroidDriver)?.pressKey(
                KeyEvent(AndroidKey.valueOf(key))
            )
        }
//        (driver as? AndroidDriver)?.hideKeyboard()
    })
}
