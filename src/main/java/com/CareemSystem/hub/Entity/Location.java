package com.CareemSystem.hub.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @GeneratedValue(
            generator = "location_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "location_id",
            sequenceName = "location_id",
            allocationSize = 1
    )
    @Id
    private Integer id;
    private Double latitude;
    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean withinRadius(Location userLocation, double radiusInKm) {
            double userLat = userLocation.getLatitude() * Math.PI / 180;
            double userLon = userLocation.getLongitude() * Math.PI / 180;

            double lat = this.getLatitude() * Math.PI / 180;
            double lon = this.getLongitude() * Math.PI / 180;

            double dlon = lon - userLon;
            double dlat = lat - userLat;

            double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                    Math.cos(userLat) * Math.cos(lat) *
                            Math.sin(dlon / 2) * Math.sin(dlon / 2);

            double c = 2 * Math.asin(Math.sqrt(a));
            double earthRadiusInKm = 6371; // Earth radius in kilometers

            double distanceInKm = earthRadiusInKm * c;
            return distanceInKm <= radiusInKm;
        }
}
