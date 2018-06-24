package Report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.AccountManager;
import Model.CostRecord;

public class Form {

	private String FILE_NAME ;  //MyFirstExcel.xlsx";

	public int montlyCostListSize;
	AccountManager accountManager;
	
	private String year;
	private String month;

	public Form(String year, String month) {
		accountManager = new AccountManager(year, month, "");
		montlyCostListSize = 0;
		FILE_NAME = "";
		this.year = year;
		this.month = month;
	}

	public String[][] readDataFromManager() {

		ArrayList<CostRecord> montlyCostList = accountManager.getMonthlyCost();
		String[][] mydata = new String[montlyCostList.size()][3];
		montlyCostListSize = montlyCostList.size();

		for (int i = 0; i < montlyCostList.size(); i++) {
			mydata[i][0] = montlyCostList.get(i).gettype();
			mydata[i][1] = montlyCostList.get(i).getcost();
			mydata[i][2] = montlyCostList.get(i).getContent();
		}
		return mydata;
	}

	public int getMontlyTotalCost() {
		return accountManager.getMontlyTotalCost();
	}
	
	public void createFile() {	
		 FILE_NAME = "ReportExport/" + year + "_" + month + "Report.xlsx";
		 File file = new File( FILE_NAME );
		 if(!file.exists())
		 {
		     file.getParentFile().mkdirs();
		     try {
		         file.createNewFile();
		     } catch (IOException e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		     }
		 }
	}

	public void exportAsExcelFile() {
		
		createFile();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		Object[][] datatypes = readDataFromManager();

		int rowNum = 0;
		System.out.println("Creating excel");

		for (Object[] datatype : datatypes) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) 
					cell.setCellValue((String) field);				
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
		JOptionPane.showMessageDialog(null, "DONE");
	} 

}
