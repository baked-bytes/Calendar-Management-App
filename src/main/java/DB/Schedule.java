package DB;

public class Schedule {
	private String year;
	private String month;
	private String day;
	private String content;
	private String time;
	private boolean isNotify;
	
	
	public boolean getisNotify(){
	  return isNotify;
	}
	
	public void setisNotify(boolean isNotify){
		this.isNotify = isNotify;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
