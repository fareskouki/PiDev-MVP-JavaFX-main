package com.gn.module.add_blog;

import com.MVP.Entite.Blog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.MVP.Service.BlogService;

public class AddBlogController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentField;
    @FXML
    private Button selectImageButton;
    @FXML
    private Button addBlogButton;
    @FXML
    private FontAwesomeIconView backToBlogList;
    @FXML
    private TextField selectedImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void addBlog(ActionEvent event) throws IOException {
        BlogService blogService = new BlogService();

        // Save image to your desired directory and use the path in the Blog entity
        String imagePath = selectedImage.getText();

        blogService.addBlog(new Blog(titleField.getText(), contentField.getText(), imagePath));

        // Show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Blog Added");
        alert.setHeaderText(null);
        alert.setContentText("The blog has been successfully added.");
        alert.showAndWait();

        // Navigate to the Blog List view
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BlogList.fxml"));

        Parent root = loader.load();
        titleField.getScene().setRoot(root);*/
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImage.setText(selectedFile.getName());
            // To create a folder in the project directory called "Uploads" if folder
            // doesn't already exist and copy the image
            File dir = new File("PI-DEV\\Blog Desktop\\src\\imagezz");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File dest = new File(dir, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(AddBlogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void backToBlogList(MouseEvent event) {
        /*try {
            Parent root = FXMLLoader.load(getClass().getResource("BlogList.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void clear() {
        titleField.clear();
        contentField.clear();
        selectedImage.clear();
    }

}
