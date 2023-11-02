/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.Cart_Produit;
import com.MVP.IService.IService;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import com.MVP.Utils.DataBase;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author House
 */
public class ServiceCart_Produit implements IService<Cart_Produit> {

    private Connection con;
    private Statement ste;

    public ServiceCart_Produit() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Cart_Produit c) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `cart_produit` (`id`, `cart_id`, `produit_id`) VALUES (NULL, '" + c.getCart_id() + "', '" + c.getProduit_id() + "');";
        ste.executeUpdate(requeteInsert);
    }
    public void ajouter1(Cart_Produit c) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `cart_produit` ( `cart_id`, `produit_id`) VALUES ( ?, ?);");
    pre.setInt(1, c.getCart_id());
    pre.setInt(2, c.getProduit_id());
    pre.executeUpdate();
    }       

    @Override
    public boolean delete(Cart_Produit c) throws SQLException {
        PreparedStatement pre=con.prepareStatement("DELETE FROM `cart_produit` WHERE `cart_id`= ? AND `produit_id`= ? ");
        pre.setInt(1,c.getCart_id());
        pre.setInt(2,c.getProduit_id());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public boolean update(Cart_Produit c) throws SQLException {
       
    return false;
    }

    @Override
    public List<Cart_Produit> readAll() throws SQLException {
    List<Cart_Produit> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from `cart_produit` ORDER BY cart_id; ");
     while (rs.next()) {                
               int cart_id=rs.getInt("cart_id");
               int produit_id=rs.getInt("produit_id");
               Cart_Produit p=new Cart_Produit( cart_id, produit_id);
     arr.add(p);
     }
    return arr;
    }

public Cart_Produit readOne(int cID, int pID) throws SQLException {
    Cart_Produit p = new Cart_Produit();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from `cart_produit` WHERE produit_id="+pID+" and cart_id="+cID);
     while (rs.next()) {                
               
                p=new Cart_Produit(cID,pID);
     }
     return p;

    }
    
    public List<Cart_Produit> displayClause(String cl) throws SQLException {
        String requeteInsert = "Select * from `cart_produit` "+cl+" ORDER BY cart_id;";
        List<Cart_Produit> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(new Cart_Produit(rs.getInt("cart_id"),rs.getInt("produit_id")));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public List<String> getProduit_ids() throws SQLException {
        String requeteInsert = "Select DISTINCT produit_id from `cart` ";
        List<String> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(String.valueOf(rs.getInt("produit_id")));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public int Count() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `cart_produit`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Count(String Clause) throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `cart_produit` "+Clause);
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Sum() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select Sum(cart_id) from `cart_produit`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
}
