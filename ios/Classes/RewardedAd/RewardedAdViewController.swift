/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

final class RewardedAdViewController: BaseFullscreenAdViewController, YMARewardedAdDelegate {
    func rewardedAd(_ rewardedAd: YMARewardedAd, didReward reward: YMAReward) {
        fullScreenEvendDelegate.respond(.onRewarded, ["type": reward.type, "amount": reward.amount])
    }
    
    func rewardedAd(_ rewardedAd: YMARewardedAd, didFailToShowWithError error: Error) {
        super.adDidFailToShow(error)
    }
    
    func rewardedAdDidShow(_ rewardedAd: YMARewardedAd) {
        fullScreenEvendDelegate.respond(.onAdShown)
    }
    
    func rewardedAdDidDismiss(_ rewardedAd: YMARewardedAd) {
        super.adDidDismiss()
    }

    func rewardedAdDidClick(_ ad: YMARewardedAd) {
        fullScreenEvendDelegate.respond(.onAdClicked)
    }

    func rewardedAd(_ ad: YMARewardedAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        fullScreenEvendDelegate.respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
