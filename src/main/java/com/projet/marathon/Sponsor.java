package com.projet.marathon;

public class Sponsor {
    private int id;
    private String nom;
    private String logo;
    public int montant;
    public String marathon;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getMarathon() {
        return marathon;
    }

    public void setMarathon(String marathon) {
        this.marathon = marathon;
    }
}
