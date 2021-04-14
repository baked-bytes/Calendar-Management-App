package CalendarView;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import Model.Schedule;
import Model.ScheduleBuilder;

public class PopUp {
	private String year;
	private String month;
	private String day;
	private Schedule needtoremind;
	public int flag;
	Timer timer;
	TimerTask tt;
	public PopUp(Schedule needtoremind) {
		  this.year = needtoremind.getYear();
		  this.month = needtoremind.getMonth();
		  this.day = needtoremind.getDay();
		  this.needtoremind = needtoremind;
		  this.flag=0;
		  this.pop();	 
	}
	public void pop() {
	    timer = new Timer();
	    tt = new TimerTask() {
	        @Override
	        public void run() {
	            Calendar cal = Calendar.getInstance();

	            int hour = cal.get(Calendar.HOUR_OF_DAY);
	            int min = cal.get(Calendar.MINUTE);
	            int sec = cal.get(Calendar.SECOND);
	            String[] parts = needtoremind.getTime().split("-");
	            
	            if (flag==0 && hour == Integer.parseInt(parts[0].substring(0, 2))  && min == Integer.parseInt(parts[0].substring(2, 4))  && sec == 0) {
	                JOptionPane.showMessageDialog(null, "PopUp Success at "+new Date().toString());
	                System.out.println("PopUp Success at "+new Date().toString());
	            }
	        }
	    };
	    timer.schedule(tt, 1000, 1000 * 1);
	}
	public void deleteReminder()
	{
		tt.cancel();
		timer.cancel();
		timer.purge();
	}
	}
