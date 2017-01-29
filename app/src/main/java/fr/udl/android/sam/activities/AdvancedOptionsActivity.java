package fr.udl.android.sam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import fr.udl.android.sam.listener.onClick.button.AdvancedOptionsValidationButtonListener;
import fr.udl.android.sam.persistence.Entities.Categorie;
import fr.udl.android.sam.persistence.dao.DaoCategorie;
import fr.udl.android.sam.listener.onClick.CheckBoxClick;
import fr.udl.android.sam.R;

/**
 * Created by Leahpar on 01/12/2016.
 */

public class AdvancedOptionsActivity extends FragmentActivity {

    private List<String> categs = new ArrayList<>();
    private double radius = 0.0;
    private EditText distanceNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radius = this.getIntent().getExtras().getDouble("RADIUS_METER");
        setContentView(R.layout.advanced_options_activity);
        distanceNum = (EditText) findViewById(R.id.distanceNum);
        distanceNum.setText(String.valueOf(radius));

        ListView advancedCategs = (ListView) findViewById(R.id.advancedCateg);
        advancedCategs.setFocusableInTouchMode(false);

        fillList();
        // set adapter for listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view, categs);

        advancedCategs.setAdapter(adapter);
        advancedCategs.setItemsCanFocus(false);
        advancedCategs.requestFocus();
        // we want multiple clicks
        advancedCategs.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        advancedCategs.setOnItemClickListener(new CheckBoxClick(this));

        for(String check :MapActivity.checkedList){
            advancedCategs.setItemChecked(adapter.getPosition(check), true);
        }

        Button validateButton = (Button) findViewById(R.id.validateAdvancedOptions);
        validateButton.setOnClickListener(new AdvancedOptionsValidationButtonListener(this));


    }

    public void fillList(){
        List<Categorie> strings = new DaoCategorie(this).toutCategories();

        int listSize = strings.size();
        for(int i=0; i<listSize; i++){
            String name = strings.get(i).getNom();
            categs.add(name);
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public EditText getDistanceNum() {
        return distanceNum;
    }
}
