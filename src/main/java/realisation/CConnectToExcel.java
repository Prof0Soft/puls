package main.java.realisation;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;

/**
 * Class Connect to Excel file
 * Класс для подключения к файлу Excel
 * Created by Borsuk Sergey
 * on 08.01.2018.
 */
public class CConnectToExcel {

    private String NameFileExcel;           // имя открываемого файла
    private FileInputStream fiosFileExcel;  // потоковый стандарт файлового ввода/вывода
    private HSSFWorkbook hssfWorkbookExcel; // активная книга подключаемого файла
    private HSSFSheet hssfSheetExcel;       // активный лист подключаемого файла

    /**
     *  Class constructor
     *  класс конструктор
     * @param fileExcelName - path and name for connect
     */
    public CConnectToExcel(String fileExcelName){
        this.NameFileExcel = fileExcelName;
    }

    /**
     * Connect to selected file excel
     * Подключение к файлу Excel
     * @return - Sheet (HSSFSheet) for work with excel
     */
    public HSSFSheet connectToExcel(){
        try {
            fiosFileExcel = new FileInputStream(new File(NameFileExcel));
            hssfWorkbookExcel = new HSSFWorkbook(fiosFileExcel);
            hssfSheetExcel = hssfWorkbookExcel.getSheetAt(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException eIO){
            eIO.printStackTrace();
        }

        return hssfSheetExcel;
    }


    /**
     * Method getting count of row in Excel file
     * Метод возвращает кол-во строк в файле Excel
     * @return
     */
    public int getRowCount() {
    	return hssfSheetExcel.getLastRowNum();
    }

    /**
     * Close opened connection
     * Закрытие открытого подключения
     * @return
     * @throws IOException
     */
    public boolean closeExcelConnect(){
        boolean isCloseConnection = true;     // для проверки корректности закрытия соединения

        try {
            hssfWorkbookExcel.close();
        } catch (IOException e) {
            isCloseConnection = false;
            e.printStackTrace();
        }

        return isCloseConnection;
    }
}




























