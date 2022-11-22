/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

class AdSize {

  static const _initialHeight = 1;

  const AdSize._(this.width, this.height, this._type);

  const AdSize.flexible({
    required int width,
    required int height,
  }) : this._(width, height, _AdSizeType.flexible);

  const AdSize.sticky({
    required int width,
  }) : this._(width, _initialHeight, _AdSizeType.sticky);

  final int width;
  final int height;
  final _AdSizeType _type;
}

enum _AdSizeType { sticky, flexible }
