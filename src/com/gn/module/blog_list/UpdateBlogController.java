package com.gn.module.blog_list;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.MVP.Entite.Blog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.MVP.Service.BlogService;
import com.gn.module.add_blog.AddBlogController;

public class UpdateBlogController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentField;
    @FXML
    private Button selectImageButton;
    @FXML
    private FontAwesomeIconView backToBlogList;
    @FXML
    private TextField selectedImage;

    private Blog blog;
    private BlogService blogService;
    private String imagePath;

    private void initializeBlogFields() {
        if (blog != null) {
            titleField.setText(blog.getTitle());
            contentField.setText(blog.getContent());
            selectedImage.setText(blog.getImage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogService = new BlogService();
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
        System.out.println("Blog object: " + blog);
        initializeBlogFields();
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
    private void cancel(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void backToBlogList(MouseEvent event) {
        
    }

@FXML
private void update(MouseEvent event) {
    blog.setTitle(titleField.getText());
    blog.setContent(contentField.getText());
    if (!selectedImage.getText().isEmpty()) {
        imagePath = "PI-DEV\\Blog Desktop\\src\\imagezz" + selectedImage.getText();
    }
    blog.setImage(imagePath);
    blogService.updateBlog(blog);

    backToBlogList(event);
}



}
