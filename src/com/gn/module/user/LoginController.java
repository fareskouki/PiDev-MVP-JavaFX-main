/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.user;

import com.MVP.Service.UserService;
import com.MVP.Utils.UserSession;
import com.gn.App;
import com.gn.global.plugin.ViewManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author The Nutorious BIG
 */
public class LoginController implements Initializable {
	private UserService UserService;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginBtn;
	@FXML
	private Label errorLabel;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	// Called when the user clicks on the login button
	@FXML
	void login(ActionEvent event) {

		UserService = new UserService();
		String emailText = email.getText();
		String passwordText = password.getText();
		if (!emailText.isEmpty() || !passwordText.isEmpty()) {
			if (UserService.login(emailText, passwordText)) // Returns true if email & password are verified & if user is enabled
			{ System.out.println("login success");
				// Load the home page
					if(UserService.getRole(emailText).equals("['ROLE_ADMIN']")) {
                                            App.decorator.setContent(ViewManager.getInstance().get("main"));
					}
					else if(UserService.getRole(emailText).equals("['ROLE_USER']")) {
                                            App.decorator.setContent(ViewManager.getInstance().get("Front"));
					}
					
						
					
				
			}
		} else {
			// Update errorLabel
			errorLabel.setText("Email or password is incorrect");
			// Change color of errorLabel
			errorLabel.setStyle("-fx-text-fill: red");
		}

	}
@FXML
	void inscription(ActionEvent event) {

Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/com/gn/module/inscription/Inscription.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage();
			window.setTitle("GameGalaxy");
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
}
