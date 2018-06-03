package Report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DetailReportView extends JFrame implements ActionListener {

	private String year;
	private String month;
	private Form form;

	private JButton pieChart;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JButton monthTable;
	private JButton barChart;
	

	public DetailReportView(String year, String month) {
		this.month = month;
		this.year = year;
		form = new Form(year, month);
		init(); 		
	}

	public void init() {
		
		JPanel panelButton = new JPanel();

		monthTable = new JButton();
		monthTable.setText("Table");
		panelButton.add(monthTable);
		monthTable.addActionListener(this);

		pieChart = new JButton();
		pieChart.setText("Pie Chart");
		pieChart.addActionListener(this);
		panelButton.add(pieChart);

		barChart = new JButton();
		barChart.setText("Bar Chart");
		barChart.addActionListener(this);
		panelButton.add(barChart);
		// ********************************

		setTitle(year + "/" + month + " Monthly cost");
		this.getContentPane().setBackground(Color.YELLOW);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTable table = new JTable( setTableModel() );
		table.setEnabled(false); //

		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowHeight(50);
		table.setFont(new Font("Serif", Font.BOLD, 20));

		setSize(700, 600);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelButton, BorderLayout.NORTH);

		JLabel label = new JLabel("total cost  $" + form.getMontlyTotalCost());
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Serif", Font.BOLD, 24));
		panel.add(label, BorderLayout.SOUTH);

		getContentPane().add(panel);
		setVisible(true);
	}
	
	private DefaultTableModel setTableModel(){
		String[] columns = { "TYPE", "COST", "CONTENT" };		
		Object[][] data = form.getDataFromManager();
		DefaultTableModel model = new DefaultTableModel(data, columns);
		return model;
	}
	

	public void actionPerformed(ActionEvent e) {

		BorderLayout layout = (BorderLayout) panel.getLayout();
		panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));

		if (e.getSource() == monthTable) {
			panel.add(scrollPane, BorderLayout.CENTER);
			setVisible(true);
		} else if (e.getSource() == pieChart) {
			Chart pieChart = new PieChart(year, month);
			panel.add(pieChart.generate(), BorderLayout.CENTER);
		} else if (e.getSource() == barChart) {
			Chart barChart = new BarChart(year, month);
			panel.add(barChart.generate(), BorderLayout.CENTER);
		}
		
		setVisible(true);
	}

	public static void main(String[] args) {
		DetailReportView detailReportView = new DetailReportView("2018", "5");
		//detailReportView.init();

	}

}
