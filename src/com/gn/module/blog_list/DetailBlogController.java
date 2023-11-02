package com.gn.module.blog_list;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.MVP.Entite.Blog;
import com.MVP.Entite.Comment;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.MVP.Service.BlogService;
import com.MVP.Service.CommentService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileInputStream;
import com.itextpdf.text.DocumentException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.MVP.Entite.Blog;
import com.MVP.Entite.Comment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import com.MVP.Service.BlogService;
import com.MVP.Service.CommentService;
import com.MVP.Utils.PdfCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.embed.swing.SwingFXUtils;

public class DetailBlogController {

    @FXML
    public Label titleField;

    @FXML
    public Label contentField;

    @FXML
    public ImageView selectedImage;

    @FXML
    private TableView<Comment> commentsTable;

    @FXML
    private TableColumn<Comment, Integer> idColumn;

    @FXML
    private TableColumn<Comment, String> contentColumn;

    @FXML
    private TableColumn<Comment, Timestamp> createdAtColumn;

    @FXML
    private TableColumn<Comment, Comment> actionsColumn;

    @FXML
    private TextField commentTextField;

    @FXML
    private FontAwesomeIconView addcomment;
    @FXML
    private FontAwesomeIconView backToBlogList;
    @FXML
    private TextField searchField;

    private Blog blog;
    private BlogService blogService = new BlogService();
    private CommentService commentService = new CommentService();

    public DetailBlogController() {

    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        if (blog != null) {
            List<Comment> comments = commentService.readCommentsByBlogId(blog.getId());
            commentsTable.getItems().setAll(comments);
        } else {
            System.out.println("Blog is null.");
        }

        Callback<TableColumn<Comment, Comment>, TableCell<Comment, Comment>> cellFactory = new Callback<TableColumn<Comment, Comment>, TableCell<Comment, Comment>>() {

            @Override
            public TableCell<Comment, Comment> call(final TableColumn<Comment, Comment> param) {
                final TableCell<Comment, Comment> cell = new TableCell<Comment, Comment>() {

                    final FontAwesomeIconView btnDelete = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    final HBox actionGroup = new HBox(btnDelete);

                    {
                        actionGroup.setSpacing(10);
                        actionGroup.setAlignment(Pos.CENTER);
                        btnDelete.setStyle("-fx-fill: #f44336;");
                        btnDelete.setOnMouseClicked(event -> {
                            Comment comment = getTableView().getItems().get(getIndex());
                            if (comment != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure you want to delete this comment?");

                                if (alert.showAndWait().get().getText().equals("OK")) {
                                    commentService.deleteComment(comment.getId());
                                    List<Comment> comments = commentService.readCommentsByBlogId(blog.getId());
                                    commentsTable.getItems().setAll(comments);
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Comment comment, boolean empty) {
                        super.updateItem(comment, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(actionGroup);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionsColumn.setCellFactory(cellFactory);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchComments());

    }

    private void initializeBlogFields() {
        if (blog != null) {
            String imagePath = blog.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                try {
                    FileInputStream inputStream = new FileInputStream(imageFile);
                    Image image = new Image(inputStream);
                    selectedImage.setImage(image);
                } catch (FileNotFoundException e) {
                    System.out.println("Image file not found.");
                    e.printStackTrace();
                }
            }
            titleField.setText(blog.getTitle());
            contentField.setText(blog.getContent());
            List<Comment> comments = commentService.readCommentsByBlogId(blog.getId());
            commentsTable.getItems().setAll(comments);
        } else {
            System.out.println("Blog is null.");
        }
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
        initializeBlogFields();
    }

    @FXML
    private void searchComments() {
        String searchText = searchField.getText().toLowerCase().trim();
        List<Comment> comments = commentService.readCommentsByBlogId(blog.getId());
        comments = comments.stream()
                .filter(comment -> comment.getContent().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        commentsTable.getItems().setAll(comments);
    }

    @FXML
    private void downloadPDF() {
        try {
            String imagePath = blog.getImage();
            Image image = new Image(new FileInputStream(imagePath));
            java.awt.image.BufferedImage awtImage = SwingFXUtils.fromFXImage(image, null);
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(awtImage, null);
            PdfCreator.createPDF(blog.getTitle(), blog.getContent(), pdfImage);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToBlogList(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("BlogList.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addComment() {
        String content = commentTextField.getText().trim();
        if (!content.isEmpty()) {
            Comment comment = new Comment(blog.getId(), content);
            comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commentService.addComment(comment);
            List<Comment> comments = commentService.readCommentsByBlogId(blog.getId());
            commentsTable.getItems().setAll(comments);
            commentTextField.clear();
        }
    }

}
