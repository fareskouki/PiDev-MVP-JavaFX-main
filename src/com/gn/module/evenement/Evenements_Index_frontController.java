package com.gn.module.evenement;

import com.MVP.Entite.Evenement;
import com.MVP.Entite.User;
import com.MVP.Service.EvenementService;
import com.MVP.Service.ReservationService;
import com.MVP.Service.UserService;
import com.MVP.Utils.UserSession;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Evenements_Index_frontController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private HBox elementHBox;

    @FXML
    private Label nomLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dureeLabel;

    @FXML
    private Label capaciteLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Button CalendarBtn;

    @FXML
    private VBox containerVBox;
    @FXML
    private Button reserverBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EvenementService dao = new EvenementService();
        UserService dao2 = new UserService();
        ReservationService dao3 = new ReservationService();
        User currentUser = UserSession.getInstance().getUser();
        ArrayList<HBox> HBoxList = new ArrayList<>();
        try {
            // Get the ArrayList from the service
            ArrayList<Evenement> evenements = dao.afficherAll();
            int size = evenements.size();
            nomLabel.setText(evenements.get(0).getNom().toString());
            dateLabel.setText("Date: "+evenements.get(0).getDate().toString());
            dureeLabel.setText("Durée: "+String.valueOf(evenements.get(0).getDuree())+" heure(s).");
            capaciteLabel.setText(String.valueOf(evenements.get(0).getCapacite())+" places restantes.");
            descriptionLabel.setText("Au programme: "+evenements.get(0).getDescription());
            // Set the image of the event
            try{
                URL url = getClass().getResource("../Resources/Images/Uploads/Evenement/"+evenements.get(0).getImage());
                File file = new File(url.getPath());
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);

                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
  
            //Disable the button reserverBtn if the event is full or if currentUser has already reserved it
            

            //Create an on action event for every reserverBtn created in each HBox, to book for that event for the currentUser
            
            reserverBtn.setOnAction(event -> {
                dao3.reserver(currentUser, evenements.get(0));
                System.out.println("Reservation done!");
            });
            int i;
            for (i = 1; i < size; i++) {
                try {
                    Evenement evenement = evenements.get(i);
                    // Displays the evenements in the containerVBox by spawning newElementHBox in it
                    // for each evenement and assigning the values to the labels

                    HBox newElementBox = (HBox) FXMLLoader.load(getClass().getResource("EvenementElement.fxml"));
                    // Assigns the evenement values to the labels in the newElementHBox
                    // Gets the Labels
                    Label nomLabel = (Label) newElementBox.lookup("#nomLabel");
                    Label dateLabel = (Label) newElementBox.lookup("#dateLabel");
                    Label dureeLabel = (Label) newElementBox.lookup("#dureeLabel");
                    Label capaciteLabel = (Label) newElementBox.lookup("#capaciteLabel");
                    Label descriptionLabel = (Label) newElementBox.lookup("#descriptionLabel");
                    ImageView imageView = (ImageView) newElementBox.lookup("#imageView");
                    //Disable the button reserverBtn if the event is full or if currentUser has already reserved it
                    Button reserverBtn = (Button) newElementBox.lookup("#reserverBtn");
                    if(evenement.getCapacite() == 0){
                        reserverBtn.setDisable(true);
                    }
                    else if(dao3.isReserved(currentUser, evenement)){
                        reserverBtn.setDisable(true);
                    }

                    //Create an on action event for every reserverBtn created in each HBox, to book for that event for the currentUser
                    
                    reserverBtn.setOnAction(event -> {
                        dao3.reserver(currentUser, evenement);
                        System.out.println("Reservation done!");
                    }
                );
                    
                    // Assigns the values
                    nomLabel.setText(evenement.getNom());
                    dateLabel.setText("Date: "+evenement.getDate().toString());
                    dureeLabel.setText("Durée: "+String.valueOf(evenement.getDuree())+" heure(s).");
                    capaciteLabel.setText(String.valueOf(evenement.getCapacite())+" places restantes.");
                    descriptionLabel.setText("Au programme: "+evenement.getDescription());
                    try{
                        URL url = getClass().getResource("../Resources/Images/Uploads/Evenement/"+evenement.getImage());
                        File file2 = new File(url.getPath());
                        Image image2 = new Image(file2.toURI().toString());
                        imageView.setImage(image2);
                        
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }

                    // Add the newElementBox to the ArrayList
                    HBoxList.add(newElementBox);
                } catch (Exception ex) {
                    //Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // iterate through the ArrayList and add the HBoxes to the containerVBox
            for (HBox hBox : HBoxList) {
                containerVBox.getChildren().add(hBox);
            }

        } catch (SQLException ex) {
            //Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void close(ActionEvent event) {
        

    }

    @FXML
    void showCalendar(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            // Get the controller and add the calendar view to it
           // Controller controller = loader.getController();
            // controller.calendarPane.getChildren().add(new
            // FullCalendarView(YearMonth.now()).getView());
            stage.show();
        } catch (Exception ex) {
            //Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      

}
