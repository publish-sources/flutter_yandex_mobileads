package support

import com.yandex.plugin_tests_support.BaseTest
import com.yandex.plugin_tests_support.allureStep
import com.yandex.plugin_tests_support.getElementContentDescription
import keys.CommonKeys
import java.time.Duration
import kotlin.time.TimeSource
import kotlin.time.toKotlinDuration

fun BaseTest.waitLogsCallback(expectedCallback: String, timeout: Duration = Duration.ofSeconds(20)) {
    allureStep("Wait for logs callback: $expectedCallback") {
        val timeSource = TimeSource.Monotonic
        val start = timeSource.markNow()
        while (timeSource.markNow().minus(start) < timeout.toKotlinDuration()) {
            val logs = getElementContentDescription(CommonKeys.logText)
            if (logs.contains(expectedCallback)) {
                return@allureStep
            }
        }

        throw AssertionError("$expectedCallback not found")
    }
}
