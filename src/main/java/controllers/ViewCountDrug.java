package main.java.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import main.java.CConnectionToMySQL;
import main.java.realisation.CConfig;
import main.java.realisation.CDataDrug;
import main.java.realisation.CPharmacy;

public class ViewCountDrug implements Initializable {
	ObservableList<CDataDrug> obsDrug = FXCollections.observableArrayList();
	@FXML
	TreeTableView<CDataDrug> treeTableView;
	@FXML
	TreeTableColumn<CDataDrug, String> clmnNameDrug;
	@FXML
	TreeTableColumn<CDataDrug, String> clmnCount;
	@FXML
	TreeTableColumn<CDataDrug, Double> clmnCoast;
	@FXML
	TreeTableColumn<CDataDrug, Double> clmnCoastAll;
	@FXML
	DatePicker datePickerStart;
	@FXML
	DatePicker datePickerEnd;
	@FXML
	Button btnShow;
	
	/**
	 * On click button start function for export excel. Export possible only when press 
	 * button submit for report.
	 */
	@FXML
	public void btnExportExcel() {
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// add true date in DatePicker
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy");
		datePickerStart.setPromptText("03." + dateFormat.format(date));
		Calendar calendar = Calendar.getInstance();
		int day = 1;
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		LocalDate dateStart = LocalDate.of(year, month, day);
		datePickerStart.setValue(dateStart);
		datePickerStart.setFocusTraversable(false);
			
		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		datePickerEnd.setPromptText(dateFormat.format(date));
		datePickerEnd.setValue(LocalDate.now());
		datePickerEnd.setFocusTraversable(false);
		
		btnShow.requestFocus();
	}
	
	
	/**
	 * Export and show need information for drug
	 * @throws SQLException 
	 */
	@FXML
	public void btnShowDrugClick() throws SQLException {
		//clear treetableview
		treeTableView.setRoot(null);
		// clear observableList of overviewDrug
		obsDrug.clear();
		CConnectionToMySQL conPharm = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		CConnectionToMySQL conDrug = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		String query;
		ResultSet resPharm;
		ResultSet resDrug;
				
		String idRegion = String.valueOf(CConfig.getUserMainIDRegion());			// id region of user main
		String dateStart = datePickerStart.getValue().toString();					// date of range (start)
		String dateEnd = datePickerEnd.getValue().toString();						// date of range (end)
				
		// Export All drug
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".drug";
		resDrug = conDrug.getResultQuery(query);
		
		while (resDrug.next()) {
			// Export count dug for selected pharmacy
			/*query = "SELECT * FROM countdrugpharm INNER JOIN pharmacy ON countdrugpharm.idPharmacy = pharmacy.idPharmacy WHERE idDrug = " + resDrug.getInt("idDrug") + " AND idRegion = " + idRegion + 
					" AND dateEnter >= '" + dateStart + "' AND .dateEnter <= '" + dateEnd + "'";
			*/
			
			query = "SELECT * FROM " + CConfig.NAME_BASE + ".countdrugpharm INNER JOIN drug ON countdrugpharm.idDrug = drug.idDrug INNER JOIN pharmacy ON countdrugpharm.idPharmacy = pharmacy.idPharmacy " + 
					"WHERE countdrugpharm.idRegion = " + idRegion + " AND countdrugpharm.idDrug = " + String.valueOf(resDrug.getInt("idDrug")) + " AND " +
					"countdrugpharm.dateEnter >= '" + dateStart + "' AND countdrugpharm.dateEnter <= '" + dateEnd + "'";
			
			
			resPharm = conPharm.getResultQuery(query);
			ObservableList<CPharmacy> dataPharmacy = FXCollections.observableArrayList();
			while (resPharm.next()) {
				int idPharmacy = resPharm.getInt("idPharmacy");
				String fullNamePharmacy = "null";
				String namePharmacy = resPharm.getString("namePharmacy");
				String numberPharmacy = String.valueOf(resPharm.getInt("numberPharmacy"));
				int idRegion1 = resPharm.getInt("idRegion");
				int exportCount = resPharm.getInt("countExport");
				Double isLike = 0.0;
				boolean visible = true;
				dataPharmacy.add(new CPharmacy(idPharmacy, fullNamePharmacy, namePharmacy, numberPharmacy, idRegion1, isLike, exportCount, visible));				
			}
			CDataDrug buf_Drug = new CDataDrug();
			buf_Drug.setIndexInDB(String.valueOf(resDrug.getInt("idDrug")));
			buf_Drug.setNameDrug(resDrug.getString("nameDrug"));
			buf_Drug.setCoastDrug(resDrug.getDouble("coastDrug"));
			buf_Drug.setPharmacy(dataPharmacy);
			buf_Drug.summExportCount();
			buf_Drug.summAllCoast();
			obsDrug.add(buf_Drug);
		}
		
		//create treeTableView
		createTreeTableView(obsDrug);
		//disconnect for MySQL
		conPharm.disconnectMysSQL();
		conDrug.disconnectMysSQL();
	}
	
	/**
	 * Create data with treeTabeView
	 * @param obsData
	 */
	@SuppressWarnings("unchecked")
	public void createTreeTableView(ObservableList<CDataDrug> obsData) {
		TreeItem<CDataDrug> itemRoot_0 = new TreeItem<CDataDrug>();
		
		for (CDataDrug overviewDrug : obsData) {
			TreeItem<CDataDrug> itemRoot_1 = new TreeItem<CDataDrug>(overviewDrug);
			
			// add children item
			for (int i = 0; i < overviewDrug.getCountPharmacy(2); i++) {
				CDataDrug childOverItem = overviewDrug.getChildDrug(i);
				TreeItem<CDataDrug> itemRoot_2 = new TreeItem<CDataDrug>(childOverItem);
				itemRoot_1.getChildren().addAll(itemRoot_2);
			}
					
			itemRoot_0.getChildren().addAll(itemRoot_1);
		}
				
		treeTableView.setShowRoot(false);
		
		treeTableView.setRoot(itemRoot_0);
		
		clmnNameDrug.setCellValueFactory(new TreeItemPropertyValueFactory<CDataDrug, String>("nameDrug"));
		clmnCount.setCellValueFactory(new TreeItemPropertyValueFactory<CDataDrug, String>("numberExport"));
		clmnCoast.setCellValueFactory(new TreeItemPropertyValueFactory<CDataDrug, Double>("coastDrug"));
		clmnCoastAll.setCellValueFactory(new TreeItemPropertyValueFactory<CDataDrug, Double>("summCoast"));
	}
}


































