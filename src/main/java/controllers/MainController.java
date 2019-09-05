package main.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.java.realisation.CButtonMenu;
import main.java.realisation.CConfig;
import main.java.realisation.VistaNavigator;

/**
 * Main controller class for the entire layout.
 */
public class MainController implements Initializable {

    /** Holder of a switchable vista. */
    @FXML
    private StackPane vistaHolder;
    @FXML
    private Label lblVelcome;
    @FXML
    private Accordion leftPanelVBox;
    @FXML
    private HBox topPanelHBox;
    @FXML
    private ImageView imgLogin;
    @FXML
	private Button btnOverviewAllPharmacy;
    @FXML
    private Button btnViewCountDrug;
	@FXML
	private Button btnPanelAdminAdddata;
	@FXML
	private Button btnCoastDrug;
	@FXML
	private Button btnAddLikePharmacy;
	@FXML
	private Button btnAboutProgramm;

    // array list for overview menu
    ObservableList<CButtonMenu> massBtnMenu = FXCollections.observableArrayList();

    @FXML
    void load_PANEL_ADMIN_ADDDATA(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.PANEL_ADMIN_ADDDATA);
    }
    @FXML
    void load_ADD_LIKE_PHARMACY(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.ADD_LIKE_PHARMACY);
    }
    @FXML
    void load_OVERVIEW_ALL_PHARMACY(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.OVERVIEW_ALL_PHARMACY);
    }
    @FXML
    void load_COAST_DRUG(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.COAST_DRUG);
    }
    @FXML
    void load_VIEW_COUNT_DRUG(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.VIEW_COUNT_DRUG);
    }
    @FXML
    void load_ABOUT_PROGRAMM(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.ABOUT_PROGRAMM);
    }


    /**
     * CONSTRUCTOR
     * Give class Login link on MainController
     */
    public MainController() {
    	Login.setMainController(this);
    }

    /**
     * initialize method for MainController
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	setVisibleLeftMenu(false);
    	setVisibleTopMenu(false);

    	// create button menu massive
    	CButtonMenu btn1 = new CButtonMenu(btnOverviewAllPharmacy, 3);
    	CButtonMenu btn2 = new CButtonMenu(btnViewCountDrug, 4);
    	CButtonMenu btn3 = new CButtonMenu(btnPanelAdminAdddata, 0);
    	CButtonMenu btn4 = new CButtonMenu(btnCoastDrug, 3);
    	CButtonMenu btn5 = new CButtonMenu(btnAddLikePharmacy, 3);
    	CButtonMenu btn6 = new CButtonMenu(btnAboutProgramm, 99);

    	massBtnMenu.add(btn1);
    	massBtnMenu.add(btn2);
    	massBtnMenu.add(btn3);
    	massBtnMenu.add(btn4);
    	massBtnMenu.add(btn5);
    	massBtnMenu.add(btn6);

    	int k = leftPanelVBox.getPanes().size();
    	TitledPane tlpane = leftPanelVBox.getPanes().get(0);

    	ObservableList<Node> listAccordion = tlpane.getChildrenUnmodifiable();


    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle(String.valueOf(k));

    	alert.show();
    }

    /**
     * Set visible for left Menu program
     * @param value - parameter of visible
     */
    public void setVisibleLeftMenu(boolean value) {
    	if (value) {
    		Image image = new Image("main/resources/IMG/user.png");
    		imgLogin.setImage(image);
    		leftPanelVBox.setPrefWidth(200);
        	leftPanelVBox.setVisible(true);

        	// visible need button for menu
        	for (CButtonMenu cButtonMenu : massBtnMenu) {
        		cButtonMenu.instVisible(CConfig.getUserMain().getLevelaccess());
			}
    	}else {
    		leftPanelVBox.setPrefWidth(0);
        	leftPanelVBox.setVisible(false);
    	}
    }

    /**
     * Set visible for top Menu program
     * @param value - parameter of visible
     */
    public void setVisibleTopMenu(boolean value) {
    	if (value) {
    		topPanelHBox.setPrefHeight(33);
        	topPanelHBox.setVisible(true);
    	}else {
    		topPanelHBox.setPrefHeight(0);
    		topPanelHBox.setVisible(false);
    	}
    }


    /**
     * Method set text on label velcome
     * @param text - changing text
     */
    public void setTextLblVelocome(String text) {
    	lblVelcome.setText(text);
    }

    public ImageView getImgLogin() {
    	return imgLogin;
    }


    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
    	vistaHolder.getChildren().setAll(node);
    }



}