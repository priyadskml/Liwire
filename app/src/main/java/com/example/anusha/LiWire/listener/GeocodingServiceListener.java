package com.example.anusha.LiWire.listener;


import com.example.anusha.LiWire.data.LocationResult;


public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
