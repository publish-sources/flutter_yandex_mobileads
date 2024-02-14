/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of 'mobile_ads.dart';

final _finalizer = Finalizer<MethodChannel>((channel) {
  channel.invokeMethod('destroy');
});

mixin _Ad {
  late final MethodChannel _channel;

  Future<void> destroy() async {
    _channel.invokeMethod('destroy');
    _finalizer.detach(this);
  }
}
