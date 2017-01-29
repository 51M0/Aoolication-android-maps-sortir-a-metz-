package fr.udl.android.sam.listener.onClick.button;

import android.content.Intent;
import android.view.View;

import fr.udl.android.sam.activities.SaisieActivity;

/**
 * Created by Leahpar on 09/12/2016.
 */

public class AnnulationButtonListener implements View.OnClickListener {

    private SaisieActivity activity;

    public AnnulationButtonListener (SaisieActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        activity.setResult(activity.RESULT_CANCELED, intent);
        activity.finish();
    }
}
