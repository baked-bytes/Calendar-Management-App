package Model;

public class ScheduleBuilder {
	private String year;
	private String month;
	private String day;
	private String content;
	private String time;
	private String id;
	private String isNotify;

	public static ScheduleBuilder newInstance() {
		return new ScheduleBuilder();
	}

	public ScheduleBuilder year(String year) {
		this.year = year;
		return this;
	}

	public ScheduleBuilder month(String month) {
		this.month = month;
		return this;
	}

	public ScheduleBuilder day(String day) {
		this.day = day;
		return this;
	}

	public ScheduleBuilder content(String content) {
		this.content = content;
		return this;
	}

	public ScheduleBuilder time(String time) {
		this.time = time;
		return this;
	}

	public ScheduleBuilder id(String id) {
		this.id = id;
		return this;
	}

	public ScheduleBuilder isNotify(String isNotify) {
		this.isNotify = isNotify;
		return this;
	}

	public Schedule build() {
		return new Schedule(year, month, day, content, time, id, isNotify);
	}

}
