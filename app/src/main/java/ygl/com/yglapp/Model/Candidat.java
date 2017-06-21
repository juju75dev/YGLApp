package ygl.com.yglapp.Model;

import java.io.Serializable;

/**
 * Created by juju on 21/06/2017.
 */

public class Candidat implements Serializable {
    public String prenom;
    public String nom;
    public String email;

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Candidat() {

    }

    public Candidat(String prenom, String nom, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }
}
