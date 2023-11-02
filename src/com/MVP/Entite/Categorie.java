/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Entite;

import java.util.List;

public class Categorie {
    private int id;
    private String nom_categorie;
    private int etat;
    private String type;
    private List<Produit> produits; // Add a list of Produit objects associated with this category

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

       public Categorie(){}

    public Categorie(int id, String nom_categorie, int etat, String type) {
        this.id = id;
        this.nom_categorie = nom_categorie;
        this.etat = etat;
        this.type = type;
    }

    public Categorie(String nom_categorie, int etat, String type) {
        this.nom_categorie = nom_categorie;
        this.etat = etat;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", nom_categorie=" + nom_categorie + ", etat=" + etat + ", type=" + type + ", produits=" + produits + '}';
    }

    public Categorie(int id, String nom_categorie) {
        this.id = id;
        this.nom_categorie = nom_categorie;
    }
}

