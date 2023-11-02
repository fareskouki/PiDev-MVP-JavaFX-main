/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gn.module.produit_user;

import com.MVP.Entite.*;
import com.MVP.Service.Produitservice;
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




public class ControllerFront implements Initializable {

    @FXML
    private ComboBox<Categorie> categoryComboBox;

    @FXML
    private Button closeBtn;

    @FXML
    private HBox elementHBox;

    @FXML
    private Label nomLabel;

    @FXML
    private Label PrixLabel;

    @FXML
    private Label StockLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label id;

    @FXML
    private ImageView imageView;

    @FXML
    private Label errorMsg;

    @FXML
    private Button CalendarBtn;

    @FXML
    private VBox containerVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Produitservice dao = new Produitservice();

        List<Produit> produits;

        try {
            // Get the ArrayList from the service
            produits = dao.afficherAll();
        } catch (SQLException ex) {
            Logger.getLogger(com.gn.module.produit.ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        for (Produit produit : produits) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitElement.fxml"));
                HBox hbox = loader.load();

                Label nomLabel = (Label) loader.getNamespace().get("nomLabel");
                nomLabel.setText("Nom: " + produit.getNom_produit());

                Label prixLabel = (Label) loader.getNamespace().get("PrixLabel");
                prixLabel.setText("Prix: " + produit.getPrix());

                Label stockLabel = (Label) loader.getNamespace().get("StockLabel");
                stockLabel.setText("Stock: " + produit.getStock());

                Label ratingLabel = (Label) loader.getNamespace().get("ratingLabel");
                ratingLabel.setText("Rating: " + produit.getRating());

                Label descriptionLabel = (Label) loader.getNamespace().get("descriptionLabel");
                descriptionLabel.setText("Description: " + produit.getDescription());
                Label id = (Label) loader.getNamespace().get("id");
                id.setText(String.valueOf(produit.getId()));

                ImageView imageView = (ImageView) loader.getNamespace().get("imageView");
                String imagePath = produit.getImg();
                if (imagePath != null) {
                    File file = new File(imagePath);

                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitHeight(113);
                        imageView.setFitWidth(243);
                    }
                }

                        // Generate the QR code for the product ID
         QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix qrCodeMatrix = qrCodeWriter.encode(String.valueOf(produit.getId()), BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(qrCodeMatrix);

        // Convert the QR code image to a JavaFX image
        Image fxQrCodeImage = SwingFXUtils.toFXImage(qrCodeImage, null);

        // Create an ImageView for the QR code and add it to the HBox
        ImageView qrCodeImageView = new ImageView(fxQrCodeImage);
        hbox.getChildren().add(qrCodeImageView);

        // Add the HBox to the parent container
        containerVBox.getChildren().add(hbox);


            } catch (IOException ex) {
                Logger.getLogger(com.gn.module.produit.ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriterException ex) {
                Logger.getLogger(ControllerFront.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

// Get the categories list from the service
        List<Categorie> categoriesList = dao.getAllCategories();
        ObservableList<Categorie> categories = FXCollections.observableArrayList(categoriesList);
        Collections.sort(categories, Comparator.comparing(Categorie::getNom_categorie)); // sort by name
        categoryComboBox.setItems(categories);
        categoryComboBox.setConverter(new StringConverter<Categorie>() {
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

// Handle category selection
categoryComboBox.setOnAction(event -> {
    try {
        // Clear the container
        containerVBox.getChildren().clear();
        
        // Get the selected category from the ComboBox
        Categorie selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();

        // Filter the products by the selected category
        Produitservice dao2 = new Produitservice();
        List<Produit> produitsFiltered = dao2.filterByCategory(selectedCategory);

        // If no products found, display message
        if (produitsFiltered.isEmpty()) {
            Label noProductsLabel = new Label("No products found for this category.");
            containerVBox.getChildren().add(noProductsLabel);
        } else {
            // Add the filtered products to the container
            for (Produit produit : produitsFiltered) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitElement.fxml"));
                    HBox hbox = loader.load();

                    Label nomLabel = (Label) loader.getNamespace().get("nomLabel");
                    nomLabel.setText("Nom: " + produit.getNom_produit());

                    Label prixLabel = (Label) loader.getNamespace().get("PrixLabel");
                    prixLabel.setText("Prix: " + produit.getPrix());

                    Label stockLabel = (Label) loader.getNamespace().get("StockLabel");
                    stockLabel.setText("Stock: " + produit.getStock());

                    Label ratingLabel = (Label) loader.getNamespace().get("ratingLabel");
                    ratingLabel.setText("Rating: " + produit.getRating());

                    Label descriptionLabel = (Label) loader.getNamespace().get("descriptionLabel");
                    descriptionLabel.setText("Description: " + produit.getDescription());

                    ImageView imageView = (ImageView) loader.getNamespace().get("imageView");
                    String imagePath = produit.getImg();
                    if (imagePath != null) {
                        File file = new File(imagePath);

                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            imageView.setImage(image);
                            imageView.setFitHeight(113);
                            imageView.setFitWidth(243);
                        }
                    }

                    // Add the HBox to the parent container
                    containerVBox.getChildren().add(hbox);

                } catch (IOException ex) {
                    Logger.getLogger(com.gn.module.produit.ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(com.gn.module.produit.ControllerProduit.class.getName()).log(Level.SEVERE, null, ex);
    }
});
    }
    
@FXML
    void close(ActionEvent event)
    {
        
    

    }

@FXML void ajouter() {

    //System.out.println("AJOUTER");
}



}
