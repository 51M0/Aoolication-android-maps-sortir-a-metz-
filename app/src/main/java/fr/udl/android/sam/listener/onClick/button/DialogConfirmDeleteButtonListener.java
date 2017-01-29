package fr.udl.android.sam.listener.onClick.button;

import android.content.DialogInterface;

import fr.udl.android.sam.activities.MapActivity;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoSite;

/**
 * Created by Leahpar on 09/12/2016.
 */

public class DialogConfirmDeleteButtonListener implements DialogInterface.OnClickListener {
    private MapActivity activity;
    private DaoSite daoSite;
    private Site site;

    public DialogConfirmDeleteButtonListener(MapActivity activity, Site site, DaoSite daoSite){
        this.activity = activity;
        this.site = site;
        this.daoSite = daoSite;
    }

    public void onClick(DialogInterface dialog, int which) {
        daoSite.supprimer(site.getId());
        activity.getLastMarker().remove();
        activity.setLastMarker(null);
    }
}
