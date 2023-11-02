/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsscreator.ui.viewFeed;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rsscreator.ui.main.FXMLDocumentController;
import rsscreator.ui.resourses.FeedMessage;
import rsscreator.ui.resourses.filesHandler;

/**
 * FXML Controller class
 *
 * @author redayoub
 */
public class ViewFeedFXMLController implements Initializable {

    @FXML
    private JFXTextField titleFiled;
    @FXML
    private JFXTextField authorFiled;
    @FXML
    private JFXTextField linkFiled;
    @FXML
    private JFXTextField guidFiled;
    @FXML
    private JFXTextArea descrFiled;
    @FXML
    private JFXButton addBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(filesHandler.selFeedMsg==null){
                setEditing(true);
        }else{
                titleFiled.setText(filesHandler.selFeedMsg.getTitle());
                authorFiled.setText(filesHandler.selFeedMsg.getAuthor());
                linkFiled.setText(filesHandler.selFeedMsg.getLink());
                guidFiled.setText(filesHandler.selFeedMsg.getGuid());
                descrFiled.setText(filesHandler.selFeedMsg.getDescription());
                setEditing(false);
                addBtn.setText("Save");
        }
        Platform.runLater(()->{
            if (filesHandler.feed==null)initFeed(); 
        });
       
    }



    @FXML
    private void addMsg(ActionEvent event) {
        if(addBtn.getText().equals("Save")){ // save the edited msg
             if(filesHandler.checkGUID(guidFiled.getText())){
                showSameGUIDAlert();
                return;
            }
            filesHandler.selFeedMsg.setAuthor(authorFiled.getText());
            filesHandler.selFeedMsg.setTitle(titleFiled.getText());
            filesHandler.selFeedMsg.setLink(linkFiled.getText());
            filesHandler.selFeedMsg.setGuid(guidFiled.getText());
            filesHandler.selFeedMsg.setDescription(descrFiled.getText());
            FXMLDocumentController.myList.getItems().set(
                    filesHandler.selFeedIndexListView,
                    FXMLDocumentController.createFeedListItem(filesHandler.selFeedMsg)
            );
            filesHandler.selFeedIndexListView=-1;
            filesHandler.selFeedMsg=null;
            
        }else{ // add the msg to the feed
            if(filesHandler.feed==null){ // create the feed
                initFeed();
            }
            if(filesHandler.checkGUID(guidFiled.getText())){
                showSameGUIDAlert();
                return;
            }
            FeedMessage msg=new FeedMessage(
                    titleFiled.getText(),
                    authorFiled.getText(),
                    linkFiled.getText(),
                    guidFiled.getText(),
                    descrFiled.getText()
            );
            filesHandler.feed.getEntries().add(msg);
            filesHandler.selFeedMsg=msg;
            filesHandler.msgAdded=true;
            FXMLDocumentController.updateList();
            
        }
        
        titleFiled.getScene().getWindow().hide();
    }

    @FXML
    private void editMsg(ActionEvent event) {
        setEditing(true);
    }

    @FXML
    private void deleMsg(ActionEvent event) {
        if((filesHandler.selFeedMsg!=null)&&(filesHandler.selFeedIndexListView!=-1)){
            filesHandler.feed.getEntries().remove(filesHandler.selFeedMsg);
            FXMLDocumentController.myList.getItems().remove(filesHandler.selFeedIndexListView);
            filesHandler.selFeedMsg=null;
            filesHandler.selFeedIndexListView=-1;
            titleFiled.getScene().getWindow().hide();
        }
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        titleFiled.getScene().getWindow().hide();
    }
    
    private void setEditing(boolean editable){
        titleFiled.setEditable(editable);
        authorFiled.setEditable(editable);
        guidFiled.setEditable(editable);
        linkFiled.setEditable(editable);
        descrFiled.setEditable(editable);
        
    }

    private void showSameGUIDAlert() {
        Alert a=new Alert(Alert.AlertType.ERROR);
        a.setTitle("Invalid guid");
        a.setContentText("The same guid is used for anothor Feed Massage \n Plz,change your guid ");
        a.show();
    }

    
    // to make feed init view moveable
    double xOffset = 0;
    double yOffset = 0;

    private void initFeed() {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("/rsscreator/ui/viewInitFeed/viewInitFeedFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setTitle("Create New Feed");
           

            // make it moveable
            root.setOnMousePressed((ev) -> {
                xOffset = ev.getSceneX();
                yOffset = ev.getSceneY();
            });
            root.setOnMouseDragged((ev2) -> {
                stage.setX(ev2.getScreenX() - xOffset);
                stage.setY(ev2.getScreenY() - yOffset);
            });
            stage.setOnCloseRequest((ev)->{
                titleFiled.getScene().getWindow().hide();
            });
            
            //show
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
