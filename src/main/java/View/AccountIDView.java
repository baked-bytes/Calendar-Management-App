package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import DB.AccountManager;
import DB.CostRecord;

public class AccountIDView extends JDialog implements ActionListener{
	
	private String year;
	private String month;
	private String day;	
	private String id;
	
	private JButton btnDelete;
	private JButton btnEdit;
	private JTextField textId;

	public AccountIDView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;	
        init();
	}
	
	public void init(){
		setBounds(100, 100, 260, 180);
		setTitle("Input ID number");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		textId = new JTextField();
		textId.setFont(textId.getFont().deriveFont(16f));
		textId.setBounds(70, 30, 75, 30);
		getContentPane().add(textId);

		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(this);
		btnDelete.setBounds(0, 93, 120, 30);
		getContentPane().add(btnDelete);

		btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(this);
		btnEdit.setBounds(120, 93, 120, 30);
		getContentPane().add(btnEdit);
	}
	
	
	public String getDeleteId(){
	  return id;
	}

	public static void main(String[] args) {
//		CalendarIDView dialog = new CalendarIDView("2018","4","26");
//		dialog.setModal(true);
//		dialog.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		AccountManager accountManager = new AccountManager(year, month, day);
		String id = textId.getText();	
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

