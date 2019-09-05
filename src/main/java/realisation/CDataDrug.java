package main.java.realisation;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Container for extracted data from Excel file
 * @author Borsuk S.V.
 *
 */
public class CDataDrug {
	/**
	 * Constructor #0
	 */
	public CDataDrug() {
		
	}
	/**
	 * Constructor #1
	 */
	public CDataDrug(String sign, String nameDrug, String dateEnterDrug, String serialNumberTheDrug, String indexInDB,
			String numberFharmacy, String healthlife, String namePharmacy, String numberStart, String numberImport,
			String numberExport, String numberEnd) {
		super();
				
		this.sign = sign;
		this.nameDrug = new SimpleStringProperty(nameDrug);
		this.setDateEnterDrug(dateEnterDrug);
		this.serialNumberTheDrug = serialNumberTheDrug;
		this.indexInDB = indexInDB;
		this.numberFharmacy = numberFharmacy;	// delete zero after dot
		this.healthlife = healthlife;
		this.namePharmacy = namePharmacy;
		this.numberStart = numberStart;			// delete zero after dot
		this.numberImport = numberImport;		// delete zero after dot
		this.numberExport = numberExport;		// delete zero after dot
		this.numberEnd = numberEnd;
		
		correctData();
	}
	
	/**
	 * Constructor #2
	 */
	public CDataDrug(String indexInDB, String nameDrug,  String numberExport, String dateEnterDrug) {
		super();
				
		this.sign = null;
		this.nameDrug = new SimpleStringProperty(nameDrug);
		this.dateEnterDrug = dateEnterDrug;
		this.serialNumberTheDrug = null;
		this.indexInDB = indexInDB;
		this.numberFharmacy = "";			
		this.healthlife = null;
		this.namePharmacy = "";
		this.numberStart = "";			
		this.numberImport = "";			
		this.numberExport = numberExport;	
		this.numberEnd = "";
		
		correctData();
	}
		
	/**
	 * Constructor #3
	 */
	public CDataDrug(String indexInDB, String nameDrug,  Double coastDrug) {
		super();
			
		this.indexInDB = indexInDB;
		this.sign = null;
		this.nameDrug = new SimpleStringProperty(nameDrug);
		this.dateEnterDrug = null;
		this.serialNumberTheDrug = null;
		this.numberFharmacy = "";			
		this.healthlife = null;
		this.namePharmacy = "";
		this.numberStart = "";			
		this.numberImport = "";			
		this.numberExport = "";	
		this.numberEnd = "";
		this.coastDrug = new SimpleDoubleProperty(coastDrug);
	}
	
	private String sign;
	private SimpleStringProperty nameDrug = new SimpleStringProperty();
	private String dateEnterDrug;
	private String serialNumberTheDrug;
	private String indexInDB;
	private String numberFharmacy;
	private String healthlife;
	private String namePharmacy;
	private String numberStart;
	private String numberImport;
	private String numberExport;
	private String numberEnd;
	private SimpleDoubleProperty coastDrug = new SimpleDoubleProperty();
	private int idPharmacy;
	private SimpleStringProperty status = new SimpleStringProperty();
	private ObservableList<CPharmacy> pharmacy = FXCollections.observableArrayList();
	private SimpleDoubleProperty summCoast = new SimpleDoubleProperty();
	
	
	
	/**
	 * Calculates all summ coast of pharmacy data
	 */
	public void summAllCoast() {
		this.summCoast.set(Double.parseDouble(numberExport) * coastDrug.get());
	}
	
	/**
	 * Generate othe CDataDrug from field pharmacy 
	 * @param index
	 * @return
	 */
	public CDataDrug getChildDrug(int index) {
		CDataDrug chDrug;
		CPharmacy dataPharmacy = pharmacy.get(index);
				
		String nameItem = dataPharmacy.getNamePharmacy();
		int exportCount = dataPharmacy.getExportCount();
				
		chDrug = new CDataDrug();
		chDrug.setNameDrug(nameItem);
		chDrug.setNumberExport(Integer.toString(exportCount));
		chDrug.setCoastDrug(coastDrug.get());
		chDrug.summAllCoast();
		
		return chDrug;
	}
	
	public void summExportCount() {
		int summ = 0;
		
		for (CPharmacy cPharmacy : pharmacy) {
			summ += cPharmacy.getExportCount();
		}
		numberExport = String.valueOf(summ);
	}
	
	/**
	 * Search count of pharmcy with sign:
	 * 1 - all pharmacy
	 * 2 - only pharmacy with sign visible = true
	 * @param signCount
	 * @return
	 */
	public int getCountPharmacy(int signCount) {
		int count = 0;
		if (signCount == 1) {
			count = pharmacy.size();
		}
		if (signCount == 2) {
			for (CPharmacy cPharmacy : pharmacy) {
				if (cPharmacy.getVisible()) {
					count ++;
				}
			}
		}
		return count;
	}
	
	
	/**
	 * @return the summCoast
	 */
	public Double getSummCoast() {
		return summCoast.get();
	}
	/**
	 * @param summCoast the summCoast to set
	 */
	public void setSummCoast(Double summCoast) {
		this.summCoast.set(summCoast);
	}
	/**
	 * @return the summCoast
	 */
	public SimpleDoubleProperty getSummCoastProperty() {
		return summCoast;
	}
	/**
	 * @param summCoast the summCoast to set
	 */
	public void setSummCoastProperty(SimpleDoubleProperty summCoast) {
		this.summCoast = summCoast;
	}
	/**
	 * @return the pharmacy
	 */
	public ObservableList<CPharmacy> getPharmacy() {
		return pharmacy;
	}

	/**
	 * @param pharmacy the pharmacy to set
	 */
	public void setPharmacy(ObservableList<CPharmacy> pharmacy) {
		this.pharmacy = pharmacy;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status.get();
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status.set(status);
	}
	
	
	/**
	 * @param nameDrug the nameDrug to set
	 */
	public void setNameDrug(SimpleStringProperty nameDrug) {
		this.nameDrug = nameDrug;
	}

	/**
	 * @param coastDrug the coastDrug to set
	 */
	public void setCoastDrug(SimpleDoubleProperty coastDrug) {
		this.coastDrug = coastDrug;
	}

	/**
	 * @return the coastDrug
	 */
	public Double getCoastDrug() {
		return coastDrug.get();
	}

	/**
	 * @param coastDrug the coastDrug to set
	 */
	public void setCoastDrug(Double coastDrug) {
		this.coastDrug.set(coastDrug);
	}

	/**
	 * @return the idPharmacy
	 */
	public int getIdPharmacy() {
		return idPharmacy;
	}


	/**
	 * @param idPharmacy the idPharmacy to set
	 */
	public void setIdPharmacy(int idPharmacy) {
		this.idPharmacy = idPharmacy;
	}


	private void correctData() {
		if (numberFharmacy.trim() != "" && numberFharmacy != null && numberFharmacy != "null")
			numberFharmacy = String.valueOf(Math.round((float) Double.parseDouble(numberFharmacy.trim())));		// delete zero after dot
		else
			numberFharmacy = "-1";
		
		if (numberStart.trim() != "" && numberStart != null && numberStart != "null")
			numberStart = String.valueOf(Math.round((float) Double.parseDouble(numberStart.trim())));			// delete zero after dot
		
		if (numberImport.trim() != "" && numberImport != null && numberImport != "null")
			numberImport = String.valueOf(Math.round((float) Double.parseDouble(numberImport.trim())));			// delete zero after dot
		
		if (numberExport.trim() != "" && numberExport != null && numberExport != "null")
			numberExport = String.valueOf(Math.round((float) Double.parseDouble(numberExport.trim())));			// delete zero after dot
		
		if (numberEnd.trim() != "" && numberEnd != null && numberEnd != "null")
			numberEnd = String.valueOf(Math.round((float) Double.parseDouble(numberEnd.trim())));				// delete zero after dot
	}
	
	
	/**
	 * @return the serialNumberTheDrug
	 */
	public String getSerialNumberTheDrug() {
		return serialNumberTheDrug;
	}
	/**
	 * @param serialNumberTheDrug the serialNumberTheDrug to set
	 */
	public void setSerialNumberTheDrug(String serialNumberTheDrug) {
		this.serialNumberTheDrug = serialNumberTheDrug;
	}
	/**
	 * @return the series
	 */
	public String getIndexInDB() {
		return indexInDB;
	}
	/**
	 * @param series the series to set
	 */
	public void setIndexInDB(String series) {
		this.indexInDB = series;
	}
	/**
	 * @return the numberFharmacy
	 */
	public String getNumberFharmacy() {
		return numberFharmacy;
	}
	/**
	 * @param numberFharmacy the numberFharmacy to set
	 */
	public void setNumberFharmacy(String numberFharmacy) {
		this.numberFharmacy = numberFharmacy;
	}
	/**
	 * @return the healthlife
	 */
	public String getHealthlife() {
		return healthlife;
	}
	/**
	 * @param healthlife the healthlife to set
	 */
	public void setHealthlife(String healthlife) {
		this.healthlife = healthlife;
	}
	/**
	 * @return the namePhatmacy
	 */
	public String getNamePharmacy() {
		return namePharmacy;
	}
	/**
	 * @param namePhatmacy the namePhatmacy to set
	 */
	public void setNamePharmacy(String namePhatmacy) {
		this.namePharmacy = namePhatmacy;
	}
	/**
	 * @return the numberStart
	 */
	public String getNumberStart() {
		return numberStart;
	}
	/**
	 * @param numberStart the numberStart to set
	 */
	public void setNumberStart(String numberStart) {
		this.numberStart = numberStart;
	}
	/**
	 * @return the numberImport
	 */
	public String getNumberImport() {
		return numberImport;
	}
	/**
	 * @param numberImport the numberImport to set
	 */
	public void setNumberImport(String numberImport) {
		this.numberImport = numberImport;
	}
	/**
	 * @return the numberExport
	 */
	public String getNumberExport() {
		return numberExport;
	}
	/**
	 * @param numberExport the numberExport to set
	 */
	public void setNumberExport(String numberExport) {
		this.numberExport = numberExport;
	}
	/**
	 * @return the numberEnd
	 */
	public String getNumberEnd() {
		return numberEnd;
	}
	/**
	 * @param numberEnd the numberEnd to set
	 */
	public void setNumberEnd(String numberEnd) {
		this.numberEnd = numberEnd;
	}
	/**
	 * @return the nameDrug
	 */
	public String getNameDrug() {
		return nameDrug.get();
	}
	/**
	 * @param nameDrug the nameDrug to set
	 */
	public void setNameDrug(String nameDrug) {
		this.nameDrug.set(nameDrug);
	}
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * @return the dateEnterDrug
	 */
	public String getDateEnterDrug() {
		return dateEnterDrug;
	}
	/**
	 * @param dateEnterDrug the dateEnterDrug to set
	 */
	public void setDateEnterDrug(String dateEnterDrug) {
		this.dateEnterDrug = dateEnterDrug;
	}
}
