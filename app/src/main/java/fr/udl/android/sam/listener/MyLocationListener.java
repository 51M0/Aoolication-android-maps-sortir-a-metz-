package fr.udl.android.sam.listener;

import android.location.Location;
import android.location.LocationListener;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import fr.udl.android.sam.activities.MapActivity;


public class MyLocationListener implements LocationListener {

    private MapActivity mActivity;
    public MyLocationListener(MapActivity mActivity){
        this.mActivity = mActivity;
    }

    @Override
    public void onLocationChanged(Location location) {

        mActivity.setCurrentLoc(location);
        mActivity.setCurrentPos(new LatLng(mActivity.getCurrentLoc().getLatitude(), mActivity.getCurrentLoc().getLongitude()));
        mActivity.getmCircle().remove();
        mActivity.getCircleOptions().center(mActivity.getCurrentPos());
        mActivity.setmCircle(mActivity.getmMap().addCircle(mActivity.getCircleOptions()));
        mActivity.setMarker();
        if(mActivity.isLocked()){
            mActivity.getmMap().animateCamera(CameraUpdateFactory.newLatLng(mActivity.getCurrentPos()));
        }
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}