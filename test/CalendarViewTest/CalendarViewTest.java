package CalendarViewTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CalendarView.CalendarView;
import Model.Schedule;
import Model.ScheduleBuilder;
import junit.framework.Assert;

public class CalendarViewTest {

	CalendarView calendarView;
	ArrayList<Schedule> scheduleList;

	private String year;
	private String month;
	private String day;

	@Before
	public void setUp() throws Exception {
		calendarView = new CalendarView("2018", "5", "7");
		scheduleList = new ArrayList<Schedule>();

		this.year = "2018";
		this.month = "5";
		this.day = "7";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getdataTest_One() {
		Schedule schedule = new ScheduleBuilder().year(year).month(month).day(day).time("1200-1500")
				.content("test content").isNotify("true").build();
		
		scheduleList.add(schedule);
		String[][] data = calendarView.getdata(scheduleList);

		assertEquals("1", data[0][0]);
		assertEquals("1200-1500", data[0][1]);
		assertEquals("test content", data[0][2]);
		assertEquals("O", data[0][3]);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getdataErrorTest() {
		String[][] data = calendarView.getdata(scheduleList);
		System.out.println(data[0][4]);
	}
	
	@Test
	public void getdataTest_Two() {
		Schedule schedule = new ScheduleBuilder().year(year).month(month).day(day).time("1200-1500")
				.content("test content").isNotify("true").build();
		
		Schedule schedule2 = new ScheduleBuilder().year(year).month(month).day(day).time("1300-1500")
				.content("test content second").isNotify("false").build();
		
		scheduleList.add(schedule);
		scheduleList.add(schedule2);
		
		String[][] data = calendarView.getdata(scheduleList);

		assertEquals("1", data[0][0]);
		assertEquals("1200-1500", data[0][1]);
		assertEquals("test content", data[0][2]);
		assertEquals("O", data[0][3]);
		
		assertEquals("2", data[1][0]);
		assertEquals("1300-1500", data[1][1]);
		assertEquals("test content second", data[1][2]);
		assertEquals("X", data[1][3]);
		
	    try{
	    	System.out.println(data[2][0]);
	        fail("expected IndexOutOfBoundsException");
	    } catch(IndexOutOfBoundsException e) {
	    } 
	}

}
