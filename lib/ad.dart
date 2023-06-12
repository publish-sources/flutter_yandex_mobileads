/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

mixin _Ad {
  static const pluginType = 'plugin_type';
  static const pluginVersion = 'plugin_version';
  static const flutter = 'flutter';

  static final _finalizer = Finalizer<MethodChannel>((channel) {
    channel.invokeMethod('destroy');
  });

  late final String adUnitId;
  late final _EventListener _eventListener;
  late final MethodChannel _channel;
  bool _isLoaded = false;

  bool get isLoaded => _isLoaded;

  Future<void> load({AdRequest? adRequest}) async {
    _isLoaded = false;
    final map = adRequest?._toMap() ?? {};
    map['parameters'] = {
      pluginType: flutter,
      pluginVersion: MobileAds.pluginVersion,
    }..addAll(adRequest?.parameters ?? {});
    _channel.invokeMethod('load', map);
    var result = await _eventListener
        .waitFor([_CallbackName.onAdLoaded, _CallbackName.onAdFailedToLoad]);
    if (result['name'] == _CallbackName.onAdFailedToLoad.name) {
      throw AdLoadError(result['code'], result['description']);
    }
    _isLoaded = true;
    _finalizer.attach(this, _channel, detach: this);
  }

  Future<void> destroy() async {
    _channel.invokeMethod('destroy');
    _finalizer.detach(this);
  }
}
