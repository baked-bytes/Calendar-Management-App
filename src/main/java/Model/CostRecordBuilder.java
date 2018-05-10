package Model;

public class CostRecordBuilder {
	private String year;
	private String month;
	private String day;
	private String content;
	private String cost;
	private String id;
	private String type;

	public CostRecordBuilder year(String year) {
		this.year = year;
		return this;
	}

	public CostRecordBuilder month(String month) {
		this.month = month;
		return this;
	}

	public CostRecordBuilder day(String day) {
		this.day = day;
		return this;
	}

	public CostRecordBuilder content(String content) {
		this.content = content;
		return this;
	}

	public CostRecordBuilder cost(String cost) {
		this.cost = cost;
		return this;
	}

	public CostRecordBuilder type(String type) {
		this.type = type;
		return this;
	}

	public CostRecordBuilder id(String id) {
		this.id = id;
		return this;
	}
	public CostRecord build() {
		return new CostRecord(year, month, day, content, type, id, cost);
	}

}