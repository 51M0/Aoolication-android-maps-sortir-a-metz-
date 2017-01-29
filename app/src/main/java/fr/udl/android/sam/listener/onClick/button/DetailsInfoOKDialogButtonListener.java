package fr.udl.android.sam.listener.onClick.button;

import android.app.AlertDialog;
import android.content.DialogInterface;

import fr.udl.android.sam.activities.MapActivity;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoSite;

/**
 * Created by Leahpar on 09/12/2016.
 */

public class DetailsInfoOKDialogButtonListener implements DialogInterface.OnClickListener{

    private MapActivity activity;
    private DaoSite daoSite;
    private Site site;
    public DetailsInfoOKDialogButtonListener(MapActivity activity, Site site, DaoSite daoSite){
        this.activity = activity;
        this.site = site;
        this.daoSite = daoSite;
    }

    public void onClick(DialogInterface dialog, int which) {
        new AlertDialog.Builder(activity)
                .setTitle("Delete " + site.getNom() +"?")
                .setMessage("Are you sure you want to delete " + site.getNom() +"?")
                .setPositiveButton("OK", new DialogConfirmDeleteButtonListener(activity, site, daoSite) {
                })
                .setNegativeButton(android.R.string.no, new DialogNothingChangesButtonListener())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
