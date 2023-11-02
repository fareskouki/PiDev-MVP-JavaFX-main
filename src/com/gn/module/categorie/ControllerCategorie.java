/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gn.module.categorie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.MVP.Service.CategorieService;
import com.MVP.Entite.Categorie;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


public class ControllerCategorie implements Initializable {
    // FXML components
    @FXML
    private TextField tfnc;
    @FXML
    private TextField tfe;
    @FXML
    private TextField tft;
    @FXML
    private TableView<Categorie> tableview;
    @FXML
    private TableColumn<Categorie, String> colnom;
    @FXML
    private TableColumn<Categorie, String> coletat;
    @FXML
    private TableColumn<Categorie, String> coltype;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;

    // ObservableList for tableview
    private ObservableList<Categorie> categories;

    // Variable to store selected category index
    private int selectedIndex = -1;

    // CategorieService instance
    private CategorieService categorieService;

    private void consulter() {
    CategorieService service = new CategorieService();
    try {
        // Get the ArrayList from the service
        ArrayList<Categorie> categories = service.afficherAll();

        // Convert the ArrayList to an ObservableList
        ObservableList<Categorie> observableList = FXCollections.observableArrayList(categories);

        // Set the ObservableList as the data source for the TableView
        tableview.setItems(observableList);

        // Set a listener for the TableView selection
        tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Set the selected category's information to the corresponding text fields
                tfnc.setText(newValue.getNom_categorie());
                tfe.setText(Integer.toString(newValue.getEtat()));
                tft.setText(newValue.getType());
            }
            selectedIndex = tableview.getSelectionModel().getSelectedIndex();
        });

    } catch (SQLException ex) {
        Logger.getLogger(ControllerCategorie.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Set cell value factories for the table columns
    colnom.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
    coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));
    coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
}
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("HERE");
       consulter();
    }
  
    /**
     * Add a new category to the database and update the tableview.
     */
    
    @FXML
private void ajouter(ActionEvent event) throws IOException {
   String nomCategorie = tfnc.getText();
String etatStr = tfe.getText(); // Get the text value from the TextField
int etat = Integer.parseInt(etatStr); // Convert the String to an int
String type = tft.getText();


    // Check if any of the input fields are empty
    if (nomCategorie.isEmpty() || type.isEmpty() ) {
        // Display an error message if any of the fields are empty
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return; // exit the method to prevent further processing
    }

   
   

   

    // Create a new Produit object with the values entered in the text fields
    Categorie c = new Categorie(nomCategorie, etat, type);

    // Insert the new Produit into the database
    CategorieService ps = new CategorieService();
    ps.insert(c);

    // Clear the text fields
    tfnc.clear();
    tfe.clear();
    tft.clear();

    // Display a message to indicate that the product was successfully added
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Ajout de produit");
    alert.setHeaderText(null);
    alert.setContentText("Le produit a été ajouté avec succès !");
    alert.showAndWait();
    consulter();
}

    
    

// Function to update the selected Categorie in the TableView and the database
@FXML
private void update(ActionEvent event) {
    Categorie selectedCategory = tableview.getSelectionModel().getSelectedItem();
    if (selectedCategory != null) {
        try {
            // Get the updated category data from the text fields
            String nom_categorie = tfnc.getText();
            int etat = Integer.parseInt(tfe.getText());
            String type = tft.getText();

            // Update the selected category
            selectedCategory.setNom_categorie(nom_categorie);
            selectedCategory.setEtat(etat);
            selectedCategory.setType(type);
            CategorieService categorieService = new CategorieService();
            categorieService.update(selectedCategory);


            // Clear the text fields
            tfnc.clear();
            tfe.clear();
            tft.clear();

            // Display a message to indicate that the category was successfully updated
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour de la catégorie");
            alert.setHeaderText(null);
            alert.setContentText("La catégorie a été mise à jour avec succès !");
            alert.showAndWait();

            // Refresh the table view
            consulter();
        } catch (NumberFormatException ex) {
            // Handle the case where the user enters invalid data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une valeur entière valide pour le champ 'Etat'.");
            alert.showAndWait();
        }
    } else {
        // Handle the case where no category is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une catégorie à mettre à jour.");
        alert.showAndWait();
    }
}
@FXML
private void switchToCategorie(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/Produit.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

@FXML
private void handleDeleteButton(ActionEvent event) {
    // Get the selected product from the TableView
    Categorie selectedCategorie = tableview.getSelectionModel().getSelectedItem();
    if (selectedCategorie != null) {
        // Show a confirmation dialog to confirm the deletion
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Instantiate and initialize the Produitservice class
            CategorieService produitService = new CategorieService();
            // Call the delete method in the service to delete the selected product
            produitService.delete(selectedCategorie.getId());
            // Refresh the TableView to reflect the changes
            tableview.refresh();
            // Show a success message
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Suppression réussie");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Produit supprimé avec succès !");
            successAlert.showAndWait();
        }
    } else {
        // Show an error message if no product is selected
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Erreur de suppression");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez sélectionner un produit à supprimer.");
        errorAlert.showAndWait();
    }    consulter();

}
}