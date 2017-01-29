package fr.udl.android.sam.listener.onClick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import fr.udl.android.sam.activities.MapActivity;
import fr.udl.android.sam.activities.SaisieActivity;
import fr.udl.android.sam.listener.onClick.button.DetailsInfoOKDialogButtonListener;
import fr.udl.android.sam.listener.onClick.button.DialogNothingChangesButtonListener;
import fr.udl.android.sam.persistence.Entities.Categorie;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoCategorie;
import fr.udl.android.sam.persistence.dao.DaoSite;

/**
 * Created by Leahpar on 06/12/2016.
 */

public class MyInfoWindowClickListener implements GoogleMap.OnInfoWindowClickListener{

    private MapActivity activity;
    private Marker marker;
    private int requestCode;
    private DaoSite daoSite;
    private Site site;


    public MyInfoWindowClickListener(MapActivity activity, Marker marker, int requestCode){
        this.activity = activity;
        this.marker = marker;
        this.requestCode = requestCode;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        daoSite = new DaoSite(activity);

        site = daoSite.getSiteByName(marker.getTitle());

        if(site != null){

            Categorie categorie = new DaoCategorie(activity).getCategorie(site.getId_categorie());
            final String categ = categorie.getNom();
            new AlertDialog.Builder(activity)
                    .setTitle(site.getNom())
                    .setMessage("Adresse: " + site.getAdress() + " \n"
                            + "Résumé: " + site.getRésume() + " \n"
                            + "Categorie: " + categ + " \n"
                            + "Lattitude: " + site.getLattitude() + " \n"
                            + "Longitude: " + site.getLongitude()
                    )
                    .setPositiveButton("DELETE", new DetailsInfoOKDialogButtonListener(activity, site, daoSite))
                    .setNegativeButton("OK", new DialogNothingChangesButtonListener())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }else{
            Intent intent = new Intent(activity, SaisieActivity.class);
            intent.putExtra("TITLE", marker.getTitle());
            intent.putExtra("LATITUDE", marker.getPosition().latitude);
            intent.putExtra("LONGITUDE", marker.getPosition().longitude);
            intent.putExtra("RESUME", "fill");
            activity.startActivityForResult(intent, requestCode);
        }

    }

    public MapActivity getActivity() {
        return activity;
    }

    public void setActivity(MapActivity activity) {
        this.activity = activity;
    }
}
