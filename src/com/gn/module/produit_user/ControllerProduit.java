/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gn.module.produit_user;

import com.MVP.Entite.*;
import com.MVP.Service.Produitservice;
import com.MVP.Service.ServiceCart;
import com.MVP.Service.ServiceCart_Produit;
import com.gn.global.util.Alerts;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;




public class ControllerProduit implements Initializable {
    @FXML
    private Label id;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
   

@FXML void ajouter() {

    System.out.println("AJOUTER"+id.getText());

Cart tmp=new Cart(); 
        try {
                 
                 
                 ServiceCart ser = new ServiceCart();
                 ServiceCart_Produit serP = new ServiceCart_Produit();
                 Produitservice serPr = new Produitservice();
                 int pID = Integer.parseInt(id.getText()); 
                 Cart_Produit tmp2=serP.readOne(pID,2);
            System.out.println("ID"+tmp2.getCart_id());
                 if(tmp2.getCart_id() != 0) {
                     Cart cart = ser.readOne(2);
                     cart.setPrix((cart.getPrix()/cart.getQuantite())*(cart.getQuantite()+1));
                     cart.setQuantite(cart.getQuantite()+1);
                     ser.update(cart);
                 } else {
                     Produit p = serPr.rechercherProduitParId(pID);
                     Cart cart = ser.ajouter1(new Cart(0,p.getPrix(),1));
                     System.out.println("cart"+cart.getId());
                     Cart_Produit cp = new Cart_Produit(cart.getId(),pID);
                     
                     serP.ajouter1(cp);
                 }                    

                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        
}



}
