package com.example.anusha.LiWire;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.anusha.LiWire.data.Condition;
import com.example.anusha.LiWire.data.LocationResult;
import com.example.anusha.LiWire.listener.WeatherServiceListener;
import com.example.anusha.LiWire.service.WeatherCacheService;
import com.example.anusha.LiWire.service.YahooWeatherService;
import com.example.anusha.today.R;
import com.example.anusha.LiWire.data.Channel;
import com.example.anusha.LiWire.listener.GeocodingServiceListener;
import com.example.anusha.LiWire.service.GoogleMapsGeocodingService;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceListener, GeocodingServiceListener, LocationListener {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private Button back;

    private YahooWeatherService weatherService;
    private GoogleMapsGeocodingService geocodingService;
    private WeatherCacheService cacheService;

    private ProgressDialog dialog;

    // weather service fail flag
    private boolean weatherServicesHasFailed = false;

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        weatherService = new YahooWeatherService(this);
        weatherService.setTemperatureUnit(preferences.getString(getString(R.string.pref_temperature_unit), null));

        geocodingService = new GoogleMapsGeocodingService(this);
        cacheService = new WeatherCacheService(this);

        if (preferences.getBoolean(getString(R.string.pref_needs_setup), true)) {
            startSettingsActivity();
        } else {

            dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setCancelable(false);
            dialog.show();

            String location = null;

            if (preferences.getBoolean(getString(R.string.pref_geolocation_enabled), true)) {
                String locationCache = preferences.getString(getString(R.string.pref_cached_location), null);

                if (locationCache == null) {
                    getWeatherFromCurrentLocation();
                } else {
                    location = locationCache;
                }
            } else {
                location = preferences.getString(getString(R.string.pref_manual_location), null);
            }

            if(location != null) {
                weatherService.refreshWeather(location);
            }
        }

    }

    private void getWeatherFromCurrentLocation() {
        //implement your custom logic here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //implement your custom logic here
    }

    @Override
    public void serviceSuccess(Channel channel) {
        //implement your custom logic here
    }

    @Override
    public void serviceFailure(Exception exception) {
        //implement your custom logic here
    }

    @Override
    public void geocodeSuccess(LocationResult location) {
        //implement your custom logic here
    }

    @Override
    public void geocodeFailure(Exception exception) {
        // GeoCoding failed, try loading weather data from the cache
        cacheService.load(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        geocodingService.refreshLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // OPTIONAL: implement your custom logic here
    }

    @Override
    public void onProviderEnabled(String s) {
        // OPTIONAL: implement your custom logic here
    }

    @Override
    public void onProviderDisabled(String s) {
        // OPTIONAL: implement your custom logic here
    }
}
