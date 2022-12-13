part of yandex_mobileads;

/// An AdRequest contains targeting information used to fetch an ad.
/// Ad requests are created using {@link AdRequest.Builder}.
class AdRequest {
  /// User's age that was set into AdRequest.Builder.
  final int? age;

  /// Context query entered inside the app.
  final String? contextQuery;

  /// Context tags describing current user context inside the app.
  final List<String>? contextTags;

  /// User's gender.
  final String? gender;

  /// Custom parameters for ad loading reequest.
  final Map<String, String>? parameters;

  /// Bidding data for ad loading from mediation.
  final String? biddingData;

  const AdRequest(
      {this.age,
      this.contextQuery,
      this.contextTags,
      this.gender,
      this.parameters,
      this.biddingData});

  Map<String, dynamic> _toMap() => {
        'age': age.toString(),
        'contextQuery': contextQuery,
        'contextTags': contextTags,
        'gender': gender,
        'parameters': parameters,
        'biddingData': biddingData,
      };
}
