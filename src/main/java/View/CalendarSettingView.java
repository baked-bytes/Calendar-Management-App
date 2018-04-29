package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DB.CalendarManager;
import DB.Schedule;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;

public class CalendarSettingView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField startTimeText;
	private JTextField endTimeText;
	private JCheckBox windowRemiderCheck;
	private JTextArea textContentArea;
	private JButton btnOKButton;

	private String year;
	private String month;
	private String day;
	private CalendarView calendarView;
	private Schedule schedule = null;
	boolean isEdit = false;

	public CalendarSettingView(String year, String month, String day, CalendarView calendarView) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.calendarView = calendarView;
		init();
	}

	public CalendarSettingView(String year, String month, String day, Schedule schedule, CalendarView calendarView) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.schedule = schedule;
		this.calendarView = calendarView;
		init();
		setEditDataInText();
		isEdit = true;
	}

	public CalendarSettingView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
		init();
	}

	public void setEditDataInText() {
		String[] parts = schedule.getTime().split("-");
		startTimeText.setText(parts[0]);
		endTimeText.setText(parts[1]);
		textContentArea.setText(schedule.getContent());
		if (schedule.getisNotify().equals("true"))
			windowRemiderCheck.setSelected(true);
	}

	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 191);
		contentPane = new JPanel();
		this.setTitle("Calendar " + year + " /" + month + " /" + day);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		textContentArea = new JTextArea();
		textContentArea.setFont(textContentArea.getFont().deriveFont(16f));
		contentPane.add(textContentArea, BorderLayout.CENTER);

		btnOKButton = new JButton("OK");
		btnOKButton.addActionListener(this);

		contentPane.add(btnOKButton, BorderLayout.SOUTH);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		JLabel lblNewLabel = new JLabel("add time");
		panel.add(lblNewLabel);

		startTimeText = new JTextField();
		startTimeText.setFont(textContentArea.getFont().deriveFont(16f));
		startTimeText.setColumns(5);

		panel.add(startTimeText);
		panel.add(new JLabel("--"));

		endTimeText = new JTextField();
		endTimeText.setFont(endTimeText.getFont().deriveFont(16f));
		panel.add(endTimeText);

		endTimeText.setColumns(5);
		windowRemiderCheck = new JCheckBox("Remider");
		panel.add(windowRemiderCheck);
	}

	public void actionPerformed(ActionEvent e) {
		
		CalendarManager calendarManager = new CalendarManager(year, month, day);
		if (isEdit) {
			calendarManager.editSchedule(getScheduleInputInfo(),schedule.getId() );
			calendarView.refreshTableData();
			dispose();
		} else {			
			calendarManager.addSchedule(getScheduleInputInfo());
			calendarView.refreshTableData();
			dispose();
		}
	}

	public Schedule getScheduleInputInfo() {
		Schedule schedule = new Schedule();
		String content = textContentArea.getText();
		String time = startTimeText.getText() + "-" + endTimeText.getText();
		String RemiderCheck = (windowRemiderCheck.isSelected()) ? "true" : "false";
		System.out.println("con = " + content + "time = " + time + " " + RemiderCheck);
		schedule.setContent(content);
		schedule.setTime(time);
		schedule.setisNotify(RemiderCheck);
		schedule.setDay(day);
		schedule.setMonth(month);
		schedule.setYear(year);
		return schedule;
	}

	public static void main(String[] args) {
		CalendarSettingView calendarSettingView = new CalendarSettingView("2018", "4", "26");
		calendarSettingView.setVisible(true);

	}

}
