package fr.udl.android.sam.listener.onClick;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PointOfInterest;

import fr.udl.android.sam.activities.MapActivity;

/**
 * Created by Leahpar on 06/12/2016.
 */

public class MyPOIClickListener implements GoogleMap.OnPoiClickListener{

    private MapActivity activity;

    public MyPOIClickListener(MapActivity activity){
        this.activity = activity;
    }

    @Override
    public void onPoiClick(PointOfInterest pointOfInterest) {

        Location loc = new Location("");
        loc.setLatitude(pointOfInterest.latLng.latitude);
        loc.setLongitude(pointOfInterest.latLng.longitude);
        float dist = activity.getCurrentLoc().distanceTo(loc);
        // float rad = ;
        if(activity.isLocked()){
            if(Float.compare(dist, (float) activity.getRadiusInMeters()) <= 0 ){
                activity.putMarkerOnMapClick(pointOfInterest.latLng, pointOfInterest.name);
            }
        }else{
            activity.putMarkerOnMapClick(pointOfInterest.latLng, pointOfInterest.name);
        }
    }
}
