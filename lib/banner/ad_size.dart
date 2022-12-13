/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

/// [AdSize] represents the size of a banner ad.
class AdSize {
  static const _initialHeight = 1;

  const AdSize._(this.width, this.height, this._type);

  ///
  /// Returns flexible banner size.
  /// Banners with flexible sizes stretch to container width and height when possible.
  ///
  /// [width] The width of the ad container in density-independent pixels (dp).
  /// [height] The height of the ad container in density-independent pixels (dp).
  ///
  const AdSize.flexible({
    required int width,
    required int height,
  }) : this._(width, height, _AdSizeType.flexible);

  ///
  /// Returns sticky banner size with the given width.
  ///
  /// [width] The width of the ad container in density-independent pixels (dp).
  ///
  const AdSize.sticky({
    required int width,
  }) : this._(width, _initialHeight, _AdSizeType.sticky);

  /// Width in density-independent pixels (dp).
  final int width;

  /// Height in density-independent pixels (dp).
  final int height;
  final _AdSizeType _type;
}

enum _AdSizeType { sticky, flexible }
