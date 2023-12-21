/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

import CoreLocation
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
        request.location = self[.location]
        request.adTheme = self.stringToYMAAdTheme(adTheme: self[.adTheme] as String?)
        request.parameters = self[.parameters]

        return request
    }

    private subscript<T>(_ key: AdRequestParameter) -> T? {
        self[key.rawValue] as? T
    }

    func toAdRequestConfiguration(adUnitID: String) -> YMAAdRequestConfiguration {
        let requestConfiguration = YMAMutableAdRequestConfiguration(adUnitID: adUnitID)
        if let age = self[AdRequestParameter.age.rawValue] as? String {
            requestConfiguration.age = Int(age) as? NSNumber
        }
        requestConfiguration.contextQuery = self[.contextQuery]
        requestConfiguration.contextTags = self[.contextTags]
        requestConfiguration.gender = self[.gender]
        if let locationMap = self[AdRequestParameter.location.rawValue] as? [String: Double] {
            if let location = self.mapToCLLocation(locationMap: locationMap) {
                requestConfiguration.location = location
            }
        }
        requestConfiguration.adTheme = self.stringToYMAAdTheme(adTheme: self[.adTheme] as String?)
        requestConfiguration.parameters = self[.parameters]

        return requestConfiguration
    }


    private func stringToYMAAdTheme(adTheme: String?) -> YMAAdTheme {
        if let theme = adTheme {
            switch AdTheme(rawValue: theme) {
            case .dark:
                return YMAAdTheme.dark
            case .light:
                return YMAAdTheme.light
            case .unspecified:
                return YMAAdTheme.unspecified
            case .none:
                return YMAAdTheme.unspecified
            }
        } else {
            return YMAAdTheme.unspecified
        }
    }

    private func mapToCLLocation(locationMap: [String: Double]) -> CLLocation? {
        let latitude = locationMap["latitude"]
        let longitude = locationMap["longitude"]
        let horizontalAccuracy = locationMap["accuracy"]
        if let latitude, let longitude {
            return CLLocation(coordinate: .init(latitude: latitude, longitude: longitude),
                              altitude: 0,
                              horizontalAccuracy: horizontalAccuracy ?? 0.1,
                              verticalAccuracy: 0.1,
                              timestamp: Date())
        }
        return nil
    }
}
