package main.java.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.CConnectionToMySQL;
import main.java.realisation.CConfig;
import main.java.realisation.CPharmacy;
import main.java.realisation.CUser;

public class AddLikePharmacy implements Initializable{
	
	ObservableList<CUser> obserUsers = FXCollections.observableArrayList();
	ObservableList<CPharmacy> likePharmacy = FXCollections.observableArrayList();
	@FXML
	TableView<CPharmacy> pharmacyTableView;
	@FXML
	TableColumn< CPharmacy, String> namePharmacy;
	@FXML
	Button btnSave;
	
	@FXML
	private ListView<CUser> userList; 
	
	CUser userSelectList = new CUser(-1);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CConnectionToMySQL con = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		String query;
		ResultSet res;
		
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".user WHERE levelaccess = 4 and idregion = " + CConfig.getNumberRegion();
		res = con.getResultQuery(query);
		try {
			while (res.next()) {
				obserUsers.add(new CUser(
											res.getInt("idUser"), 
											res.getInt("idregion"), 
											res.getString("email"), 
											res.getString("login"), 
											res.getString("firstName"), 
											res.getString("lastName"), 
											res.getString("patronymic"), 
											res.getInt("levelaccess"),
											res.getInt("sublevelaccess"),
											res.getString("telNumber"), 
											res.getString("photo")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userList.setItems(obserUsers);
		userList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				
		// get data about all pharmacy in DB and paint in tableview
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".pharmacy";
		res = con.getResultQuery(query);
		
		try {
			while (res.next()) {
				likePharmacy.add(new CPharmacy(res.getInt("idPharmacy"), res.getString("namePharmacy"), "null", null, res.getInt("idRegion"), 0.0));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//add data to ObservableList 
		namePharmacy.setCellValueFactory(new PropertyValueFactory<CPharmacy, String>("fullNamePharmacy"));
		
		//Add for tableView listener at change data of like Pharmacy
		 //The pseudo classes 'up' and 'down' that were defined in the css file.
	    PseudoClass isLike = PseudoClass.getPseudoClass("up");
	    PseudoClass isNotLike = PseudoClass.getPseudoClass("down");
		
		
		pharmacyTableView.setRowFactory(tableView -> {
			// on dbl Click on row tableView
			TableRow<CPharmacy> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) && userSelectList.getIdUser() > 0 ) {
                    CPharmacy rowData = row.getItem();
                    onDblCellClick_CheangeLikePharmacy(rowData);
                }
            });
			// Change color depending on like pharmacy
	        ChangeListener<Number> changeListener = (obs, oldVal, newVal) -> {
	            row.pseudoClassStateChanged(isLike, newVal.doubleValue() > 0);
	            row.pseudoClassStateChanged(isNotLike, newVal.doubleValue() <= 0);
	        };

	        row.itemProperty().addListener((obs, previousStock, currentStock) -> {
	        	if (previousStock != null) {
	                previousStock.getIsLikeChangeProperty().removeListener(changeListener);
	            }
	            if (currentStock != null) {
	                currentStock.getIsLikeChangeProperty().addListener(changeListener);
	                row.pseudoClassStateChanged(isLike, currentStock.getIsLikeChange() > 0);
	                row.pseudoClassStateChanged(isNotLike, currentStock.getIsLikeChange() <= 0);
	            } else {
	                row.pseudoClassStateChanged(isLike, false);
	                row.pseudoClassStateChanged(isNotLike, false);
	            }
	        });
	        return row;
	    });
				
		pharmacyTableView.setItems(likePharmacy);
		
		con.disconnectMysSQL();
	}
		
	@FXML 
	public void onMouseClickedUserList() throws SQLException {
		CConnectionToMySQL con = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		String query;
		ResultSet res;
		
		//CUser user = userList.getSelectionModel().getSelectedItem();
		userSelectList = userList.getSelectionModel().getSelectedItem();
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".likepharmacy WHERE idUser = " + String.valueOf(userSelectList.getIdUser());
		res = con.getResultQuery(query);
					
		for (CPharmacy pharm: pharmacyTableView.getItems()) {
			pharm.setIslike(0.0);
		}
		
		int idLikePharmacy = -1;
		while (res.next()) {
			idLikePharmacy = res.getInt("idPharmacy");
			for (CPharmacy pharm: pharmacyTableView.getItems()) {
				if (idLikePharmacy == pharm.getIdPharmacy()) {
					pharm.setIslike(1.0);
				}
			}
		}
		
		con.disconnectMysSQL();	
		btnSave.setDisable(true);
	}
		
	public void onDblCellClick_CheangeLikePharmacy( CPharmacy pharm) {
		btnSave.setDisable(false);
		if (pharm.getIsLike() == 0.0) {
			pharm.setIsLikeChange(2.0);
		}else {
			pharm.setIsLikeChange(0.0);
		}
	}
	
	@FXML
	public void onSaveChangeLikePharmacy() {
		CConnectionToMySQL con = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		String query;
		PreparedStatement preStm;
		
		query = "DELETE FROM " + CConfig.NAME_BASE + ".likepharmacy WHERE  `idUser`=" + String.valueOf(userSelectList.getIdUser());
		try {
			preStm = con.getPrepareStm(query);
			preStm.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (CPharmacy pharm : pharmacyTableView.getItems()) {
			if (pharm.getIsLike() > 0) {
				query = "INSERT INTO " + CConfig.NAME_BASE + ".likepharmacy (idUser, idPharmacy) VALUES (?, ?)";
				try {
					preStm = con.getPrepareStm(query);
					preStm.setInt(1, userSelectList.getIdUser());
					preStm.setInt(2, pharm.getIdPharmacy());
					preStm.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		con.disconnectMysSQL();
		btnSave.setDisable(true);
	}
}

































