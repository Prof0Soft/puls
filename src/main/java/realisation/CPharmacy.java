package main.java.realisation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CPharmacy {
		
	
	public CPharmacy(int idPharmacy, String fullNamePharmacy, String namePharmacy,
			String numberPharmacy, int idRegion, Double islike) {
		super();
		this.idPharmacy = new SimpleIntegerProperty(idPharmacy);
		this.fullNamePharmacy = new SimpleStringProperty(fullNamePharmacy);
		this.namePharmacy = new SimpleStringProperty(namePharmacy);
		this.numberPharmacy = new SimpleStringProperty(numberPharmacy);
		this.idRegion = new SimpleIntegerProperty(idRegion);
		this.isLike = new SimpleDoubleProperty(islike);
	}
	/**
	 * With count use drug
	 * @param idPharmacy
	 * @param fullNamePharmacy
	 * @param namePharmacy
	 * @param numberPharmacy
	 * @param idRegion
	 * @param islike
	 * @param exportCount
	 * @param visible
	 */
	public CPharmacy(int idPharmacy, String fullNamePharmacy, String namePharmacy,
			String numberPharmacy, int idRegion, Double islike, int exportCount, boolean visible) {
		super();
		this.idPharmacy = new SimpleIntegerProperty(idPharmacy);
		this.fullNamePharmacy = new SimpleStringProperty(fullNamePharmacy);
		this.namePharmacy = new SimpleStringProperty(namePharmacy);
		this.numberPharmacy = new SimpleStringProperty(numberPharmacy);
		this.idRegion = new SimpleIntegerProperty(idRegion);
		this.isLike = new SimpleDoubleProperty(islike);
		this.exportCount.set(exportCount);
		this.visible.set(visible);
	}
				IntegerProperty idPharmacy;
				StringProperty fullNamePharmacy;
				StringProperty namePharmacy;
				StringProperty numberPharmacy;
				IntegerProperty idRegion;
				SimpleIntegerProperty exportCount = new SimpleIntegerProperty();
	protected 	SimpleDoubleProperty isLike;
	protected 	SimpleBooleanProperty visible = new SimpleBooleanProperty(true);
	
	
	
	/**
	 * @return the exportCount
	 */
	public int getExportCount() {
		return exportCount.get();
	}
	/**
	 * @param exportCount the exportCount to set
	 */
	public void setExportCount(int exportCount) {
		this.exportCount.set(exportCount);
	}
	/**
	 * @return the visible
	 */
	public boolean getVisible() {
		return visible.get();
	}
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible.set(visible);
	}
	/**/
	public DoubleProperty getIsLikeChangeProperty() {
	    return isLike;
	}
	public double getIsLikeChange() {
	    return isLike.get();
	}
	public void setIsLikeChange(Double pr) {
		isLike.set(pr);
	}
		
	/**
	 * @return the islike
	 */
	public Double getIsLike() {
		return isLike.get();
	}
	/**
	 * @param islike the islike to set
	 */
	public void setIslike(Double islike) {
		this.isLike.set(islike);
	}
	/**
	 * @return the idPharmacy
	 */
	public int getIdPharmacy() {
		return idPharmacy.get();
	}
	/**
	 * @param idPharmacy the idPharmacy to set
	 */
	public void setIdPharmacy(int idPharmacy) {
		this.idPharmacy.set(idPharmacy);
	}
	/**
	 * @return the fullNamePharmacy
	 */
	public String getFullNamePharmacy() {
		return fullNamePharmacy.get();
	}
	/**
	 * @param fullNamePharmacy the fullNamePharmacy to set
	 */
	public void setFullNamePharmacy(String fullNamePharmacy) {
		this.fullNamePharmacy.set(fullNamePharmacy);
	}
	/**
	 * @return the namePharmacy
	 */
	public String getNamePharmacy() {
		return namePharmacy.get();
	}
	/**
	 * @param namePharmacy the namePharmacy to set
	 */
	public void setNamePharmacy(String namePharmacy) {
		this.namePharmacy.set(namePharmacy);
	}
	/**
	 * @return the numberPharmacy
	 */
	public String getNumberPharmacy() {
		return numberPharmacy.get();
	}
	/**
	 * @param numberPharmacy the numberPharmacy to set
	 */
	public void setNumberPharmacy(String numberPharmacy) {
		this.numberPharmacy.set(numberPharmacy);
	}
	/**
	 * @return the idRegion
	 */
	public int getIdRegion() {
		return idRegion.get();
	}
	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(int idRegion) {
		this.idRegion.set(idRegion);
	}
}
