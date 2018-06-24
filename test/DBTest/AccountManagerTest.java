package DBTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import AccountView.AccountView;
import DB.AccountManager;
import Model.CostRecord;
import Model.CostRecordBuilder;
import junit.framework.Assert;

public class AccountManagerTest {
	AccountManager accountManager;
	private String year;
	private String month;
	private String day;
	AccountView accountView;

	@Before
	public void setUp(){
		year = "2018";
		month = "5";
		day = "11";
		accountManager = new AccountManager(year, month, day);
		accountView = new AccountView(year, month, day);
		accountView.init();
	}
	@After
	public void tearDown(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "DELETE FROM Account WHERE CONTENT LIKE 'Test%';";

			stmt.executeUpdate(sql);
			c.setAutoCommit(false);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Test data in table was deleted successfully");
	}

	@Test
	public void addCostRecordTest(){
		CostRecord costRecord = new CostRecordBuilder()
					.year(year)
					.month(month)
					.day(day)
					.content("Test for add")
					.cost("100")
					.type("Food")
					.build();

		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE YEAR = \"2018\" "
					+ "AND MONTH = \"5\" "
					+ "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for add\" "
					+ "AND COST = \"100\" "
					+ "AND TYPE = \"Food\";";
			ResultSet rs = stmt.executeQuery(sql);
			rs.getString("YEAR");//For checking whether the data exists
		} catch (SQLException e){
			Assert.fail("Adding costRecord was failed.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void editCostRecordTest(){
		/*ADD THE TEST DATA FIRST*/
		CostRecord costRecord = new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for edit")
				.cost("100")
				.type("Food")
				.build();

		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE YEAR = \"2018\" "
					+ "AND MONTH = \"5\" "
					+ "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for edit\" "
					+ "AND COST = \"100\" "
					+ "AND TYPE = \"Food\";";
			ResultSet rs = stmt.executeQuery(sql);
			ID = rs.getString("ID");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*THEN EDIT THE DATA CREATED ABOVE*/
		costRecord.settype("Housing");
		accountManager.editCostRecord(costRecord, ID);

		Connection connForEdit = null;
		Statement stmtForEdit = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForEdit = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForEdit = connForEdit.createStatement();
			String sql = "SELECT * FROM Account WHERE YEAR = \"2018\" "
					+ "AND MONTH = \"5\" "
					+ "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for edit\" "
					+ "AND COST = \"100\" "
					+ "AND TYPE = \"Housing\";";
			ResultSet rs = stmtForEdit.executeQuery(sql);
			String actualType = rs.getString("TYPE");
			Assert.assertEquals("Housing", actualType);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (stmtForEdit != null)
					stmtForEdit.close();
				if (connForEdit != null)
					connForEdit.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void deleteCostRecordTest(){
		/*ADD THE TEST DATA FIRST*/
		CostRecord costRecord = new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for delete")
				.cost("100")
				.type("Food")
				.build();

		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE YEAR = \"2018\" "
					+ "AND MONTH = \"5\" "
					+ "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for delete\" "
					+ "AND COST = \"100\" "
					+ "AND TYPE = \"Food\";";
			ResultSet rs = stmt.executeQuery(sql);
			ID = rs.getString("ID");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*THEN DELETE THE DATA CREATED ABOVE*/
		accountManager.deleteDayCostRecord(ID);

		Connection connForDelete = null;
		Statement stmtForDelete = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForDelete = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForDelete = connForDelete.createStatement();
			String sql = "SELECT * FROM Account WHERE YEAR = \"2018\" "
					+ "AND MONTH = \"5\" "
					+ "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for delete\" "
					+ "AND COST = \"100\" "
					+ "AND TYPE = \"Food\";";
			ResultSet rs = stmtForDelete.executeQuery(sql);
			rs.getString("ID");//For Checking whether the data exists
		} catch (SQLException e){
			Assert.assertEquals("ResultSet closed", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (stmtForDelete != null)
					stmtForDelete.close();
				if (connForDelete != null)
					connForDelete.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void getADayCostRecordIDListTest(){
		/*ADD TEST DATA FIRST*/
		CostRecord costRecord = new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getAdayCostRecordIDList")
				.cost("100")
				.type("Food")
				.build();

		accountManager.addCostRecord(costRecord);

		ArrayList<String> costRecordIDList = accountManager.getadayCostRecordIDList();
		int actualListSize = costRecordIDList.size();
		Assert.assertEquals(1, actualListSize);
	}

	@Test
	public void getIDCostRecordTest(){/*ADD THE TEST DATA FIRST*/
		CostRecord costRecord = new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getIDCostRecord")
				.cost("100")
				.type("Food")
				.build();

		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE YEAR = \"2018\" "
					+ "AND MONTH = \"5\" "
					+ "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for getIDCostRecord\" "
					+ "AND COST = \"100\" "
					+ "AND TYPE = \"Food\";";
			ResultSet rs = stmt.executeQuery(sql);
			costRecord.setId(rs.getString("ID"));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		CostRecord actualCostRecord = accountManager.getIdCostRecord(costRecord.getId());
		String actualYear = actualCostRecord.getYear();
		String actualMonth = actualCostRecord.getMonth();
		String actualDay = actualCostRecord.getDay();
		String actualContent = actualCostRecord.getContent();
		String actualCost = actualCostRecord.getcost();
		String actualType = actualCostRecord.gettype();
		
		Assert.assertEquals("2018", actualYear);
		Assert.assertEquals("5", actualMonth);
		Assert.assertEquals("11", actualDay);
		Assert.assertEquals("Test for getIDCostRecord", actualContent);
		Assert.assertEquals("100", actualCost);
		Assert.assertEquals("Food", actualType);
	}
	
	@Test
	public void getMonthlyCostTest(){
		CostRecord costRecordStub = new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getMonthlyCost")
				.cost("100")
				.type("Food")
				.build();
		CostRecord costRecordStub2 = new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getMonthlyCost")
				.cost("200")
				.type("Clothing")
				.build();
		
		accountManager.addCostRecord(costRecordStub);
		accountManager.addCostRecord(costRecordStub2);
		
		ArrayList<CostRecord> monthlyCostRecord = 
				accountManager.getMonthlyCost();
		ArrayList<CostRecord> actualMonthlyCostRecord = 
				new ArrayList<CostRecord>();
		
		for (CostRecord costRecordForTest : monthlyCostRecord) {
			if (costRecordForTest.getContent().equals("Test for getMonthlyCost"))
				actualMonthlyCostRecord.add(costRecordForTest);
		}
		
		String actualContentStub = actualMonthlyCostRecord.get(0).getContent();
		String actualCostStub = actualMonthlyCostRecord.get(0).getcost();
		String actualTypeStub = actualMonthlyCostRecord.get(0).gettype();
		
		String actualContentStub2 = actualMonthlyCostRecord.get(1).getContent();
		String actualCostStub2 = actualMonthlyCostRecord.get(1).getcost();
		String actualTypeStub2 = actualMonthlyCostRecord.get(1).gettype();
		
		Assert.assertEquals("Test for getMonthlyCost", actualContentStub);
		Assert.assertEquals("100", actualCostStub);
		Assert.assertEquals("Food", actualTypeStub);
		
		Assert.assertEquals("Test for getMonthlyCost", actualContentStub2);
		Assert.assertEquals("200", actualCostStub2);
		Assert.assertEquals("Clothing", actualTypeStub2);
	}
}