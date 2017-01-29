package fr.udl.android.sam.listener.onClick.button;

import android.view.View;

import fr.udl.android.sam.activities.MapActivity;

/**
 * Created by Leahpar on 06/12/2016.
 */

public class SaveCurrentPosButtonListener implements View.OnClickListener {

    private MapActivity activity;
    public SaveCurrentPosButtonListener(MapActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        activity.putMarkerOnMapClick(activity.getCurrentPos(),null);
    }
}
