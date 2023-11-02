/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVP.Service;

import com.MVP.Entite.Order;
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
public class ServiceOrder implements IService<Order> {

    private Connection con;
    private Statement ste;

    public ServiceOrder() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Order c) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `order` (`id`, `total`, `status`, `payment_id`) VALUES (NULL, '" + c.getTotal() + "', '" + c.getStatus() + "', '" + c.getPayment_id() + "');";
        ste.executeUpdate(requeteInsert);
    }
    public void ajouter1(Order c) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `order` ( `total`, `status`) VALUES ( ?, ?);");
    pre.setFloat(1, c.getTotal());
    pre.setString(2, c.getStatus());
    pre.executeUpdate();
    }       

    @Override
    public boolean delete(Order c) throws SQLException {
        PreparedStatement pre=con.prepareStatement("DELETE FROM `order` WHERE `id`= ?");
        pre.setInt(1,c.getId());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public boolean update(Order c) throws SQLException {
       PreparedStatement pre=con.prepareStatement("UPDATE  `order` SET `total`= ?, `status`= ? WHERE `id`= ?;");
    pre.setFloat(1, c.getTotal());
    pre.setString(2, c.getStatus());
    pre.setInt(3, c.getId());
    if(pre.executeUpdate()==1)
        return true;
    return false;
    }

    @Override
    public List<Order> readAll() throws SQLException {
    List<Order> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from `order` ORDER BY total");
     while (rs.next()) {                
               int id=rs.getInt(1);
               float total=rs.getFloat("total");
               String status=rs.getString("status");
               String payment_id=rs.getString("payment_id");
               Order p=new Order(id, total, status, payment_id);
     arr.add(p);
     }
    return arr;
    }
    
    public List<Order> displayClause(String cl) throws SQLException {
        String requeteInsert = "Select * from `order` "+cl+" ORDER BY total;";
        List<Order> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(new Order(rs.getInt("id"),rs.getFloat("total"),rs.getString("status"),rs.getString("payment_id")));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public List<String> getStatuss() throws SQLException {
        String requeteInsert = "Select DISTINCT status from `order` ";
        List<String> clss=new ArrayList<>();
        ste = con.prepareStatement(requeteInsert);
        ResultSet rs=ste.executeQuery(requeteInsert);
        while(rs.next())
        {
            clss.add(rs.getString("status"));
            System.out.println(clss.isEmpty());
        }
        return clss;
    }
    
    public int Count() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `order`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Count(String Clause) throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select COUNT(*) from `order` "+Clause);
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
    
    public int Sum() throws SQLException{
        int count=0;
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select Sum(total) from `order`");
        while(rs.next())
            count=rs.getInt(1);
        return count;
    }
}
