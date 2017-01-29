package fr.udl.android.sam.listener.onClick.button;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fr.udl.android.sam.R;
import fr.udl.android.sam.activities.SaisieActivity;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoCategorie;
import fr.udl.android.sam.persistence.dao.DaoSite;

/**
 * Created by Leahpar on 08/12/2016.
 */

public class SaisieValidButtonListener implements View.OnClickListener {

    private SaisieActivity saisieActivity;
    private Site site;

    public SaisieValidButtonListener(SaisieActivity activity){
        this.saisieActivity = activity;
        this.site = saisieActivity.getSite();
    }

    @Override
    public void onClick(View v) {

        site.setNom(saisieActivity.getTitleEdit().getText().toString());
        site.setRésume(saisieActivity.getResumeEdit().getText().toString());
        site.setAdress(saisieActivity.getAdressEdit().getText().toString());
        site.setLongitude(saisieActivity.getLon());
        site.setLattitude(saisieActivity.getLat());

        String categName = saisieActivity.getCategSpinner().getSelectedItem().toString();
        long categId = new DaoCategorie(saisieActivity).getCategorie(categName).getId();
        site.setId_categorie(categId);


        new DaoSite(saisieActivity).inserer(site);

        Intent intent = new Intent();
        saisieActivity.setResult(saisieActivity.RESULT_OK, intent);
        saisieActivity.finish();
    }
}
