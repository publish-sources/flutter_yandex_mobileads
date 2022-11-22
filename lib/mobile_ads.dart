/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

library yandex_mobileads;

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

part 'ad.dart';
part 'fullscreen_ad.dart';
part 'banner/ad_size.dart';
part 'ad_request.dart';
part 'banner/banner_ad.dart';
part 'interstitial/interstitial_ad.dart';
part 'rewarded/rewarded_ad.dart';
part 'rewarded/reward.dart';
part 'events/callback_name.dart';
part 'events/event_listener.dart';
part 'events/error.dart';
part 'platform/platform_interface.dart';
part 'platform/android_interface.dart';
part 'platform/ios_interface.dart';

class MobileAds {

  static const _path = 'yandex_mobileads.mobileAds';
  static const _channel = MethodChannel(_path);

  static var _loggingEnabled = false;
  static var _locationConsent = false;
  static var _userConsent = false;
  static var _debugErrorIndicatorEnabled = false;
  static var _ageRestrictedUser = false;

  /// Returns the library version as a string.
  static const libraryVersion = '0.0.1';

  /// A private constructor to prevent instancing.
  /// Using it inside the library won't be useful.
  MobileAds._();

  /// Whether the library outputs log messages.
  static bool get loggingEnabled => _loggingEnabled;

  /// Enables logging.
  static Future<void> enableLogging() async {
    _loggingEnabled = true;
    await _channel.invokeMethod('enableLogging');
  }

  /// Whether using location for targeting ads is allowed.
  static bool get locationConsent => _locationConsent;

  /// Sets the location consent.
  static Future<void> setLocationConsent(bool value) async {
    await _channel.invokeMethod('setLocationConsent', value);
    _locationConsent = value;
  }

  /// Whether the user from a GDPR country has allowed
  /// using their personal data for targeting ads.
  static bool get userConsent => _userConsent;

  /// Sets the user consent.
  static Future<void> setUserConsent(bool value) async {
    await _channel.invokeMethod('setUserConsent', value);
    _userConsent = value;
  }

  /// Whether the indicator for native ad integration errors is shown.
  static bool get debugErrorIndicatorEnabled => _debugErrorIndicatorEnabled;

  /// Enables or disables the debug error indicator.
  static Future<void> enableDebugErrorIndicator() async {
    await _channel.invokeMethod('enableDebugErrorIndicator');
    _debugErrorIndicatorEnabled = true;
  }

  static bool get ageRestrictedUser => _ageRestrictedUser;

  static Future<void> setAgeRestrictedUser(bool value) async {
    await _PlatformInterface.instance.setAgeRestrictedUser(_channel, value);
    _ageRestrictedUser = value;
  }

  /// Initializes the Mobile Ads SDK.
  /// Call this in the `initState` method of your app widget.
  static Future<void> initialize() async {
    await _PlatformInterface.instance.initialize(_channel);
  }
}
