package fr.udl.android.sam.listener.onClick;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import fr.udl.android.sam.activities.MapActivity;

/**
 * Created by Leahpar on 07/12/2016.
 */

public class MyOnCameraMoveListener implements GoogleMap.OnCameraMoveListener {

    private MapActivity mapActivity;

    public MyOnCameraMoveListener(MapActivity mActivity){
        this.mapActivity = mActivity;
    }
    @Override
    public void onCameraMove() {
        GoogleMap mMap = mapActivity.getmMap();

        Location loc = new Location("");
        loc.setLatitude(mMap.getCameraPosition().target.latitude);
        loc.setLongitude(mMap.getCameraPosition().target.longitude);
        float dist = mapActivity.getCurrentLoc().distanceTo(loc);

        if(mapActivity.isLocked()){
            if(!(Float.compare(dist, (float) mapActivity.getRadiusInMeters()) <= 0) ){

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mapActivity.getLastValidPos().getLatitude(), mapActivity.getLastValidPos().getLongitude())));
            }else{
                mapActivity.setLastValidPos(loc);
            }
        }else{

        }
    }
}
