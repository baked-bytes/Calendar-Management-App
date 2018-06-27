package DBTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DB.CalendarManager;
import Model.Schedule;
import Model.ScheduleBuilder;

import junit.framework.Assert;

public class CalendarManagerTest {
	CalendarManager calendarManager;
	private String year;
	private String month;
	private String day;

	@Before
	public void setUp() {
		year = "2018";
		month = "5";
		day = "11";
		calendarManager = new CalendarManager(year, month, day);
	}

	@After
	public void tearDown() {
		calendarManager.setSchedule();
		ArrayList<Schedule> data = calendarManager.getSchedule();
		for (Schedule schedule : data) {
			if (schedule.getContent().contains("Test")) {
				calendarManager.deleteDaySchedule(data.size());
				System.out.println("Test data in table was deleted successfully");
			}
		}
	}

	@Test
	public void addScheduleTest() {
		Schedule schedule = new ScheduleBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for add")
				.time("1400-1500")
				.isNotify("false")
				.build();
		calendarManager.addSchedule(schedule);

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule WHERE YEAR = \"2018\" " + "AND MONTH = \"5\" " + "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for add\" " + "AND TIME = \"1400-1500\" " + "AND NOTIFY = \"false\";";
			ResultSet rs = stmt.executeQuery(sql);
			rs.getString("YEAR");// For checking whether the data exists
		} catch (SQLException e) {
			Assert.fail("Adding schedule was failed");
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
				e.printStackTrace();
			}
		}
	}

	@Test
	public void addScheduleWithRemindTest() {
		Schedule schedule = new ScheduleBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for add")
				.time("1400-1500")
				.isNotify("true")
				.build();

		calendarManager.addSchedule(schedule);

		String filePath = "reminderrecord.txt";
		BufferedReader br = null;
		String expectExistedContent = null;
		String fileContentInString = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
			String fileContent = br.readLine();

			while (fileContent != null) {
				sb.append(fileContent);
				sb.append("\n");
				fileContent = br.readLine();
			}

			expectExistedContent = schedule.getYear() + "/" 
					+ ((schedule.getMonth().length() > 1) ? schedule.getMonth() : "0" + schedule.getMonth())  + "/" 
					+ schedule.getDay() + " "
					+ schedule.getTime().substring(0, 2) + ":" + schedule.getTime().substring(2, 4) + ":00" + " "
					+ schedule.getContent();

			fileContentInString = sb.toString();
			
			System.out.println("fileContentInString = " + fileContentInString);
			System.out.println("expect = " + expectExistedContent);

			Assert.assertTrue(fileContentInString.contains(expectExistedContent));

		} catch (FileNotFoundException e) {
			System.err.println("Need to check whether the " + filePath + " exists");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			RandomAccessFile raf = null;
			try {
				if (br != null)
					br.close();
				if (fileContentInString.contains(expectExistedContent)) {//Check whether it's needed to restore the "reminderrecord.txt"
					//Restore "reminderrecord.txt" to the original state
					raf = new RandomAccessFile(filePath, "rw");
					long length = raf.length() - 1;
					byte b;
					do {
						length -= 1;//Get rid of the EOF character
						raf.seek(length);//Point to the corresponding location
						b = raf.readByte();
					} while (b != 10 && length > 0);//byte = 10 : escape character
					raf.setLength(length + 1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (raf != null)
						raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void editScheduleTest() {
		/* ADD THE TEST DATA FIRST */
		Schedule schedule = new ScheduleBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for edit")
				.time("1400-1500")
				.isNotify("false")
				.build();
		
		calendarManager.addSchedule(schedule);

		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule WHERE YEAR = \"2018\" " + "AND MONTH = \"5\" " + "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for edit\" " + "AND TIME = \"1400-1500\" " + "AND NOTIFY = \"false\";";
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
				e.printStackTrace();
			}
		}
		/* THEN EDIT THE DATA CREATED ABOVE */
		schedule.setTime("1600-1700");
		calendarManager.editSchedule(schedule, ID);

		Connection connForEdit = null;
		Statement stmtForEdit = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForEdit = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForEdit = connForEdit.createStatement();
			String sql = "SELECT * FROM Schedule WHERE YEAR = \"2018\" " + "AND MONTH = \"5\" " + "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for edit\" " + "AND TIME = \"1600-1700\" " + "AND NOTIFY = \"false\";";
			ResultSet rs = stmtForEdit.executeQuery(sql);
			String actualTime = rs.getString("TIME");
			Assert.assertEquals("1600-1700", actualTime);
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
				e.printStackTrace();
			}
		}
	}

	@Test
	public void editScheduleWithReminderTest() {
		Schedule schedule = new ScheduleBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for edit")
				.time("1400-1500")
				.isNotify("false")
				.build();

		calendarManager.addSchedule(schedule);
		
		Connection c = null;
		Statement stmt = null;
		String ID = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule WHERE YEAR = \"2018\" " + "AND MONTH = \"5\" " + "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for edit\" " + "AND TIME = \"1400-1500\" " + "AND NOTIFY = \"false\";";
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
				e.printStackTrace();
			}
		}
		
		schedule.setIsNotify("true");
		calendarManager.editSchedule(schedule, ID);

		String filePath = "reminderrecord.txt";
		BufferedReader br = null;
		String expectExistedContent = null;
		String fileContentInString = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
			String fileContent = br.readLine();

			while (fileContent != null) {
				sb.append(fileContent);
				sb.append("\n");
				fileContent = br.readLine();
			}

			expectExistedContent = schedule.getYear() + "/" 
					+ ((schedule.getMonth().length() > 1) ? schedule.getMonth() : "0" + schedule.getMonth())  + "/" 
					+ schedule.getDay() + " "
					+ schedule.getTime().substring(0, 2) + ":" + schedule.getTime().substring(2, 4) + ":00" + " "
					+ schedule.getContent();

			fileContentInString = sb.toString();
			
			System.out.println("fileContentInString = " + fileContentInString);
			System.out.println("expect = " + expectExistedContent);

			Assert.assertTrue(fileContentInString.contains(expectExistedContent));

		} catch (FileNotFoundException e) {
			System.err.println("Need to check whether the " + filePath + " exists");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			RandomAccessFile raf = null;
			try {
				if (br != null)
					br.close();
				if (fileContentInString.contains(expectExistedContent)) {//Check whether it's needed to restore the "reminderrecord.txt"
					//Restore "reminderrecord.txt" to the original state
					raf = new RandomAccessFile(filePath, "rw");
					long length = raf.length() - 1;
					byte b;
					do {
						length -= 1;//Get rid of the EOF character
						raf.seek(length);//Point to the corresponding location
						b = raf.readByte();
					} while (b != 10 && length > 0);//byte = 10 : escape character
					raf.setLength(length + 1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (raf != null)
						raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void deleteScheduleTest() {
		/* ADD THE TEST DATA FIRST */
		Schedule schedule = new ScheduleBuilder()
				.year(year)
				.month(month)
				.day(day)
				.content("Test for delete")
				.time("1400-1500")
				.isNotify("false")
				.build();
		
		calendarManager.addSchedule(schedule);

		/* THEN DELETE THE DATA CREATED ABOVE */
		calendarManager.setSchedule();
		ArrayList<Schedule> data = calendarManager.getSchedule();
		calendarManager.deleteDaySchedule(data.size());
		Connection connForDelete = null;
		Statement stmtForDelete = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connForDelete = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmtForDelete = connForDelete.createStatement();
			String sql = "SELECT * FROM Schedule WHERE YEAR = \"2018\" " + "AND MONTH = \"5\" " + "AND DAY = \"11\" "
					+ "AND CONTENT = \"Test for delete\" " + "AND TIME = \"1400-1500\" " + "AND NOTIFY = \"false\";";
			ResultSet rs = stmtForDelete.executeQuery(sql);
			rs.getString("ID");// For Checking whether the data exists
		} catch (SQLException e) {
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
				e.printStackTrace();
			}
		}
	}
}
