package org.eclipse.paho.android.service.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by michal on 9-12-15.
 */
public class GpsService extends Service {

    private static final String TAG = "GpsService";

    private NotificationManager mNotificationManager;

    private int udpateInterval = 1000;
    private float minimumDistance = 1;

    public static double currentLat = 0;
    public static double currentLon = 0;

    private LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            String gpsLocation = "LAT: " + Double.toString(location.getLatitude()) + " LON: " + Double.toString(location.getLongitude());
            Log.d(TAG, gpsLocation);
            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public static String getLatLong() {
        return "lat:" + Double.toString(GpsService.currentLat) + ",lon:" + Double.toString(GpsService.currentLon);
    }


    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        super.onCreate();
        Log.i(TAG, "Service Started.");

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location manager permissions not granted");
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, udpateInterval, minimumDistance, mLocationListener);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}