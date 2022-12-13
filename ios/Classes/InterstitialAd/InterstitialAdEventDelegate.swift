/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

class InterstitialAdEventDelegate: EventDelegate, YMAInterstitialAdDelegate {

    func interstitialAdDidLoad(_ ad: YMAInterstitialAd) {
        respond(.onAdLoaded)
    }

    func interstitialAdDidFail(toLoad ad: YMAInterstitialAd, error: Error) {
        respond(.onAdFailedToLoad, error.toMap())
    }

    func interstitialAdDidClick(_ ad: YMAInterstitialAd) {
        respond(.onAdClicked)
    }

    func interstitialAdDidAppear(_ interstitialAd: YMAInterstitialAd) {
        respond(.onAdShown)
    }

    func interstitialAdDidFail(toPresent interstitialAd: YMAInterstitialAd, error: Error) {
        respond(.onAdFailedToShow, error.toMap())
    }

    func interstitialAdDidDisappear(_ interstitialAd: YMAInterstitialAd) {
        respond(.onAdDismissed)
    }

    func interstitialAdWillLeaveApplication(_ ad: YMAInterstitialAd) {
        respond(.onLeftApplication)
    }

    func interstitialAd(_ ad: YMAInterstitialAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        respond(.onImpression, ["impressionData": impressionData?.rawData])
    }
}
