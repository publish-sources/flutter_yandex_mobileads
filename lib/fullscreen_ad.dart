/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

abstract class _FullscreenAd with _Ad {
  _FullscreenAd({
    required String adUnitId,
    required String channelName,
    required int id,
    void Function()? onAdLoaded,
    void Function(AdLoadError error)? onAdFailedToLoad,
    void Function()? onAdShown,
    void Function()? onAdDismissed,
    void Function(Reward reward)? onRewarded,
    void Function()? onAdClicked,
    void Function()? onLeftApplication,
    void Function()? onReturnedToApplication,
    void Function(String? impressionData)? onImpression,
  }) {
    this.adUnitId = adUnitId;
    _channel = MethodChannel('$channelName.$id');
    _eventListener = _EventListener(
      channelName: '$channelName.$id.events',
      onAdLoaded: onAdLoaded,
      onAdFailedToLoad: onAdFailedToLoad,
      onAdClicked: onAdClicked,
      onAdShown: onAdShown,
      onAdDismissed: onAdDismissed,
      onRewarded: onRewarded,
      onLeftApplication: onLeftApplication,
      onReturnedToApplication: onReturnedToApplication,
      onImpression: onImpression,
    );
    _eventListener.setupCallbacks();
  }

  Future<void> show() async {
    _channel.invokeMethod('show');
    var result = await _eventListener.waitFor([
      _CallbackName.onAdShown,
      _CallbackName.onAdFailedToShow,
    ]);
    if (result['name'] == _CallbackName.onAdFailedToShow.name) {
      throw AdShowError(result['code'], result['description']);
    }
  }

  Future waitForDismiss();
}
