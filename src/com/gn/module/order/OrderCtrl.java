/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.order;

import com.MVP.Entite.Order;
import com.MVP.Service.ServiceOrder;
import com.MVP.Service.SendMail;
import com.gn.global.util.Alerts;
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
public class OrderCtrl implements Initializable{
    
    @FXML private TableView<Order> tableView;
    @FXML private TableColumn<String, Order> c1;
    @FXML private TableColumn<String, Order> c2;
    @FXML private TableColumn<String, Order> c3;
    @FXML private TableColumn<String, Order> c4;
    @FXML private TableColumn<String, Order> c5;
    
    @FXML private TextField total;
    @FXML private TextField payment_id;
    @FXML private ComboBox status;
    
    @FXML private TextField totalS;
    
    @FXML private Label countC;
    
    @FXML private Label capTotal;
    @FXML private Label capUsed;
    @FXML private Text err;
    
    
    @FXML private BarChart barChart;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
        ServiceOrder ser = new ServiceOrder();
        List<Order> list = ser.readAll();
        ObservableList<Order> cls = FXCollections.observableArrayList();
        ObservableList<String> cls2 = FXCollections.observableArrayList();
        ObservableList<String> cls3 = FXCollections.observableArrayList();
        ObservableList<String> cls4 = FXCollections.observableArrayList();
        ObservableList<String> cls6 = FXCollections.observableArrayList();
        
        for (Order aux : list)
        {
          cls.add(new Order(aux.getId(),aux.getTotal(), aux.getStatus(), aux.getPayment_id()));  
          cls3.add(Integer.toString(aux.getId()));
        }

        
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("total"));
        c3.setCellValueFactory(new PropertyValueFactory<>("status"));
        c4.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        
        tableView.setItems(cls);
        
        cls2.add("Pending");
        cls2.add("Confirmed");
        cls2.add("Cancelled");
        
        status.setItems(cls2); 
        updateStats();
        
        //test
        
        
        }
        catch (SQLException ex) {
                    System.out.println(ex);
                 }
    }
        
    @FXML
    public void Add(){
        
        try {
            if(status.getSelectionModel().getSelectedItem() != null && total.getText() != "") {

                 float Tot= (float) Float.valueOf(total.getText());
                
                 String Status= (String) status.getSelectionModel().getSelectedItem().toString();
                 
                 String PaymentID= (String) payment_id.getText();
                if(Status != null && Tot > 0) {
                 ServiceOrder ser = new ServiceOrder();
                 Order C1 = new Order(Tot, Status, PaymentID);
                 
                    ser.ajouter1(C1);
                     List<Order> list = ser.readAll();
                     ObservableList<Order> cls = FXCollections.observableArrayList();
                     for (Order aux : list)
                     {
                      cls.add(new Order(aux.getId(),aux.getTotal(), aux.getStatus(), aux.getPayment_id())); 
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.success("Ajout", "Ordere Ajouté.");
                     err.setText("");
                } else {
                     err.setText("Please verify your inputs.");
                }
            } else {
                err.setText("Please verify your inputs.");
            }
                 
                 
                     

                    
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        
    }
    
    @FXML
    public void Update(){
        if(tableView.getSelectionModel().getSelectedItem() != null){
        try {
            if(status.getSelectionModel().getSelectedItem() != null && total.getText() != "") {

                 float Tot= (float) Float.valueOf(total.getText());
                
                 String Status= (String) status.getSelectionModel().getSelectedItem().toString();
                 
                 String PaymentID= (String) payment_id.getText();
                 ServiceOrder ser = new ServiceOrder();
                 Order tmp2=tableView.getSelectionModel().getSelectedItem();
                 if(tmp2 != null && Status != null && Tot >0) {
                  Order C1 = new Order(tmp2.getId(),Tot, Status, PaymentID);
                 
                    ser.update(C1);
                     List<Order> list = ser.readAll();
                     ObservableList<Order> cls = FXCollections.observableArrayList();
                     for (Order aux : list)
                     {
                      cls.add(new Order(aux.getId(),aux.getTotal(), aux.getStatus(), aux.getPayment_id()));  
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.info("Modification", "Ordre Modifié.");
                     err.setText("");
                } else {
                    err.setText("Please verify your inputs.");
                }
            } else {
                err.setText("Please verify your inputs.");
            }
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        } else {
          err.setText("Please select an Order.");
        }
        
        
    }
    
    @FXML
    public void Delete(){
        if(tableView.getSelectionModel().getSelectedItem()!=null){
        try {
                 
                 
                 ServiceOrder ser = new ServiceOrder();
                 Order tmp2=tableView.getSelectionModel().getSelectedItem();
                 Order C1 = new Order(tmp2.getId(),tmp2.getTotal(), tmp2.getStatus(), tmp2.getPayment_id());
                    ser.delete(C1);
                     List<Order> list = ser.readAll();
                     ObservableList<Order> cls = FXCollections.observableArrayList();
                     ObservableList<String> cls3 = FXCollections.observableArrayList();
                     for (Order aux : list)
                     {
                      cls.add(new Order(aux.getId(),aux.getTotal(), aux.getStatus(), aux.getPayment_id())); 
                      cls3.add(Integer.toString(aux.getId()));
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.error("Suppression", "Ordere Supprimé.");
                     err.setText("");
                    
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        } else {
          err.setText("Please select an Order.");
        }

        
    }
    
    
    
    @FXML
    public void Assign(){
       

        
    }
    
    @FXML
    public void Remove(){
        
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
                 
                 
                 ServiceOrder ser = new ServiceOrder();
                     List<Order> list = ser.displayClause(" WHERE status LIKE '%"+totalS.getText()+"%' or payment_id LIKE '%"+totalS.getText()+"%' or id LIKE '%"+totalS.getText()+"%' ");
                     ObservableList<Order> cls = FXCollections.observableArrayList();
                     for (Order aux : list)
                     {
                      cls.add(new Order(aux.getId(),aux.getTotal(), aux.getStatus(), aux.getPayment_id()));  
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
        ServiceOrder se=new ServiceOrder();
        try
        {
        List<Order> list = se.readAll();
                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(FILE));
                        document.open();
                        pdf.addMetaData(document);
                        pdf.addTitlePage(document, list);
                        document.close();
        }
        catch (SQLException ex) {
                    System.out.println(ex);
                 }
        
    }
    @FXML
    public void sendMAIL(){
        SendMail sm=new SendMail();
        ServiceOrder se=new ServiceOrder();
        try
        {
        List<Order> list = se.readAll();
        sm.sendMail("yosri.kossontini@esprit.tn","Orderes",list.toString());
        }
        catch (SQLException ex) {
                    System.out.println(ex);
                 }
        
    }
    
    @FXML
    public void Excel()
    {
        
    }
    
    public void updateStats(){
        ServiceOrder sc=new ServiceOrder();
        
        
        try{
        List<String>brs=sc.getStatuss();
        //Order Stats
        int CountC=sc.Count();
        int sumOrderes=sc.Sum();
        int confirmed=sc.Count("where status='Confirmed'");
        float ratio = (float) confirmed/CountC;
        
        int cntBr=0;        
        List<XYChart.Series<String, Number>> lst=new ArrayList();
        

        for (int i=0;i<brs.size();i++)
        {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            cntBr=sc.Count("WHERE status='"+brs.get(i)+"'");
            series.setName(brs.get(i));
            series.getData().add(new XYChart.Data<>(String.valueOf(i+1), cntBr));
            lst.add(series);
        }
        barChart.getData().setAll(lst);
        
        countC.setText(String.valueOf(CountC));
        capTotal.setText(String.valueOf(sumOrderes));
        capUsed.setText(String.valueOf(ratio));
        }catch(SQLException ex){
            System.out.print(ex);
        }
    }
        
}
