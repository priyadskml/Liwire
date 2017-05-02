package com.example.anusha.LiWire.listener;

/**
 * Created by anusha on 13/5/16.
 */

import com.example.anusha.LiWire.data.Channel;


public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
