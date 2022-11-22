package com.yandex.mobile.ads.flutter.banner

import kotlin.math.roundToInt

object BannerUtil {

    fun Int.toDp(density: Float): Int {
        return if (density == 0.0F) 0 else (this / density).roundToInt()
    }
}
