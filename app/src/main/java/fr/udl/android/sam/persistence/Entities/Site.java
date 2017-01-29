package fr.udl.android.sam.persistence.Entities;

/**
 * Created by 51M0 on 02/12/2016.
 */

public class Site {
    private long id,id_categorie;
    private double longitude,lattitude;
    private String nom,adress,résume;

    public Site(){};


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(long id_categorie) {
        this.id_categorie = id_categorie;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getRésume() {
        return résume;
    }

    public void setRésume(String résume) {
        this.résume = résume;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Site(long id,String nom, double lattitude, double longitude, String adress, long id_categorie, String résume) {
        this.id=id;
        this.nom = nom;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.adress = adress;
        this.id_categorie=id_categorie;
        this.résume = résume;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", id_categorie=" + id_categorie +
                ", longitude=" + longitude +
                ", lattitude=" + lattitude +
                ", nom='" + nom + '\'' +
                ", adress='" + adress + '\'' +
                ", résume='" + résume + '\'' +
                '}';
    }

    public Site(String nom, double lattitude, double longitude, String adress, long id_categorie, String résume) {
        this.nom = nom;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.adress = adress;
        this.id_categorie=id_categorie;
        this.résume = résume;
    }
}

