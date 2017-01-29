package fr.udl.android.sam.persistence.Entities;

/**
 * Created by 51M0 on 02/12/2016.
 */

public class Categorie {
    private Long id;
    private String nom;
    private boolean checked=false;

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Categorie(Long id, String nom) {
        this.id=id;
        this.nom=nom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }


    public Categorie(){

    }
}
