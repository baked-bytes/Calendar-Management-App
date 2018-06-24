package ReportTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import AccountView.AccountView;
import DB.AccountManager;
import Model.CostRecordBuilder;
import Report.Form;

public class FormTest {
	AccountManager accountManager;
	private String year;
	private String month;
	private String day;
	AccountView accountView;
	Form form;
	
	@Before
	public void setUp() {
		year = "2018";
		month = "7";
		day = "1";
		accountManager = new AccountManager(year, month, day);
		accountView = new AccountView(year, month, day);
		form = new Form(year, month);
		accountView = new AccountView(year, month, day);
		accountView.init();
	}
	
	@After
	public void tearDown() {
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
	public void getDataFromManagerTest() {
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getDataFromManager")
				.cost("100")
				.type("Food")
				.build());
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getDataFromManager")
				.cost("100")
				.type("Clothing")
				.build());
		
		String[][] actualData = form.getDataFromManager();
		String actualType1 = actualData[0][0];
		String actualCost1 = actualData[0][1];
		String actualContent1 = actualData[0][2];
		String actualType2 = actualData[1][0];
		String actualCost2 = actualData[1][1];
		String actualContent2 = actualData[1][2];
		
		Assert.assertEquals("Food", actualType1);
		Assert.assertEquals("100", actualCost1);
		Assert.assertEquals("Test for getDataFromManager", actualContent1);
		Assert.assertEquals("Clothing", actualType2);
		Assert.assertEquals("100", actualCost2);
		Assert.assertEquals("Test for getDataFromManager", actualContent2);
	}
}
