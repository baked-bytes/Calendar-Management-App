package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import AccountView.AccountView;
import CalendarView.CalendarView;
import DB.AccountManager;
import Model.CostRecord;

public class FunctionChosenView extends JDialog implements ActionListener{

	private String year;
	private String month;
	private String day;
	private String id;
	private JButton btncalendar;
	private JButton btnaccount;
	public FunctionChosenView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
        init();
	}

	public void init(){
		setBounds(100, 100, 255, 70);
		setTitle("current day " + year + "/" + month + "/" + day);
		getContentPane().setLayout(null);
		btncalendar = new JButton("Calendar");
		btncalendar.addActionListener(this);
		btncalendar.setBounds(0, 0, 120, 30);
		getContentPane().add(btncalendar);

		btnaccount = new JButton("Account");
		btnaccount.addActionListener(this);
		btnaccount.setBounds(120, 0, 120, 30);
		getContentPane().add(btnaccount);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btncalendar) {
			try {
				CalendarView frame = new CalendarView(year, month, day);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if( e.getSource() == btnaccount ) {
			try {
				AccountView frame = new AccountView(year, month, day);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}