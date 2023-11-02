package com.gn.module.blog_list_user;

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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import com.MVP.Service.BlogService;
import com.gn.module.blog_list.DetailBlogController;

public class BlogListUserController {

    @FXML
    private FontAwesomeIconView close;

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

    private BlogService blogService = new BlogService();

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
                    final HBox actionGroup = new HBox(btnView);

                    {
                        actionGroup.setSpacing(10);
                        actionGroup.setAlignment(Pos.CENTER);

                        btnView.setStyle("-fx-fill: #2196f3;");

                        setGraphic(actionGroup);
                        setText(null);

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
                                    }
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };
                return cell;
            }
        };

        actionsColumn.setCellFactory(cellFactory);

        readAll();
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
