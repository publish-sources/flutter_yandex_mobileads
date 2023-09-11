/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

final class RewardedAdEventDelegate: FullScreenEventDelegate, YMARewardedAdDelegate {
    
    func rewardedAd(_ rewardedAd: YMARewardedAd, didReward reward: YMAReward) {
        respond(.onRewarded, ["type": reward.type, "amount": reward.amount])
    }
    
    func rewardedAd(_ rewardedAd: YMARewardedAd, didFailToShowWithError error: Error) {
        respond(.onAdFailedToShow, error.toMap())
    }
    
    func rewardedAdDidShow(_ rewardedAd: YMARewardedAd) {
        respond(.onAdShown)
    }
    
    func rewardedAdDidDismiss(_ rewardedAd: YMARewardedAd) {
        respond(.onAdDismissed)
    }

    func rewardedAdDidClick(_ ad: YMARewardedAd) {
        respond(.onAdClicked)
    }

    func rewardedAd(_ ad: YMARewardedAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        respond(.onAdImpression, ["impressionData": impressionData?.rawData])
    }
}
