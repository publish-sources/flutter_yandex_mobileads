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

  onAdLoaded,
  onAdFailedToLoad,
  onAdClicked,
  onAdShown,
  onAdFailedToShow,
  onAdDismissed,
  onRewarded,
  onLeftApplication,
  onReturnedToApplication,
  onImpression;

  static _CallbackName? find(String name) {
    for (_CallbackName value in _CallbackName.values) {
      if (value.name == name) return value;
    }

    return null;
  }
}
