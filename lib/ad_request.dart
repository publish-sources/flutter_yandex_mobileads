part of yandex_mobileads;

class AdRequest {

  final int? age;
  final String? contextQuery;
  final List<String>? contextTags;
  final String? gender;
  final Map<String, String>? parameters;
  final String? biddingData;

  const AdRequest({
    this.age,
    this.contextQuery,
    this.contextTags,
    this.gender,
    this.parameters,
    this.biddingData
  });

  Map<String, dynamic> _toMap() => {
    'age': age.toString(),
    'contextQuery': contextQuery,
    'contextTags': contextTags,
    'gender': gender,
    'parameters': parameters,
    'biddingData': biddingData,
  };
}
