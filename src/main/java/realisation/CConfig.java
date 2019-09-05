package main.java.realisation;

public final class  CConfig {
	
	private CConfig() {
		throw new AssertionError();
	}
	/*
	public static final String URL  = "jdbc:mysql://localhost:3306/bpuls?autoReconnect=true&useSSL=false";
	public static final String LOGIN = "root";
	public static final String PSW = "1234";
	*/
	public static final String URL  = "jdbc:mysql://profsolv.beget.tech?autoReconnect=true&useSSL=false";
	public static final String LOGIN = "profsolv_bpuls";
	public static final String PSW = "123456";
	public static final String NAME_BASE = "profsolv_bpuls";
	
	private static int numberRegion = 3;			// Change for work program
	private static CUser userMain;					// user program
	
	
	
	public static int getUserMainIDRegion() {
		return userMain.getIdRegion();
	}
	
	public static int getUserMainID() {
		return userMain.getIdUser();
	}
	
	/**
	 * @return the userMain
	 */
	public static CUser getUserMain() {
		return userMain;
	}
	/**
	 * @param userMain the userMain to set
	 */
	public static void setUserMain(CUser userMain) {
		CConfig.userMain = userMain;
	}
	public static int getNumberRegion() {
		return numberRegion;
	}
	public static void setNumberRegion(int numberRegion) {
		CConfig.numberRegion = numberRegion;
	}
	
	
}
             