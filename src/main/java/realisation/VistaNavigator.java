package main.java.realisation;
import javafx.fxml.FXMLLoader;
import main.java.controllers.MainController;

import java.io.IOException;

/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class VistaNavigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
	public static final String pathFXML = "/main/resources/FXML/";
    public static final String MAIN    = pathFXML + "main.fxml";
    public static final String PANEL_ADMIN_ADDDATA = pathFXML +  "admin_addData.fxml";
    public static final String ADD_LIKE_PHARMACY = pathFXML +  "addLikePharmacy.fxml";
    public static final String OVERVIEW_ALL_PHARMACY = pathFXML +  "overviewAllPharmacy.fxml";
    public static final String COAST_DRUG = pathFXML +  "coastDrug.fxml";
    public static final String VIEW_COUNT_DRUG = pathFXML +  "viewCountDrug.fxml";
    public static final String LOGIN = pathFXML +  "login.fxml";
    public static final String CLEAR_FRAME = pathFXML +  "clearFrame.fxml";
    public static final String ABOUT_PROGRAMM = pathFXML +  "aboutProgramm.fxml";


    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     *
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     *
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     *   cache FXMLLoaders
     *   cache loaded vista nodes, so they can be recalled or reused
     *   allow a user to specify vista node reuse or new creation
     *   allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadVista(String fxml) {
        try {
            mainController.setVista(FXMLLoader.load(VistaNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}