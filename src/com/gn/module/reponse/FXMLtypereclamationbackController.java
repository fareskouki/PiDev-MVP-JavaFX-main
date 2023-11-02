/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.reponse;

import com.MVP.Entite.Reclamation;
import com.MVP.Entite.Repons;
import com.MVP.Entite.Statut;
import com.MVP.Service.ServiceReclamation;
import com.MVP.Service.ServiceRepons;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author winxspace
 */
public class FXMLtypereclamationbackController implements Initializable {

    @FXML
    private TextField tfstatus;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfdesc;
    @FXML
    private TextField tfid;
    @FXML
    private TableView<Repons> tvtype;
    @FXML
    private TableColumn<Repons, Integer> cid;
    @FXML
    private TableColumn<Repons, String> cnom;
    @FXML
    private TableColumn<Repons, Date> cdate;
    @FXML
    private TableColumn<Repons, String> cstatus;
    @FXML
    private TableColumn<Repons, String> cdesc;
    @FXML
    private ComboBox<String> reclamant;
    
    ServiceReclamation str=new ServiceReclamation();
    ServiceRepons sre=new ServiceRepons();
    ObservableList<String> data=FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //tfdate.setValue(LocalDate.now());
        data.addAll(str.getAllTitre());
        reclamant.setItems(data);
        displayData();
        tfdate.setValue(LocalDate.now());
         
    }
    
@FXML
private void ajouter(ActionEvent event) {
    
    ServiceReclamation service_rec=new ServiceReclamation();
    // Get the values from the UI controls
    if (controleDeSaisie().length() > 0) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur ajout reclamation");
        alert.setContentText(controleDeSaisie());
        alert.showAndWait();
    } else {
    int reclamationId = str.getReclamationByTitre(reclamant.getSelectionModel().getSelectedItem().toString());
    
    String contenuRep = tfdesc.getText();
    LocalDate localDate = tfdate.getValue();
    java.sql.Date dateRep = java.sql.Date.valueOf(localDate);
Reclamation rec= service_rec.getReclamationById(reclamationId);
rec.setStatut_rec(Statut.TRAITE);
    service_rec.modifier(rec, reclamationId);
    // Create a new Repons object with the entered values
    Repons newRepons = new Repons(reclamationId, dateRep, contenuRep,Statut.TRAITE);           
    // Call the add method and refresh the table view
    sre.ajouter(newRepons);
    displayData();
    
    
    // Clear the UI controls
    reclamant.getSelectionModel().clearSelection();
 
    tfdesc.clear();
    tfdate.setValue(null);
}
        

}


    
    
    
    @FXML
private void modifier(ActionEvent event) {
    // Check if any row is selected
    Repons selectedRepons = tvtype.getSelectionModel().getSelectedItem();
    if (selectedRepons == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a row to update!");
        alert.showAndWait();
        return;
    }
    
    // Get the updated values from the UI controls
    System.out.println("test");
    int reclamationId = str.getReclamationByTitre(reclamant.getSelectionModel().getSelectedItem().toString());
     
    String contenuRep = tfdesc.getText();
    LocalDate localDate = tfdate.getValue();
    Date dateRep = Date.valueOf(localDate);
    
    // Update the selected Repons object
    selectedRepons.setId_reclamation_id(reclamationId);
    selectedRepons.setStatus_rep(Statut.TRAITE);
    selectedRepons.setContenu_rep(contenuRep);
    selectedRepons.setDate_rep(dateRep);
    
    // Call the update method and refresh the table view
    sre.modifier(selectedRepons, 1);
    
    displayData();
}
@FXML
private void afficher(ActionEvent event)
{
 displayData();
}
    
    @FXML
    private void supprimer(ActionEvent event) throws Exception {
        if(tvtype.getSelectionModel().getSelectedItem()!=null){
            int id=tvtype.getSelectionModel().getSelectedItem().getId();
            sre.supprimer(id);
            displayData();
        }
    }
    
      @FXML
private void displayData() {
    ObservableList<Repons> dataList = FXCollections.observableArrayList(sre.afficher());
    cid.setCellValueFactory(new PropertyValueFactory<>("id"));
    cnom.setCellValueFactory(new PropertyValueFactory<>("id_reclamation_id"));
    cdate.setCellValueFactory(new PropertyValueFactory<>("date_rep"));
    cstatus.setCellValueFactory(new PropertyValueFactory<>("status_rep"));
    cdesc.setCellValueFactory(new PropertyValueFactory<>("contenu_rep"));
    tvtype.setItems(dataList);
}
    
public String controleDeSaisie(){
        String erreur="";
        if(tfdesc.getText().trim().isEmpty()){
            erreur+="Description vide!\n";
        }
        return erreur;
    }
    @FXML
    private void gotogstrec(ActionEvent event) {
        
    }

}
