package fr.udl.android.sam.listener.onClick.button;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import fr.udl.android.sam.activities.AdvancedOptionsActivity;

/**
 * Created by Leahpar on 09/12/2016.
 */

public class AdvancedOptionsValidationButtonListener implements View.OnClickListener {

    private AdvancedOptionsActivity activity;

    public AdvancedOptionsValidationButtonListener (AdvancedOptionsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

        String distance = activity.getDistanceNum().getText().toString();
        Log.i("jk", "" + distance);
        if(distance != null && !distance.isEmpty()){
            activity.setRadius(Double.parseDouble(distance)) ;
        }

        Intent intent = new Intent();
        intent.putExtra("RADIUS_METER", activity.getRadius());
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
    }
}
