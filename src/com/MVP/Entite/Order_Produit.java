/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Entite;

/**
 *
 * @author Imén
 */
public class Order_Produit {
    private int id;
    private int order_id;
    private int produit_id;
    


public Order_Produit() {}

public Order_Produit(int id,int order_id,int produit_id) {
        this.id = id;
        this.order_id=order_id;
        this.produit_id=produit_id;
        
    }

    public Order_Produit(int order_id, int produit_id) {
        this.order_id = order_id;
        this.produit_id = produit_id;
    }


    public int getId() {
        return id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }





}