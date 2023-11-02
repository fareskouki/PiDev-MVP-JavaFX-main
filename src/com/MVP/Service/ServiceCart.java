/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.Cart;
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
public class ServiceCart implements IService<Cart> {

    private Connection con;
    private Statement ste;

    public ServiceCart() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Cart c) throws SQLException {
    }
    public Cart ajouter1(Cart c) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `cart` ( `prix`, `quantite`,`session`,`titre`) VALUES ( ?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
    pre.setFloat(1, c.getPrix());
    pre.setInt(2, c.getQuantite());
    pre.setString(3,"session");
    pre.setString(4, "title");
    pre.executeUpdate();
    ResultSet generatedKeys = pre.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new Cart(
                    generatedKeys.getInt(1),
                    c.getPrix(),
                    c.getQuantite()
                );
            } else {
                return new Cart();
            }
    }       

    @Override
    public boolean delete(Cart c) throws SQLException {
        PreparedStatement pre=con.prepareStatement("DELETE FROM `cart` WHERE `id`= ?");
        pre.setInt(1,c.getId());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public boolean update(Cart c) throws SQLException {
       PreparedStatement pre=con.prepareStatement("UPDATE  `cart` SET `prix`= ?, `quantite`= ? WHERE `id`= ?;");
    pre.setFloat(1, c.getPrix());
    pre.setInt(2, c.getQuantite());
    pre.setInt(3, c.getId());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public List<Cart> readAll() throws SQLException {
    List<Cart> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from `cart` ORDER BY prix");
     while (rs.next()) {                
               int id=rs.getInt(1);
               float prix=rs.getFloat("prix");
               int quantite=rs.getInt("quantite");
               Cart p=new Cart(id, prix, quantite);
     arr.add(p);
     }
    return arr;
    }

public Cart readOne(int cID) throws SQLException {
    Cart p = new Cart();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from `cart` WHERE id="+cID);
     while (rs.next()) {                
               int id=rs.getInt(1);
               float prix=rs.getFloat("prix");
               int quantite=rs.getInt("quantite");
                p=new Cart(id, prix, quantite);
     }
     return p;

    }
    
    public List<Cart> displayClause(String cl) throws SQLException {
        String requeteInsert = "Select * from `cart` "+cl+" ORDER BY prix;";
        List<Cart> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(new Cart(rs.getInt("id"),rs.getFloat("prix"),rs.getInt("quantite")));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public int Count() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `cart`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    public List<Integer> getQuantity() throws SQLException {
        String requeteInsert = "Select DISTINCT quantite from `cart` ";
        List<Integer> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(rs.getInt("quantite"));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public int Count(String Clause) throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `cart` "+Clause);
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Sum() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select Sum(prix) from `cart`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
}
