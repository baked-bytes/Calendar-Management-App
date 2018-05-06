package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import DB.AccountManager;
import DB.CostRecord;

public class AccountIDView extends JDialog implements ActionListener{

	private String year;
	private String month;
	private String day;
	private String id;
	public static JComboBox<String> idBox = new JComboBox<String>();
	private JButton btnDelete;
	private JButton btnEdit;
	private ArrayList<String> adayCostRecordIDList;
	public AccountIDView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
        init();
	}

	public void init(){
		AccountManager accountManager = new AccountManager(year, month, day);
		adayCostRecordIDList = accountManager.getadayCostRecordIDList();
		setBounds(100, 100, 260, 180);
		setTitle("Select ID number");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		idBox.setBounds(70, 30, 100, 30);
		getContentPane().add(idBox);
		JLabel label = new JLabel("Select ID");
		label.setBounds(20, 30, 50, 30);
		getContentPane().add(label);
		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(this);
		btnDelete.setBounds(0, 93, 120, 30);
		getContentPane().add(btnDelete);

		btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(this);
		btnEdit.setBounds(120, 93, 120, 30);
		getContentPane().add(btnEdit);
	}

	public static void main(String[] args) {
		AccountIDView test = new AccountIDView("2018","5","5");
		test.init();
	}

	public void actionPerformed(ActionEvent e) {
		AccountManager accountManager = new AccountManager(year, month, day);
		System.out.println(idBox.getSelectedIndex());
		String id = adayCostRecordIDList.get(idBox.getSelectedIndex());
		if (e.getSource() == btnDelete) {
			accountManager.deleteDayCostRecord(id);
			dispose();
		}
		else if( e.getSource() == btnEdit ) {
		  CostRecord costRecord = accountManager.getIdCostRecord(id);
		  AccountSettingView accountSettingView = new AccountSettingView(year, month, day, costRecord);
		  accountSettingView.setVisible(true);
		  accountSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  dispose();
		}
	}

}

