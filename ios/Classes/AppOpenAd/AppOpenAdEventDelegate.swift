/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

final class AppOpenAdEventDelegate: FullScreenEventDelegate, YMAAppOpenAdDelegate {

    func appOpenAd(_ interstitialAd: YMAAppOpenAd, didFailToShowWithError error: Error) {
        respond(.onAdFailedToShow, error.toMap())
    }
    
    func appOpenAdDidShow(_ interstitialAd: YMAAppOpenAd) {
        respond(.onAdShown)
    }
    
    func appOpenAdDidDismiss(_ interstitialAd: YMAAppOpenAd) {
        respond(.onAdDismissed)
    }
    
    func appOpenAdDidClick(_ ad: YMAAppOpenAd) {
        respond(.onAdClicked)
    }

    func appOpenAd(_ ad: YMAAppOpenAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
