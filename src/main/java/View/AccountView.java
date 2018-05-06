package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DB.AccountManager;
import DB.CalendarManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AccountView extends JFrame{
	private String year;
	private String month;
	private String day;
	public static JTable table;
	public AccountView(final String year, final String month, final String day) {
		this.year = year;
		this.month = month;
		this.day = day;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Serif", Font.BOLD, 20));

        JLabel lblHeading = new JLabel("current day " + year + "/"+ month + "/" + day);
        lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT,24));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        getContentPane().add(scrollPane,BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setVisible(true);

        JPanel panel=new JPanel();
        add(panel, BorderLayout.SOUTH);
        JButton addButton = new JButton("ADD CostRecord");
        JButton okButton = new JButton("EDIT/DELETE CostRecord");
        addButton.setFont(new Font("Arial", Font.PLAIN, 24));
        okButton.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(addButton);
        panel.add(okButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	AccountSettingView accountSettingView = new AccountSettingView(year,month,day);
            	accountSettingView.setVisible(true);
            	accountSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	AccountIDView accountIDView = new AccountIDView(year,month,day);
            	accountIDView.setVisible(true);
            	accountIDView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
	}

	public void init() {
		AccountManager test = new AccountManager(year,month,day);
        test.setCostRecord();
	}
    public static void main(String[] args) {
    	AccountView AccountViewtest = new AccountView("2018","4","27");
    	AccountViewtest.init();
    }
}