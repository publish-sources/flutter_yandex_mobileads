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

  /// Notifies that the ad loaded successfully.
  final void Function()? onAdLoaded;

  /// Notifies that the ad failed to load.
  final void Function(AdLoadError error)? onAdFailedToLoad;

  /// Notifies that the ad has been shown.
  final void Function()? onAdShown;

  /// Notifies that the ad has been dismissed.
  final void Function()? onAdDismissed;

  /// Notifies that the user should be rewarded for viewing an ad (impression counted).
  final void Function(Reward reward)? onRewarded;

  /// Notifies that the user clicked on the ad.
  final void Function()? onAdClicked;

  /// Notifies that the app will run in the background now because the user clicked on the ad and is about to switch to a different app (Phone, App Store, and so on).
  final void Function()? onLeftApplication;

  /// Notifies that the user returned to app.
  final void Function()? onReturnedToApplication;

  /// Notifies that an ad impression has been counted.
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
          onAdFailedToLoad
              ?.call(AdLoadError(result['code'], result['description']));
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
