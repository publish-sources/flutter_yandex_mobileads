/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

enum AdRequestErrorCode {
  // from Android
  internalError,
  invalidRequest,
  networkError,
  systemError,
  unknownError,
  // from iOS
  emptyAdUnitId,
  invalidUUID,
  noSuchAdUnitId,
  badServerResponse,
  adSizeMismatch,
  adTypeMismatch,
  serviceTemporarilyNotAvailable,
  adHasAlreadyBeenPresented,
  nullPresentingViewController,
  incorrectFullscreenView,
  // exists in both
  noFill,
}
