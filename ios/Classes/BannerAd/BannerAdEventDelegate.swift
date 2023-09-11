/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Flutter
import YandexMobileAds

final class BannerAdEventDelegate: EventDelegate, YMAAdViewDelegate {

    func adViewDidLoad(_ adView: YMAAdView) {
        let width = Int(adView.adContentSize().width)
        let height = Int(adView.adContentSize().height)
        respond(.onAdLoaded, ["width": width, "height": height])
    }

    func adViewDidFailLoading(_ adView: YMAAdView, error: Error) {
        respond(.onAdFailedToLoad, error.toMap())
    }

    func adViewDidClick(_ adView: YMAAdView) {
        respond(.onAdClicked)
    }

    func adViewWillLeaveApplication(_ adView: YMAAdView) {
        respond(.onLeftApplication)
    }

    func adView(_ adView: YMAAdView, didTrackImpressionWith impressionData: YMAImpressionData?) {
        respond(.onImpression, ["impressionData": impressionData?.rawData])
    }
}
