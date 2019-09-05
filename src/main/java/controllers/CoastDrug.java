package main.java.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import main.java.CConnectionToMySQL;
import main.java.realisation.CConfig;
import main.java.realisation.CDataDrug;

public class CoastDrug implements Initializable {
	ObservableList<CDataDrug> obsCheangeData = FXCollections.observableArrayList();
	@FXML
	TableView<CDataDrug> tableView;
	@FXML
	TableColumn<CDataDrug, String> tvcNameDrug;
	@FXML
	TableColumn<CDataDrug, Double> tvcCoastDrug;
	@FXML
	Button saveData;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// import data for drug
		CConnectionToMySQL con = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		ObservableList<CDataDrug> obsDrug = FXCollections.observableArrayList();
		String query;
		ResultSet res;
		
		query = "SELECT * FROM " + CConfig.NAME_BASE + ".drug";
		res = con.getResultQuery(query);
		try {
			while (res.next()) {
				String id = String.valueOf(res.getInt("idDrug"));
				String name = res.getString("nameDrug");
				Double coast = res.getDouble("coastDrug");
				obsDrug.add(new CDataDrug(id, name, coast));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		tvcCoastDrug.setEditable(true);
		tvcCoastDrug.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		tvcCoastDrug.setOnEditCommit(
				new EventHandler<CellEditEvent<CDataDrug, Double>>() {
					@Override
					public void handle(CellEditEvent<CDataDrug, Double> event) {
						Double oldV = event.getOldValue();
						Double newV = event.getNewValue();
						if (oldV.doubleValue() != newV.doubleValue()) {
							((CDataDrug) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCoastDrug(event.getNewValue());
							saveData.setDisable(false);
							CDataDrug dataDrug = ((CDataDrug) event.getTableView().getItems().get(event.getTablePosition().getRow()));
							obsCheangeData.add(dataDrug);
						}
					}
				}
				);
		
		tvcNameDrug.setCellValueFactory(new PropertyValueFactory<CDataDrug, String>("nameDrug"));
		tvcCoastDrug.setCellValueFactory(new PropertyValueFactory<CDataDrug, Double>("coastDrug"));
						
		tableView.setItems(obsDrug);
						
		con.disconnectMysSQL();
	}
	
	
	@FXML
	public void btnClickSaveCheange() {
		CConnectionToMySQL con = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
		String query;
		PreparedStatement prepStm;
		
		
		for (CDataDrug cDataDrug : obsCheangeData) {
			query = "UPDATE " + CConfig.NAME_BASE + ".drug SET coastDrug = " + cDataDrug.getCoastDrug().toString() + " WHERE  idDrug = " + cDataDrug.getIndexInDB();
			try {
				prepStm = con.getPrepareStm(query);
				prepStm.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		con.disconnectMysSQL();	
		saveData.setDisable(true);
	}
	

}





























