package DB;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CalendarManager {
	
	private String year;
	private String month;
	private String day;
	
	private ArrayList<Schedule> allScheduleList = new ArrayList<Schedule>();
	
	public CalendarManager(String year, String month , String day) {
	  this.year = year;
	  this.month = month;
	  this.day = day;
	}

	public void createTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE Schedule( ID INTEGER PRIMARY KEY AUTOINCREMENT ,YEAR TEXT NOT NULL,MONTH TEXT NOT NULL,DAY TEXT NOT NULL,CONTENT TEXT NOT NULL,TIME TEXT NOT NULL, NOTIFY BOOLEAN NOT NULL);";
					
			stmt.executeUpdate(sql);
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

	public void addSchedule(Schedule data) {
		String sql = "insert into Schedule( YEAR, MONTH, DAY,CONTENT, TIME, NOTIFY )" + "VALUES(\'" + data.getYear() + "','"
				+ data.getMonth() + "','" + data.getDay() + "','" + data.getContent() + "','" + data.getTime() + 
				"','"+ data.getisNotify()+ "\');";
		executeSQL(sql);
	}

	public void editSchedule(Schedule schedule, String idDelete) {
		deleteDaySchedule(idDelete);
		addSchedule(schedule);
	}	

	public void deleteDaySchedule(String id) {
		String sql = "delete from Schedule where id ='" + id + "'";
		executeSQL(sql);
	}
	
	public ArrayList<Schedule> getSchedule(){
	  return allScheduleList;
	}
	
	public void setSchedule(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule where year='" + year +"'AND month='" + month +"'AND day='" + day + "';";
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {				
				Schedule schedule = new Schedule();
				schedule.setDay(rs.getString("day"));
				schedule.setYear(rs.getString("year"));
				schedule.setTime(rs.getString("time"));
				schedule.setisNotify(rs.getBoolean("notify"));
				schedule.setTime(rs.getString("time"));
				schedule.setContent(rs.getString("CONTENT"));
				
				allScheduleList.add(schedule);				
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

	public static void main(String args[]) {
		CalendarManager jd = new CalendarManager("2018", "4", "4");
//		jd.createTable();
//		 Schedule data = new Schedule();
//		 data.setYear("2018");
//		 data.setMonth("4");
//		 data.setDay("26");
//		 data.setTime("17:00-21:30");
//		 data.setContent("aaaasa");
//		 data.setisNotify(false);


		
		
//		jd.getAllDataInDB();
	}
}
