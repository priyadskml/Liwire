package com.example.anusha.LiWire.listener;

/**
 * Created by anusha on 13/5/16.
 */

import com.example.anusha.LiWire.data.LocationResult;


public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
