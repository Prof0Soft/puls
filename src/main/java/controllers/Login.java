package main.java.controllers;

import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.CConnectionToMySQL;
import main.java.realisation.CConfig;
import main.java.realisation.CUser;
import main.java.realisation.VistaNavigator;


public class Login implements Initializable {

	@FXML
	Label lblError;
	@FXML
	TextField txtLogin;
	@FXML
	PasswordField txtPassword;
	@FXML
	static private MainController mainController;
	private CUser newUser = null;
	/**
	 * Method set main controller
	 * @param mc - instance mainController object
	 */
	static public void setMainController(MainController mc) {
		mainController = mc;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblError.setVisible(false);

	}

	@FXML	// confirm button click on sign frame
	public void btnClickLogiIn() throws SQLException {

		String query;
		String login = "";
		String password = "";
		ResultSet resLog;

		// For debug !!!!!!!!!!!!!!!!!!!!
		if (1==1){
			newUser = new CUser(52, 3, "email", "login", "firstName",	"lastName",	"patronymic", 1, 1, "telNumber", "photo");
			signOn();
			return;
		}
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		login = txtLogin.getText();
		password = txtPassword.getText();

		CConnectionToMySQL conLogin = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".user WHERE login = \"" + login + "\" AND password = \"" + password + "\"";
		resLog = conLogin.getResultQuery(query);

		boolean isLogin = false;
		while(resLog.next()) {
			isLogin = true;
			// create new user class
			newUser = new CUser(resLog.getInt("idUser"),
					resLog.getInt("idRegion"),
					resLog.getString("email"),
					resLog.getString("login"),
					resLog.getString("firstName"),
					resLog.getString("lastName"),
					resLog.getString("patronymic"),
					resLog.getInt("levelaccess"),
					resLog.getInt("sublevelaccess"),
					resLog.getString("telNumber"),
					resLog.getString("photo"));
		}
		// if login and password incorrect
		if (!isLogin) {
			lblError.setVisible(true);
			txtPassword.setText("");
		}else {	// if login and password correct
			signOn();
		}

	}

	private void signOn(){
		CConfig.setUserMain(newUser);
		VistaNavigator.loadVista(VistaNavigator.CLEAR_FRAME);
		String s = "Добро пожаловать, " + newUser.getFirstName();
		mainController.setTextLblVelocome(s);
		mainController.setVisibleLeftMenu(true);
		mainController.setVisibleTopMenu(true);

		lblError.setVisible(false);

	}
}














