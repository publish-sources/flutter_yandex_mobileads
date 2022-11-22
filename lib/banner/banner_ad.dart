/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

part of yandex_mobileads;

class BannerAd with _Ad {

  static const _channelPath = 'yandex_mobileads.bannerAd';
  static var _idCount = 0;

  final AdSize adSize;
  final AdRequest? adRequest;
  final int _id = _idCount++;

  late final Widget _widget = _PlatformInterface.instance.buildBannerAd(
    adUnitId: adUnitId,
    adSize: adSize,
    id: _id,
    onPlatformViewCreated: (_) async {
      _eventListener.setupCallbacks();
      load(adRequest: adRequest);
      final result = await _eventListener.waitFor([_CallbackName.onAdLoaded]);
      _onAdLoaded?.call(result['width'], result['height']);
    }
  );

  void Function(int width, int height)? _onAdLoaded;

  BannerAd({
    required String adUnitId,
    required this.adSize,
    this.adRequest,

    void Function()? onAdLoaded,
    void Function(AdLoadError error)? onAdFailedToLoad,
    void Function()? onAdClicked,
    void Function()? onLeftApplication,
    void Function()? onReturnedToApplication,
    void Function(String? impressionData)? onImpression,
  }) {
    this.adUnitId = adUnitId;
    _channel = MethodChannel('$_channelPath.$_id');
    _eventListener = _EventListener(
      channelName: '$_channelPath.$_id.events',
      onAdLoaded: onAdLoaded,
      onAdFailedToLoad: onAdFailedToLoad,
      onAdClicked: onAdClicked,
      onLeftApplication: onLeftApplication,
      onReturnedToApplication: onReturnedToApplication,
      onImpression: onImpression,
    );
    _Ad._finalizer.attach(this, _channel);
  }
}

class AdWidget extends StatefulWidget {

  final BannerAd bannerAd;

  const AdWidget({
    Key? key,
    required this.bannerAd,
  }) : super(key: key);

  @override
  State<AdWidget> createState() => _AdWidgetState();
}

class _AdWidgetState extends State<AdWidget> {
  late int width, height;

  @override
  void initState() {
    super.initState();
    width = widget.bannerAd.adSize.width;
    height = AdSize._initialHeight;
    widget.bannerAd._onAdLoaded = (width, height) => setState(() {
      this.width = width;
      this.height = height;
    });
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: width.toDouble(),
      height: height.toDouble(),
      child: widget.bannerAd._widget,
    );
  }
}
