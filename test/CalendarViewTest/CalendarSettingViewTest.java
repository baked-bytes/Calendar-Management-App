package CalendarViewTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CalendarView.CalendarSettingView;
import CalendarView.CalendarView;

public class CalendarSettingViewTest {

	CalendarSettingView calendarSettingView;

	@Before
	public void setUp() {
		CalendarView calendarView = null;
		calendarSettingView = new CalendarSettingView("2018", "5", "6", calendarView);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheckTimeFormat() {
		assertFalse(calendarSettingView.checkTimeFormat("12"));
		assertFalse(calendarSettingView.checkTimeFormat("120000"));
		assertFalse(calendarSettingView.checkTimeFormat("hell"));
		assertFalse(calendarSettingView.checkTimeFormat(""));
		assertFalse(calendarSettingView.checkTimeFormat("2500"));
		assertFalse(calendarSettingView.checkTimeFormat("2460"));
		assertFalse(calendarSettingView.checkTimeFormat("2410"));
	}

	@Test
	public void testCheckTimeFormatTrue() {
		assertTrue(calendarSettingView.checkTimeFormat("1200"));
		assertTrue(calendarSettingView.checkTimeFormat("1233"));
	}

	@Test
	public void testStartTimeMustSmallerThanEndTime() {
       assertTrue(calendarSettingView.startTimeMustSmallerThanEndTime( "1200", "1300" ) );
       assertTrue(calendarSettingView.startTimeMustSmallerThanEndTime( "2359", "2400" ) );
       assertTrue(calendarSettingView.startTimeMustSmallerThanEndTime( "0000", "1359" ) );
       assertTrue(calendarSettingView.startTimeMustSmallerThanEndTime( "1200", "1200" ) );
       
       assertFalse(calendarSettingView.startTimeMustSmallerThanEndTime( "1200", "1100" ) );
       assertFalse(calendarSettingView.startTimeMustSmallerThanEndTime( "1200", "1159" ) );
       assertFalse(calendarSettingView.startTimeMustSmallerThanEndTime( "1210", "1200" ) );
       assertFalse(calendarSettingView.startTimeMustSmallerThanEndTime( "2400", "2330" ) );
	}
	
	@Test
	public void testConfirmTheDataInputFormat() {
		boolean timeLengthIsOne = calendarSettingView.confirmTheDataInputFormat("test", "2000", "1");
		assertFalse(timeLengthIsOne);
		
		boolean sartTimeCannotBeEnglish = calendarSettingView.confirmTheDataInputFormat("test", "ss", "1300");
		assertFalse(sartTimeCannotBeEnglish);
		
		boolean startTimeIsGreaterThanEndTime = calendarSettingView.confirmTheDataInputFormat("test", "1310", "1300");
		assertFalse(startTimeIsGreaterThanEndTime);
		
		boolean endTimeCannotBeEnglish = calendarSettingView.confirmTheDataInputFormat("test", "1310", "ss");
		assertFalse(endTimeCannotBeEnglish);
		
		boolean contentCannotBeEmpty = calendarSettingView.confirmTheDataInputFormat("", "1300", "1400");
		assertFalse(contentCannotBeEmpty);		
		
		boolean contentExceedLength = calendarSettingView.confirmTheDataInputFormat("hello, i am a test content and i am over 20 words.", "1300", "1400");
		assertFalse(contentExceedLength);		
	}
	
	@Test
	public void testConfirmTheDataInputFormatTrue(){
		boolean test1 = calendarSettingView.confirmTheDataInputFormat("test~", "1300", "1400");
		assertTrue(test1);
		
		boolean test2 = calendarSettingView.confirmTheDataInputFormat("test~", "2230", "2300");
		assertTrue(test2);
		
		boolean test3 = calendarSettingView.confirmTheDataInputFormat("this is a test", "1800", "2400");
		assertTrue(test3);
	}
	
}
