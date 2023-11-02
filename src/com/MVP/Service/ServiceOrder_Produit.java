/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.Order_Produit;
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
public class ServiceOrder_Produit implements IService<Order_Produit> {

    private Connection con;
    private Statement ste;

    public ServiceOrder_Produit() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Order_Produit c) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `order_produit` (`id`, `order_id`, `produit_id`) VALUES (NULL, '" + c.getOrder_id() + "', '" + c.getProduit_id() + "');";
        ste.executeUpdate(requeteInsert);
    }
    public void ajouter1(Order_Produit c) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `order_produit` ( `order_id`, `produit_id`) VALUES ( ?, ?);");
    pre.setInt(1, c.getOrder_id());
    pre.setInt(2, c.getProduit_id());
    pre.executeUpdate();
    }       

    @Override
    public boolean delete(Order_Produit c) throws SQLException {
        PreparedStatement pre=con.prepareStatement("DELETE FROM `order_produit` WHERE `order_id`= ? AND `produit_id`= ? ");
        pre.setInt(1,c.getOrder_id());
        pre.setInt(2,c.getProduit_id());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public boolean update(Order_Produit c) throws SQLException {
       PreparedStatement pre=con.prepareStatement("UPDATE  `order_produit` SET `order_id`= ?, `produit_id`= ? WHERE `id`= ?;");
    pre.setInt(1, c.getOrder_id());
    pre.setInt(2, c.getProduit_id());
    pre.setInt(3, c.getId());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public List<Order_Produit> readAll() throws SQLException {
    List<Order_Produit> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from `order_produit` ORDER BY order_id; ");
     while (rs.next()) {                
               int order_id=rs.getInt("order_id");
               int produit_id=rs.getInt("produit_id");
               Order_Produit p=new Order_Produit( order_id, produit_id);
     arr.add(p);
     }
    return arr;
    }
    
    public List<Order_Produit> displayClause(String cl) throws SQLException {
        String requeteInsert = "Select * from `order_produit` "+cl+" ORDER BY order_id;";
        List<Order_Produit> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(new Order_Produit(rs.getInt("order_id"),rs.getInt("produit_id")));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public List<String> getProduit_ids() throws SQLException {
        String requeteInsert = "Select DISTINCT produit_id from `order` ";
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
        ResultSet rs=ste.executeQuery("select COUNT(*) from `order_produit`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Count(String Clause) throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `order_produit` "+Clause);
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Sum() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select Sum(order_id) from `order_produit`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
}
