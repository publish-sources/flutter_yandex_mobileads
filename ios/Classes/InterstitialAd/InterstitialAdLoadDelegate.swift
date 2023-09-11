/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

final class InterstititalAdLoadDelegate: LoadDelegate, YMAInterstitialAdLoaderDelegate {
    
    private let adCreator: FullScreenAdCreator
    
    init(adCreator: FullScreenAdCreator) {
        self.adCreator = adCreator
    }
    
    func interstitialAdLoader(_ adLoader: YMAInterstitialAdLoader, didLoad interstitialAd: YMAInterstitialAd) {
        let id = adCreator.createInterstitialAd(ad: interstitialAd)
        respond(.onAdLoaded, ["id" : id, "adInfo" : adInfoToMap(adInfo: interstitialAd.adInfo)])
    }
    
    func interstitialAdLoader(_ adLoader: YMAInterstitialAdLoader, didFailToLoadWithError error: YMAAdRequestError) {
        let errorMap = error.error.toMap()
        respond(.onAdFailedToLoad, ["adUnitId": error.adUnitId, "code": errorMap["code"] ?? 1, "description": errorMap["description"] ?? ""])
    }
}
