package main.java.realisation;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.util.StringUtil;
import javafx.collections.FXCollections;
import main.java.CConnectionToMySQL;
import main.java.CWithString;
import main.java.realisation.CConnectToExcel;
import javafx.collections.ObservableList;

/**
 * Class, Work with Excel file and import data in base MySQL.
 * Класс работы импорта данных из файла Excel
 * @author Borsuk S. from 14.01.2018 
 *
 */
public class CWorkWithExcel {
	/**
	 * path to file Excel (by default for test)
	 *
	 */
	private String xlsFileName = "Рубикон январь 2017.xls";
	/**
	 * Excel connection sheet
	 * поле для подключаемого листа файла Excel
	 */
	private HSSFSheet xlsSheet;									  
	/**
	 * Massive title which searching in table Excel (need columns) </br>
	 * 
	 * Numbers begin with 1 </br>
	 * Fields: </br>
	 * 1: Series or date </br>
	 * 2: Number pharmacy or health life </br>
	 * 3: Pharmacy </br>
	 * 4: The number start </br>
	 * 5: The number import </br>
	 * 6: The number export </br>
	 * 7: The number end  </br>
	 */
	private int[] numberColumns = new int[8];					 
	/**
	 * massive drug which searching in table Excel (rows) </br>
	 * массив номеров строк найденых препаратов
	 * 1: index in DB </br>
	 * 2: name the drug </br>
	 * 3: number line in Excel file </br>
	 */
	private String[][] numberRows = null;			
	/**
	 * maximum rows in table Excel
	 * максимальное количество строк в файле Excel
	 */
	private int lenghDocument = 3000;
	
	/**
	 * Main massive of data from Excel file. The massive stores data about consumption the drug
	 * массив с импортруемыми данными
	 * </br>
	 * Fields: </br>
	 * 1: Sign of data </br>
	 * 2: Date or series the drug </br>
	 * 3: Number pharmacy or helth date the drug </br>
	 * 4: Name the pharmacy </br>
	 * 5: The number start </br>
	 * 6: The number inport </br>
	 * 7: The number export  </br>
	 * 8: The number end </br>
	 */
	private ObservableList<CDataDrug> massDataExcel = FXCollections.observableArrayList();
	// данные для подлкючения к серверу
	/*private String url  = "jdbc:mysql://localhost:3306/bpuls";
	private String login = "root";
	private String psw = "1234";*/
	private CConnectionToMySQL connect;
		
	// names search column (default)
	// имена заголовков столбцов для поиска
	String nameColumnSeries = "Серия";
	String nameColumnNumberFarmacy = "Номер";
	String nameColumnNameFarmacy = "производитель";
	String nameColumnKolNach = "Количество (начало)";
	String nameColumnKolCome = "Количество (приход)";
	String nameColumnKolOut = "Количество (расход)";
	String nameColumnKolEnd = "Количество (конец)";
	
	/**
	 * Default constructor. Parameters is default
	 * конструктор по умолчанию
	 */
	public CWorkWithExcel() {
		// connect to MySQL
		connect = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
	}

	/**
	 * Constructor more parameters
	 * Конструктор с параметрами для извлечения данных непосредственно из файла Excel
	 * @param xlsFileName 	- path to extract file Excel / путь для
	 * @param url 			- url for connect to MySQL server
	 * @param login			- login for connect to MySQL server
	 * @param psw			- password for connect to MySQL server
	 */
	public CWorkWithExcel(String xlsFileName) {
		this.xlsFileName = xlsFileName;
		// connect to MySQL
		connect = new CConnectionToMySQL(CConfig.URL, CConfig.LOGIN, CConfig.PSW);
	}
	
	/**
	 * Main method for execute data from file Excel. Return 'true' if process is end.
	 * главный метод обработки файла
	 * 
	 * @return if process is end return true
	 */
	public boolean mainThreadExcel() {
		boolean isEnd = true;		
		
		CConnectToExcel xlsConnect = new CConnectToExcel(xlsFileName); 		// connect to Excel / подключение к Excel
		xlsSheet = xlsConnect.connectToExcel();								// get sheet Excel / получение активного листа Excel
		lenghDocument = xlsConnect.getRowCount();
		
		isEnd = searchColumnIndex();		// поиск номеров столбцов
		isEnd = searchRowIndexForDrug();	// поиск номеров строк
		isEnd = getDataFromTableExcel();	// импорт данных
		
		connect.disconnectMysSQL();
		
		return isEnd;
	}
	
	/**
	 * Import data from file Excel
	 * Импорт данных из файла Excel
	 */
	 private boolean getDataFromTableExcel() {
		 boolean isDataCorrect = true;			// корректность данных
		 HSSFRow xlsRow;						// строка Excel
		 String buff;							// временный буфер для работы
		 String sign;							// признак импортируемых данных
		 String nameDrug = null;				// имя препарата
		 String dateEnterDrug = null;			// дата загрузки препарата
		 String serialNumberTheDrug = null;		// серийный номер препарата
		 String indexInDB = null;				// номер индекса в базе MySQL
		 String numberFharmacy = null;			// номер аптеки
		 String healthlife = null;				// срок годности препарата
		 String namePhatmacy = null;			// имя аптеки
		 String numberStart;					// кол-во начало
		 String numberImport;					// кол-во пришло
		 String numberExport;					// кол-во ушло
		 String numberEnd;						// кол-во конец
		 int numberRowLenght = 0;				// количество строк (временно)
		 CDataDrug dataDrug = null;				// pojo препарат
		 int numIterr = 0;						//count of iteration for record field - health life, sign - P in class CdataDrug.

		 // определение кол-ва записей в массиве
		 for (numberRowLenght = 0; numberRowLenght <= numberRows.length; numberRowLenght++) {
			 if (numberRows[numberRowLenght][0] == null) {
				 break;
			 }
		 }
		 numberRowLenght--;
		 int countLineDrug = 0;
		 int start = 0;
		 int end = 0;
		 // импорт данных из файла Excel
		 for (int i = 0; i <= numberRowLenght; i++) {
			 // в зависимости от номера записи присваеваем стартовую и конечную позицию
		 	if (i == numberRowLenght) {
				 countLineDrug = lenghDocument;
				 start = Integer.parseInt(numberRows[i][2]) - 1;
				 end = countLineDrug;
			 }				 
			 else {
				 countLineDrug = Integer.parseInt(numberRows[i+1][2]) - Integer.parseInt(numberRows[i][2]);
				 start = Integer.parseInt(numberRows[i][2]) - 1;
				 end = countLineDrug + start;
			 }
			
			 // проход по файлу Excel
			 for (int j = start; j < end; j++) {
			 	 xlsRow = xlsSheet.getRow(j);
				 				 			 
				 buff = getStringInCellExcel(xlsRow.getCell(numberColumns[1]-1));
				 if (buff == "null")
					 continue;
				
				 sign = "";
				 // если объект препарат
				 if (j == start) {																		// if object is drug
					 sign = "P";								    									// sign the Drug
					 nameDrug = getStringInCellExcel(xlsRow.getCell(numberColumns[2]-1));
					 indexInDB = numberRows[i][0];
					 dateEnterDrug = "";
					 serialNumberTheDrug = "";
					 numberFharmacy = "";
					 namePhatmacy = "";
					 healthlife = "";
				 }
				 // если объект дата
				 if (xlsCellIsDate(buff)) {																// if object is date
					 sign = "Farm";																		// sign the pharmacy
					 namePhatmacy = getStringInCellExcel(xlsRow.getCell(numberColumns[3]-1));	  		// Pharmacy 
					 numberFharmacy = getStringInCellExcel(xlsRow.getCell(numberColumns[2]-1));			// Number pharmacy or health life ;
					 dateEnterDrug = getStringInCellExcel(xlsRow.getCell(numberColumns[1]-1));			// Series or date;
				 }
				 else {
				 	// если объект серийный номер
					 if (CAsisstant.isNumeric(buff))
						 if (Double.parseDouble(buff) > 100) { 											// if object is number of serial
							 sign = "Ser";																// sign the Drug
							 serialNumberTheDrug = buff;												// Serial number party the drug 
							 healthlife = getStringInCellExcel(xlsRow.getCell(numberColumns[2]-1));
						 }
				 }
					
				 if (sign == "") {
					 continue;
				 }
				 numberStart = getStringInCellExcel(xlsRow.getCell(numberColumns[4]-1));				// The number start;
				 numberImport = getStringInCellExcel(xlsRow.getCell(numberColumns[5]-1));				// The number import;
				 numberExport = getStringInCellExcel(xlsRow.getCell(numberColumns[6]-1));				// The number export;
				 numberEnd = getStringInCellExcel(xlsRow.getCell(numberColumns[7]-1));					// The number end ;
				
				 if (sign == "Ser" && dataDrug != null && numIterr > 0) {
					 dataDrug.setHealthlife(healthlife);
					 numIterr = 0;
				 }
				 // если объект определился как аптека но у него нет экспорта и номера аптеки то
				 // признак устанавливается в Other
				 if (sign == "Farm" && (numberExport == "null" || numberFharmacy == "null")) {
					 sign = "Other";
				 }
				 // запись данных в pojo объект
				 dataDrug = new CDataDrug(sign, nameDrug, dateEnterDrug, serialNumberTheDrug, 
						 indexInDB, numberFharmacy, healthlife, namePhatmacy, numberStart, numberImport, numberExport, numberEnd);
				 
				 massDataExcel.add(dataDrug);
				 
				 numIterr++;
			}
								 
		}
		return isDataCorrect;
	}

	// геттер массива данных из Excel файла
	 public ObservableList<CDataDrug> getMassDataExcel() {
		return massDataExcel;
	}

	/**
	  * Import from Excel cells in <b>String</b> format
	  * Импорт из файла Excel в String формате
	  * @param cell - object from file Excel
	  * @return	format <b>String</b>  
	  */
	private String getStringInCellExcel(HSSFCell cell) {
		String returnString = "null";
		
		if (cell != null) {
			 if (cell.getCellTypeEnum() == CellType.STRING) {
				 returnString = cell.getStringCellValue();
			 }
			 if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				 returnString = Double.toString(cell.getNumericCellValue());
			 }
		}
		return returnString;
	}
	 	
	/**
	 * Search necessary number column in Excel end save them. 
	 * поиск необходимого столбца в файле Excel и сохранение его номера
	 * @return isDateCorrect - return <b>false</b> if number columns differs of number 7
	 */
	 private boolean searchColumnIndex() {
		
		boolean isDateCorrect = true;
		// names search columns
		// имена столбцов
		final String nameColumnSeries = "Серия";
		final String nameColumnNumberFarmacy = "Номер";
		final String nameColumnNameFarmacy = "Производитель";
		final String nameColumnKolNach = "Количество (начало)";
		final String nameColumnKolCome = "Количество (приход)";
		final String nameColumnKolOut = "Количество (расход)";
		final String nameColumnKolEnd = "Количество (конец)";
		// rows / строки
		HSSFRow xlsRow;
		String buff = "";
		int count = 0;
				
		for (int i = 0; i < 20; i++) {
			
			xlsRow = xlsSheet.getRow(i);
			for(int j = 0; j < 25; j++) {
								
				if (xlsCellIsString(xlsRow.getCell(j)))
					buff = xlsRow.getCell(j).getStringCellValue();
								
				// save number column
				switch (buff) {
				case nameColumnSeries: numberColumns[1] = j+1; count++; buff = ""; break;
				case nameColumnNumberFarmacy: numberColumns[2] = j+1; count++; buff = ""; break;
				case nameColumnNameFarmacy: numberColumns[3] = j+1; count++; buff = ""; break;
				case nameColumnKolNach: numberColumns[4] = j+1; count++; buff = ""; break;
				case nameColumnKolCome: numberColumns[5] = j+1; count++; buff = ""; break;
				case nameColumnKolOut: numberColumns[6] = j+1; count++;  buff = "";break;
				case nameColumnKolEnd: numberColumns[7] = j+1; count++; buff = ""; break;
				default:
					break;
				}
			}
		}
		
		if (count != 7) 
			isDateCorrect = false;
		
		return isDateCorrect;
	}

	/**
	 * Method validate Excel cells on <b>String</b> type 
	 * метод определяет ячейки Excel имеют тип String или нет
	 * @param hssfCellValue - object cell
	 * @return - validation data on <b>String</b> type
	 */
	private boolean xlsCellIsString(HSSFCell hssfCellValue) {
		boolean isValueValid = false;
		
		if (hssfCellValue != null) {
			if (hssfCellValue.getCellTypeEnum() == CellType.STRING) 
				isValueValid = true;
		}
		
		return isValueValid;
	}
	
	/**
	 * Validate Excel cells on <b>Date</b> type
	 * Проверка данных Excel ячейки на тип Date
	 * @param inspect string
	 * @return validation data on <b>Date</b> type
	 */
	private boolean xlsCellIsDate(String strValue){
		boolean isValueValid = false;
				
		if (StringUtil.countMatches(strValue, '.') == 2) {						// if string is date
			isValueValid = true; 
		 }
				
		return isValueValid; 
	}

	/**
	 * Method search row the drug in file Excel and save they number row in the stack massive
	 * метод ищет номера строк препаратов в фале Excel и сохраняет эти строки в массиве
	 * @return
	 */
	private boolean searchRowIndexForDrug() {
		boolean isEnd = true;
		String[][] nameRow = null;	// именая и номера искомых строк
		HSSFRow xlsRow;
		String buff = "";
		ResultSet res;
				
		try {
			// get count fields of table drug
			// запрос на количество имен препаратов в базе MySQL
			res = connect.getResultQuery("SELECT COUNT(*) FROM " + CConfig.NAME_BASE + ".drug");
			res.next();
			int count = res.getInt(1);										
			numberRows = new String [count][3];							// Initialize numberRows
			nameRow = new String [count][3];							// Initialize nameRow
			
			// get records on drug table drag
			// запрос на имена препаратов в базе MySQL
			res = connect.getResultQuery("SELECT * FROM " + CConfig.NAME_BASE + ".drug");
			int i = 0;
			// формирование массива из препаратов
			while (res.next()) {
				nameRow[i][0] = res.getString("idDrug");
				nameRow[i][1] = res.getString("nameDrug");
				nameRow[i][2] = res.getString("nameForSearch");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// проверка на соответсвии препаратов из базы и файла Excel
		int countNumberRows = 0;
		int numberColumnSearch = numberColumns[2]-1; 
		for (int i = 0; i < lenghDocument; i++)
		{
			xlsRow = xlsSheet.getRow(i);
			if (xlsCellIsString(xlsRow.getCell(numberColumnSearch))) {
				buff = xlsRow.getCell(numberColumnSearch).getStringCellValue();
				
				for (int k = 0; k < nameRow.length; k++) {
					if (CWithString.isSearchString(buff, nameRow[k][2]) && nameRow[k][2] != "null") {
						numberRows[countNumberRows][0] = nameRow[k][0];
						numberRows[countNumberRows][1] = buff;
						numberRows[countNumberRows][2] = Integer.toString(i+1);
						nameRow[k][2] = "null";
						countNumberRows++;
						break;
					}
				}
			}
		}
		
		return isEnd;
	}
	
	/**
	 * Count search drug in table Excel
	 * кол-во искомых препаратов в таблице Excel
	 * @return count (int)
	 */
	public int getCountDrugRows() {
		int count = 0;
		
		for (int i = 0; i < numberRows.length; i++) {
			if (numberRows[i][0] != null) {
				count++;
			}
		}
		return count;
	}
		
}
