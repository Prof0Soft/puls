package main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutProgrammController implements Initializable {
	
	Stage stage;
	@FXML
	private Button closeButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		closeButton.setVisible(false);

	}

	@FXML
	public void exitVista(){
		stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
