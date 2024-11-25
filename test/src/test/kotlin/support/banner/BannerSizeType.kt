package support.banner

import keys.BannerKeys
import support.BaseTest
import support.enterText
import support.waitAndClick

sealed class BannerSizeType {
    class Inline(val width: Int?, val height: Int?) : BannerSizeType()
    class Sticky(val width: Int?) : BannerSizeType()
}

public fun BaseTest.setBannerSizeType(type: BannerSizeType) {
    when (type) {
        is BannerSizeType.Inline -> {
            waitAndClick(BannerKeys.inline)
            if (type.width != null) {
                enterText(BannerKeys.width, "${type.width}")
            }
            if (type.height != null) {
                enterText(BannerKeys.height, "${type.height}")
            }
        }
        is BannerSizeType.Sticky -> {
            waitAndClick(BannerKeys.sticky)
            if (type.width != null) {
                enterText(BannerKeys.width, "${type.width}")
            }
        }
    }
}
