package fr.udl.android.sam.listener.onClick;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import fr.udl.android.sam.activities.MapActivity;

/**
 * Created by Leahpar on 06/12/2016.
 */

public class MyOnMapClickListener implements GoogleMap.OnMapClickListener {

    private MapActivity mapActivity;

    public MyOnMapClickListener(MapActivity activity){

        this.mapActivity = activity;
    }

    @Override
    public void onMapClick(LatLng latLng) {


        Location loc = new Location("");

        loc.setLatitude(latLng.latitude);
        loc.setLongitude(latLng.longitude);

        Location currentLoc = mapActivity.getCurrentLoc();

        float dist = currentLoc.distanceTo(loc);

        // float rad = ;
        if(mapActivity.isLocked()){

            if(Float.compare(dist, (float) mapActivity.getRadiusInMeters()) <= 0 ){

                mapActivity.putMarkerOnMapClick(latLng, null);
                mapActivity.getCircleOptions().center(mapActivity.getCurrentPos());

            }else{
                mapActivity.setLastMarker(null);
            }
        }else{
            mapActivity.putMarkerOnMapClick(latLng, null);
        }
    }
}
