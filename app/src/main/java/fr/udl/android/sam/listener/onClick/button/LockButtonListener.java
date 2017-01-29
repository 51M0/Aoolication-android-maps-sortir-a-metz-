package fr.udl.android.sam.listener.onClick.button;

import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

import fr.udl.android.sam.activities.MapActivity;
import fr.udl.android.sam.R;

/**
 * Created by Leahpar on 06/12/2016.
 */

public class LockButtonListener implements View.OnClickListener {

    private final int advancedOptionsRequestCode = 1;

    private MapActivity activity;
    private Circle mCircle;
    private LatLng currentPos;
    private GoogleMap googleMap;


    public LockButtonListener(MapActivity activity){

        this.activity = activity;

    }

    @Override
    public void onClick(View v) {
        Button lockButton = (Button) v.findViewById(R.id.lockPosButton);

        if(activity.isLocked()){
            lockButton.setText("lock position");
            activity.setLocked(false);
            activity.setMarker();
        }else{
            lockButton.setText("unlock position");
            activity.setLocked(true);
            mCircle = activity.getmCircle();
            mCircle.remove();
            mCircle.setCenter(activity.getCurrentPos());
            googleMap = activity.getmMap();
            activity.setmCircle(googleMap.addCircle(activity.getCircleOptions()));
            currentPos = activity.getCurrentPos();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
            activity.setMarker();
        }
    }
}
