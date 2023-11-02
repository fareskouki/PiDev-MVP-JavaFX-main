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
public class Reservation {

    private int id;
    private String date;
    private User res_user;
    private Evenement res_evenement;

    public Reservation(int id, String date, User res_user, Evenement res_evenement) {
        this.id = id;
        this.date = date;
        this.res_user = res_user;
        this.res_evenement = res_evenement;
    } 

    public Reservation(String date, User id_membre, Evenement id_evenement) {
        this.date = date;
        this.res_user = id_membre;
        this.res_evenement = id_evenement;
    }

    public Reservation() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int gettId() {
        return this.id;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getDate() {
        return date;
    }


    public User getRes_user() {
        return res_user;
    }

    public Evenement getRes_evenement() {
        return res_evenement;
    }

    public void setRes_user(User res_user) {
        this.res_user = res_user;
    }

    public void setRes_evenement(Evenement res_evenement) {
        this.res_evenement = res_evenement;
    }

    @Override
    public String toString() {
        return "Reservation pour " + "le " + date + " par " + res_user.getPseudo() + " pour " + res_evenement.getNom() ;
    }

    public int getId() {
        return id;
    }

    
}
