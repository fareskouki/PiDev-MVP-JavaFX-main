/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Entite;

/**
 *
 * @author Imén
 */
public class Cart {
    private int id;
    private String titre;
    private String session;
    private float prix;
    private int quantite;



 public Cart () {}


 public Cart(int id,float prix,int quantite) {
        this.id = id;
        this.prix = prix;
        this.quantite = quantite;
    }



public Cart(float prix,int quantite) {
        this.prix = prix;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


@Override
    public String toString() {
        return "Cart{" + "id=" + id + ",titre=" +titre +  ", session=" +session +
", prix=" + prix +", quantite=" + quantite + '}';
    }

}