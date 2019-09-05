package main.java.controllers;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import main.java.CConnectionToMySQL;
import main.java.realisation.CConfig;
import main.java.realisation.CDataDrug;
import main.java.realisation.CWorkWithExcel;
import main.java.CWithString;
/**
 * Controller class for the first vista.
 */
public class AdminAddDataController {
	
	private final String IMPORT_ROW = "Всего загружено строк - ";
	private final String IMPORT_ALL_DRUG = "Найдено препаратов - ";
	private final String IMPORT_ALL_PHARM = "Найдено аптек - ";
	private final String EXPORT_ROW = "Всего выгружено строк - ";
	private final String EXPORT_DRUG = "Выгружено препаратов - ";
	private final String EXPORT_PHARM = "Выгружено аптек - ";
	private final String EXPORT_INSERT_PHARM = "Добавлено в базу аптек -";
	private final String EXPORT_SKIP_PHARM = "Пропущено аптек - ";
	private final String EXPORT_ERROR = "Ошибок - ";
	
	protected ObservableList<CDataDrug> massDataExcel = FXCollections.observableArrayList();
	@FXML
	private TableView<CDataDrug> adminTableView;
	@FXML
	private TableColumn<CDataDrug, String> idSign;
	@FXML
	private TableColumn<CDataDrug, String> idIndexInDB;
	@FXML
	private TableColumn<CDataDrug, String> idNameDrug;
	@FXML
	private TableColumn<CDataDrug, String> idDateEnterDrug;
	@FXML
	private TableColumn<CDataDrug, String> idSerialNumberTheDrug;
	@FXML
	private TableColumn<CDataDrug, String> idNumberFharmacy;
	@FXML
	private TableColumn<CDataDrug, String> idhealthlife;
	@FXML
	private TableColumn<CDataDrug, String> idNamePharmacy;
	@FXML
	private TableColumn<CDataDrug, String> idNumberStart;
	@FXML
	private TableColumn<CDataDrug, String> idNumberImport;
	@FXML
	private TableColumn<CDataDrug, String> idNumberExport;
	@FXML
	private TableColumn<CDataDrug, String> idNumberEnd;
	@FXML
	private TableColumn<CDataDrug, String> status;
	@FXML
	private Label  lblImportRow;
	@FXML
	private Label  lblImportAllDrug;
	@FXML
	private Label  lblImportAllPharm;
	@FXML
	private Label  lblExportRow;
	@FXML
	private Label  lblExportDrug;
	@FXML
	private Label  lblExportPharm;
	@FXML
	private Label  lblExportError;
	@FXML
	private Label  lblExportInsertPharm;
	@FXML
	private Label  lblExportSkipPharm;
	@FXML
	private Button btnRecordInMySQL;
		
    /**
     * for button btnLoadDataFromExcel
     * @param event
     */
    @FXML
    void onClickLoadDataFromFile(ActionEvent event) {
    	String pathChooseFile = null;
    	
    	//to zero all parameters
    	adminTableView.getItems().clear();
    	massDataExcel.clear();
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Выберите файл для импорта данных");
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
    	fileChooser.getExtensionFilters().add(extFilter);
    	File file = fileChooser.showOpenDialog(null);
    	
    	if (file != null){
    		pathChooseFile = file.getPath();	// get path to file excel
    	}
    	
    	// extract data from excel file
    	CWorkWithExcel cwrkExcel = new CWorkWithExcel(pathChooseFile);
    	cwrkExcel.mainThreadExcel();
    	massDataExcel = cwrkExcel.getMassDataExcel();
    	
    	// input in data in tableview
    	// set type and value wich safe in column
    	idSign.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("sign"));
    	idIndexInDB.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("indexInDB"));
    	idNameDrug.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("nameDrug"));
    	idDateEnterDrug.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("dateEnterDrug"));
    	idSerialNumberTheDrug.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("serialNumberTheDrug"));
    	idNumberFharmacy.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("numberFharmacy"));
    	idhealthlife.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("healthlife"));
    	idNamePharmacy.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("namePharmacy"));
    	idNumberStart.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("numberStart"));
    	idNumberImport.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("numberImport"));
    	idNumberExport.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("numberExport"));
    	idNumberEnd.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("numberEnd"));
    	status.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("status"));
    	
    	adminTableView.setItems(massDataExcel);
    	
    	// report about import data
    	int importRowCount = 0;
    	int importAllDrugCount = 0;
    	int importAllPharmCount = 0;
    	for (CDataDrug cDataDrug : massDataExcel) {
			importRowCount++;
			if (cDataDrug.getSign() == "P") {
				importAllDrugCount++;
			}
			if (cDataDrug.getSign() == "Farm") {
				importAllPharmCount++;
			}
		}
    	
    	lblImportRow.setText(IMPORT_ROW + Integer.toString(importRowCount));
    	lblImportAllDrug.setText(IMPORT_ALL_DRUG + Integer.toString(importAllDrugCount));
    	lblImportAllPharm.setText(IMPORT_ALL_PHARM + Integer.toString(importAllPharmCount));
    	lblImportRow.setVisible(true);
    	lblImportAllDrug.setVisible(true);
    	lblImportAllPharm.setVisible(true);
    	btnRecordInMySQL.setDisable(false);
    }
    
    /**
     * 	Export data in MySQL base
     * @param event
     */
    
    private int exportRowCount = 0;
    private int exportDrugCount = 0;
    private int exportPharmCount = 0;
    private int errorCount = 0;
    private int exportInsertPharmCount = 0;
    private int exportSkipPharmCount = 0;
    @FXML
	protected boolean onClickExportToMySQL(ActionEvent event) {
    	boolean isCorrect = false;
    	CConnectionToMySQL conMySQL = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
    	
    	    	
    	massDataExcel.forEach((drug) ->{
    		exportRowCount++;
    		if (drug.getSign() == "P") {
    			exportDrugCount++;
    		}
    		
    		if (drug.getSign() == "Ser") {
    			
    		}
    		
    		if (drug.getSign() == "Farm") {
    			exportPharmCount++;
    			try {
					addMySQLFarm(conMySQL, drug);
				} catch (ParseException | SQLException e) {
					e.printStackTrace();
					drug.setStatus("Error");
					errorCount++;
					
				}
    		}
    											
    	});
    	
    	conMySQL.disconnectMysSQL();
    	
    	lblExportRow.setText(EXPORT_ROW + Integer.toString(exportRowCount));
    	lblExportDrug.setText(EXPORT_DRUG + Integer.toString(exportDrugCount));
    	lblExportPharm.setText(EXPORT_PHARM + Integer.toString(exportPharmCount));
    	lblExportError.setText(EXPORT_ERROR + Integer.toString(errorCount));
    	lblExportInsertPharm.setText(EXPORT_INSERT_PHARM + Integer.toString(exportInsertPharmCount));
    	lblExportSkipPharm.setText(EXPORT_SKIP_PHARM + Integer.toString(exportSkipPharmCount)); 
    	
    	lblExportRow.setVisible(true);
    	lblExportDrug.setVisible(true);
    	lblExportPharm.setVisible(true);
    	lblExportError.setVisible(true);
    	lblExportInsertPharm.setVisible(true);
    	lblExportSkipPharm.setVisible(true);
    	    	
    	return isCorrect;
    }
    /** 
     * For Insert to MySQL with sign Farm
     * @param con 	- connection to MySQL
     * @param data 	- class with data information
     * @throws ParseException 
     * @throws SQLException 
     */
    private void addMySQLFarm(CConnectionToMySQL con, CDataDrug data) throws ParseException, SQLException {
    	ResultSet res;
    	String dateEnterDrug = CWithString.convertToMySQLDAteS(data.getDateEnterDrug());
    	String query;
    	int count = -1;
    	PreparedStatement prepareStm;
    	
    	// search id pharmacy
    	/*
    	query = "SELECT COUNT(*) AS total FROM pharmacy WHERE idRegion = " + CConfig.getNumberRegion() + " AND numberPharmacy = " + data.getNumberFharmacy();
    	res = con.getResultQuery(query);
    	if (res.next()) {
    		count = res.getInt("total");
    	}
    	*/
    	
    	query = "SELECT * FROM " + CConfig.NAME_BASE + ".pharmacy";
    	res = con.getResultQuery(query);
    	    
    	while (res.next()) {
    		if (CWithString.isSearchString(res.getString("namePharmacy"), data.getNamePharmacy(), 80)) {
    			count = 1;
    			data.setIdPharmacy(res.getInt("idPharmacy"));
    			break;
    		}
    	}
    	// if count rows = 0 add new pharmacy in table
    	if (count <= 0) {
    		query = "INSERT INTO " + CConfig.NAME_BASE + ".pharmacy (idRegion, numberPharmacy, namePharmacy) VALUES (?, ?, ?)";
    		prepareStm = con.getPrepareStm(query);
    		prepareStm.setInt(1, CConfig.getNumberRegion());
    		prepareStm.setInt(2, Integer.parseInt(data.getNumberFharmacy()));
    		prepareStm.setString(3, data.getNamePharmacy());
    		prepareStm.execute();
    		
    		query = "SELECT * FROM " + CConfig.NAME_BASE + ".pharmacy WHERE idRegion = " + CConfig.getNumberRegion() + " AND numberPharmacy = " + data.getNumberFharmacy();
        	res = con.getResultQuery(query);
        	res.next();
        	data.setIdPharmacy(res.getInt("idPharmacy"));
    	}
    	
    	/*query = "SELECT * FROM pharmacy WHERE idRegion = " + CConfig.getNumberRegion() + " AND numberPharmacy = " + data.getNumberFharmacy();
    	res = con.getResultQuery(query);
    	if (res.next()) {
    		idPharmacyMySQL = res.getInt("idPharmacy");
    	}
    	*/
    	// count fields of table countdrugpharm
    	// !!!!!!!!!! не хватает поиска по id препарата
    	query = "SELECT COUNT(*) AS total FROM " + CConfig.NAME_BASE + ".countdrugpharm WHERE dateEnter = '" + dateEnterDrug + "' AND idRegion = " + CConfig.getNumberRegion()
    					+ " AND idPharmacy = " + data.getIdPharmacy() + " AND idDrug = " + data.getIndexInDB() + " AND countExport = " + data.getNumberExport();
    	
    	
    	res = con.getResultQuery(query);
    	if (res.next()) {
    		count = res.getInt("total");
    	}
    	
    	// insert data if record is empty
    	if (count == 0) {
    		query = "INSERT INTO " + CConfig.NAME_BASE + ".countdrugpharm (idRegion, idPharmacy, idDrug, dateEnter, countExport) VALUES (?, ?, ?, ?, ?)";
    		prepareStm = con.getPrepareStm(query);
    		prepareStm.setInt(1, CConfig.getNumberRegion());
    		prepareStm.setInt(2, data.getIdPharmacy());
    		prepareStm.setInt(3, Integer.parseInt(data.getIndexInDB()));
    		prepareStm.setDate(4, CWithString.convertToMySQLDAteD(dateEnterDrug));
    		prepareStm.setInt(5, Integer.parseInt(data.getNumberExport()));
    		prepareStm.execute();
    		exportInsertPharmCount++;
    	}else {
    		data.setStatus("Skip");
    		exportSkipPharmCount++;
    	}
    }
    
    //for testing
    public ObservableList<CDataDrug> getMassDataExcel() {
		return massDataExcel;
	}
    //for testing
	public void setMassDataExcel(ObservableList<CDataDrug> massDataExcel) {
		this.massDataExcel = massDataExcel;
	}

}






































