package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class CalendarSettingView extends JFrame {

	private JPanel contentPane;
	private JTextField startTimeText;
	private JTextField endTimeText;
	private JCheckBox windowRemiderCheck;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarSettingView frame = new CalendarSettingView("", "", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalendarSettingView(String year, String month, String day) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 191);
		contentPane = new JPanel();
		this.setTitle("Calendar " + year + " /" + month + " /" + day );
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(textArea.getFont().deriveFont(24f));
		contentPane.add(textArea, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("add time");
		panel.add(lblNewLabel);
		
		
		startTimeText = new JTextField();
		startTimeText.setFont(textArea.getFont().deriveFont(16f));
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

}
