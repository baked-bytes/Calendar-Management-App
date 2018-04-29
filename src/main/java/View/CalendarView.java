package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DB.CalendarManager;
import DB.Schedule;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalendarView extends JFrame implements ActionListener {

	private String year;
	private String month;
	private String day;
	private JTable table;
	private JButton addButton;
	private JButton editButton;

	private static CalendarView calendarView;
	private int scheduleDataSize;

	public CalendarView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
		scheduleDataSize = 0;
		init();
	}

	public String[][] getdata() {
		System.out.println("getdata()");
		CalendarManager calendarManager = new CalendarManager(year, month, day);
		calendarManager.setSchedule();
		ArrayList<Schedule> data = calendarManager.getSchedule();
		scheduleDataSize = data.size();
		String[][] mydata = new String[data.size()][3];

		for (int i = 0; i < data.size(); i++) {
			mydata[i][0] = data.get(i).getId();
			// mydata[i][0] = Integer.toString(i+1);
			mydata[i][1] = data.get(i).getTime();
			mydata[i][2] = data.get(i).getContent();
		}
		return mydata;
	}

	public void refreshTableData() {
		// DefaultTableModel model = (DefaultTableModel) table.getModel();
		// Object[] data = {Integer.toString(++scheduleDataSize),
		// schedule.getTime(), schedule.getContent()};
		// model.addRow(data);

		CalendarManager calendarManager = new CalendarManager(year, month, day);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		calendarManager.setSchedule();
		ArrayList<Schedule> data = calendarManager.getSchedule();
		scheduleDataSize = data.size();
		String[] mydata = new String[3];
		for (int i = 0; i < data.size(); i++) {
			mydata[0] = data.get(i).getId();
			mydata[1] = data.get(i).getTime();
			mydata[2] = data.get(i).getContent();
			model.addRow(mydata);
		}
		model.fireTableDataChanged();

	}

	public void deleteRowTableData(int num) {
		((DefaultTableModel) table.getModel()).removeRow(num);
	}

	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] columns = { "ID", "Time", "Schedule" };

		Object[][] data = getdata();

		DefaultTableModel model = new DefaultTableModel(data, columns);
		table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowHeight(30);
		table.setFont(new Font("Serif", Font.BOLD, 20));

		JLabel lblHeading = new JLabel("current day " + year + "/" + month + "/" + day);
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(lblHeading, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setVisible(true);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		addButton = new JButton("     ADD Schedule      ");
		editButton = new JButton("EDIT/DELETE Schedule");
		addButton.setFont(new Font("Arial", Font.PLAIN, 24));
		editButton.setFont(new Font("Arial", Font.PLAIN, 24));
		panel.add(addButton);
		panel.add(editButton);

		addButton.addActionListener(this);
		editButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			CalendarSettingView calendarSettingView = new CalendarSettingView(year, month, day, this);
			calendarSettingView.setVisible(true);
			calendarSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else if (e.getSource() == editButton) {
			CalendarIDView dialog = new CalendarIDView(year, month, day, this);
			dialog.setModal(true);
			dialog.setVisible(true);
		}
	}

	public static void main(String[] args) {
		calendarView = new CalendarView("2018", "4", "26");
		calendarView.getdata();
	}
}