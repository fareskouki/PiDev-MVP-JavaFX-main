/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.User;
import com.MVP.Utils.DataBase;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author Hassène
 */
public class UserService {
    private Connection con = DataBase.getInstance().getConnection();

   /*  public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> UserList = new ArrayList<>();
        String requete = "SELECT * FROM user";
        PreparedStatement pst = con.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setPseudo(rs.getString("pseudo"));
            user.setEmail(rs.getString("email"));
            
            UserList.add(user);
        }
        return UserList;
    }*/
    public User getUserById(int id){
        User user = new User();
        String requete = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    public void signUp(User user) {
        String query = "INSERT INTO user (email,roles,password,pseudo,addresse,date_naissance,disable_token,activation_token,reset_token) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRoles());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPseudo());
            statement.setString(5, user.getAddresse());
            
            user.getDate_naissance();
            //Convert the date to a SQL date
            java.sql.Date sqlDate = java.sql.Date.valueOf(user.getDate_naissance());
            statement.setDate(6, sqlDate);
            statement.setString(7, user.getDisable_token());
            statement.setString(8, user.getActivation_token());
            statement.setString(9, user.getReset_token());
            statement.executeUpdate();
            System.out.println("ok");
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public void deleteAccount(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("ok");
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    /// Activer un utilisateur
    public void enableUser(User user, int id) {
        String query = "UPDATE user SET status = ? WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, "enable");
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Désactiver un utilisateur
    public void disableUser(User user, int id) {
        String query = "UPDATE user SET status = ? WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, "desable");
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    // Aficher tous les utilisateurs
    public ArrayList<User> getAllUsers() {
        //Returns an ObservableList of all User objects in the database
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setRoles(rs.getString("roles"));
                user.setPassword(rs.getString("password"));
                user.setPseudo(rs.getString("pseudo"));
                user.setAddresse(rs.getString("addresse"));
                Date date=rs.getDate("date_naissance");
                //Convert the SQL date to a String foramtted dd-MM-yyyy
                String dateString = date.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                user.setDate_naissance(dateString);
                user.setDisable_token(rs.getString("disable_token"));
                user.setActivation_token(rs.getString("activation_token"));
                user.setReset_token(rs.getString("reset_token"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return users;
    }

    //Verifies the login details, returns true if they match an entry in the database
    public boolean login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if(rs.getString("disable_token").equals("Disabled") || rs.getString("activation_token")!=null){
                    return false;
                }
              
                //Check if password matches hashed password in database
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return true;
                }
                else
                    return false;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;

        
    }


    public String getRole(String username) {
        String query = "SELECT roles FROM user WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("roles");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    //Checks if user is already registered by email
    public boolean CheckUserRegistered(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public boolean verifyActivationToken(String token) {
        String query = "SELECT * FROM user WHERE activation_token = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public void verifyAccount(User user)
    {
        //Set activation_token to null and disable_token to "Enabled"
        String query = "UPDATE user SET activation_token = ?, disable_token = ? WHERE activation_token = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, null);
            statement.setString(2, "Enabled");
            statement.setString(3, user.getActivation_token());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }

    public User getUserByEmail(String email)
    {
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setRoles(rs.getString("roles"));
                user.setPassword(rs.getString("password"));
                user.setPseudo(rs.getString("pseudo"));
                user.setAddresse(rs.getString("addresse"));
                Date date=rs.getDate("date_naissance");
                //Convert the SQL date to a String foramtted dd-MM-yyyy
                String dateString = date.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                user.setDate_naissance(dateString);
                user.setDisable_token(rs.getString("disable_token"));
                user.setActivation_token(rs.getString("activation_token"));
                user.setReset_token(rs.getString("reset_token"));
                return user;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public boolean checkIfPseudoExists(String pseudo){
        String query = "SELECT * FROM user WHERE pseudo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }


}
