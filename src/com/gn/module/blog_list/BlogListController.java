package com.gn.module.blog_list;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.layout.Pane;
import java.util.List;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.MVP.Entite.Blog;
import java.io.File;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import com.MVP.Service.BlogService;

public class BlogListController {

    @FXML
    private FontAwesomeIconView close;

    @FXML
    private FontAwesomeIconView backToHome;

    @FXML
    private Button refreshTable;

    @FXML
    private FontAwesomeIconView backToaddBlog;

    @FXML
    private TableView<Blog> blogTable;

    @FXML
    private TableColumn<Blog, Integer> idColumn;

    @FXML
    private TableColumn<Blog, String> titleColumn;

    @FXML
    private TableColumn<Blog, String> contentColumn;

    @FXML
    private TableColumn<Blog, String> imageColumn;

    @FXML
    private TableColumn<Blog, Blog> actionsColumn;

    @FXML
    private TextField searchField;

    private BlogService blogService = new BlogService();

    private List<Blog> allBlogs;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        blogTable.getSelectionModel().setCellSelectionEnabled(false);

        Callback<TableColumn<Blog, Blog>, TableCell<Blog, Blog>> cellFactory = new Callback<TableColumn<Blog, Blog>, TableCell<Blog, Blog>>() {

            @Override
            public TableCell<Blog, Blog> call(final TableColumn<Blog, Blog> param) {
                final TableCell<Blog, Blog> cell = new TableCell<Blog, Blog>() {

                    final FontAwesomeIconView btnView = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                    final FontAwesomeIconView btnDelete = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    final FontAwesomeIconView btnEdit = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                    final HBox actionGroup = new HBox(btnView, btnEdit, btnDelete);

                    {
                        actionGroup.setSpacing(10);
                        actionGroup.setAlignment(Pos.CENTER);

                        btnView.setStyle("-fx-fill: #2196f3;");
                        btnDelete.setStyle("-fx-fill: #f44336;");
                        btnEdit.setStyle("-fx-fill: #4caf50;");

                        btnView.setOnMouseClicked(event -> {
                            Blog selectedBlog = getTableView().getItems().get(getIndex());
                            if (selectedBlog != null) {
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/gn/module/blog_list/DetailBlog.fxml"));
                                    Parent root = fxmlLoader.load();
                                    DetailBlogController controller = fxmlLoader.getController();
                                    controller.setBlog(selectedBlog);
                                    controller.titleField.setText(selectedBlog.getTitle());
                                    controller.contentField.setText(selectedBlog.getContent());

                                    String imagePath = selectedBlog.getImage();
                                    if (imagePath != null && !imagePath.isEmpty()) {
                                        File imageFile = new File(imagePath);
                                        Image image = new Image(imageFile.toURI().toString());
                                        controller.selectedImage.setImage(image);
                                    } else {
                                        controller.selectedImage.setImage(null);
                                    }
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        btnEdit.setOnMouseClicked(event -> {
                            Blog selectedBlog = blogTable.getSelectionModel().getSelectedItem();
                            System.out.println("Selected blog: " + selectedBlog);

                            if (selectedBlog != null) {
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/gn/module/blog_list/UpdateBlog.fxml"));
                                    Parent root = fxmlLoader.load();

                                    UpdateBlogController controller = fxmlLoader.getController();
                                    controller.setBlog(selectedBlog);

                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        btnDelete.setOnMouseClicked(event -> {
                            Blog blog = getTableView().getItems().get(getIndex());
                            if (blog != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure you want to delete this blog?");

                                if (alert.showAndWait().get().getText().equals("OK")) {
                                    blogService.deleteBlog(blog.getId());
                                    readAll();
                                }
                            }
                        });

                    }

                    @Override
                    protected void updateItem(Blog blog, boolean empty) {
                        super.updateItem(blog, empty);
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

        allBlogs = blogService.readAllBlogs();
        blogTable.getItems().setAll(allBlogs);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                blogTable.getItems().setAll(allBlogs);
                return;
            }

            List<Blog> filteredBlogs = allBlogs.stream()
                    .filter(blog -> blog.getTitle().toLowerCase().contains(newValue.toLowerCase())
                    || blog.getContent().toLowerCase().contains(newValue.toLowerCase()))
                    .collect(Collectors.toList());

            blogTable.getItems().setAll(filteredBlogs);
        });
    }

    @FXML
    private void searchBlogs(KeyEvent event) {
        String searchText = searchField.getText();

        List<Blog> filteredBlogs = allBlogs.stream()
                .filter(blog -> blog.getTitle().toLowerCase().contains(searchText.toLowerCase())
                || blog.getContent().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());

        blogTable.getItems().setAll(filteredBlogs);
    }

    @FXML
    private void backToaddBlog(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddBlog.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToHome(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomeBlog.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void readAll() {
        List<Blog> blogs = blogService.readAllBlogs();
        blogTable.getItems().setAll(blogs);
    }

    @FXML
    private void refreshTable() {
        readAll();
    }
}
