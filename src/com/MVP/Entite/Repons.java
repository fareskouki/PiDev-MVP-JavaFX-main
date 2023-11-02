/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Entite;

import java.sql.Date;

/**
 *
 * @author winxspace
 */
public class Repons {
    private int id;
    private int id_reclamation_id;//changer
    private Date date_rep;
   // private Reclamation rec;
    private String contenu_rep;
    private Statut status_rep ;

    public Repons() {
        
    }

    public Repons(int id_reclamation_id, Date date_rep, String contenu_rep, Statut status_rep) {
        this.id_reclamation_id = id_reclamation_id;
        this.date_rep = date_rep;
        this.contenu_rep = contenu_rep;
        this.status_rep = status_rep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_reclamation_id() {
        return id_reclamation_id;
    }

    public void setId_reclamation_id(int id_reclamation_id) {
        this.id_reclamation_id = id_reclamation_id;
    }

    public Date getDate_rep() {
        return date_rep;
    }

    public void setDate_rep(Date date_rep) {
        this.date_rep = date_rep;
    }

    public String getContenu_rep() {
        return contenu_rep;
    }

    public void setContenu_rep(String contenu_rep) {
        this.contenu_rep = contenu_rep;
    }

    public Statut getStatus_rep() {
        return status_rep;
    }

    public void setStatus_rep(Statut status_rep) {
        this.status_rep = status_rep;
    }

    @Override
    public String toString() {
        return "Repons{" + "id=" + id + ", id_reclamation_id=" + id_reclamation_id + ", date_rep=" + date_rep + ", contenu_rep=" + contenu_rep + ", status_rep=" + status_rep + '}';
    }


    
}
