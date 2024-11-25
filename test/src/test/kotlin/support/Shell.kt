package support

import io.appium.java_client.AppiumDriver

public fun executeShellCommand(driver: AppiumDriver, command: String, args: Array<String>) {
    driver.executeScript("mobile: shell", mapOf("command" to command, "args" to args))
}
