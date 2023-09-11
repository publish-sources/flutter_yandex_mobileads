/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

/// This class is responsible for showing an rewarded ad.
class RewardedAd extends _FullscreenAd {
  static const _channelPath = 'yandex_mobileads.rewardedAd';

  RewardedAd._({
    required super.channelName,
    required super.id,
    super.adInfo,
  });

  static RewardedAd _create({
    required int id,
    required AdInfo adInfo,
  }) {
    return RewardedAd._(channelName: _channelPath, id: id, adInfo: adInfo);
  }

  Future<void> setAdEventListener(
      {required RewardedAdEventListener eventListener}) async {
    _setAdEventListener(
        eventListener: _FullScreenAdEventListener(
            channelName: '${_channel.name}.events',
            onAdShown: eventListener.onAdShown,
            onAdFailedToShow: eventListener.onAdFailedToShow,
            onAdDismissed: eventListener.onAdDismissed,
            onAdClicked: eventListener.onAdClicked,
            onAdImpression: eventListener.onAdImpression,
            onRewarded: eventListener.onRewarded));
  }

  @override
  Future<Reward?> waitForDismiss() async {
    Reward? reward;
    _eventListener?.waitFor([_FullScreenAdCallbackName.onRewarded]).then(
        (result) => reward = Reward._(result['type'], result['amount']));
    await _eventListener?.waitFor([_FullScreenAdCallbackName.onAdDismissed]);
    return reward;
  }
}
