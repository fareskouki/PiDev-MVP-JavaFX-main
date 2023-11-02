/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Entite;

public class Produit {
    
    private int id;
    private String nom_produit;
    private float prix;
    private String description;
    private int stock;
    private int rating;
    private String img; 
    private Categorie categorie;
     
    public Produit(){}

    public Produit(int id, String nom_produit, float prix, String description, int stock, int rating, String img, Categorie categorie) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        this.stock = stock;
        this.rating = rating;
        this.img = img;
        this.categorie = categorie;
    }

    public Produit(String nom_produit, float prix, String description, int stock, int rating, String img, Categorie categorie) {
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        this.stock = stock;
        this.rating = rating;
        this.img = img;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String image) {
        this.img = image;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom_produit=" + nom_produit + ", prix=" + prix + ", description=" + description + ", stock=" + stock + ", rating=" + rating + ", img=" + img + ", categorie=" + categorie + '}';
    }
}
