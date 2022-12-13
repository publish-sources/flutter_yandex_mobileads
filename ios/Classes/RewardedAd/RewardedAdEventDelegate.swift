/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import YandexMobileAds

class RewardedAdEventDelegate: EventDelegate, YMARewardedAdDelegate {

    func rewardedAdDidLoad(_ ad: YMARewardedAd) {
        respond(.onAdLoaded)
    }

    func rewardedAdDidFail(toLoad ad: YMARewardedAd, error: Error) {
        respond(.onAdFailedToLoad, error.toMap())
    }

    func rewardedAdDidClick(_ ad: YMARewardedAd) {
        respond(.onAdClicked)
    }

    func rewardedAdDidAppear(_ rewardedAd: YMARewardedAd) {
        respond(.onAdShown)
    }

    func rewardedAdDidFail(toPresent rewardedAd: YMARewardedAd, error: Error) {
        respond(.onAdFailedToShow, error.toMap())
    }

    func rewardedAdDidDisappear(_ rewardedAd: YMARewardedAd) {
        respond(.onAdDismissed)
    }

    func rewardedAd(_ rewardedAd: YMARewardedAd, didReward reward: YMAReward) {
        respond(.onRewarded, ["type": reward.type, "amount": reward.amount])
    }

    func rewardedAdWillLeaveApplication(_ ad: YMARewardedAd) {
        respond(.onLeftApplication)
    }

    func rewardedAd(_ ad: YMARewardedAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        respond(.onImpression, ["impressionData": impressionData?.rawData])
    }
}
