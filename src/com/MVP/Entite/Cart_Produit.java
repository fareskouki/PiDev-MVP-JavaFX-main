/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Entite;

/**
 *
 * @author Imén
 */
public class Cart_Produit {

    private int cart_id;
    private int produit_id;

    public Cart_Produit() {
    }

    public Cart_Produit(int cart_id, int produit_id) {
        this.cart_id = cart_id;
        this.produit_id = produit_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }


    ////// recuperation des donnees directement lkol 
}
