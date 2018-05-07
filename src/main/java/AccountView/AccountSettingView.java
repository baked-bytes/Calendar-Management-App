package AccountView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import DB.AccountManager;
import Model.CostRecord;
import Model.CostRecordBuilder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AccountSettingView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField costText;
	private JTextArea textContentArea;
	private JButton btnOKButton;
	private JComboBox<String> typeBox = new JComboBox<String>();
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
		typeBox.setSelectedItem(costRecord.gettype());
		textContentArea.setText(costRecord.getContent());
	}

	public void init() {
		typeBox.addItem("Food");
		typeBox.addItem("Clothing");
		typeBox.addItem("Housing");
		typeBox.addItem("Transportation");
		typeBox.addItem("Entertainment");
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
		panel.add(typeBox);
	}

	public void actionPerformed(ActionEvent e) {

		AccountManager accountManager = new AccountManager(year, month, day);
		if(!getCostRecordInputInfo().getContent().isEmpty() && !getCostRecordInputInfo().getcost().isEmpty() && getCostRecordInputInfo().getcost().length() <= 12 && getCostRecordInputInfo().getContent().length() <= 20 && getCostRecordInputInfo().getcost().matches("\\d+")){
			if (isEdit) {
				accountManager.editCostRecord(getCostRecordInputInfo(),costRecord.getId());
				dispose();
			} else {
				accountManager.addCostRecord(getCostRecordInputInfo());
				dispose();
			}
		}
		else {
			if(getCostRecordInputInfo().getContent().isEmpty() || getCostRecordInputInfo().getcost().isEmpty())
				JOptionPane.showMessageDialog(null, "Content is empty or Cost is empty", "Error", JOptionPane.ERROR_MESSAGE);
			else if(! getCostRecordInputInfo().getcost().matches("\\d+"))
				JOptionPane.showMessageDialog(null, "Cost format error", "Error", JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Cost length > 12 or Content length > 20", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public CostRecord getCostRecordInputInfo() {
		String content = textContentArea.getText();
		String cost = costText.getText();
		String type = typeBox.getSelectedItem().toString();
		CostRecord costRecord = new CostRecordBuilder().year(year).month(month).day(day).cost(cost).type(type).content(content).build();
		return costRecord;
	}

	public static void main(String[] args) {
		AccountSettingView accountSettingView = new AccountSettingView("2018", "4", "26");
		accountSettingView.setVisible(true);

	}

}