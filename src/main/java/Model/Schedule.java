package Model;

public class Schedule {
	private String year;
	private String month;
	private String day;
	private String content;
	private String time;
	private String id;
	private String isNotify;
	private String invite;
	
	public Schedule( String year,String month,String day,String content,String time,String id,String isNotify, String invite ){
	  this.year = year;
	  this.month = month;
	  this.content = content;
	  this.time = time;
	  this.id = id;
	  this.isNotify = isNotify;
	  this.day = day;
	  this.invite = invite;
	}
	
	public String getId(){
	  return id;
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
	
	public String getInvite() {
		return invite;
	}

	public String getTime() {
		return time;
	}

	public String getIsNotify() {
		return isNotify;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setInvite(String invite) {
		this.invite = invite;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}


}
