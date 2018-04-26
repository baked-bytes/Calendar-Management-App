package DB;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBC {


	public void createTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE Calendar(" + "P_Id int NOT NULL AUTO_INCREMENT,"
					+ "YEAR           TEXT    NOT NULL," + " MONTH          TEXT    NOT NULL,"
					+ "DAY          TEXT    NOT NULL," + "CONTENT         TEXT    NOT NULL," + "TIME TEXT NOT NULL);";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
	
	public void addTheDaySchedule(Schedular data){
		


		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery("insert into Calendar( YEAR, MONTH, DAY,CONTENT, TIME )"
//					+ "values(\"" + data.getYear() + "\",\"" + data.getMonth()+ "\",\""+ data.getDay()+"\",\"" +
//			            data.getContent()+ "\",\"" + data.getTime() + "\");" );
			
			String sql = "insert into Calendar( YEAR, MONTH, DAY,CONTENT, TIME )"
					+ "VALUES(\'" + data.getYear() + "','" + data.getMonth()+ "','"+ data.getDay()+"','" +
		            data.getContent()+ "','" + data.getTime() + "\');" ;
			System.out.println( sql );
			
			
			stmt.executeUpdate(sql);
			c.commit();
			
			//rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}
	
	public void getTheDaySchedule(String year, String month, String day ){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Calendar where year=" + year + " and month=" + month + " and day=" + day + ";");
			while (rs.next()) {
				System.out.println("ID = " + rs.getInt("id"));
				System.out.println("year = " + rs.getString("year"));
				System.out.println("month = " + rs.getString("month"));
				System.out.println("day = " + rs.getString("day"));
				System.out.println("time = " + rs.getString("time"));
				System.out.println("content = " + rs.getString("content") + "\n");

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

	public void getAllDataInDB() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Calendar;");
			while (rs.next()) {
				System.out.println("ID = " + rs.getInt("id"));
				System.out.println("year = " + rs.getString("year"));
				System.out.println("month = " + rs.getString("month"));
				System.out.println("day = " + rs.getString("day"));
				System.out.println("time = " + rs.getString("time"));
				System.out.println("content = " + rs.getString("content") + "\n");

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
		SQLiteJDBC jd = new SQLiteJDBC();
//		jd.createTable();
//		jd.getAllDataInDB();
//		jd.getTheDaySchedule("2018", "4", "18" );
////		
		
		Schedular data = new Schedular();
		data.setYear("2018");
		data.setDay("24");
		data.setMonth("5");
		data.setTime("20:00-21:30");
		data.setContent("travel");		
		jd.addTheDaySchedule(data);
		
		jd.getAllDataInDB();
	}
}
