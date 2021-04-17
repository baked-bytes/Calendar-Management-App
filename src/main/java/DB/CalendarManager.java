package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CalendarView.PopUp;
import Model.ScheduleBuilder;
import Model.Schedule;

public class CalendarManager {

	private String year;
	private String month;
	private String day;
	public static ArrayList<PopUp> timers=new ArrayList<PopUp>();
	private ArrayList<Schedule> allScheduleList = new ArrayList<Schedule>();

	public CalendarManager(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public void createTable() {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = connection.createStatement();
			String sql = "CREATE TABLE Schedule( ID INTEGER PRIMARY KEY AUTOINCREMENT ,YEAR TEXT NOT NULL,MONTH TEXT NOT NULL,DAY TEXT NOT NULL,CONTENT TEXT NOT NULL,TIME TEXT NOT NULL, NOTIFY BOOLEAN NOT NULL, INVITE TEXT NULL);";

			stmt.executeUpdate(sql);
			connection.commit();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public void executeSQL(String sqlStmt) {
		System.out.println("executeSQL");
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = connection.createStatement();
			stmt.executeUpdate(sqlStmt);
			connection.commit();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

	public void addSchedule(Schedule data) {
		System.out.println("addschedule");
		String sql = "insert into Schedule( YEAR, MONTH, DAY,CONTENT, TIME, NOTIFY, INVITE )" + "VALUES(\'" + data.getYear()
				+ "','" + data.getMonth() + "','" + data.getDay() + "','" + data.getContent() + "','" + data.getTime()
				+ "','" + data.getIsNotify() + "','"+ data.getInvite() +"\');";
		executeSQL(sql);
		if(data.getIsNotify() == "true") {
			Connection c = null;
			Statement stmt = null;
			Schedule reminderdata = null;
		
		
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
				c.setAutoCommit(false);
				System.out.println("Opened database successfully");

				stmt = c.createStatement();
				String sql2 = "SELECT * FROM Schedule";

				ResultSet rs = stmt.executeQuery(sql2);
				while (rs.next()) {
					 reminderdata = new ScheduleBuilder().year(year).month(month).day(day)
							.isNotify(rs.getString("notify")).time(rs.getString("time")).id(rs.getString("id"))
							.content(rs.getString("CONTENT")).invite(rs.getString("INVITE")).build();

				}
				rs.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			timers.add(new PopUp(reminderdata));
			System.out.print(timers);
			
		}
	}

	public void editSchedule(Schedule data, String idEdit) {
		System.out.println("editSchedule");
		Connection c = null;
		Statement stmt = null;
		Schedule originaldata = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sqlOriginal = "SELECT * FROM Schedule where id ='" + idEdit + "'";
			System.out.print(idEdit);
			ResultSet rs = stmt.executeQuery(sqlOriginal);
			while (rs.next()) {
				 originaldata = new ScheduleBuilder().year(year).month(month).day(day)
						.isNotify(rs.getString("notify")).time(rs.getString("time")).id(rs.getString("id"))
						.content(rs.getString("CONTENT")).invite(rs.getString("INVITE")).build();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		if(!(originaldata.getIsNotify().equals(data.getIsNotify()))){
			if(!timers.isEmpty())
			{
				for(int i=0;i<timers.size();i++)
				{
					//System.out.print(timers.get(i).needtoremind.getTime());
					if(timers.get(i).needtoremind.getTime().equals(originaldata.getTime()))
					{
						//System.out.println("delete");
						timers.get(i).deleteReminder();
						timers.remove(i);
					}
				}
			}
			timers.add(new PopUp(data));
		}
		
		String sql = "update Schedule set CONTENT='" + data.getContent() + "',TIME = '" + data.getTime() + "',NOTIFY='"
				+ data.getIsNotify() +"',INVITE = '"+ data.getInvite()+ "'where id = '" + idEdit + "';";
		executeSQL(sql);
		
	}

	public void deleteDaySchedule(int dayNumId) {
		
		System.out.println("deleteDaySchedule");
		setSchedule();
		String id = getSchedule().get(dayNumId-1).getId();
		
		String sql = "delete from Schedule where id ='" + id + "' AND year='" + year + "'AND month='" + month
				+ "'AND day='" + day + "';";
		Connection c = null;
		Statement stmt = null;
		Schedule reminderdata = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql2 = "SELECT * FROM Schedule where id ='" + id + "'";

			ResultSet rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				 reminderdata = new ScheduleBuilder().year(year).month(month).day(day)
						.isNotify(rs.getString("notify")).time(rs.getString("time")).id(rs.getString("id"))
						.content(rs.getString("CONTENT")).invite(rs.getString("INVITE")).build();

			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if(!(reminderdata.getIsNotify().equals(true))){
			
			//System.out.print(reminderdata.getTime());
			//System.out.print(timers);
			if(!timers.isEmpty())
			{
				for(int i=0;i<timers.size();i++)
				{
					//System.out.print(timers.get(i).needtoremind.getTime());
					if(timers.get(i).needtoremind.getTime().equals(reminderdata.getTime()))
					{
						//System.out.println("delete");
						timers.get(i).deleteReminder();
						timers.remove(i);
					}
				}
			}

		}
		executeSQL(sql);
	}

	public ArrayList<Schedule> getSchedule() {
		return allScheduleList;
	}
	

	public Schedule getIdSchdule(int dayNumId) {
		Connection c = null;
		Statement stmt = null;
		Schedule schedule = null ;
		setSchedule();
		String id = getSchedule().get(dayNumId-1).getId();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule where id='" + id + "' AND year='" + year + "'AND month='" + month
					+ "'AND day='" + day + "';";
			System.out.println("sql = " + sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				schedule = new ScheduleBuilder().year(year).month(month).day(day)
						.isNotify(rs.getString("notify")).time(rs.getString("time")).id(rs.getString("id"))
						.content(rs.getString("CONTENT")).invite(rs.getString("INVITE")).build();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return schedule;
	}

	public void setSchedule() {
		Connection c = null;
		Statement stmt = null;
		allScheduleList.clear();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Schedule where year='" + year + "'AND month='" + month + "'AND day='" + day
					+ "' order by time ASC;";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Schedule schedule = new ScheduleBuilder().year(year).month(month).day(day)
						.isNotify(rs.getString("notify")).time(rs.getString("time")).id(rs.getString("id"))
						.content(rs.getString("CONTENT")).invite(rs.getString("INVITE")).build();

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
	
}
