/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Entite;

/**
 *
 * @author The Nutorious BIG
 */
public class User {
    private int id;
    private String email;
    private String roles;
    private String password;
    private String pseudo;
    private String addresse;
    private String date_naissance;
    private String disable_token;
    private String activation_token;
    private String reset_token;


    public User(int id, String email, String roles, String password, String pseudo, String addresse,
            String date_naissance, String disable_token, String activation_token, String reset_token) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.pseudo = pseudo;
        this.addresse = addresse;
        this.date_naissance = date_naissance;
        this.disable_token = disable_token;
        this.activation_token = activation_token;
        this.reset_token = reset_token;
    }

    
    public User(String email, String roles, String password, String pseudo, String addresse, String date_naissance,
            String disable_token, String activation_token, String reset_token) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.pseudo = pseudo;
        this.addresse = addresse;
        this.date_naissance = date_naissance;
        this.disable_token = disable_token;
        this.activation_token = activation_token;
        this.reset_token = reset_token;
    }


    public User() {
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getAddresse() {
        return addresse;
    }
    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }
    public String getDate_naissance() {
        return date_naissance;
    }
    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }
    public String getDisable_token() {
        return disable_token;
    }
    public void setDisable_token(String disable_token) {
        this.disable_token = disable_token;
    }
    public String getActivation_token() {
        return activation_token;
    }
    public void setActivation_token(String activation_token) {
        this.activation_token = activation_token;
    }
    public String getReset_token() {
        return reset_token;
    }
    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }


    @Override
    public String toString() {
        return pseudo;
    }

    
    
}
