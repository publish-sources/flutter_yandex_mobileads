package support

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.EspressoOptions
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.ios.options.XCUITestOptions
import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import org.testng.annotations.*
import java.io.File

public const val appPackage = "com.yandex.mobile.ads.flutter.example"
public const val activityName = "${appPackage}.MainActivity"
public const val bundleId = "com.yandex.mobile.ads.yandexMobileadsExample"

private val appPath = System.getenv("APP_PATH")

open class BaseTest {
    lateinit var service: AppiumDriverLocalService
    lateinit var driver: AppiumDriver

    @BeforeClass
    fun setupSuite() {
        service = AppiumDriverLocalService.buildService(
            AppiumServiceBuilder()
                .usingAnyFreePort()
                .withAppiumJS(File("node_modules/appium/build/lib/main.js"))
                .withArgument { "--allow-insecure=adb_shell" }
        )
        service.start()
    }

    @AfterClass
    fun tearDownSuite() {
        service.stop()
    }

    @Parameters("emulatorName", "simulatorName", "wdaPort")
    @BeforeMethod
    fun setupTest(@Optional emulatorName: String?, @Optional simulatorName: String?, @Optional wdaPort: String?) {
        platformDependant(ios = {
            var options = XCUITestOptions()
                .setBundleId(bundleId)
                .setDeviceName(simulatorName)
                .setApp(appPath)
                .setShowXcodeLog(true)
                .setUsePrebuiltWda(true)

            if (wdaPort != null) {
                options = options.setWdaLocalPort(wdaPort.toInt())
            }

            driver = IOSDriver(service.url, options)
        }, android = {
            var options = EspressoOptions()
                .setAppPackage(appPackage)
                .setAppActivity(activityName)
                .setApp(appPath)
                .setAvd(emulatorName)

            if (wdaPort != null) {
                options = options.setSystemPort(wdaPort.toInt())
            }

            driver = AndroidDriver(service.url, options)

            executeShellCommand(driver, "pm", arrayOf("clear", "com.android.chrome"))
            executeShellCommand(driver, "am", arrayOf("set-debug-app", "--persistent", "com.android.chrome"))
            executeShellCommand(driver, "echo", arrayOf("\"chrome --disable-fre --no-default-browser-check --no-first-run\"", ">", "/data/local/tmp/chrome-command-line"))
        })
    }

    @AfterMethod
    fun tearDownTest() {
        driver.quit()
    }
}
