package fr.udl.android.sam.listener.onClick;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import fr.udl.android.sam.activities.MapActivity;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoSite;

/**
 * Created by Leahpar on 07/12/2016.
 */

public class MyOnMarkerClickListener implements GoogleMap.OnMarkerClickListener {
    private MapActivity mActivity;

    public MyOnMarkerClickListener(MapActivity activity){
        this.mActivity = activity;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("onMarkerClick", "i'm in");
        Marker lastMarker = mActivity.getLastMarker();
        // Animating to the touched position
        mActivity.getmMap().animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        Site site = new DaoSite(mActivity).getSiteByName(marker.getTitle());


        if(lastMarker!=null && lastMarker.equals(marker)){
            Log.i("onMarkerClick", " if(site  != null){");
            if(site  != null){
                Log.i("onMarkerClick", "if(marker.isInfoWindowShown()){");
                    Log.i("onMarkerClick", "mActivity.setLastMarker(null);");
                    marker.hideInfoWindow();
                    mActivity.setLastMarker(null);

            }else{
                Log.i("onMarkerClick", "marker.remove();");
                marker.remove();
            }
        }else{
            Log.i("onMarkerClick", "if(lastMarker != null){");
            if(lastMarker != null){
                Log.i("onMarkerClick", "DaoSite(mActivity).getSiteByName(lastMarker.getTitle()");
                Site lastMarkerSite = new DaoSite(mActivity).getSiteByName(lastMarker.getTitle());
                if(lastMarkerSite == null){
                    Log.i("onMarkerClick", " lastMarker.remove();");
                    lastMarker.remove();
                }
            }
            Log.i("onMarkerClick", " mActivity.setLastMarker(marker);");
            mActivity.setLastMarker(marker);
            marker.showInfoWindow();
        }
        return true;
    }
}
