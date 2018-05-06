package Model;

public class Schedule {
	private String year;
	private String month;
	private String day;
	private String content;
	private String time;
	private String id;
	private String isNotify;
	
	public Schedule( String year,String month,String day,String content,String time,String id,String isNotify ){
	  this.year = year;
	  this.month = month;
	  this.content = content;
	  this.time = time;
	  this.id = id;
	  this.isNotify = isNotify;
	  this.day = day;
	}
	
	public String getId(){
	  return id;
	}	
	
	public String getisNotify(){
	  return isNotify;
	}
	
	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public String getContent() {
		return content;
	}


	public String getTime() {
		return time;
	}


}