/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.Reservation;
import com.MVP.Entite.Evenement;
import com.MVP.Entite.User;
import com.MVP.Utils.JavaMailUtil;
import com.MVP.Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Hassène
 */
public class ReservationService {

    private Connection con = DataBase.getInstance().getConnection();

    public void ajouter(Reservation E) throws SQLException { // NE9SA DATE
        String requete = "INSERT INTO reservation (date,id_membre,id_evenement) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(requete);

        pst.setString(1, E.getDate());
        pst.setInt(2, E.getRes_user().getId());
        pst.setInt(3, E.getRes_evenement().getId());

        pst.executeUpdate();
        System.out.println("Réservation ajoutée avec succès !");
        //Send email to user
        String subject = "Réservation d'événement";
        //Variable body contains the reservation's details
        String body= E.getRes_user().getPseudo() + " a réservé l'événement " + E.getRes_evenement().getNom() + " le " + E.getDate();
        System.out.println("Sending mail...");
        try{
            JavaMailUtil.SendReservationEmail(E.getRes_user().getEmail(), subject, body);
        }
        catch(Exception e){
            System.out.println("///////////////// Error sending mail /////////////// /n" + e.getMessage());
        }
    
    
    }

    public void update(Reservation E) throws SQLException {
        String requete = "UPDATE reservation SET date=?, id_membre=?, id_evenement=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setString(1, E.getDate());
        pst.setInt(2, E.getRes_user().getId());
        pst.setInt(3, E.getRes_evenement().getId());
        pst.setInt(4, E.getId());
        pst.executeUpdate();
        // COMPLETE THIS METHOD
        System.out.println("Réservation modifiée avec succès !");
    }

    public void delete(int id) throws SQLException {
        String requete = "DELETE FROM reservation WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Réservation supprimée avec succès !");
    }

    public ArrayList<Reservation> afficherAll() throws SQLException {
        ArrayList<Reservation> ReservationList = new ArrayList<>();
        String requete = "SELECT r.*, u.id AS user_id, u.pseudo, u.email, " +
                "e.id AS evenement_id, e.nom, e.date, e.description, " +
                "e.duree, e.capacite, e.type, e.image " +
                "FROM reservation r " +
                "JOIN user u ON r.id_membre = u.id " +
                "JOIN evenement e ON r.id_evenement = e.id";
        PreparedStatement pst = con.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getInt("id"));
            reservation.setDate(rs.getString("date"));

            // Fetch User object from ResultSet
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setPseudo(rs.getString("pseudo"));
            user.setEmail(rs.getString("email"));
            reservation.setRes_user(user);

            // Fetch Evenement object from ResultSet
            Evenement evenement = new Evenement();
            evenement.setId(rs.getInt("evenement_id"));
            evenement.setNom(rs.getString("nom"));
            evenement.setDate(rs.getString("date"));
            evenement.setDescription(rs.getString("description"));
            evenement.setDuree(rs.getInt("duree"));
            evenement.setCapacite(rs.getInt("capacite"));
            evenement.setType(rs.getString("type"));
            evenement.setImage(rs.getString("image"));
            reservation.setRes_evenement(evenement);

            ReservationList.add(reservation);
        }
        return ReservationList;
    }

    public User getUserById(int id) throws SQLException {
        User user = new User();
        String requete = "SELECT * FROM user WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setPseudo(rs.getString("pseudo"));
            user.setEmail(rs.getString("email"));
        }
        return user;
    }

    public Evenement getEvenementById(int id) throws SQLException {
        Evenement evenement = new Evenement();
        String requete = "SELECT * FROM evenement WHERE id=?";
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
        return evenement;
    }

    public Reservation getReservationById(int id) throws SQLException {
        Reservation reservation = new Reservation();
        String requete = "SELECT * FROM reservation WHERE id=?";
        PreparedStatement pst = con.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            reservation.setId(rs.getInt("id"));
            reservation.setDate(rs.getString("date"));

            // Fetch User object from ResultSet
            User user = this.getUserById(rs.getInt("id_membre"));
            reservation.setRes_user(user);
            // Fetch Evenement object from ResultSet
            Evenement evenement = getEvenementById(rs.getInt("id_evenement"));
            reservation.setRes_evenement(evenement);
        }
        return reservation;

    }

    public void reserver(User user, Evenement evenement)
    {
        Reservation reservation = new Reservation();
        reservation.setRes_user(user);
        reservation.setRes_evenement(evenement);
        //Set date to now
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        reservation.setDate(strDate);
        try {
            this.ajouter(reservation);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
        //Checks if reservation is made by the user
    public boolean isReserved(User user, Evenement event){
        boolean isFound = false;
        try {
            ArrayList<Reservation> reservations = this.afficherAll();
            for (Reservation reservation : reservations) {
                if (reservation.getRes_user().getId() == user.getId() && reservation.getRes_evenement().getId() == event.getId()) {
                    isFound = true;
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isFound;
        
    }

}
