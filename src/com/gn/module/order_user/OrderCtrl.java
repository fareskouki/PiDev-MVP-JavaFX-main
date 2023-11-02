/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.order_user;

import java.sql.Date;
import com.MVP.Entite.Order;
import com.MVP.Service.ServiceOrder;
;
import com.MVP.Entite.Order_Produit;
import com.MVP.Service.ServiceOrder_Produit;
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
import org.json.JSONObject;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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

    @FXML private TableView<Order_Produit> tableView1;
    @FXML private TableColumn<String, Order_Produit> c11;
    @FXML private TableColumn<String, Order_Produit> c12;

    
    
    @FXML private TextField totalS;
    
    @FXML private Label capTotal;
    @FXML private Label capUsed;
    @FXML private Label countC;
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

        
        c11.setCellValueFactory(new PropertyValueFactory<>("order_id"));
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
        Order tmp=new Order(); 
        if(tableView.getSelectionModel().getSelectedItem() != null){
        try {
                 
                 
                 ServiceOrder ser = new ServiceOrder();
                 Order tmp2=tableView.getSelectionModel().getSelectedItem();
                 Order C1 = new Order(tmp2.getId(),tmp2.getTotal(), "Cancelled", tmp2.getPayment_id());
                 
                    ser.update(C1);
                    ////////////////
                    String ACCOUNT_SID = "ACf24faf4bf4c128ad707d87536532ee4c";
                    String AUTH_TOKEN = "6585f51524df6cfe6f8fcdebba4c7e67";

                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    String recipientNumber = "+21655311029";
                    String message = "Hello Imen ben Atig\n, Your order has been cancelled";

                    Message twilioMessage = Message.creator(
                                new PhoneNumber(recipientNumber),
                                new PhoneNumber("+16206589212"),
                                message)
                                .create();
                    System.out.println("SMS envoyé : " + twilioMessage.getSid());
                    /////////////////////
                     List<Order> list = ser.readAll();
                     ObservableList<Order> cls = FXCollections.observableArrayList();
                     for (Order aux : list)
                     {
                      cls.add(new Order(aux.getId(),aux.getTotal(), aux.getStatus(), aux.getPayment_id()));  
                     }
                     tableView.setItems(cls);
                     updateStats();
                     Alerts.info("Modification", "Ordere Modifié.");
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
    public void Delete(){
        Order tmp=new Order(); 
        if(tableView.getSelectionModel().getSelectedItem() != null){
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
        }  else {
            err.setText("Please select an Order.");
        }
        
    }
    
    
    
    @FXML
    public void Assign(){
       

        
    }
    
    @FXML
    public void Remove(){
       Order_Produit tmp=new Order_Produit(); 
        if(tableView1.getSelectionModel().getSelectedItem() != null){
        try {
                 
                 
                 ServiceOrder_Produit ser = new ServiceOrder_Produit();
                 Order_Produit tmp2=tableView1.getSelectionModel().getSelectedItem();
                 Order_Produit C1 = new Order_Produit(tmp2.getOrder_id(),tmp2.getProduit_id());
                    ser.delete(C1);
                     products();
                     Alerts.error("Suppression", "Ordere Supprimé.");
                     err.setText("");
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        }  else {
            err.setText("Please select an Order Item.");
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
                        PdfWriter.getInstance(document, new FileOutputStream("output_user.pdf"));
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
       
    }
    
    @FXML
    public void Excel()
    {
        
    }
    @FXML
    public void products()
    {
        try{
            ServiceOrder_Produit ser = new ServiceOrder_Produit();
            List<Order_Produit> list = ser.displayClause("WHERE order_id = '"+tableView.getSelectionModel().getSelectedItem().getId()+"'");

            ObservableList<Order_Produit> cls = FXCollections.observableArrayList();
            for (Order_Produit aux : list)
                {
                  cls.add(new Order_Produit(aux.getOrder_id(),aux.getProduit_id()));  
                }
            tableView1.setItems(cls);
            
        } catch(SQLException ex) {
                    System.out.println(ex);
                 }


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
