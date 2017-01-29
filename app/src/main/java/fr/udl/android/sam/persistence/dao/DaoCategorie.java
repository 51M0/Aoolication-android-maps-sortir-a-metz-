package fr.udl.android.sam.persistence.dao;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import fr.udl.android.sam.persistence.Entities.Categorie;

/**
 * Created by 51M0 on 07/12/2016.
 */

public class DaoCategorie {
    Activity main;
    public final Uri CONTENT_URL =
            Uri.parse("content://com.example.a51m0.appli_base_donne.CategorieProvider/categorie");
    ContentResolver content;

    public DaoCategorie(Activity main) {
        this.main = main;
        content=main.getContentResolver();
    }
    public Categorie getCategorie(long id){
        // Projection contains the columns we want
        String[] projection = new String[]{"_ID", "Nom"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = content.query(CONTENT_URL, projection, "_ID=  ?", new String[]{String.valueOf(id)}, null);

        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{

                String name = cursor.getString(cursor.getColumnIndex("Nom"));

                return new Categorie(id,name);

            }while (cursor.moveToNext());

        }

        return null;

    }

    public Categorie getCategorie(String name){
        // Projection contains the columns we want
        String[] projection = new String[]{"_ID", "Nom"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = content.query(CONTENT_URL, projection, "Nom=  ?", new String[]{name}, null);

        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{
                Long id = cursor.getLong(cursor.getColumnIndex("_ID"));
                String categ = cursor.getString(cursor.getColumnIndex("Nom"));

                return new Categorie(id,categ);

            }while (cursor.moveToNext());

        }

        return null;

    }

    public List<Categorie> toutCategories (){
        String[] projection = new String[]{"_ID", "Nom"};
        Cursor cursor = content.query(CONTENT_URL, projection,null, null, null);
        List <Categorie> list=new ArrayList<Categorie>();
        if(cursor.moveToFirst()){

            do{

                Long id = cursor.getLong(cursor.getColumnIndex("_ID"));
                String name = cursor.getString(cursor.getColumnIndex("Nom"));

                list.add(new Categorie(id,name));

            }while (cursor.moveToNext());

        }

        return list;
    }
    public int supprimer(long id){
        return content.delete(CONTENT_URL, "_ID = ?", new String[]{String.valueOf(id)});
    }
    public void inserer( Categorie c){
        ContentValues valeurs = new ContentValues();
        valeurs.put("Nom", c.getNom());
        content.insert(CONTENT_URL,valeurs);
    }
}
