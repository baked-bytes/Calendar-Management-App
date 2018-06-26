package CalendarView;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DB.CalendarManager;
import Model.Schedule;
import Model.ScheduleBuilder;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class CalendarSettingView extends JFrame implements ActionListener {

	private JTextField startTimeText;
	private JTextField endTimeText;
	private JCheckBox windowRemiderCheck;
	private JTextArea textContentArea;
	private JButton btnOKButton;

	private String year;
	private String month;
	private String day;
	private CalendarView calendarView;
	private Schedule scheduleToBeEdited = null;
	private Schedule scheduleAfterUserInput;
	boolean isEdit = false;
	private String errorMessage;

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
		this.scheduleToBeEdited = schedule;
		this.calendarView = calendarView;
		init();
		setEditDataInTextField();
		isEdit = true;
	}

	public void setEditDataInTextField() {
		String[] parts = scheduleToBeEdited.getTime().split("-");
		startTimeText.setText(parts[0]);
		endTimeText.setText(parts[1]);
		textContentArea.setText(scheduleToBeEdited.getContent());
		if (scheduleToBeEdited.getIsNotify().equals("true"))
			windowRemiderCheck.setSelected(true);
	}

	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 250);
		JPanel contentPane = new JPanel();
		this.setTitle(year + " /" + month + " /" + day);
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
		JLabel lblNewLabel = new JLabel("TIME ");
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

		String userInputContent = textContentArea.getText();
		String userInputStartTime = startTimeText.getText();
		String userInputEndTime = endTimeText.getText();
		String userRemiderCheck = (windowRemiderCheck.isSelected()) ? "true" : "false";

		if (!confirmTheDataInputFormat(userInputContent, userInputStartTime, userInputEndTime))
			showErrorMessageDialog(errorMessage);
		else {
			CalendarManager calendarManager = new CalendarManager(year, month, day);
			if (isEdit) {
				setScheduleAfterUserInputTheEditContent(userInputContent, userInputStartTime, userInputEndTime,
						userRemiderCheck);
				calendarManager.editSchedule(scheduleToBeEdited, scheduleToBeEdited.getId());
			} else {
				setScheduleAfterUserInput(userInputContent, userInputStartTime, userInputEndTime, userRemiderCheck);
				calendarManager.addSchedule(scheduleAfterUserInput);
			}
			calendarView.refreshTableData();
			dispose();
		}
	} // actionPerformed()

	public boolean confirmTheDataInputFormat(String content, String startTime, String endTime ) {

		if (!checkTimeFormat(startTime) || !checkTimeFormat(endTime)) {
			errorMessage = "The time format is wrong.";
			return false;
		} else if (!startTimeMustSmallerThanEndTime(startTime, endTime)) {
			errorMessage = "The start time must smaller than end time.";
			return false;
		} else if (content.length() == 0) {
			errorMessage = "Content is empty, please enter the schedule content.";
			return false;
		} else if (content.length() >= 20) {
			errorMessage = "Content is too long, please limit to 20 words.";
			return false;
		}
		return true;
	}

	public void setScheduleAfterUserInputTheEditContent(String content, String startTime, String endTime,
			String RemiderCheck) {
		String time = startTime + "-" + endTime;
		scheduleToBeEdited.setContent(content);
		scheduleToBeEdited.setTime(time);
		scheduleToBeEdited.setIsNotify(RemiderCheck);
	}

	public void setScheduleAfterUserInput(String content, String startTime, String endTime, String RemiderCheck) {
		String time = startTime + "-" + endTime;
		scheduleAfterUserInput = new ScheduleBuilder().year(year).month(month).day(day).isNotify(RemiderCheck)
				.time(time).content(content).build();
	}

	public void showErrorMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public boolean checkTimeFormat(String time) { // not a number
		if (!time.matches("\\d+") || time.length() != 4)
			return false;
		else {
			int hour = Integer.parseInt(String.valueOf(time.charAt(0)) + String.valueOf(time.charAt(1)));
			int min = Integer.parseInt(String.valueOf(time.charAt(2)) + String.valueOf(time.charAt(3)));

			if (hour > 24)
				return false;
			else if (min > 59)
				return false;
			else if (hour == 24 && min != 00)
				return false;
		}
		return true;
	}

	public boolean startTimeMustSmallerThanEndTime(String startTime, String endTime) {
		int startHour = Integer.parseInt(String.valueOf(startTime.charAt(0)) + String.valueOf(startTime.charAt(1)));
		int endHour = Integer.parseInt(String.valueOf(endTime.charAt(0)) + String.valueOf(endTime.charAt(1)));

		int startMin = Integer.parseInt(String.valueOf(startTime.charAt(2)) + String.valueOf(startTime.charAt(3)));
		int endMin = Integer.parseInt(String.valueOf(endTime.charAt(2)) + String.valueOf(endTime.charAt(3)));

		if (startHour > endHour)
			return false;
		else if (startHour == endHour && startMin > endMin)
			return false;

		return true;
	}
}
