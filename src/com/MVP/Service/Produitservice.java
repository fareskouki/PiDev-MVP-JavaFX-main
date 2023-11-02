/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Service;

import com.MVP.Entite.Categorie;
import com.MVP.Entite.Produit;
import java.util.logging.Logger;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import com.MVP.Utils.DataBase;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Winxspace
 */
public class Produitservice {

    

    private Connection conn;
    
    public Produitservice(){
        conn = DataBase.getInstance().getConnection();
    }
    
    
public void insert(Produit t) {
    String requete = "insert into produit(nom_produit,id_categorie_id, prix, description, stock, rating, img) values(?, ?, ?, ?, ?, ?,?)";
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setString(1, t.getNom_produit());
        pst.setInt(2, t.getCategorie().getId());
        pst.setFloat(3, t.getPrix());
        pst.setString(4, t.getDescription());
        pst.setInt(5, t.getStock());
        pst.setInt(6, t.getRating());
        pst.setString(7, t.getImg());
        pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void update(Produit t) {
    String requete = "UPDATE produit SET nom_produit = ?, prix = ?, description = ?, stock = ?, rating = ?, img = ?, id_categorie_id = ? WHERE id = ?";
    try {
        PreparedStatement pst = conn.prepareStatement(requete);

        pst.setString(1, t.getNom_produit());
        pst.setFloat(2, t.getPrix());
        pst.setString(3, t.getDescription());
        pst.setInt(4, t.getStock());
        pst.setInt(5, t.getRating());
        pst.setString(6, t.getImg());
        pst.setInt(7, t.getCategorie().getId()); // Assuming the Categorie object has an id property
        pst.setInt(8, t.getId());

        pst.executeUpdate();

        pst.close(); // Close the PreparedStatement
    } catch (SQLException ex) {
        Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
    }
}

 
        
      public void insertPrd(Produit p) {
    String requete = "insert into produit (nom_produit, prix, description, stock, rating, img,id_categorie) values (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)) {
        pst.setString(1, p.getNom_produit());
        pst.setFloat(2, p.getPrix());
        pst.setString(3, p.getDescription());
        pst.setInt(4, p.getStock());
        pst.setInt(5, p.getRating());
        pst.setString(6, p.getImg());

        int rowsInserted = pst.executeUpdate();

        if (rowsInserted > 0) {
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
            }conn.commit();

        }
    } catch (SQLException ex) {
        Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
    }
}


public void delete(int id) {
    String requete = "DELETE FROM produit WHERE id=?";
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setInt(1, id);
        int rowsDeleted = pst.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Produit supprimé avec succès !");
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Aucune ligne supprimée. L'ID du produit n'existe pas.");
            alert.showAndWait();
        }

        pst.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de la suppression du produit.");
        alert.showAndWait();
    }
}
    


public List<Produit> readAll() {
    List<Produit> produitList = new ArrayList<>();
    String requete = "SELECT * FROM produit";
    try {
        Statement ste = conn.createStatement();
        ResultSet rs = ste.executeQuery(requete);
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom_produit = rs.getString("nom_produit");
            String categorie = rs.getString("categorie");
            float prix = rs.getFloat("prix");
            String description = rs.getString("description");
            int stock = rs.getInt("stock");
            int rating = rs.getInt("rating");
            String img = rs.getString("img");
            Categorie categ = new Categorie();
            categ.setNom_categorie(categorie);
            Produit produit = new Produit(id, nom_produit, prix, description, stock, rating, img, categ);
            produitList.add(produit);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
    }
    return produitList;
}

public Produit readById(int id) {
    Produit produit = null;
    String requete = "SELECT * FROM produit WHERE id = ?";
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int productId = rs.getInt("id");
            String nom_produit = rs.getString("nom_produit");
            String categorie= rs.getString("categorie"); // Missing column
            float prix = rs.getFloat("prix");
            String description = rs.getString("description");
            int stock = rs.getInt("stock");
            int rating = rs.getInt("rating");
            String img = rs.getString("img");
            produit = new Produit(productId, nom_produit, prix, description, stock, rating, img, null); // Add null for the missing category
        }
        pst.close();
    } catch (SQLException ex) {
        Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
    }
    return produit;
}

public List<Produit> afficherAll() throws SQLException { // Change method name to follow naming conventions
    List<Produit> produitList = new ArrayList<>();
    String requete = "SELECT * FROM produit";
    PreparedStatement pst = conn.prepareStatement(requete);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
        Produit produit = new Produit();
        produit.setId(rs.getInt("id"));
        produit.setNom_produit(rs.getString("nom_produit"));
        produit.setDescription(rs.getString("description"));
        produit.setPrix(rs.getFloat("prix"));
        produit.setStock(rs.getInt("stock"));
        produit.setRating(rs.getInt("rating"));
        produit.setImg(rs.getString("img"));
        produitList.add(produit);
    }
    return produitList;
}

public List<Categorie> getAllCategories() {
    
    ObservableList<Categorie> categories = FXCollections.observableArrayList();
    
    String requete = "SELECT * FROM categorie";
    try {
        Statement ste = conn.createStatement();
        ResultSet rs = ste.executeQuery(requete);
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom_categorie = rs.getString("nom_categorie");
            int etat = rs.getInt("etat");
            String type = rs.getString("type");
            Categorie categorie = new Categorie(id, nom_categorie, etat, type);
            categories.add(categorie);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Produitservice.class.getName()).log(Level.SEVERE, null, ex);
    }
    return categories;
}

public Produit rechercherProduitParId(int id) {
        try {
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM produit WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNom_produit(rs.getString("nom_produit"));
                produit.setPrix(rs.getFloat("prix"));
                produit.setRating(rs.getInt("rating"));
                produit.setDescription(rs.getString("description"));
                produit.setImg(rs.getString("img"));
                
                return produit;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

public List<Produit> filterByCategory(Categorie categorie) throws SQLException {
    List<Produit> produitList = new ArrayList<>();
    String query = "SELECT * FROM produit WHERE id_categorie_id = ?";
    PreparedStatement preparedStatement = conn.prepareStatement(query);
    preparedStatement.setInt(1, categorie.getId());

    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String nom_produit = resultSet.getString("nom_produit");
        float prix = resultSet.getFloat("prix");
        String description = resultSet.getString("description");
        int stock = resultSet.getInt("stock");
        int rating = resultSet.getInt("rating");
        String img = resultSet.getString("img");
        Categorie category = new Categorie(resultSet.getInt("id"), resultSet.getString("nom_produit"));

        Produit produit = new Produit(id, nom_produit, prix, description, stock, rating, img, categorie);
        produitList.add(produit);
    }
    return produitList;
}



}
