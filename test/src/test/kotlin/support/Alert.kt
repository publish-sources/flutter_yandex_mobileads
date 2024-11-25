package support

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

fun acceptIOSAlert(driver: AppiumDriver, acceptLabel: String) {
    val wait = WebDriverWait(driver, Duration.ofSeconds(5))
    wait.until(ExpectedConditions.alertIsPresent())
    driver.executeScript("mobile: alert", mapOf("action" to "accept", "buttonLabel" to acceptLabel))
}
