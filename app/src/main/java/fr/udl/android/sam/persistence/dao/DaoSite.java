package fr.udl.android.sam.persistence.dao;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.activities.MapActivity;

/**
 * Created by 51M0 on 07/12/2016.
 */

public class DaoSite {
    Activity main;
    public final Uri CONTENT_URL =
            Uri.parse("content://com.example.a51m0.appli_base_donne.SiteProvider/site");
    ContentResolver content;

    public DaoSite(Activity main) {
        this.main = main;
        content=main.getContentResolver();
    }
    public Site getSite(long id){
        // Projection contains the columns we want
        String[] projection = new String[]{"_ID", "Nom","latitude","longitude","adresse","ID_categorie","resume"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = content.query(CONTENT_URL, projection, "_ID=  ?", new String[]{String.valueOf(id)}, null);

        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{

                String nom = cursor.getString(cursor.getColumnIndex("Nom"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
                long ID_categorie = cursor.getLong(cursor.getColumnIndex("ID_categorie"));
                String resume = cursor.getString(cursor.getColumnIndex("resume"));
                if(latitude!= null && longitude !=null && nom!= null && adresse != null){
                    return new Site(id, nom,latitude,longitude,adresse,ID_categorie,resume);
                }



            }while (cursor.moveToNext());

        }

        return null;

    }

    public Site getSiteByName(String siteName){
        // Projection contains the columns we want
        String[] projection = new String[]{"_ID", "Nom","latitude","longitude","adresse","ID_categorie","resume"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = content.query(CONTENT_URL, projection, "Nom=  ?", new String[]{siteName}, null);

        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{
                long id = cursor.getLong(cursor.getColumnIndex("_ID"));
                String nom = cursor.getString(cursor.getColumnIndex("Nom"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
                long ID_categorie = cursor.getLong(cursor.getColumnIndex("ID_categorie"));
                String resume = cursor.getString(cursor.getColumnIndex("resume"));

                return new Site(id, nom,latitude,longitude,adresse,ID_categorie,resume);



            }while (cursor.moveToNext());

        }

        return null;

    }

    public List<Site> getSitesByCategory(long id){
        // Projection contains the columns we want
        String[] projection = new String[]{"_ID", "Nom","latitude","longitude","adresse","ID_categorie","resume"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = content.query(CONTENT_URL, projection, "ID_categorie=  ?", new String[]{String.valueOf(id)}, null);
        List <Site> list = new ArrayList<Site>();
        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{
                String nom = cursor.getString(cursor.getColumnIndex("Nom"));
                double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
                long ID_categorie = cursor.getLong(cursor.getColumnIndex("ID_categorie"));
                String resume = cursor.getString(cursor.getColumnIndex("resume"));

                if(latitude!= 0 && longitude !=0 && nom!= null){
                    list.add( new Site( nom,latitude,longitude,adresse,ID_categorie,resume));
                }


            }while (cursor.moveToNext());

        }

        return list;

    }
    public List<Site> toutSites (){
        String[] projection =  new String[]{"_ID", "Nom","latitude","longitude","adresse","ID_categorie","resume"};
        Cursor cursor = content.query(CONTENT_URL, projection,null, null, null);
        List <Site> list=new ArrayList<Site>();
        if(cursor.moveToFirst()){

            do{
                long id = cursor.getLong(cursor.getColumnIndex("_ID"));
                String nom = cursor.getString(cursor.getColumnIndex("Nom"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
                Long ID_categorie = cursor.getLong(cursor.getColumnIndex("ID_categorie"));
                String resume = cursor.getString(cursor.getColumnIndex("resume"));
                if(latitude!= 0 && longitude !=0 && nom!= null && ID_categorie!=null){
                    list.add( new Site( nom,latitude,longitude,adresse,ID_categorie,resume));
                }


            }while (cursor.moveToNext());

        }

        return list;

    }
    public int supprimer(long id){
       return content.delete(CONTENT_URL, "_ID = ?", new String[]{String.valueOf(id)});
    }
    
    public void inserer (Site site){
        ContentValues valeurs = new ContentValues();
        valeurs.put("Nom", site.getNom());
        valeurs.put("latitude",site.getLattitude());
        valeurs.put("longitude",site.getLongitude());
        valeurs.put("adresse",site.getAdress());
        valeurs.put("ID_categorie",site.getId_categorie());
        valeurs.put("resume",site.getRésume());
       content.insert(CONTENT_URL,valeurs);

    }

}
