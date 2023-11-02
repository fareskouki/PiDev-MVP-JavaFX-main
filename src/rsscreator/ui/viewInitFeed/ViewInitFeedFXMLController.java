/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsscreator.ui.viewInitFeed;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import rsscreator.ui.resourses.Feed;
import rsscreator.ui.resourses.filesHandler;

/**
 * FXML Controller class
 *
 * @author redayoub
 */
public class ViewInitFeedFXMLController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField link;
    @FXML
    private JFXTextField lang;
    @FXML
    private JFXTextField copyR;
    @FXML
    private TextArea descr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void save(ActionEvent event) {
        Feed feed=new  Feed(
                title.getText(),
                link.getText(),
                descr.getText(),
                lang.getText(),
                copyR.getText(),
                Calendar.getInstance().getTime().toString()
        );
        filesHandler.feed=feed;
        title.getScene().getWindow().hide();
    }
    
}
