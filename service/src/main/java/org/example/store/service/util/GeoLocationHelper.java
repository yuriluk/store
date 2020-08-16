package org.example.store.service.util;

import org.example.store.model.GeoLocation;

/**
 *  This class uses haversine formula to calculate distance between two points on the Earth
 *  EARTH_RADIUS given in kilometers.
 *
 */

public class GeoLocationHelper {

    private final static double EARTH_RADIUS = 6372.795;

    public static double calculateDistance(double lat1, double lon1, GeoLocation endGeoLocation) {

        double lat2 = endGeoLocation.getLatitude().doubleValue();
        double lon2 = endGeoLocation.getLongitude().doubleValue();

        double dLat = getDistanceBetweenCoordinates(lat1, lat2);
        double dLon = getDistanceBetweenCoordinates(lon1, lon2);

        lat1 = convertToRadians(lat1);
        lat2 = convertToRadians(lat2);

        return getDistanceByHaversineFormulaCalculation(lat1, lat2, dLat, dLon);
    }

    private static double getDistanceByHaversineFormulaCalculation(double lat1, double lat2, double dLat, double dLon) {
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }

    private static double convertToRadians(double lat1) {
        return Math.toRadians(lat1);
    }

    private static double getDistanceBetweenCoordinates(double lat1, double lat2) {
        return convertToRadians(lat2 - lat1);
    }
}
