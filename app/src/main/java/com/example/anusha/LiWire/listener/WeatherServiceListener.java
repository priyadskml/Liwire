package com.example.anusha.LiWire.listener;



import com.example.anusha.LiWire.data.Channel;


public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
