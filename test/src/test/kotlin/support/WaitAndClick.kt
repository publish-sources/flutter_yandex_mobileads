package support

import io.appium.java_client.AppiumDriver
import io.appium.java_client.ios.IOSDriver
import io.qameta.allure.Allure.step
import keys.NativeAccessibilityKey
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


fun BaseTest.waitAndClick(key: NativeAccessibilityKey) {
    platformDependant(ios = {
        waitAndClick(key.iosId)
    }, android = {
        step("Click on Android native view with tag one of ${key.androidIds}") { _ ->
            val wait = WebDriverWait(driver, Duration.ofSeconds(5))

            var element: WebElement? = null
            for (id in key.androidIds) {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(id)))
                    element = driver.findElement(By.tagName(id))
                } catch (_: Exception) {

                }

                if (element != null)
                    break
            }
            assert(element != null)
            element?.click()
        }
    })
}

fun BaseTest.waitAndClick(id: String) {
    step("Click on view with id $id") { _ ->
        val wait = WebDriverWait(driver, Duration.ofSeconds(20))

        platformDependant(ios = {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
            driver.findElement(By.id(id)).click()
        }, android = {
            driver.executeScript(
                "mobile: uiautomator",
                mapOf("strategy" to "res", "locator" to id, "action" to "click")
            )
        })
    }
}
