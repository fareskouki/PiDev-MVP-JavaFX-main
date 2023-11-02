/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsscreator.ui.main;

import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rsscreator.ui.resourses.FeedMessage;
import rsscreator.ui.resourses.RSSFeedWriter;
import rsscreator.ui.resourses.filesHandler;

/**
 *
 * @author redayoub
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXListView<TextFlow> listView;
    public static JFXListView<TextFlow> myList;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setExpanded(true);
        listView.depthProperty().set(1);
        listView.setOnMouseClicked((ev) -> {
            selFeedMsg(ev);
        });
        myList = listView;

    }


    @FXML
    private void importFile(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File selFile=chooser.showOpenDialog(null);
        if(selFile!=null){
            try {
                filesHandler.importFrom(selFile);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
    @FXML
    private void importUsingUrl(ActionEvent event) {
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Add a URL");
        dialog.setHeaderText("Add a URL");
        dialog.setContentText("Plz, enter your URL :");
        
        Optional<String> result=dialog.showAndWait();
        if (result.isPresent()&&(!result.get().equals(""))){
            
            try {
                URL url = new URL(result.get());
                filesHandler.importFrom(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
       
        
    }


    @FXML
    void exportToXML(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selFile=chooser.showSaveDialog(null);
        if(selFile!=null){
            RSSFeedWriter.writeRSSFeed(filesHandler.feed, selFile);
        }
    }

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    

    @FXML
    private void addFeed(ActionEvent event) {
        showFeedMsg();
    }

    // to make feed view moveable
    double xOffset = 0;
    double yOffset = 0;

    private void showFeedMsg() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/rsscreator/ui/viewFeed/ViewFeedFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            if (filesHandler.selFeedMsg == null) {
                stage.setTitle("Add a feed message");
            } else {
                stage.setTitle(filesHandler.selFeedMsg.getTitle());
            }

            // make it moveable
            root.setOnMousePressed((ev) -> {
                xOffset = ev.getSceneX();
                yOffset = ev.getSceneY();
            });
            root.setOnMouseDragged((ev2) -> {
                stage.setX(ev2.getScreenX() - xOffset);
                stage.setY(ev2.getScreenY() - yOffset);
            });

            //show
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static TextFlow createFeedListItem(FeedMessage f) {
        TextFlow tf = new TextFlow();

        Text title = new Text(f.getTitle() + "  ");
        title.setFill(Color.web("#4444ff"));
        title.setFont(Font.font(Font.getDefault().getName(), 15));
        tf.getChildren().add(title);

        Text descr = new Text(f.getDescription());
        descr.setFont(Font.font(Font.getDefault().getName(), 13));
        tf.getChildren().add(descr);

        Text guid = new Text(f.getGuid());
        guid.setFont(Font.font(Font.getDefault().getName(), 1));
        guid.setVisible(false);
        tf.getChildren().add(guid);

        return tf;
    }

    public static void updateList() {


        if (filesHandler.msgAdded) {
            myList.getItems().add(createFeedListItem(filesHandler.selFeedMsg));
        }
        filesHandler.selFeedMsg = null;
        filesHandler.msgAdded = false;

    }

    public static void createList(){
        if (filesHandler.feed==null)return;
        myList.getItems().clear();
        for (FeedMessage msg:filesHandler.feed.getEntries()){
            myList.getItems().add(createFeedListItem(msg));
        }
    }
    
    private void selFeedMsg(MouseEvent ev) {
        System.out.println(((Node)ev.getTarget()).getParent().toString());
        
        
        if (ev.getTarget() instanceof TextFlow) {System.out.println("working2");
            TextFlow selFeedItem = (TextFlow) ev.getTarget();
            filesHandler.selFeedIndexListView=listView.getItems().indexOf(selFeedItem);
            String selGuid = ((Text) selFeedItem.getChildren().get(2)).getText();
            FeedMessage selMsg;
            for (FeedMessage msg : filesHandler.feed.getEntries()) {
                if (msg.getGuid().equals(selGuid)) {
                    filesHandler.selFeedMsg = msg;
                    showFeedMsg();
                    return;
                }
            }
        }

        //filesHandler.selFeed=selFeed;
    }

    
    

}
