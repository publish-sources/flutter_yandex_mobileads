/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

class AdLoadError extends Error {
  /// Error code.
  final int code;

  /// Error description.
  final String description;

  AdLoadError(this.code, this.description);

  @override
  String toString() => "Ad failed to load with code $code: $description";
}

class AdShowError extends Error {
  final int code;
  final String description;

  AdShowError(this.code, this.description);

  @override
  String toString() => "Ad failed to show with code $code: $description";
}
