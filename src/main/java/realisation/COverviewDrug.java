package main.java.realisation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class COverviewDrug {
	/**
	 * Constructor number 1
	 * @param idItemInDB
	 * @param nameItem
	 * @param obsDrugs
	 */
	public COverviewDrug(int idItemInDB, String nameItem, ObservableList<CDataDrug> obsDrugs) {
		super();
		this.idItemInDB = new SimpleIntegerProperty(idItemInDB);
		this.nameItem = new SimpleStringProperty(nameItem);
		this.obsDrugs = obsDrugs;
		this.numberExport = new SimpleIntegerProperty(0);
	}
	/**
	 * Constructor number two
	 * @param idItemInDB
	 * @param nameItem
	 * @param numberExport
	 */
	public COverviewDrug(int idItemInDB, String nameItem, int numberExport) {
		super();
		this.idItemInDB = new SimpleIntegerProperty(idItemInDB);
		this.nameItem = new SimpleStringProperty(nameItem);
		this.obsDrugs = null;
		this.numberExport = new SimpleIntegerProperty(numberExport);
	}
	
	private SimpleIntegerProperty idItemInDB;
	private SimpleStringProperty nameItem;
	private ObservableList<CDataDrug> obsDrugs;
	private SimpleIntegerProperty numberExport;
		
	
	
	public int getCountDrugs() {
		return obsDrugs.size();
	}
	
	/**
	 * Generate COverviewDrug data for export in table. Take drug with index of obsDrugs and 
	 * @param index
	 * @return
	 */
	public COverviewDrug getOverDrug(int index) {
		COverviewDrug oDrug;
		CDataDrug dataDrug = obsDrugs.get(index); 
		
		int id = Integer.parseInt((dataDrug.getIndexInDB()));
		String nameItem = dataDrug.getNameDrug();
		int numberExport = Integer.parseInt(dataDrug.getNumberExport());
				
		oDrug = new COverviewDrug(id, nameItem, numberExport);
		
		return oDrug;
	}
		
	/**
	 * Search all sum for all drug
	 */
	public void sumAllDrug() {
		int count = 0;
		for (CDataDrug dataDrug : obsDrugs) {
			count = count + Integer.parseInt(dataDrug.getNumberExport());
		}
		numberExport.set(count);
	}
	
	/**
	 * @return the countDrug
	 */
	public int getNumberExport() {
		return numberExport.get();
	}
	/**
	 * @param countDrug the countDrug to set
	 */
	public void setNumberExport(int numberExport) {
		this.numberExport.set(numberExport);
	}
	/**
	 * @return the obsDrugs
	 */
	public ObservableList<CDataDrug> getObsDrugs() {
		return obsDrugs;
	}
	/**
	 * @param obsDrugs the obsDrugs to set
	 */
	public void setObsDrugs(ObservableList<CDataDrug> obsDrugs) {
		this.obsDrugs = obsDrugs;
	}
	/**
	 * @return the idDrug
	 */
	public int getIdDrug() {
		return idItemInDB.get();
	}
	/**
	 * @param idDrug the idDrug to set
	 */
	public void setIdDrug(int idDrug) {
		this.idItemInDB.set(idDrug);
	}
	/**
	 * @return the nameDrug
	 */
	public String getNameItem() {
		return nameItem.get();
	}
	/**
	 * @param nameDrug the nameDrug to set
	 */
	public void setNameDrug(String nameDrug) {
		this.nameItem.set(nameDrug);
	}

	
}
