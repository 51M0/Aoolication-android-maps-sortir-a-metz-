package fr.udl.android.sam.listener.onClick.button;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import fr.udl.android.sam.activities.AdvancedOptionsActivity;
import fr.udl.android.sam.activities.MapActivity;

/**
 * Created by Leahpar on 06/12/2016.
 */

public class AdvancedOptionsButtonListener implements View.OnClickListener {

    private final int advancedOptionsRequestCode = 1;

    private MapActivity activity;
    public AdvancedOptionsButtonListener(MapActivity activity){

        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, AdvancedOptionsActivity.class);
        intent.putExtra("RADIUS_METER", activity.getRadiusInMeters());
        activity.startActivityForResult(intent, advancedOptionsRequestCode);
    }
}
