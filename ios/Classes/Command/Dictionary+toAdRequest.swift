/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import Foundation
import YandexMobileAds

extension Dictionary where Key == String {
    func toAdRequest() -> YMAAdRequest {
        let request = YMAMutableAdRequest()
        if let age = self[AdRequestParameter.age.rawValue] as? String {
            request.age = Int(age) as? NSNumber
        }
        request.contextQuery = self[.contextQuery]
        request.contextTags = self[.contextTags]
        request.gender = self[.gender]
        request.parameters = self[.parameters]
        request.biddingData = self[.biddingData]

        return request
    }

    private subscript<T>(_ key: AdRequestParameter) -> T? {
        self[key.rawValue] as? T
    }
}

private enum AdRequestParameter: String {
    case age
    case contextQuery
    case contextTags
    case gender
    case parameters
    case biddingData
}