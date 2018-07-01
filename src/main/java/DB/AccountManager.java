package DB;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import AccountView.AccountIDView;
import AccountView.AccountView;
import Model.CostRecord;
import Model.CostRecordBuilder;

public class AccountManager {

	private String year;
	private String month;
	private String day;
	
	private int totalCost;

	private ArrayList<String> adayCostRecordIDList = new ArrayList<String>();

	public AccountManager(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
		totalCost = 0;
	}

	public void createTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE Account( ID INTEGER PRIMARY KEY AUTOINCREMENT ,YEAR TEXT NOT NULL,MONTH TEXT NOT NULL,DAY TEXT NOT NULL,CONTENT TEXT NOT NULL,COST TEXT NOT NULL, TYPE TEXT NOT NULL);";

			stmt.executeUpdate(sql);
			c.setAutoCommit(false);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public void executeSQL(String sqlStmt) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			stmt.executeUpdate(sqlStmt);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

	public void addCostRecord(CostRecord data) {
		String sql = "insert into Account( YEAR, MONTH, DAY,CONTENT, COST, TYPE )" + "VALUES(\'" + data.getYear()
				+ "','" + data.getMonth() + "','" + data.getDay() + "','" + data.getContent() + "','" + data.getcost()
				+ "','" + data.gettype() + "\');";
		executeSQL(sql);
		setCostRecord();
	}

	public void editCostRecord(CostRecord data, String idEdit) {
		String sql = "update Account set CONTENT='" + data.getContent() + "',COST = '" + data.getcost() + "',TYPE='"
				+ data.gettype() + "'where id = '" + idEdit + "';";
		executeSQL(sql);
		setCostRecord();
	}

	public void deleteDayCostRecord(String id) {
		String sql = "delete from Account where id ='" + id + "'";
		executeSQL(sql);
		setCostRecord();
	}

	public ArrayList<CostRecord> getMonthlyCost() {
		Connection c = null;
		Statement stmt = null;
		totalCost = 0;
		ArrayList<CostRecord> monthlyCostRecord = new ArrayList<CostRecord>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			System.out.println(year);
			String sql = "SELECT * FROM Account where year='" + year + "'AND month='" + month + "';";
			ResultSet rs = stmt.executeQuery(sql);
			adayCostRecordIDList.clear();
			while (rs.next()) {				
				CostRecord costRecord = new CostRecordBuilder().content(rs.getString("content"))
						.cost(rs.getString("cost")).type(rs.getString("type")).build();
				totalCost += Integer.parseInt(rs.getString("cost"));
				monthlyCostRecord.add(costRecord);
				
			} // while
			
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return monthlyCostRecord;
	}
	
	public int getMontlyTotalCost(){
	  return totalCost;
	}

	public ArrayList<String> getadayCostRecordIDList() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			System.out.println(year);
			String sql = "SELECT * FROM Account where year='" + year + "'AND month='" + month + "'AND day='" + day
					+ "';";
			ResultSet rs = stmt.executeQuery(sql);
			adayCostRecordIDList.clear();
			while (rs.next()) {
				adayCostRecordIDList.add(rs.getString("id"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return adayCostRecordIDList;
	}

	public CostRecord getIdCostRecord(String id) {
		Connection c = null;
		Statement stmt = null;
		CostRecord costRecord = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account where id='" + id + "';";

			ResultSet rs = stmt.executeQuery(sql);
			costRecord = new CostRecordBuilder().year(rs.getString("year")).month(rs.getString("month"))
					.day(rs.getString("day")).cost(rs.getString("cost")).type(rs.getString("type"))
					.content(rs.getString("CONTENT")).id(rs.getString("id")).build();
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully----------");
		return costRecord;
	}

	public void setCostRecord() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			System.out.println(year);
			String sql = "SELECT * FROM Account where year='" + year + "'AND month='" + month + "'AND day='" + day
					+ "';";

			ResultSet rs = stmt.executeQuery(sql);
			AccountIDView.idBox.removeAllItems();
			DefaultComboBoxModel model = (DefaultComboBoxModel) AccountIDView.idBox.getModel();
			DefaultTableModel tm = (DefaultTableModel) AccountView.table.getModel();
			ResultSetMetaData rsmd = rs.getMetaData();
			adayCostRecordIDList.clear();
			tm.setColumnCount(0);
			tm.setRowCount(0);
			tm.addColumn("ID");
			tm.addColumn("COST");
			tm.addColumn("TYPE");
			tm.addColumn("CONTENT");
			int i = 1;
			while (rs.next()) {
				String[] a = new String[4];
				a[0] = Integer.toString(i);
				a[1] = rs.getString("cost");
				a[2] = rs.getString("type");
				a[3] = rs.getString("CONTENT");
				tm.addRow(a);
				i++;
				adayCostRecordIDList.add(rs.getString("id"));
			}
			for (int x = 0; x < adayCostRecordIDList.size(); x++) {
				model.addElement(Integer.toString(x + 1));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

}
