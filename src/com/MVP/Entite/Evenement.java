/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Entite;

/**
 *
 * @author Hassène
 */
public class Evenement {
    
    private int id;
    private String nom;
   private String date;

    
    private String description;
    private int duree;
    private int capacite;
    private String type;
    private String image;

    public Evenement() {
    }
    
    
     public Evenement(int id, String nom, String description, int duree, int capacite, String type, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.capacite = capacite;
        this.type = type;
        this.image = image;
    }

    public Evenement(int id) {
        this.id = id;
        //Set everything else to empty strings and zero
        this.nom = "";
        this.description = "";
        this.duree = 0;
        this.capacite = 0;
        this.type = "";
        this.image = "";
    }


    public Evenement(String nom, String description, int duree, int capacite, String type, String image) {
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.capacite = capacite;
        this.type = type;
        this.image = image;
    }

    public Evenement(String nom, String date, String desc, int duree, int cap, String type, String img) {
     this.nom = nom;
      this.date = date;
     
        this.description = desc;
        this.duree = duree;
        this.capacite = cap;
        this.type = type;
        this.image = img;
    }
    
    
public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    @Override
    public String toString() {
        return nom + "["+ date +"]";
    }

    
   
}
