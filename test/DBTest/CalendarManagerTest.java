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
import DB.CalendarManager;
import DB.CostRecord;
import DB.Schedule;
import View.AccountView;
import junit.framework.Assert;

public class CalendarManagerTest {
	CalendarManager calendarManager;
	private String year;
	private String month;
	private String day;

	@Before
	public void setUp(){
		year = "2018";
		month = "5";
		day = "11";
		calendarManager = new CalendarManager(year, month, day);
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
			String sql = "DELETE FROM Schedule WHERE CONTENT LIKE 'Test%';";

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
	public void addScheduleTest(){
		Schedule schedule = new Schedule();
		schedule.setYear(year);
		schedule.setMonth(month);
		schedule.setDay(day);
		schedule.setContent("Test for add");
		schedule.setTime("14:00-15:00");
		schedule.setisNotify("true");
		calendarManager.addSchedule(schedule);

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule WHERE CONTENT = \"Test for add\";";
			ResultSet rs = stmt.executeQuery(sql);
			String actualYear = rs.getString("YEAR");
			String actualMonth = rs.getString("MONTH");
			String actualDay = rs.getString("DAY");
			String actualContent = rs.getString("CONTENT");
			String actualTime = rs.getString("TIME");
			String actualIsNotify = rs.getString("NOTIFY");

			Assert.assertEquals("2018", actualYear);
			Assert.assertEquals("5", actualMonth);
			Assert.assertEquals("11", actualDay);
			Assert.assertEquals("Test for add", actualContent);
			Assert.assertEquals("14:00-15:00", actualTime);
			Assert.assertEquals("true", actualIsNotify);
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
	public void editScheduleTest(){
		/*ADD THE TEST DATA FIRST*/
		Schedule schedule = new Schedule();
		schedule.setYear(year);
		schedule.setMonth(month);
		schedule.setDay(day);
		schedule.setContent("Test for edit");
		schedule.setTime("14:00-15:00");
		schedule.setisNotify("true");
		calendarManager.addSchedule(schedule);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule WHERE CONTENT = \"Test for edit\";";
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
		schedule.setTime("16:00-17:00");
		calendarManager.editSchedule(schedule, ID);

		Connection connForEdit = null;
		Statement stmtForEdit = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForEdit = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForEdit = connForEdit.createStatement();
			String sql = "SELECT * FROM Schedule WHERE CONTENT = \"Test for edit\";";
			ResultSet rs = stmtForEdit.executeQuery(sql);
			String actualTime = rs.getString("TIME");
			Assert.assertEquals("16:00-17:00", actualTime);
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
	public void deleteScheduleTest(){
		/*ADD THE TEST DATA FIRST*/
		Schedule schedule = new Schedule();
		schedule.setYear(year);
		schedule.setMonth(month);
		schedule.setDay(day);
		schedule.setContent("Test for delete");
		schedule.setTime("14:00-15:00");
		schedule.setisNotify("true");
		calendarManager.addSchedule(schedule);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule WHERE CONTENT = \"Test for delete\";";
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
		calendarManager.deleteDaySchedule(ID);
		Connection connForDelete = null;
		Statement stmtForDelete = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForDelete = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForDelete = connForDelete.createStatement();
			String sql = "SELECT * FROM Schedule WHERE CONTENT = \"Test for delete\";";
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
