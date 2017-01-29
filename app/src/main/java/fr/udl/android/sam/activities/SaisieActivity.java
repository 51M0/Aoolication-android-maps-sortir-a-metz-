package fr.udl.android.sam.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.udl.android.sam.R;
import fr.udl.android.sam.listener.onClick.button.AnnulationButtonListener;
import fr.udl.android.sam.listener.onClick.button.SaisieValidButtonListener;
import fr.udl.android.sam.persistence.Entities.Categorie;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoCategorie;
import fr.udl.android.sam.persistence.dao.DaoSite;

/**
 * Created by Leahpar on 01/12/2016.
 */

public class SaisieActivity extends FragmentActivity {

    private Site site = new Site();

    private DaoSite daoSite;

    private DaoCategorie daoCategorie;

    private EditText titleEdit;

    private EditText adressEdit;

    private EditText resumeEdit;

    private Spinner categSpinner;

    private double lat;

    private double lon;

    List<String> categs = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_site);
        daoSite = new DaoSite(this);
        daoCategorie = new DaoCategorie(this);

        Bundle b = this.getIntent().getExtras();
        String title = b.getString("TITLE");
        lat = b.getDouble("LATITUDE");
        lon = b.getDouble("LONGITUDE");
        String resume = b.getString("RESUME");

        titleEdit = (EditText) findViewById(R.id.newSiteTitleEdit);
        titleEdit.setText(title);

        adressEdit = (EditText) findViewById(R.id.adressEdit);
        adressEdit.setText(findAdress());

        resumeEdit = (EditText) findViewById(R.id.resumeEdit);
        resumeEdit.setText(resume);

        categSpinner = (Spinner) findViewById(R.id.categSpinner);
        fillList();
        SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categs);

        categSpinner.setAdapter(adapter);

        Button validationButton = (Button) findViewById(R.id.validateNewSiteButton);
        validationButton.setOnClickListener(new SaisieValidButtonListener(this));

        Button annulationButton = (Button) findViewById(R.id.annulationButton);
        annulationButton.setOnClickListener(new AnnulationButtonListener(this));

    }

    public void fillList(){
        List<Categorie> strings = new DaoCategorie(this).toutCategories();

        int listSize = strings.size();
        for(int i=0; i<listSize; i++){
            String name = strings.get(i).getNom();
            categs.add(name);
        }
    }

    private String findAdress(){
        String adress ="";
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try{
            List<Address> geoAddresses = geoAddresses = gcd.getFromLocation(lat, lon, 1);
            if (geoAddresses.size() > 0) {
                String address = geoAddresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = geoAddresses.get(0).getLocality();
                String country = geoAddresses.get(0).getCountryName();
                String postalCode = geoAddresses.get(0).getPostalCode();
                adress = address + ", " + city + " " + postalCode + " " + country;
            }
        }catch (IOException e){

        }
        return adress;
    }

    public Site getSite() {
        return site;
    }

    public Spinner getCategSpinner() {
        return categSpinner;
    }

    public EditText getTitleEdit() {
        return titleEdit;
    }

    public EditText getAdressEdit() {
        return adressEdit;
    }

    public EditText getResumeEdit() {
        return resumeEdit;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}

