package DBTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DB.AccountManager;
import DB.CostRecord;
import View.AccountView;
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
		CostRecord costRecord = new CostRecord();
		costRecord.setYear(year);
		costRecord.setMonth(month);
		costRecord.setDay(day);
		costRecord.setContent("Test for add");
		costRecord.setcost("100");
		costRecord.settype("eat");
		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE CONTENT = \"Test for add\";";
			ResultSet rs = stmt.executeQuery(sql);
			String actualYear = rs.getString("YEAR");
			String actualMonth = rs.getString("MONTH");
			String actualDay = rs.getString("DAY");
			String actualContent = rs.getString("CONTENT");
			String actualCost = rs.getString("COST");
			String actualType = rs.getString("TYPE");

			Assert.assertEquals("2018", actualYear);
			Assert.assertEquals("5", actualMonth);
			Assert.assertEquals("11", actualDay);
			Assert.assertEquals("Test for add", actualContent);
			Assert.assertEquals("100", actualCost);
			Assert.assertEquals("eat", actualType);
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
		CostRecord costRecord = new CostRecord();
		costRecord.setYear(year);
		costRecord.setMonth(month);
		costRecord.setDay(day);
		costRecord.setContent("Test for edit");
		costRecord.setcost("100");
		costRecord.settype("eat");
		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE CONTENT = \"Test for edit\";";
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
		costRecord.settype("living");
		accountManager.editCostRecord(costRecord, ID);

		Connection connForEdit = null;
		Statement stmtForEdit = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForEdit = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForEdit = connForEdit.createStatement();
			String sql = "SELECT * FROM Account WHERE CONTENT = \"Test for edit\";";
			ResultSet rs = stmtForEdit.executeQuery(sql);
			String actualType = rs.getString("TYPE");
			Assert.assertEquals("living", actualType);
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
		CostRecord costRecord = new CostRecord();
		costRecord.setYear(year);
		costRecord.setMonth(month);
		costRecord.setDay(day);
		costRecord.setContent("Test for delete");
		costRecord.setcost("100");
		costRecord.settype("eat");
		accountManager.addCostRecord(costRecord);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account WHERE CONTENT = \"Test for delete\";";
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
			String sql = "SELECT * FROM Account WHERE CONTENT = \"Test for delete\";";
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

}
