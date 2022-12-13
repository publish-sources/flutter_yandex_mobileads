/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

enum _CallbackName {
  /// Notifies that the ad loaded successfully.
  onAdLoaded,

  /// Notifies that the ad failed to load.
  onAdFailedToLoad,

  /// Notifies that the user clicked on the ad.
  onAdClicked,

  /// Notifies that the ad has been shown.
  onAdShown,

  /// Notifies that the ad can’t be displayed.
  onAdFailedToShow,

  /// Notifies that the ad has been dismissed.
  onAdDismissed,

  /// Notifies that the user should be rewarded for viewing an ad (impression counted).
  onRewarded,

  /// Notifies that the app will run in the background now because the user clicked on the ad and is about to switch to a different app (Phone, App Store, and so on).
  onLeftApplication,

  /// Notifies that the user returned to app.
  onReturnedToApplication,

  /// Notifies that an ad impression has been counted.
  onImpression;

  static _CallbackName? find(String name) {
    for (_CallbackName value in _CallbackName.values) {
      if (value.name == name) return value;
    }

    return null;
  }
}
