package CalendarView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import DB.CalendarManager;
import Model.Schedule;

public class CalendarIDView extends JDialog implements ActionListener {

	private String year;
	private String month;
	private String day;
	private String id;

	private JButton btnDelete;
	private JButton btnEdit;
	private JTextField textId;
	private JComboBox<Integer> cbEditId;

	private CalendarView calendarView;
	private int scheduleDataSize;

	public CalendarIDView(String year, String month, String day, CalendarView calendarView, int scheduleDataSize) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.calendarView = calendarView;
		this.scheduleDataSize = scheduleDataSize;
		init();
	}

	public Integer[] setDayScheduleItemSize() {
		Integer[] dayScheduleItemSize = new Integer[scheduleDataSize];
		for (int i = 0; i < scheduleDataSize; i++) {
			dayScheduleItemSize[i] = i + 1;
		}
		return dayScheduleItemSize;
	}

	public void init() {
		setBounds(100, 100, 300, 200);
		setTitle("Select ID number");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		cbEditId = new JComboBox<>(setDayScheduleItemSize());
		cbEditId.setBounds(70, 30, 75, 30);
		getContentPane().add(cbEditId);

		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(this);
		btnDelete.setBounds(0, 93, 120, 30);
		getContentPane().add(btnDelete);

		btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(this);
		btnEdit.setBounds(120, 93, 120, 30);
		getContentPane().add(btnEdit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CalendarManager calendarManager = new CalendarManager(year, month, day);
		int chosenId = (int) cbEditId.getSelectedItem();

		if (e.getSource() == btnDelete) {
			calendarManager.deleteDaySchedule(chosenId);
			calendarView.refreshTableData();
			dispose();
		} else if (e.getSource() == btnEdit) {
			Schedule schedule = calendarManager.getIdSchdule(chosenId);
			CalendarSettingView calendarSettingView = new CalendarSettingView(year, month, day, schedule, calendarView);
			calendarSettingView.setVisible(true);
			calendarSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dispose();
		}

	}

}
