/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;


import com.MVP.Entite.Reclamation;
import com.MVP.Entite.Statut;
import com.MVP.Utils.DataBase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author winxspace
 */
public class ServiceReclamation {

Connection conn;
    public ServiceReclamation(){
        conn=DataBase.getInstance().getConnection();
    }
    
    public void ajouter(Reclamation t) {
        try{String query="INSERT INTO `reclamation`"
                        + "(`titre_rec`, `type_rec`,"
                        + " `date_rec`, `contenu_rec`, `statut_rec`,"
                        + " `username`) VALUES "
                        + "('"+t.getTitre_rec()+"',"
                        + "'"+t.getType_rec()+"','"+t.getDate_rec()+"',"
                        + "'"+t.getContenu_rec()+"','"+t.getStatut_rec()+"',"
                        +"'"+t.getUsername()+"')";
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        } 
        catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
   
    public void modifier(Reclamation t, int id) {
        try {
            String query="UPDATE `reclamation` SET `titre_rec`='"+t.getTitre_rec()+"',"
                    + "`type_rec`='"+t.getType_rec()+"',"
                    + "`date_rec`='"+t.getDate_rec()+"',"
                    + "`contenu_rec`='"+t.getContenu_rec()+"',"
                    + "`statut_rec`='"+t.getStatut_rec()+"',"
                    + "`username`='"+t.getUsername()+"' WHERE id="+id;
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
public Statut retrieveStatutById(int statutId) {
    Statut statut = null;
    try {
        String qry = "SELECT * FROM statut WHERE id = ?";
        System.out.println(qry);
        conn = DataBase.getInstance().getConnection();
        PreparedStatement stm = conn.prepareStatement(qry);
        stm.setInt(1, statutId);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String libelle = rs.getString("libelle");
            statut = Statut.valueOf(rs.getString("libelle"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return statut;
}
public ObservableList<Reclamation> getAllTriTitre() {
    ObservableList<Reclamation> list = FXCollections.observableArrayList();
    try {
        String qry = "SELECT * FROM reclamation ORDER BY titre_rec";
        System.out.println(qry);
        conn = DataBase.getInstance().getConnection();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(qry);
        while (rs.next()) {
            String titre_rec = rs.getString("titre_rec");
            String type_rec = rs.getString("type_rec");
            Date date_rec = rs.getDate("date_rec");
            String contenu_rec = rs.getString("contenu_rec");
            int statut_rec_id = rs.getInt("statut_rec"); // Assuming statut_rec is an integer representing the ID of the Statut
            // Retrieve the Statut object based on the statut_rec_id, e.g., using a DAO or any other means
            Statut statut_rec = retrieveStatutById(statut_rec_id); // Update this line with your actual method to retrieve Statut object
            String username = rs.getString("username");
            Reclamation a = new Reclamation(titre_rec, type_rec, date_rec, contenu_rec, statut_rec, username);
            list.add(a);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return list;
}




    public void supprimer(int id) throws Exception {
        try {
            String query="DELETE FROM `reclamation` WHERE id="+id;
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Reclamation> afficher() {
 List<Reclamation> lr=new ArrayList<>();
        try {
            String query="SELECT * FROM `reclamation`";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Reclamation r=new Reclamation();
                r.setDate_rec(rs.getDate("date_rec"));
                r.setContenu_rec(rs.getString("contenu_rec"));
                r.setUsername(rs.getString("username"));
                r.setType_rec(rs.getString("type_rec"));
                r.setTitre_rec(rs.getString("titre_rec"));
                r.setStatut_rec(Statut.valueOf(rs.getString("statut_rec")));
                r.setId(rs.getInt("id"));
                lr.add(r);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;    }
    public Reclamation getReclamationById(int id) {
    try {
        String req = "SELECT * FROM reclamation WHERE id=?";
        PreparedStatement pst = conn.prepareStatement(req);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Reclamation r = new Reclamation();
            r.setId(rs.getInt("id"));
            r.setTitre_rec(rs.getString("titre_rec"));
            r.setType_rec(rs.getString("type_rec"));
            r.setDate_rec(rs.getDate("date_rec"));
            r.setContenu_rec(rs.getString("contenu_rec"));
            r.setStatut_rec(Statut.valueOf(rs.getString("statut_rec")));
            r.setUsername(rs.getString("username"));
            return r;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}
    public List<String> getAllTitre(){
        List<String> ln=afficher().stream().map(tr->tr.getTitre_rec()).collect(Collectors.toList());
        return ln;
    }
    public int getReclamationByTitre(String titre){
        Reclamation t= afficher().stream().filter(tr->tr.getTitre_rec().equals(titre)).findFirst().orElse(null);
        if(t!=null){
            return t.getId();
        }
        else{
            return 0;
        }
    }
    public List<Reclamation> search(String fieldName, String value) {
        List<Reclamation> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM `reclamation` WHERE " + fieldName + " LIKE ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, "%" + value + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setDate_rec(rs.getDate("date_rec"));
                r.setContenu_rec(rs.getString("contenu_rec"));
                r.setUsername(rs.getString("username"));
                r.setType_rec(rs.getString("type_rec"));
                r.setTitre_rec(rs.getString("titre_rec"));
                r.setStatut_rec(Statut.valueOf(rs.getString("statut_rec")));
                r.setId(rs.getInt("id"));
                result.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
   
    
    
    
    
    
    public List<Reclamation> affichage_trie() {
 List<Reclamation> lr=new ArrayList<>();
        try {
            String query="SELECT * FROM `reclamation` ORDER BY  username" ;
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Reclamation r=new Reclamation();
                r.setDate_rec(rs.getDate("date_rec"));
                r.setContenu_rec(rs.getString("contenu_rec"));
                r.setUsername(rs.getString("username"));
                r.setType_rec(rs.getString("type_rec"));
                r.setTitre_rec(rs.getString("titre_rec"));
                r.setStatut_rec(Statut.valueOf(rs.getString("statut_rec")));
                r.setId(rs.getInt("id"));
                lr.add(r);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;    }
}
    
