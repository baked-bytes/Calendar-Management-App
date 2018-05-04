package DB;

public class CostRecord {
	private String year;
	private String month;
	private String day;
	private String content;
	private String type;
	private String id;
	private String cost;
	
	public String getId(){
	  return id;
	}
	
	public void setId(String id){
	  this.id = id;
	}
	
	
	public String gettype(){
	  return type;
	}
	
	public void settype(String type){
		this.type = type;
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

	public String getcost() {
		return cost;
	}

	public void setcost(String cost) {
		this.cost = cost;
	}

}
