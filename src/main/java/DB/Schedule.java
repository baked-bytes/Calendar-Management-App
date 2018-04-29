package DB;

public class Schedule {
	private String year;
	private String month;
	private String day;
	private String content;
	private String time;
	private String id;
	private String isNotify;
	
	public String getId(){
	  return id;
	}
	
	public void setId(String id){
	  this.id = id;
	}
	
	
	public String getisNotify(){
	  return isNotify;
	}
	
	public void setisNotify(String isNotify){
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
