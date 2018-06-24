package ReportTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import AccountView.AccountView;
import DB.AccountManager;
import Model.CostRecordBuilder;
import Report.BarChart;

public class BarChartTest {
	AccountManager accountManager;
	private String year;
	private String month;
	private String day;
	AccountView accountView;
	BarChart barChart;
	
	@Before
	public void setUp() {
		year = "2018";
		month = "7";
		day = "1";
		accountManager = new AccountManager(year, month, day);
		accountView = new AccountView(year, month, day);
		barChart = new BarChart(year, month);
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
	public void getContentScaleTest() {
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getContentScale")
				.cost("100")
				.type("Food")
				.build());
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getContentScale")
				.cost("200")
				.type("Food")
				.build());
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getContentScale")
				.cost("100")
				.type("Clothing")
				.build());
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getContentScale")
				.cost("1000")
				.type("Entertainment")
				.build());
		accountManager.addCostRecord(new CostRecordBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for getContentScale")
				.cost("800")
				.type("Housing")
				.build());
		
		int actualFoodScale = barChart.getContentTypeCost("Food");
		int actualClothingScale = barChart.getContentTypeCost("Clothing");
		int actualEntertainmentScale = barChart.getContentTypeCost("Entertainment");
		int actualHousingScale = barChart.getContentTypeCost("Housing");
		
		Assert.assertEquals(300, actualFoodScale);
		Assert.assertEquals(100, actualClothingScale);
		Assert.assertEquals(1000, actualEntertainmentScale);
		Assert.assertEquals(800, actualHousingScale);		
	}
}
