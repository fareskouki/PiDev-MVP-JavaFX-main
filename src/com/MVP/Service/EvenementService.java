/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.Evenement;
import com.MVP.Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Hassène
 */
public class EvenementService {
    
    private Connection con = DataBase.getInstance().getConnection();

    public void ajouter(Evenement E) throws SQLException { 
        String requete = "INSERT INTO evenement (nom, date, description, duree, capacite, type, image) VALUES (?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setString(1, E.getNom());
        pst.setString(2, E.getDate());
        pst.setString(3, E.getDescription());
        pst.setInt(4, E.getDuree());
        pst.setInt(5, E.getCapacite());
        pst.setString(6, E.getType());
        pst.setString(7, E.getImage());
        
        pst.executeUpdate();
        System.out.println("Evenement ajouté avec succès !");
    }
    
    public void update(Evenement E) throws SQLException {
        String requete = "UPDATE evenement SET nom=?, date=?, description=?, duree=?, capacite=?, type=?, image=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
       pst.setString(1, E.getNom());
        pst.setString(2, E.getDate());
        pst.setString(3, E.getDescription());
        pst.setInt(4, E.getDuree());
        pst.setInt(5, E.getCapacite());
        pst.setString(6, E.getType());
        pst.setString(7, E.getImage());
        pst.setInt(8, E.getId());
        pst.executeUpdate();
        
        System.out.println("Evenement modifié avec succès !");
    }

    public void delete(int id) throws SQLException {
        String requete = "DELETE FROM evenement WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Evenement supprimé avec succès !");
    }

    public ArrayList<Evenement> afficherAll() throws SQLException {
        ArrayList<Evenement> EvenementList = new ArrayList<>();
        String requete = "SELECT * FROM evenement";
        PreparedStatement pst = con.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(rs.getInt("id"));
            evenement.setNom(rs.getString("nom"));
            evenement.setDate(rs.getString("date"));
            evenement.setDescription(rs.getString("description"));
            evenement.setCapacite(rs.getInt("capacite"));
            evenement.setDuree(rs.getInt("duree"));
            evenement.setType(rs.getString("type"));
            evenement.setImage(rs.getString("image"));
            EvenementList.add(evenement);
        }
        return EvenementList;
    }

    public Evenement findEvenementById(int id)throws SQLException{
        Evenement evenement = new Evenement();
        String requete = "SELECT * FROM evenement WHERE id=?";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                evenement.setId(rs.getInt("id"));
                evenement.setNom(rs.getString("nom"));
                evenement.setDate(rs.getString("date"));
                evenement.setDescription(rs.getString("description"));
                evenement.setDuree(rs.getInt("duree"));
                evenement.setCapacite(rs.getInt("capacite"));
                evenement.setType(rs.getString("type"));
                evenement.setImage(rs.getString("image"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return evenement;
    
    }
    
    
}
