/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

final class InterstitialAdEventDelegate: FullScreenEventDelegate, YMAInterstitialAdDelegate {

    func interstitialAd(_ interstitialAd: YMAInterstitialAd, didFailToShowWithError error: Error) {
        respond(.onAdFailedToShow, error.toMap())
    }
    
    func interstitialAdDidShow(_ interstitialAd: YMAInterstitialAd) {
        respond(.onAdShown)
    }
    
    func interstitialAdDidDismiss(_ interstitialAd: YMAInterstitialAd) {
        respond(.onAdDismissed)
    }
    
    
    func interstitialAdDidClick(_ ad: YMAInterstitialAd) {
        respond(.onAdClicked)
    }

    func interstitialAd(_ ad: YMAInterstitialAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
