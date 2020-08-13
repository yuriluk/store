package org.example.store.service.util;

import org.example.store.model.GeoLocation;

public class GeoLocationHelper {

    private final static double EARTH_RADIUS = 6372.795; //in kilometers

    public static double calculateDistance(double lat1, double lon1, GeoLocation endGeoLocation) {
        // haversine formula
        double lat2 = endGeoLocation.getLatitude().doubleValue();
        double lon2 = endGeoLocation.getLongitude().doubleValue();

        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formula
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS * c;
    }
}
