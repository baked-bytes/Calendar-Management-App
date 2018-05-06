package CalendarView;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;

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
	private Schedule schedule = null;
	private Schedule scheduleAfterUserInput;
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
		setEditDataInTextField();
		isEdit = true;
	}

	public void setEditDataInTextField() {
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
		JPanel contentPane = new JPanel();
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
		
		if (confirmTheDataInputFormat()) {
			
			CalendarManager calendarManager = new CalendarManager(year, month, day);

			if (isEdit) {
				calendarManager.editSchedule(scheduleAfterUserInput, schedule.getId());
			} else {
				calendarManager.addSchedule(scheduleAfterUserInput);
			}
			
			calendarView.refreshTableData();
			dispose();
		} // if
	} // actionPerformed()

	public boolean confirmTheDataInputFormat() {
		String content = textContentArea.getText();
		String time = startTimeText.getText() + "-" + endTimeText.getText();
		String remiderCheck = (windowRemiderCheck.isSelected()) ? "true" : "false";

		if (!checkTimeFormat(startTimeText.getText()) || !checkTimeFormat(endTimeText.getText())) {
			showErrorMessageDialog("The time format is wrong.");
			return false;
		} else if (content.length() == 0) {
			showErrorMessageDialog("Content is empty, please enter the schedule content.");
			return false;
		} else if (content.length() >= 20) {
			showErrorMessageDialog("Content is too long, please limit to 20 words.");
			return false;
		}

		setScheduleAfterUserInput(content, time, remiderCheck);
		return true;
	}

	public void setScheduleAfterUserInput(String content, String time, String RemiderCheck) {
		scheduleAfterUserInput = new ScheduleBuilder().year(year).month(month).day(day).isNotify(RemiderCheck)
				.time(time).content(content).build();
	}

	public void showErrorMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public boolean checkTimeFormat(String time) {   // not a number
		if (!time.matches("\\d+") || time.length() != 4)
			return false;
		return true;
	}

	public Schedule getScheduleInputInfo() {

		String content = textContentArea.getText();
		String time = startTimeText.getText() + "-" + endTimeText.getText();
		String RemiderCheck = (windowRemiderCheck.isSelected()) ? "true" : "false";

		Schedule schedule = new ScheduleBuilder().year(year).month(month).day(day).isNotify(RemiderCheck).time(time)
				.content(content).build();
		return schedule;
	}

	public static void main(String[] args) {
		// CalendarSettingView calendarSettingView = new
		// CalendarSettingView("2018", "4", "26");
		// calendarSettingView.setVisible(true);
	}

}
