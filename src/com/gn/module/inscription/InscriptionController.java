/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.inscription;

import com.MVP.Entite.User;
import com.MVP.Service.UserService;
import com.MVP.Utils.JavaMailUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import com.MVP.Utils.JavaMailUtil;

/**
 * FXML Controller class
 *
 * @author The Nutorious BIG
 */
public class InscriptionController implements Initializable {

	/**
	 * Initializes the controller class.
	 */

	@FXML
	private TextField activation_tokenField;

	@FXML
	private Button verifyTokenBtn;
	@FXML
	private TextField password2;

	@FXML
	private Button loginBtn;

	@FXML
	private TextField pseudo;

	@FXML
	private DatePicker date_naissance;

	@FXML
	private TextField email;

	@FXML
	private PasswordField password;

	@FXML
	private TextField adresse;

	@FXML
	private Button backBtn;

	@FXML
	private PasswordField confirm;
	@FXML
	private Label errorLabel;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Set invisible activation_token field
		activation_tokenField.setVisible(false);
		// invisible verifyTokenBtn
		verifyTokenBtn.setVisible(false);

	}

	@FXML
	void backToLogin(ActionEvent event) {
		// Show Authentification.fxml
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/com/gn/module/user/Login.fxml"));
			Scene scene = new Scene(root);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setTitle("GameGalaxy");
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// When SignUp Button is clicked
	@FXML
	void signUp(ActionEvent event) {

		UserService su = new UserService();
		User u = new User();
		u.setPseudo(pseudo.getText());
		u.setEmail(email.getText());
		u.setPassword(password.getText());
		u.setAddresse(adresse.getText());
		u.setDate_naissance(date_naissance.getValue().toString());
		u.setRoles("['ROLE_USER']");
		// Generate activation_token
		String activation_token = BCrypt.hashpw(u.getEmail(), BCrypt.gensalt(13));
		u.setActivation_token(activation_token);
		// Send activation_token to email
		JavaMailUtil.SendAccountConfirmationMail(u.getEmail(), activation_token);
		u.setDisable_token("Disabled");
		u.setReset_token(null);
		if (isValid(email.getText()) == false) {
			// Set errorLabel
			errorLabel.setStyle("-fx-text-fill: red;");
			errorLabel.setText("Invalid Email");
		}else if(su.CheckUserRegistered(u.getEmail())) {
			// Set errorLabel
			errorLabel.setStyle("-fx-text-fill: red;");
			errorLabel.setText("Email already exists");

		}
		else if(su.checkIfPseudoExists(u.getPseudo())) {
			// Set errorLabel
			errorLabel.setStyle("-fx-text-fill: red;");
			errorLabel.setText("Pseudo already exists");
		}
		 else {
			if (password.getText().equals(confirm.getText())) {
				// Set errorLabel
				errorLabel.setText("");
				// Hash Password
				String hashed = BCrypt.hashpw(password.getText(), BCrypt.gensalt(13));
				u.setPassword(hashed);
				// Insert User
				su.signUp(u);
				// Set errorLabel
				errorLabel.setText("An email was send to your email address with a confirmation token.");
				// Set errorLabel to green color
				errorLabel.setStyle("-fx-text-fill: green;");

				// Wait 5 seconds
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Set invisible activation_token field
				activation_tokenField.setVisible(true);
				// invisible verifyTokenBtn
				verifyTokenBtn.setVisible(true);

			} else {
				// Set errorLabel
				errorLabel.setText("Passwords don't match");

			}
		}
	}

	private boolean isValid(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@FXML
	void verifyTokenBtn(ActionEvent event) {
		UserService su = new UserService();
		if (su.verifyActivationToken(activation_tokenField.getText())) {

			// Show Login.fxml
			Parent root;
			try {
				
				su.verifyAccount(su.getUserByEmail(email.getText()));
				root = FXMLLoader.load(getClass().getResource("/com/gn/module/user/Login.fxml"));
				Scene scene = new Scene(root);
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setTitle("GameGalaxy");
				window.setScene(scene);
				window.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// Set errorLabel
			errorLabel.setText("Invalid Token!");
		}

	}

	// When texfield is updated, check if verifyTokenBtn should be visible
	@FXML
	void updateVerifierBtn(KeyEvent event) {
		if (activation_tokenField.getText().length() != 60) {
			verifyTokenBtn.setVisible(false);
		} else {
			verifyTokenBtn.setVisible(true);
		}
	}
}
