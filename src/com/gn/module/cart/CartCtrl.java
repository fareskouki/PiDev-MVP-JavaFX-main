/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.cart;

import java.sql.Date;
import com.MVP.Entite.Cart;
import com.MVP.Service.ServiceCart;
;
import com.MVP.Entite.Cart_Produit;
import com.MVP.Service.ServiceCart_Produit;
import com.MVP.Service.SendMail;
import com.gn.global.util.Alerts;
import com.gn.module.controls.Person;
import static com.itextpdf.text.Annotation.FILE;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.chart.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author MrStealYourMom
 */
public class CartCtrl implements Initializable{
    
    @FXML private TableView<Cart> tableView;
    @FXML private TableColumn<String, Cart> c1;
    @FXML private TableColumn<String, Cart> c2;
    @FXML private TableColumn<String, Cart> c3;
    @FXML private TableColumn<String, Cart> c4;
    @FXML private TableColumn<String, Cart> c5;

    @FXML private TableView<Cart_Produit> tableView1;
    @FXML private TableColumn<String, Cart_Produit> c11;
    @FXML private TableColumn<String, Cart_Produit> c12;

    
    
    @FXML private TextField totalS;
    
    @FXML private Label countC;
    @FXML private Text err;
    
    @FXML private Label capTotal;
    @FXML private Label capUsed;
    
    @FXML private BarChart barChart;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
        ServiceCart ser = new ServiceCart();
        List<Cart> list = ser.readAll();
        ObservableList<Cart> cls = FXCollections.observableArrayList();
        ObservableList<String> cls2 = FXCollections.observableArrayList();
        ObservableList<String> cls3 = FXCollections.observableArrayList();
        ObservableList<String> cls4 = FXCollections.observableArrayList();
        ObservableList<String> cls6 = FXCollections.observableArrayList();
        
        for (Cart aux : list)
        {
          cls.add(new Cart(aux.getId(),aux.getPrix(), aux.getQuantite()));  
          cls3.add(Integer.toString(aux.getId()));
        }

        
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        c3.setCellValueFactory(new PropertyValueFactory<>("prix"));
        c4.setCellValueFactory(new PropertyValueFactory<>("id"));

        
        c11.setCellValueFactory(new PropertyValueFactory<>("cart_id"));
        c12.setCellValueFactory(new PropertyValueFactory<>("produit_id"));
        
        tableView.setItems(cls);
        updateStats();
        
        //test
        
        
        }
        catch (SQLException ex) {
                    System.out.println(ex);
                 }
    }
        
    @FXML
    public void Update(){
        Cart tmp=new Cart(); 
        if(tableView.getSelectionModel().getSelectedItem() != null){
        try {
                 
                 
                 ServiceCart ser = new ServiceCart();
                 Cart tmp2=tableView.getSelectionModel().getSelectedItem();
                 Cart C1 = new Cart(tmp2.getId(),(tmp2.getPrix()/tmp2.getQuantite())*(tmp2.getQuantite()+1), tmp2.getQuantite()+1);                 
                    ser.update(C1);
                     List<Cart> list = ser.readAll();
                     ObservableList<Cart> cls = FXCollections.observableArrayList();
                     for (Cart aux : list)
                     {
                      cls.add(new Cart(aux.getId(),aux.getPrix(), aux.getQuantite()));  
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.info("Modification", "Carte Modifié.");
                    err.setText("");

                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        } else {
            err.setText("Please select an Item.");
        }
        
        
    }
    
    @FXML
    public void Delete(){
        Cart tmp=new Cart(); 
        if(tableView.getSelectionModel().getSelectedItem() != null){
        try {
                 
                 
                 ServiceCart ser = new ServiceCart();
                 Cart tmp2=tableView.getSelectionModel().getSelectedItem();
                 Cart C1 = new Cart(tmp2.getId(),tmp2.getPrix(), tmp2.getQuantite());
                    ser.delete(C1);
                     List<Cart> list = ser.readAll();
                     ObservableList<Cart> cls = FXCollections.observableArrayList();
                     ObservableList<String> cls3 = FXCollections.observableArrayList();
                     for (Cart aux : list)
                     {
                      cls.add(new Cart(aux.getId(),aux.getPrix(), aux.getQuantite())); 
                      cls3.add(Integer.toString(aux.getId()));
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.error("Suppression", "Carte Supprimé.");
                    err.setText("");

                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        }else {
            err.setText("Please select an Item.");
        }
        
    }
    
    
    
    @FXML
    public void Assign(){
       

        
    }
    
    @FXML
    public void Remove(){
       Cart tmp=new Cart(); 
        if(tableView.getSelectionModel().getSelectedItem()!= null){
        try {
                 
                 
                 ServiceCart ser = new ServiceCart();
                 Cart tmp2=tableView.getSelectionModel().getSelectedItem();
                  Cart C1 = new Cart(tmp2.getId(),(tmp2.getPrix()/tmp2.getQuantite())*(tmp2.getQuantite()-1), tmp2.getQuantite()-1);

                 if(tmp2.getQuantite() > 1) {
                 
                    ser.update(C1);
                     List<Cart> list = ser.readAll();
                     ObservableList<Cart> cls = FXCollections.observableArrayList();
                     for (Cart aux : list)
                     {
                      cls.add(new Cart(aux.getId(),aux.getPrix(), aux.getQuantite()));  
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.info("Modification", "Carte Modifié.");
                     err.setText("");

                    
                    } else {
                    ser.delete(C1);
                     List<Cart> list = ser.readAll();
                     ObservableList<Cart> cls = FXCollections.observableArrayList();
                     ObservableList<String> cls3 = FXCollections.observableArrayList();
                     for (Cart aux : list)
                     {
                      cls.add(new Cart(aux.getId(),aux.getPrix(), aux.getQuantite())); 
                      cls3.add(Integer.toString(aux.getId()));
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.error("Suppression", "Carte Supprimé.");
                     err.setText("");

                    }
                 }
                
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        } else {
            err.setText("Please select an Item.");
        }
    }
    
    @FXML
    public void Assign1(){
        
    }
    
    @FXML
    public void Remove1(){
       

    }
    @FXML
    public void Search(){
       
        try {
                 
                 
                 ServiceCart ser = new ServiceCart();
                     List<Cart> list = ser.displayClause(" WHERE status LIKE '%"+totalS.getText()+"%' or payment_id LIKE '%"+totalS.getText()+"%' or id LIKE '%"+totalS.getText()+"%' ");
                     ObservableList<Cart> cls = FXCollections.observableArrayList();
                     for (Cart aux : list)
                     {
                      cls.add(new Cart(aux.getId(),aux.getPrix(), aux.getQuantite()));  
                     }
                     tableView.setItems(cls);
                    
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        }
    
    @FXML
    public void Search2(){
       
        }
    
     @FXML
    public void Search3(){
       
        }
    
    @FXML
    public void pdf() throws FileNotFoundException, DocumentException{
        
        
    }
    @FXML
    public void sendMAIL(){
        SendMail sm=new SendMail();
        ServiceCart se=new ServiceCart();
        try
        {
        List<Cart> list = se.readAll();
        sm.sendMail("yosri.kossontini@esprit.tn","Cartes",list.toString());
        }
        catch (SQLException ex) {
                    System.out.println(ex);
                 }
        
    }
    
    @FXML
    public void Excel()
    {
        
    }
    @FXML
    public void products()
    {
        try{
            ServiceCart_Produit ser = new ServiceCart_Produit();
            List<Cart_Produit> list = ser.displayClause("WHERE cart_id = '"+tableView.getSelectionModel().getSelectedItem().getId()+"'");

            ObservableList<Cart_Produit> cls = FXCollections.observableArrayList();
            for (Cart_Produit aux : list)
                {
                  cls.add(new Cart_Produit(aux.getCart_id(),aux.getProduit_id()));  
                }
            tableView1.setItems(cls);
            
        } catch(SQLException ex) {
                    System.out.println(ex);
                 }


    }
    public void updateStats(){
        ServiceCart sc=new ServiceCart();
        
        
        try{
        List<Integer>brs=sc.getQuantity();
        //Cart Stats
        int CountC=sc.Count();
        int sumCartes=sc.Sum();
        int confirmed=sc.Count();
        float ratio = (float) confirmed/CountC;
        
        int cntBr=0;        
        List<XYChart.Series<String, Number>> lst=new ArrayList();
        

        for (int i=0;i<brs.size();i++)
        {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            cntBr=sc.Count("WHERE quantite='"+brs.get(i)+"'");
            series.setName(String.valueOf(brs.get(i)));
            series.getData().add(new XYChart.Data<>(String.valueOf(i+1), cntBr));
            lst.add(series);
        }
        barChart.getData().setAll(lst);
        
        countC.setText(String.valueOf(CountC));
        capTotal.setText(String.valueOf(sumCartes));
        capUsed.setText(String.valueOf(ratio));
        }catch(SQLException ex){
            System.out.print(ex);
        }
    }
        
}
