/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package com.gn.module.produit;

import com.MVP.Entite.Categorie;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.MVP.Service.Produitservice;
import com.MVP.Entite.Produit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javafx.scene.input.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import javafx.util.StringConverter;
import javafx.stage.Stage;

public class ControllerProduit implements Initializable {

    @FXML
    private TableView<Produit> tableview;
    @FXML
    private TableColumn<Produit, String> columnNomProduit;
    @FXML
    private TableColumn<Produit, Float> columnPrix;
    @FXML
    private TableColumn<Produit, String> columnDescription;
    @FXML
    private TableColumn<Produit, Integer> columnRating;
    @FXML
    private TableColumn<Produit, Integer> columnStock;
    @FXML
    private TableColumn<Produit, String> columnImg;

    private int selectedIndex;

    Path selectedImagePath;
    boolean imageEdited;
    private List<Categorie> categories;

    @FXML
    private TextField filtre;

    @FXML
    private Button btn;
    @FXML
    private ComboBox<Categorie> idcat;
    @FXML
    private TextField tfid;
    @FXML
    private Button addButton;
    @FXML
    private TextField tfn;
    @FXML
    private TextField tfp;
    @FXML
    private TextField tfs;
    @FXML
    private TextField tfr;
    @FXML
    private TextField tfi;
    @FXML
    private TextField tfd;
    @FXML
    private TableColumn<Produit, String> colnom;
    @FXML
    private TableColumn<Produit, String> colcat;
    @FXML
    private TableColumn<Produit, String> coldesc;
    @FXML
    private TableColumn<Produit, Float> coldp;
    @FXML
    private TableColumn<Produit, Integer> colst;
    @FXML
    private TableColumn<Produit, Integer> colrate;
    @FXML
    private TableColumn<Produit, String> colimg;
    @FXML
    public ImageView imageIV;

        public void consulter() {
        Produitservice dao = new Produitservice();

        // Set cell value factories for the table columns
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        coldp.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colst.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colrate.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colcat.setCellValueFactory(new PropertyValueFactory<>("categorie.nom_categorie"));
        colimg.setCellValueFactory(new PropertyValueFactory<>("img"));
        colimg.setCellFactory(col -> new TableCell<Produit, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                setGraphic(imageView);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null) {
                    imageView.setImage(null);
                } else {
                    File file = new File(imagePath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                    }
                }
            }
        });

        try {
            // Get the ArrayList from the service
            List<Produit> produit = dao.afficherAll();

            // Convert the ArrayList to an ObservableList
            ObservableList<Produit> observableList = FXCollections.observableArrayList(produit);

            // Set the ObservableList as the data source for the TableView
            tableview.setItems(observableList);

            // Set a listener for the TableView selection
           tableview.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Produit> observable, Produit oldValue, Produit newValue) -> {
               if (newValue != null) {
                   // Set the selected product's information to the corresponding text fields
                   tfn.setText(newValue.getNom_produit());
                   tfp.setText(Float.toString(newValue.getPrix()));
                   tfd.setText(newValue.getDescription());
                   tfs.setText(Integer.toString(newValue.getStock()));
                   tfr.setText(Integer.toString(newValue.getRating()));
                   
                   // Set the selected product's category in the category choice box
                   String selectedCategory = newValue.getCategorie().getNom_categorie();
                   for (Categorie category : categories) {
                       if (category.getNom_categorie().equals(selectedCategory)) {
                           idcat.setValue(category);
                           break;
                       }
                   }
                   
                   // Load the image from the file path and display it in the ImageView
                   String imagePath = newValue.getImg();
                   if (imagePath != null) {
                       File file = new File(imagePath);
                       if (file.exists()) {
                           Image image = new Image(file.toURI().toString());
                           colimg.setGraphic(new ImageView(image)); // set the image in the table column
                       }
                   }
               }
               selectedIndex = tableview.getSelectionModel().getSelectedIndex();
            });


        } catch (SQLException ex) {
            Logger.getLogger(ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get the categories list from the service
        List<Categorie> categoriesList = dao.getAllCategories();
        ObservableList<Categorie> categories = FXCollections.observableArrayList(categoriesList);
        Collections.sort(categories, Comparator.comparing(Categorie::getNom_categorie)); // sort by name
        idcat.setItems(categories);
        idcat.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie category) {
                return category.getNom_categorie(); // return the category name
            }

            @Override
            public Categorie fromString(String nom) {
                // Not needed for this implementation
                return null;
            }
        });

// Set a listener for the TableView selection
        tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produit>() {
            @Override
            public void changed(ObservableValue<? extends Produit> observable, Produit oldValue, Produit newValue) {
                if (newValue != null) {
                    // Set the selected product's information to the corresponding text fields
                    tfn.setText(newValue.getNom_produit());
                    tfp.setText(Float.toString(newValue.getPrix()));
                    tfd.setText(newValue.getDescription());
                    tfs.setText(Integer.toString(newValue.getStock()));
                    tfr.setText(Integer.toString(newValue.getRating()));

                    // Set the selected product's category in the category choice box
                    String selectedCategory = newValue.getCategorie().getNom_categorie();
                    for (Categorie category : categories) {
                        if (category.getNom_categorie().equals(selectedCategory)) {
                            idcat.setValue(category);
                            break;
                        }
                    }

                    // Load the image from the file path and display it in the ImageView
                    String imagePath = newValue.getImg();
                    if (imagePath != null) {
                        File file = new File(imagePath);
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            colimg.setGraphic(new ImageView(image)); // set the image in the table column
                        }
                    }
                }
                selectedIndex = tableview.getSelectionModel().getSelectedIndex();
            }
        });

    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        String nom_produit = tfn.getText();
        String prix = tfp.getText();
        String description = tfd.getText();
        String stock = tfs.getText();
        String rating = tfr.getText();
        Categorie categorie = idcat.getValue();
        // Find the corresponding Categorie object for the selected category name

        // Check if any of the input fields are empty
        if (nom_produit.isEmpty() || prix.isEmpty() || description.isEmpty() || stock.isEmpty() || rating.isEmpty() || categorie == null) {
            // Display an error message if any of the fields are empty
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return; // exit the method to prevent further processing
        }

        // Check if prix, stock, and rating are valid numbers
        float prixValue;
        int stockValue;
        int ratingValue;
        try {
            prixValue = Float.parseFloat(prix);
            stockValue = Integer.parseInt(stock);
            ratingValue = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            // Display an error message if prix, stock, or rating are not valid numbers
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Prix, stock et rating doivent être des nombres valides !");
            alert.showAndWait();
            return; // exit the method to prevent further processing
        }

        // Check if description length is less than 255
        if (description.length() > 255) {
            // Display an error message if description length is greater than 255
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La description doit avoir moins de 255 caractères !");
            alert.showAndWait();
            return; // exit the method to prevent further processing
        }

        // Get the selected Categorie from the ComboBox
        Categorie selectedCategorie = idcat.getSelectionModel().getSelectedItem();

        String imagePath;
        createImageFile();
        imagePath = selectedImagePath.toString();

        Produitservice dao = new Produitservice();

        // Create a new Produit object with the input data and the selected Categorie
        Produit produit = new Produit(nom_produit, prixValue, description, stockValue, ratingValue, imagePath, categorie);
        produit.setCategorie(selectedCategorie);

        // Insert the Produit into the database
        dao.insert(produit);

        // Clear the input fields after adding the Produit
        tfn.clear();
        tfp.clear();
        tfd.clear();
        tfs.clear();
        tfr.clear();
        idcat.getSelectionModel().clearSelection();
        imageIV.setImage(null);

        // Refresh the TableView to display the new Produit
        consulter();

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Produitservice dao = new Produitservice();
        List<Categorie> categoriesList = dao.getAllCategories();
        ObservableList<Categorie> categories = FXCollections.observableArrayList(categoriesList);
        Collections.sort(categories, Comparator.comparing(Categorie::getNom_categorie)); // trier par nom
        idcat.setItems(categories);
        idcat.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie category) {
                return category.getNom_categorie(); // retourne le nom de la categorie
            }

            @Override
            public Categorie fromString(String nom) {
                // Pas besoin d'implémenter cette méthode car elle ne sera pas utilisée
                return null;
            }
        });
        consulter();
    }

    @FXML
    private void update(ActionEvent event) {
        Produitservice dao = new Produitservice();
        Produit produit = tableview.getSelectionModel().getSelectedItem();
        if (produit == null) {
            // Display an error message if no product is selected
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un produit à modifier !");
            alert.showAndWait();
            return; // exit the method to prevent further processing
        }

        // Get the updated product information from the text fields
        String nomProduit = tfn.getText();
        String prix = tfp.getText();
        String description = tfd.getText();
        String stock = tfs.getText();
        String rating = tfr.getText();
        Categorie categorie = idcat.getValue();

        // Check if any of the input fields are empty
        if (nomProduit.isEmpty() || prix.isEmpty() || description.isEmpty() || stock.isEmpty() || rating.isEmpty() || categorie == null) {
            // Display an error message if any of the fields are empty
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return; // exit the method to prevent further processing
        }

        // Check if prix, stock, and rating are valid numbers
        float prixValue;
        int stockValue;
        int ratingValue;
        try {
            prixValue = Float.parseFloat(prix);
            stockValue = Integer.parseInt(stock);
            ratingValue = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            // Display an error message if prix, stock, or rating are not valid numbers
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Prix, stock et rating doivent être des nombres valides !");
            alert.showAndWait();
            return; // exit the method to prevent further processing
        }

        // Update the selected product with the new information
        produit.setNom_produit(nomProduit);
        produit.setPrix(prixValue);
        produit.setDescription(description);
        produit.setStock(stockValue);
        produit.setRating(ratingValue);
        produit.setImg(selectedImagePath.toString());
        produit.setCategorie(categorie);
        try {
            dao.update(produit);
            // Refresh the TableView with the updated data
            ObservableList<Produit> observableList = FXCollections.observableArrayList(dao.afficherAll());
            tableview.setItems(observableList);
            // Clear the input fields
            tfn.clear();
            tfp.clear();
            tfd.clear();
            tfs.clear();
            tfr.clear();
            imageIV.setImage(null);
            selectedImagePath = null;
            idcat.getSelectionModel().clearSelection();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void chooseImage(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Pidev.mainStage);
        if (file != null) {
            selectedImagePath = Paths.get(file.getPath());
            imageIV.setImage(new Image(file.toURI().toString()));
        }
    }

    public void createImageFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("" + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedImagePath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCategorie(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Categorie.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        // Get the selected product from the TableView
        Produit selectedProduit = tableview.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            // Show a confirmation dialog to confirm the deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Instantiate and initialize the Produitservice class
                Produitservice produitService = new Produitservice();
                // Call the delete method in the service to delete the selected product
                produitService.delete(selectedProduit.getId());
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
        }
        consulter();

    }

    private void refreshTable() {
        try {
            List<Produit> events = new Produitservice().afficherAll();
            ObservableList<Produit> observableList = FXCollections.observableArrayList(events);
            tableview.setItems(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToCategorie(ActionEvent event) throws IOException {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/Categorie.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/
    }
    ObservableList<Produit> listeB = FXCollections.observableArrayList();

    
 
    
}
