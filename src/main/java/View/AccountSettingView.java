package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DB.AccountManager;
import DB.CalendarManager;
import DB.CostRecord;

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

public class AccountSettingView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField costText;
	private JTextField typeText;
	private JCheckBox windowRemiderCheck;
	private JTextArea textContentArea;
	private JButton btnOKButton;

	private String year;
	private String month;
	private String day;
	private CostRecord costRecord = null;
	boolean isEdit = false;

	public AccountSettingView(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
		init();
	}

	public AccountSettingView(String year, String month, String day ,CostRecord costRecord) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.costRecord = costRecord;
		this.isEdit = true;
		init();
		setEditDataInText();
	}
	public void setEditDataInText() {
		costText.setText(costRecord.getcost());
		typeText.setText(costRecord.gettype());
		textContentArea.setText(costRecord.getContent());
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
		JLabel lblNewLabel = new JLabel("cost");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(lblNewLabel);
		costText = new JTextField();
		costText.setFont(textContentArea.getFont().deriveFont(16f));
		costText.setColumns(9);

		panel.add(costText);
		panel.add(new JLabel("type"));

		typeText = new JTextField();
		typeText.setFont(typeText.getFont().deriveFont(16f));
		panel.add(typeText);

		typeText.setColumns(9);
	}

	public void actionPerformed(ActionEvent e) {
		
		AccountManager accountManager = new AccountManager(year, month, day);
		if (isEdit) {
			accountManager.editCostRecord(getCostRecordInputInfo(),costRecord.getId());
			dispose();
		} else {			
			accountManager.addCostRecord(getCostRecordInputInfo());
			dispose();
		}
	}

	public CostRecord getCostRecordInputInfo() {
		CostRecord costRecord = new CostRecord();
		String content = textContentArea.getText();
		String cost = costText.getText();
		String type = typeText.getText();
		System.out.println("con = " + content + "type = " + type + " " + cost);
		costRecord.setContent(content);
		costRecord.settype(type);
		costRecord.setcost(cost);
		costRecord.setDay(day);
		costRecord.setMonth(month);
		costRecord.setYear(year);
		return costRecord;
	}

	public static void main(String[] args) {
		AccountSettingView accountSettingView = new AccountSettingView("2018", "4", "26");
		accountSettingView.setVisible(true);

	}

}