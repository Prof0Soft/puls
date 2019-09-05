package main.java.realisation;

public class CUser {
	
	
	public CUser(int idUser, int idRegion, String email, String login, String firstName, String lastName,
			String patronymic, int levelaccess, int sublevelaccess, String telNumber, String photo) {
		super();
		this.idUser = idUser;
		this.idRegion = idRegion;
		this.email = email;
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronymic = patronymic;
		this.levelaccess = levelaccess;
		this.sublevelaccess = sublevelaccess;
		this.telNumber = telNumber;
		this.access = getFullAccess();
		this.photo = photo;
	}
	
	public CUser(int idUser) {
		super();
		this.idUser = idUser;
		this.idRegion = 3;
		this.email = null;
		this.login = null;
		this.firstName = null;
		this.lastName = null;
		this.patronymic = null;
		this.levelaccess = 99;
		this.sublevelaccess = 99;
		this.telNumber = null;
		this.access = getFullAccess();
	}

	int idUser;
	int idRegion;
	String email;
	String login;
	String firstName;
	String lastName;
	String patronymic;
	String telNumber;
	int levelaccess;
	int sublevelaccess;
	int access;
	String photo;
	
	
	@Override
	public String toString() {
		return this.lastName + " " + this.firstName;
	}
	
	/**
	 * @return the telNumber
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * @param telNumber the telNumber to set
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	
	private int getFullAccess() {
		int access;
		access = Integer.parseInt(String.valueOf(levelaccess) + String.valueOf(sublevelaccess));
		return access;
	}

	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the idRegion
	 */
	public int getIdRegion() {
		return idRegion;
	}

	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the patronymic
	 */
	public String getPatronymic() {
		return patronymic;
	}

	/**
	 * @param patronymic the patronymic to set
	 */
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	/**
	 * @return the levelaccess
	 */
	public int getLevelaccess() {
		return levelaccess;
	}

	/**
	 * @param levelaccess the levelaccess to set
	 */
	public void setLevelaccess(int levelaccess) {
		this.levelaccess = levelaccess;
	}

	/**
	 * @return the sublevelaccess
	 */
	public int getSublevelaccess() {
		return sublevelaccess;
	}

	/**
	 * @param sublevelaccess the sublevelaccess to set
	 */
	public void setSublevelaccess(int sublevelaccess) {
		this.sublevelaccess = sublevelaccess;
	}

	/**
	 * Method return full access for user
	 * @return
	 */
	public int getAccess() {
		return access;
	}
	
	/**
	 * @param access the access to set
	 */
	public void setAccess(int access) {
		this.access = access;
	}
	
	
}
