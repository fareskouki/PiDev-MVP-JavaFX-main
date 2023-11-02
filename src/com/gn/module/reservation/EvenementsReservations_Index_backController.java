package com.gn.module.reservation;

import com.MVP.Entite.Evenement;
import com.MVP.Service.EvenementService;
import com.MVP.Entite.Reservation;
import com.MVP.Service.ReservationService;
import com.MVP.Service.UserService;
import com.MVP.Entite.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.Node;
import com.gn.module.evenement_back.Evenement_Show_backController;

public class EvenementsReservations_Index_backController implements Initializable {

    @FXML
    private TabPane tabPane;

    /////////////////////////// EVENEMENTS TAB ////////////////////////////////
    @FXML
    private Tab tabEvenements;
    @FXML
    private Label showingLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Evenement> tableview;
    @FXML
    private TableColumn<Evenement, Integer> colid;
    @FXML
    private TableColumn<Evenement, String> colnom;
    @FXML
    private TableColumn<Evenement, String> coldesc;
    @FXML
    private TableColumn<Evenement, Integer> colduree;
    @FXML
    private TableColumn<Evenement, Integer> colcap;
    @FXML
    private TableColumn<Evenement, String> coltype;
    @FXML
    private TableColumn<Evenement, String> coldate;
    @FXML
    private TextField IDTextfield;
    @FXML
    private Button calendarBtn;
    @FXML
    private TextField NOM;
    @FXML
    private TextField DATE;
    @FXML
    private TextArea DESC;
    @FXML
    private TextField DUREE;
    @FXML
    private TextField CAP;
    @FXML
    private TextField TYPE;
    @FXML
    private Label IMG;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private VBox inputFields;
    @FXML
    private DatePicker DATEPICKER;
    @FXML
    private Spinner<Integer> DUREE_SPINNER;
    @FXML
    private Spinner<Integer> CAP_SPINNER;
    @FXML
    private ChoiceBox<String> TYPE_CB;
    @FXML
    private Button showBtn;
    private ObservableList<Evenement> observableList;

    ///////////////// RESERVATION TAB ///////////////////////
    @FXML
    private Tab tabReservations;
    @FXML
    private Button showReservationsBtn;
    @FXML
    private Label errorLabel_res;
    @FXML
    private TextField IDTextfield_res;
    @FXML
    private DatePicker DATEPICKER_res;
    @FXML
    private ChoiceBox<User> CB_MEMBRE;
    @FXML
    private ChoiceBox<Evenement> CB_EVENT;
    @FXML
    private Button addButton_res;
    @FXML
    private Button deleteButton_res;
    @FXML
    private Button updateButton_res;
    @FXML
    private TableView<Reservation> tableview_res;
    @FXML
    private TableColumn<?, ?> colid_res;
    @FXML
    private TableColumn<?, ?> coldate_res;
    @FXML
    private TableColumn<?, ?> colmembre;
    @FXML
    private TableColumn<?, ?> colevent;
    @FXML
    private Button deselectBtn_res;
    @FXML
    private Button refreshBtn_res;

    private ObservableList<Reservation> observableList_res;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabEvenements();
        initTabReservations();
    }

    private void initTabEvenements() {
        // Load events from database into the tableview
        EvenementService dao = new EvenementService();
        try {
            // Get the ArrayList from the service
            ArrayList<Evenement> evenements = dao.afficherAll();

            // Convert the ArrayList to an ObservableList
            observableList = FXCollections.observableArrayList(evenements);

            // Set the ObservableList as the data source for the TableView
            tableview.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set cell value factories for the table columns
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colduree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colcap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(false);
        DATEPICKER.setValue(java.time.LocalDate.now());
        IDTextfield.setDisable(true);
        showReservationsBtn.setDisable(true);

        IDTextfield.setVisible(false);

        // Set ChoiceBox options (string values) and default string value: Choices are:
        // Social, Gaming, Tournament, Meetup. Default is value: Social
        TYPE_CB.getItems().addAll("Social", "Gaming", "Tournament", "Meetup");
        TYPE_CB.setValue("Social");
        showBtn.setDisable(true);

        // Set Spinner values and default value: 1, default value: 1
        DUREE_SPINNER.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 4));
        CAP_SPINNER.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 100));

        // INITIALIZING THE INPUT FIELDS

        // Listen for selection changes and show the evenement details when changed.
        // newSelection is the newly selected item, or null if there is no selection, it
        // is used to update the textfield, in method updateItem
        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateButton.setDisable(true);
                deleteButton.setDisable(false);
                addButton.setDisable(false);
                IDTextfield.setDisable(true);
                IDTextfield.setText(String.valueOf(newSelection.getId()));
                NOM.setText(newSelection.getNom());
                DATEPICKER.setValue(java.time.LocalDate.parse(newSelection.getDate()));
                DESC.setText(newSelection.getDescription());
                DUREE_SPINNER.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, newSelection.getDuree()));
                CAP_SPINNER.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, newSelection.getCapacite()));
                TYPE_CB.setValue(newSelection.getType());
                IMG.setText(newSelection.getImage());
            }
        });
    }

    private void initTabReservations() {
        // Load events from database into the tableview
        ReservationService dao = new ReservationService();
        UserService dao2 = new UserService();
        EvenementService dao3 = new EvenementService();

        try {
            // Get the ArrayList from the service
            ArrayList<Reservation> reservations = dao.afficherAll();

            // Convert the ArrayList to an ObservableList
            observableList_res = FXCollections.observableArrayList(reservations);

            // Set the ObservableList as the data source for the TableView
            tableview_res.setItems(observableList_res);

        } catch (SQLException ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set cell value factories for the table columns
        colid_res.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldate_res.setCellValueFactory(new PropertyValueFactory<>("date"));
        colmembre.setCellValueFactory(new PropertyValueFactory<>("res_user"));
        colevent.setCellValueFactory(new PropertyValueFactory<>("res_evenement"));
        updateButton_res.setDisable(true);
        deleteButton_res.setDisable(true);
        addButton_res.setDisable(false);
        DATEPICKER_res.setValue(java.time.LocalDate.now());
        DATEPICKER_res.setDisable(true);

        IDTextfield_res.setVisible(false);

        // Clear all comboboxes and their options
        CB_MEMBRE.getItems().clear();
        CB_EVENT.getItems().clear();

        // Set CB_MEMBRE options (string values) and default string value: Choices are:
        // get all the user IDs from the database

        
            ArrayList<User> users = dao2.getAllUsers();

            for (User user : users) {
                CB_MEMBRE.getItems().add(user);
            }
        
        // Set CB_EVENT options (string values) and default string value: Choices are:
        // get all the event IDs from the database
        try {
            ArrayList<Evenement> evenements = dao3.afficherAll();

            for (Evenement evenement : evenements) {
                CB_EVENT.getItems().add(evenement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

        IDTextfield_res.setDisable(true);
        // Set errorLabel_res text color to red
        errorLabel_res.setTextFill(Color.RED);
        // Set errorLabel_res to invisible
        errorLabel_res.setVisible(false);

        // Listen for selection changes and show the evenement details when changed.
        // newSelection is the newly selected item, or null if there is no selection, it
        // is used to update the textfield, in method updateItem

        tableview_res.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection_res, newSelection_res) -> {
                    if (newSelection_res != null) {
                        updateButton_res.setDisable(false);
                        deleteButton_res.setDisable(false);
                        addButton_res.setDisable(true);
                        IDTextfield_res.setDisable(true);
                        IDTextfield_res.setText(String.valueOf(newSelection_res.getId()));
                        CB_MEMBRE.setValue(newSelection_res.getRes_user());
                        CB_EVENT.setValue(newSelection_res.getRes_evenement());
                        DATEPICKER_res.setValue(java.time.LocalDate.parse(newSelection_res.getDate()));
                    }

                });
    }

    @FXML
    private void showCalendar(ActionEvent event) {

        try {
            Stage stage = (Stage) (calendarBtn.getScene().getWindow());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            // Get the controller and add the calendar view to it
            //Controller controller = loader.getController();
           // controller.calendarPane.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
         * Evenement selectedEvent = tableview.getSelectionModel().getSelectedItem();
         * if (selectedEvent != null) {
         * try {
         * FXMLLoader loader = new
         * FXMLLoader(getClass().getResource("ShowEvenement_Window.fxml"));
         * Parent root = loader.load();
         * Stage stage = new Stage();
         * stage.setScene(new Scene(root));
         * stage.show();
         * ShowEvenement_WindowController controller = loader.getController();
         * 
         * controller.initData(selectedEvent);
         * 
         * } catch (IOException ex) {
         * Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE,
         * null, ex);
         * }
         * }

        /*
         * Evenement selectedEvent = tableview.getSelectionModel().getSelectedItem();
         * if (selectedEvent != null) {
         * try {
         * FXMLLoader loader = new
         * FXMLLoader(getClass().getResource("ShowEvenement_Window.fxml"));
         * Parent root = loader.load();
         * Stage stage = new Stage();
         * stage.setScene(new Scene(root));
         * stage.show();
         * ShowEvenement_WindowController controller = loader.getController();
         * 
         * controller.initData(selectedEvent);
         * 
         * } catch (IOException ex) {
         * Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE,
         * null, ex);
         * }
         * }
         */

    }

    @FXML
    private void closeApp(ActionEvent event) {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            IMG.setText(selectedFile.getName());
            // To create a folder in the project directory called "Evenement" if folder
            // doesn't already exist and copy the image
            
            File dir = new File("..\\Resouces\\Images\\Uploads\\Evenement");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File dest = new File(dir, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // A function to show the selected entry in a new window
    // "ShowEvenement_Window.fxml"
    @FXML
    private void show(ActionEvent event) {
        Evenement selectedEvent = tableview.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gn/module/evenement_back/Evenement_Back.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Evenement_Show_backController controller = loader.getController();

                controller.initData(selectedEvent);

            } catch (IOException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addItem(ActionEvent event) {
        if (checkInput() && checkIfDateIsPrior()) {
            EvenementService dao = new EvenementService();

            String nom = NOM.getText();
            // DatePicker formatted as yyyy-mm-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = DATEPICKER.getValue().format(formatter).toString();
            String desc = DESC.getText();
            int duree = Integer.parseInt(DUREE_SPINNER.getValue().toString());
            int cap = Integer.parseInt(CAP_SPINNER.getValue().toString());
            String type = TYPE_CB.getValue();
            String img = IMG.getText();

            Evenement evenement = new Evenement(nom, date, desc, duree, cap, type, img);

            try {
                dao.ajouter(evenement);

                tableview.getItems().clear();

                refresh();

            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void deleteItem() {
        EvenementService dao = new EvenementService();
        Evenement selectedEvent = tableview.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                dao.delete(selectedEvent.getId());
                tableview.getItems().remove(selectedEvent);
            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                // Get the ArrayList from the service
                ArrayList<Evenement> evenements = dao.afficherAll();

                // Convert the ArrayList to an ObservableList
                observableList = FXCollections.observableArrayList(evenements);

                // Set the ObservableList as the data source for the TableView
                tableview.setItems(observableList);

            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableview.refresh();
            deselectItem();
        }
    }

    // selectItem(): When row is selected, Get the selected row and set the values
    // of the selected row to the input fields.
    @FXML
    private Evenement selectItem() {
        // Get the selected row
        Evenement selectedEvent = tableview.getSelectionModel().getSelectedItem();
        // If there is a selected row
        if (selectedEvent != null) {
            // Enable the update button
            updateButton.setDisable(false);
            // Get the values from the selected row
            int id = selectedEvent.getId();
            String nom = selectedEvent.getNom();
            String date = selectedEvent.getDate();
            String desc = selectedEvent.getDescription();
            int duree = selectedEvent.getDuree();
            int cap = selectedEvent.getCapacite();
            String type = selectedEvent.getType();
            String img = selectedEvent.getImage();

            // Set the values of the selected row to the input fields
            IDTextfield.setText(String.valueOf(id));
            NOM.setText(nom);
            DATEPICKER.setValue(java.time.LocalDate.parse(date));
            DESC.setText(desc);
            DUREE_SPINNER.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, duree));
            CAP_SPINNER.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, cap));
            TYPE_CB.setValue(type);
            IMG.setText(img);
            showReservationsBtn.setDisable(false);

            // Disable add button
            addButton.setDisable(true);
            showBtn.setDisable(false);
        }
        return selectedEvent;
    }

    // deselect(): Method called when deselectBtn is clicked, it deselects the
    // selected row.
    @FXML
    private void deselectItem() {
        // Clear the selection
        tableview.getSelectionModel().clearSelection();
        // Disable the update, del, add buttons

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(false);

        // Clear the input fields
        IDTextfield.setText("");
        NOM.setText("");
        DESC.setText("");
        DUREE_SPINNER.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1));
        CAP_SPINNER.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 50));
        TYPE_CB.setValue("Social");
        IMG.setText("");
        // Set datepicker to today
        DATEPICKER.setValue(java.time.LocalDate.now());

        addButton.setText("Add");
        showBtn.setDisable(true);
        showReservationsBtn.setDisable(true);

    }

    @FXML
    private void updateItem() {
        Evenement selectedEvent = tableview.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            EvenementService dao = new EvenementService();
            String nom = NOM.getText();
            // Pick date from datepicker fxid=DATEPICKER formatted as yyyy-mm-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = DATEPICKER.getValue().format(formatter).toString();
            String desc = DESC.getText();
            int duree = Integer.parseInt(DUREE_SPINNER.getValue().toString());
            int cap = Integer.parseInt(CAP_SPINNER.getValue().toString());
            String type = TYPE_CB.getValue();
            String img = IMG.getText();

            Evenement updatedEvent = new Evenement(nom, date, desc, duree, cap, type, img);
            updatedEvent.setId(selectedEvent.getId());
            try {
                dao.update(updatedEvent);

            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
            deselectItem();
            refresh();
        }
    }

    // Method checking if the input fields are empty and if integers and strings are
    // respected
    private Boolean checkInput() {
        String nom = NOM.getText();
        String desc = DESC.getText();
        String duree = DUREE_SPINNER.getValue().toString();
        String cap = CAP_SPINNER.getValue().toString();
        String type = TYPE_CB.getValue();
        String img = IMG.getText();
        // if any of the input fields are empty, andshow an error message
        if (nom.isEmpty() || desc.isEmpty() || duree.isEmpty() || cap.isEmpty() || type.isEmpty() || img.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fields are empty.");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private Boolean checkIfDateIsPrior() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = DATEPICKER.getValue().format(formatter).toString();
        LocalDate date1 = LocalDate.parse(date);
        LocalDate date2 = LocalDate.now();
        if (date1.isBefore(date2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Date is prior to today.");
            alert.setContentText("Please choose a date after today.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void refresh() {
        EvenementService dao = new EvenementService();
        try {
            // Get the ArrayList from the service
            ArrayList<Evenement> evenements = dao.afficherAll();

            // Convert the ArrayList to an ObservableList
            observableList = FXCollections.observableArrayList(evenements);

            // Set the ObservableList as the data source for the TableView
            tableview.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableview.refresh();
    }

    @FXML
    void addItem_res(ActionEvent event) {
        ReservationService dao = new ReservationService();
        try {

            Reservation reservation = new Reservation();

            // DatePicker formatted as yyyy-mm-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = DATEPICKER_res.getValue().format(formatter).toString();
            reservation.setDate(date);

            reservation.setRes_user(CB_MEMBRE.getValue());

            reservation.setRes_evenement(CB_EVENT.getValue());

            if (!checkUserAlreadyReserved()) {
                dao.ajouter(reservation);
                refresh_res();
            }
            
            if (checkIfAllBooked()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Event is fully booked.");
                alert.setContentText("Please choose another event.");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void deleteItem_res() {
        ReservationService dao = new ReservationService();
        Reservation selectedReservation = selectItem_res();
        if (selectedReservation != null) {
            try {
                dao.delete(selectedReservation.getId());
                // tableview_res.getItems().remove(selectedReservation);
            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
            refresh_res();
        }
    }

    @FXML
    void refresh_res() {

        deselectItem_res();
        initTabReservations();

    }

    @FXML
    void updateItem_res() {
        ReservationService dao = new ReservationService();
        Reservation selectedReservation = selectItem_res();
        if (selectedReservation != null) {
            try {
                dao.update(selectedReservation);
            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
            refresh_res();
        }

    }

    Reservation selectItem_res() {
        ReservationService dao = new ReservationService();
        // Create a new Reservation by getting it from the database by ID from the
        // column colid_res
        Reservation selectedReservation = null;
        try {
            selectedReservation = dao.getReservationById(tableview_res.getSelectionModel().getSelectedItem().getId());
        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Erreur dans la récupération de la réservation");
            return null;

        }
        // Set the values of the input fields
        IDTextfield_res.setText(Integer.toString(selectedReservation.getId()));
        DATEPICKER_res.setValue(LocalDate.parse(selectedReservation.getDate()));
        CB_MEMBRE.setValue(selectedReservation.getRes_user());
        CB_EVENT.setValue(selectedReservation.getRes_evenement());
        // Update the update and delete buttons to enable them
        updateButton_res.setDisable(false);
        deleteButton_res.setDisable(false);
        addButton_res.setDisable(true);

        return selectedReservation;
    }

    @FXML
    void deselectItem_res() {
        // Clear the selection
        tableview_res.getSelectionModel().clearSelection();

        // Disable the update, del, add buttons
        updateButton_res.setDisable(true);
        deleteButton_res.setDisable(true);
        addButton_res.setDisable(false);

        // Clear the input fields
        IDTextfield_res.setText("");
        DATEPICKER_res.setValue(LocalDate.now());
        CB_MEMBRE.setValue(null);
        CB_EVENT.setValue(null);
        showingLabel.setText("Showing: all reservations.");
    }

    // Method to change to tab reservations and then only show the reservations of
    // the selected evenement
    @FXML
    void showReservations() {
        Evenement selectedEvent = selectItem();
        if (selectedEvent != null) {
            showingLabel.setText("Showing: Reservations for " + selectedEvent.getNom() + ".");
            tabPane.getSelectionModel().select(tabReservations);
            ReservationService dao = new ReservationService();
            try {
                // Get the ArrayList from the service
                ArrayList<Reservation> reservations = dao.afficherAll();
                // Convert the ArrayList to an ObservableList
                observableList_res = FXCollections.observableArrayList(reservations);
                // Set the ObservableList as the data source for the TableView
                tableview_res.setItems(observableList_res);
                // Filter the tableview to only show the reservations of the selected evenement
                FilteredList<Reservation> filteredData = new FilteredList<>(observableList_res, p -> true);
                filteredData.setPredicate(reservation -> {
                    if (reservation.getRes_evenement().getId() == selectedEvent.getId()) {
                        return true;
                    }
                    return false;
                });
                SortedList<Reservation> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tableview_res.comparatorProperty());
                tableview_res.setItems(sortedData);
            } catch (SQLException ex) {
                Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableview_res.refresh();
        }
    }

    // To check if there is already a reservation in the database with the same
    // inputted User and Event in CB_MEMBRE and CB_EVENT
    private boolean checkUserAlreadyReserved() {
        ReservationService dao = new ReservationService();
        try {
            // Get the ArrayList from the service
            ArrayList<Reservation> reservations = dao.afficherAll();
            // Convert the ArrayList to an ObservableList
            observableList_res = FXCollections.observableArrayList(reservations);
            // Set the ObservableList as the data source for the TableView
            tableview_res.setItems(observableList_res);
            // Filter the tableview to only show the reservations of the selected evenement
            FilteredList<Reservation> filteredData = new FilteredList<>(observableList_res, p -> true);
            filteredData.setPredicate(reservation -> {
                if (reservation.getRes_evenement().getId() == CB_EVENT.getValue().getId()
                        && reservation.getRes_user().getId() == CB_MEMBRE.getValue().getId()) {
                    return true;
                }
                return false;
            });
            SortedList<Reservation> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableview_res.comparatorProperty());
            tableview_res.setItems(sortedData);
        } catch (SQLException ex) {
            Logger.getLogger(EvenementsReservations_Index_backController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (tableview_res.getItems().size() > 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("User already has a reservation for this event.");
            alert.setContentText("Please select a different user or event.");
            alert.showAndWait();
            return true;
        }
        return false;

    }

    // To check if the reservation inputted has an event that has capacite = 0
    boolean checkIfAllBooked() {
        EvenementService dao = new EvenementService();
        Evenement evenement = null;
        System.out.println("CB EVENT VALUE === "+CB_EVENT.getValue().getCapacite());
        try {

            evenement = dao.findEvenementById(CB_EVENT.getValue().getId());
        } catch (SQLException e) {
            System.out.println("SQL Exception in checkIfAllBooked()");
        }

        if (evenement.getCapacite() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Event is fully booked.");
            alert.setContentText("Please select a different event.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }

    }

    @FXML
    void goToFront(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Evenements_Index_front.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runs everytime the user changes the value of searchTextField. It filters
    // 'tableview' to only show the evenements that match the search.
    @FXML
    void search(KeyEvent event) {
        FilteredList<Evenement> filteredData = new FilteredList<>(observableList, p -> true);
        filteredData.setPredicate(evenement -> {
            if (evenement.getNom().toLowerCase().contains(searchTextField.getText().toLowerCase())) {
                return true;
            }
            return false;
        });
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);

    }
}