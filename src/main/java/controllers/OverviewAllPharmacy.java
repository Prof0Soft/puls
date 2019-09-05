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
import main.java.realisation.COverviewDrug;

public class OverviewAllPharmacy implements Initializable {
	ObservableList<COverviewDrug> obsOverviewDrug = FXCollections.observableArrayList();
	@FXML
	TreeTableView<COverviewDrug> treeTableView;
	@FXML 
	TreeTableColumn<COverviewDrug, String> columnPharmacy;
	@FXML
	TreeTableColumn<COverviewDrug, Integer> numberExport;
	@FXML
	DatePicker datePickerStart;
	@FXML
	DatePicker datePickerEnd;
	@FXML
	Button btnShow;
	
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
	
	
	@FXML
	public void showOverviwAll() throws SQLException {
		//clear treetableview
		treeTableView.setRoot(null);
		// clear observableList of overviewDrug
		obsOverviewDrug.clear();
		CConnectionToMySQL conPharm = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		CConnectionToMySQL conDrug = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		String query;
		ResultSet resPharm;
		ResultSet resDrug;
				
		String idRegion = String.valueOf(CConfig.getUserMainIDRegion());			// id region of user main
		String dateStart = datePickerStart.getValue().toString();					// date of range (start)
		String dateEnd = datePickerEnd.getValue().toString();						// date of range (end)
				
		// Export Like pharmacy for main user
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".pharmacy INNER JOIN likepharmacy ON pharmacy.idPharmacy = likepharmacy.idPharmacy "
				+ "where likepharmacy.idUser = " + String.valueOf(CConfig.getUserMainID()) + " AND pharmacy.idRegion = " + idRegion;
		resPharm = conPharm.getResultQuery(query);
		
		while (resPharm.next()) {
			// Export count dug for selected pharmacy
			query = "SELECT * FROM " + CConfig.NAME_BASE + ".countdrugpharm INNER JOIN drug ON countdrugpharm.idDrug = drug.idDrug " + 
					"WHERE countdrugpharm.idRegion = " + idRegion + " AND countdrugpharm.idPharmacy = " + String.valueOf(resPharm.getInt("idPharmacy")) + " AND " + 
					"countdrugpharm.dateEnter >= '" + dateStart + "' AND countdrugpharm.dateEnter <= '" + dateEnd + "'";
			resDrug = conDrug.getResultQuery(query);
			ObservableList<CDataDrug> dataDrugForPharmacy = FXCollections.observableArrayList();
			while (resDrug.next()) {
				String idDrug = String.valueOf(resDrug.getInt("idDrug"));
				String nameDrug = resDrug.getString("nameDrug");
				String countExport = String.valueOf(resDrug.getInt("countExport"));
				String dateEnter = resDrug.getDate("dateEnter").toString();
				dataDrugForPharmacy.add(new CDataDrug(idDrug, nameDrug, countExport, dateEnter));
			}
			COverviewDrug buf_overviewDrug = new COverviewDrug(resPharm.getInt("idPharmacy"), resPharm.getString("namePharmacy"), dataDrugForPharmacy);
			buf_overviewDrug.sumAllDrug();
			obsOverviewDrug.add(buf_overviewDrug);
		}
		
		//create treeTableView
		createTreeTableView(obsOverviewDrug);
		//disconnect for MySQL
		conPharm.disconnectMysSQL();
		conDrug.disconnectMysSQL();
	}
	/**
	 * Create data with treeTabeView
	 * @param obsData
	 */
	@SuppressWarnings("unchecked")
	public void createTreeTableView(ObservableList<COverviewDrug> obsData) {
		TreeItem<COverviewDrug> itemRoot_0 = new TreeItem<COverviewDrug>();
		
		for (COverviewDrug overviewDrug : obsData) {
			TreeItem<COverviewDrug> itemRoot_1 = new TreeItem<COverviewDrug>(overviewDrug);
			
			// add children item
			for (int i = 0; i < overviewDrug.getCountDrugs(); i++) {
				COverviewDrug childOverItem = overviewDrug.getOverDrug(i);
				TreeItem<COverviewDrug> itemRoot_2 = new TreeItem<COverviewDrug>(childOverItem);
				itemRoot_1.getChildren().addAll(itemRoot_2);
			}
					
			itemRoot_0.getChildren().addAll(itemRoot_1);
		}
				
		treeTableView.setShowRoot(false);
		
		treeTableView.setRoot(itemRoot_0);
		
		columnPharmacy.setCellValueFactory(new TreeItemPropertyValueFactory<COverviewDrug, String>("nameItem"));
		numberExport.setCellValueFactory(new TreeItemPropertyValueFactory<COverviewDrug, Integer>("numberExport"));
	}

}
