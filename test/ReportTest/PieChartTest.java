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
import Report.PieChart;

public class PieChartTest {
	AccountManager accountManager;
	private String year;
	private String month;
	private String day;
	AccountView accountView;
	PieChart pieChart;
	
	@Before
	public void setUp() {
		year = "2018";
		month = "7";
		day = "1";
		accountManager = new AccountManager(year, month, day);
		accountView = new AccountView(year, month, day);
		pieChart = new PieChart(year, month);
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
		
		Double actualFoodScale = pieChart.getContentScale("Food");
		Double actualClothingScale = pieChart.getContentScale("Clothing");
		Double actualEntertainmentScale = pieChart.getContentScale("Entertainment");
		Double actualHousingScale = pieChart.getContentScale("Housing");
		
		Assert.assertEquals(13, actualFoodScale, 0.00001);
		Assert.assertEquals(4, actualClothingScale, 0.00001);
		Assert.assertEquals(45, actualEntertainmentScale, 0.00001);
		Assert.assertEquals(36, actualHousingScale, 0.00001);		
	}
}
