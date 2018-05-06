package CalendarViewTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CalendarView.CalendarIDView;
import CalendarView.CalendarView;
import junit.framework.Assert;

public class CalendarIDViewTest {

	CalendarIDView calendarIDView;
	
	@Before
	public void setUp() throws Exception {
		CalendarView calendarView = null;
		calendarIDView = new CalendarIDView("2018", "5", "6", calendarView, 5);
	}

	@After
	public void tearDown() throws Exception {
		calendarIDView = null;
	}

	@Test
	public void setDayScheduleItemSizeTest(){
		Integer[] result = calendarIDView.setDayScheduleItemSize();
		assertEquals(1, result[0].intValue());
		assertEquals(2, result[1].intValue());
		assertEquals(3, result[2].intValue());
		assertEquals(4, result[3].intValue());
		assertEquals(5, result[4].intValue());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void setDayScheduleItemSizeIndexOutOfBoundsExceptionTest() {
		Integer[] result = calendarIDView.setDayScheduleItemSize();
		result[6].intValue();
	}

}
