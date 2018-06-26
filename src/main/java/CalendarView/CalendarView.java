package CalendarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DB.CalendarManager;
import Model.Schedule;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CalendarView extends JFrame implements ActionListener {

	private String year;
	private String month;
	private String day;
	private JTable table;
	private JButton addButton;
	private JButton editButton;

	private int scheduleDataSize;
	CalendarManager calendarManager;
	CalendarSettingView calendarSettingView = null;

	public CalendarView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
		scheduleDataSize = 0;
		calendarManager = new CalendarManager(year, month, day);
		init();
	}

	public String[][] getdata(ArrayList<Schedule> data) {
		scheduleDataSize = data.size();
		String[][] mydata = new String[data.size()][4];

		for (int i = 0; i < data.size(); i++) {
			mydata[i][0] = Integer.toString(i + 1);
			mydata[i][1] = data.get(i).getTime();
			mydata[i][2] = data.get(i).getContent();
			mydata[i][3] = data.get(i).getIsNotify().equals("true") ? "O" : "X";
		}
		return mydata;
	}

	public ArrayList<Schedule> getTheDaySchedule() {
		calendarManager.setSchedule();
		ArrayList<Schedule> data = calendarManager.getSchedule();
		return data;
	}

	public void refreshTableData() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		ArrayList<Schedule> data = getTheDaySchedule();
		scheduleDataSize = data.size();
		String[] mydata = new String[4];
		for (int i = 0; i < data.size(); i++) {
			mydata[0] = Integer.toString(i + 1);
			mydata[1] = data.get(i).getTime();
			mydata[2] = data.get(i).getContent();
			mydata[3] = data.get(i).getIsNotify().equals("true") ? "O" : "X";
			model.addRow(mydata);
		}
		model.fireTableDataChanged();
	}

	public void init() {

		setTitle("Calendar Table View");
		this.getContentPane().setBackground(Color.YELLOW);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] columns = { "ID", "TIME", "SCHEDULE", "REMIND" };

		Object[][] data = getdata(getTheDaySchedule());

		DefaultTableModel model = new DefaultTableModel(data, columns);

		table = new JTable(model);
		table.setEnabled(false); //

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowHeight(50);
		table.setFont(new Font("Serif", Font.BOLD, 20));

		JLabel lblHeading = new JLabel(year + "/" + month + "/" + day);
		lblHeading.setForeground(Color.BLUE);
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(lblHeading, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
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



	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			if (calendarSettingView == null || !calendarSettingView.isDisplayable()) {
				calendarSettingView = new CalendarSettingView(year, month, day, this);
				calendarSettingView.setVisible(true);
				calendarSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} 
		} else if (e.getSource() == editButton) {
			CalendarIDView dialog = new CalendarIDView(year, month, day, this, scheduleDataSize);
			dialog.setModal(true);
			dialog.setVisible(true);
		}
	}
}