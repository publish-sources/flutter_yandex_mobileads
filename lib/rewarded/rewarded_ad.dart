/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

class RewardedAd extends _FullscreenAd {

  static const _channelPath = 'yandex_mobileads.rewardedAd';
  static const _createChannel = MethodChannel('yandex_mobileads.createAd');

  RewardedAd._({
    required super.adUnitId,
    required super.channelName,
    required super.id,
    super.onAdLoaded,
    super.onAdFailedToLoad,
    super.onAdShown,
    super.onAdDismissed,
    super.onRewarded,
    super.onAdClicked,
    super.onLeftApplication,
    super.onReturnedToApplication,
    super.onImpression,
  });

  static Future<RewardedAd> create({
    required String adUnitId,
    void Function()? onAdLoaded,
    void Function(AdLoadError error)? onAdFailedToLoad,
    void Function()? onAdShown,
    void Function()? onAdDismissed,
    void Function(Reward reward)? onRewarded,
    void Function()? onAdClicked,
    void Function()? onLeftApplication,
    void Function()? onReturnedToApplication,
    void Function(String? impressionData)? onImpression,
  }) async {
    int? id = await _createChannel.invokeMethod('rewardedAd', adUnitId);
    if (id == null) {
      throw ArgumentError('something went wrong while creating rewarded ad');
    }

    return RewardedAd._(
      adUnitId: adUnitId,
      channelName: _channelPath,
      id: id,
      onAdLoaded: onAdLoaded,
      onAdFailedToLoad: onAdFailedToLoad,
      onAdShown: onAdShown,
      onAdDismissed: onAdDismissed,
      onRewarded: onRewarded,
      onAdClicked: onAdClicked,
      onLeftApplication: onLeftApplication,
      onReturnedToApplication: onReturnedToApplication,
      onImpression: onImpression,
    );
  }

  @override
  Future<Reward?> waitForDismiss() async {
    Reward? reward;
    _eventListener.waitFor([_CallbackName.onRewarded])
      .then((result) => reward = Reward._(result['type'], result['amount']));
    await _eventListener.waitFor([_CallbackName.onAdDismissed]);
    return reward;
  }
}
