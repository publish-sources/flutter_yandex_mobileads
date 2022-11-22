/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

class _EventListener {

  final String channelName;

  final void Function()? onAdLoaded;
  final void Function(AdLoadError error)? onAdFailedToLoad;
  final void Function()? onAdShown;
  final void Function()? onAdDismissed;
  final void Function(Reward reward)? onRewarded;
  final void Function()? onAdClicked;
  final void Function()? onLeftApplication;
  final void Function()? onReturnedToApplication;
  final void Function(String? impressionData)? onImpression;

  late Stream eventStream;

  _EventListener({
    required this.channelName,
    this.onAdLoaded,
    this.onAdFailedToLoad,
    this.onAdShown,
    this.onAdDismissed,
    this.onRewarded,
    this.onAdClicked,
    this.onLeftApplication,
    this.onReturnedToApplication,
    this.onImpression,
  });

  Future<void> setupCallbacks() async {
    eventStream = EventChannel(channelName).receiveBroadcastStream();
    await for (Map result in eventStream) {
      switch (_CallbackName.find(result['name'])) {
        case _CallbackName.onAdLoaded:
          onAdLoaded?.call();
          break;
        case _CallbackName.onAdFailedToLoad:
          onAdFailedToLoad?.call(
              AdLoadError(result['code'], result['description'])
          );
          break;
        case _CallbackName.onAdClicked:
          onAdClicked?.call();
          break;
        case _CallbackName.onAdShown:
          onAdShown?.call();
          break;
        case _CallbackName.onAdDismissed:
          onAdDismissed?.call();
          break;
        case _CallbackName.onRewarded:
          onRewarded?.call(Reward._(result['type'], result['amount']));
          break;
        case _CallbackName.onLeftApplication:
          onLeftApplication?.call();
          break;
        case _CallbackName.onReturnedToApplication:
          onReturnedToApplication?.call();
          break;
        case _CallbackName.onImpression:
          onImpression?.call(result['impressionData']);
          break;
        default:
          break;
      }
    }
  }

  Future<Map> waitFor(List<_CallbackName> names) async {
    return await eventStream.firstWhere((result) {
      return names.any((name) => result['name'] == name.name);
    });
  }
}
