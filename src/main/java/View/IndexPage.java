package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Report.DetailReportView;

public class IndexPage extends JFrame implements ActionListener {

	private JComboBox MonthBox = new JComboBox();
	private JComboBox YearBox = new JComboBox();

	private JLabel YearLabel = new JLabel("year: ");
	private JLabel MonthLabel = new JLabel("month: ");

	private JButton button_ok = new JButton("check");
	private JButton button_today = new JButton("today");
	private JButton button_report = new JButton("Report");

	private Date now_date = new Date();

	private int now_year = now_date.getYear() + 1900;
	private int now_month = now_date.getMonth();
	private boolean todayFlag = false;

	private JButton[] button_day = new JButton[42];
	private final String[] week = { "SUN", "MON", "TUE", "WEN", "THR", "FRI", "SAT" };
	private JButton[] button_week = new JButton[7];
	private String year_int = null;
	private int month_int;

	public IndexPage() {
		super();
		this.setTitle("Calendar");
		this.init();
		this.setLocation(500, 300);

		this.setResizable(false);
		pack();

	}

	private void init() {
		Font font = new Font("Dialog", Font.BOLD, 16);
		YearLabel.setFont(font);
		MonthLabel.setFont(font);
		button_ok.setFont(font);
		button_today.setFont(font);
		button_report.setFont(font);

		for (int i = now_year; i <= now_year + 1; i++) {
			YearBox.addItem(i + "");
		}
		YearBox.setSelectedIndex(0);

		for (int i = 1; i <= 12; i++) {
			MonthBox.addItem(i + "");
		}
		MonthBox.setSelectedIndex(now_month);

		JPanel panel_ym = new JPanel();
		panel_ym.add(YearLabel);
		panel_ym.add(YearBox);
		panel_ym.add(MonthLabel);
		panel_ym.add(MonthBox);
		panel_ym.add(button_ok);
		panel_ym.add(button_today);
		panel_ym.add(button_report);

		button_ok.addActionListener(this);
		button_today.addActionListener(this);
		button_report.addActionListener(this);

		JPanel panel_day = new JPanel();
		// 7*7
		panel_day.setLayout(new GridLayout(7, 7, 3, 3));
		for (int i = 0; i < 7; i++) {
			button_week[i] = new JButton(" ");
			button_week[i].setText(week[i]);
			button_week[i].setForeground(Color.black);
			panel_day.add(button_week[i]);
		}
		button_week[0].setForeground(Color.red);
		button_week[6].setForeground(Color.red);

		for (int i = 0; i < 42; i++) {
			button_day[i] = new JButton(" ");

			button_day[i].addActionListener(this); // ****

			panel_day.add(button_day[i]);
		}

		this.paintDay(); // show current day

		JPanel panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout());
		panel_main.add(panel_day, BorderLayout.SOUTH);
		panel_main.add(panel_ym, BorderLayout.NORTH);
		getContentPane().add(panel_main);

	}

	private void paintDay() {
		System.out.println("todayflag = " + todayFlag);
		if (todayFlag) {
			year_int = now_year + "";
			month_int = now_month;
		} else {
			year_int = YearBox.getSelectedItem().toString();
			month_int = MonthBox.getSelectedIndex();
			System.out.println("yer: " + year_int + "month = " + month_int);
		}

		int year_sel = Integer.parseInt(year_int) - 1900;
		Date firstDay = new Date(year_sel, month_int, 1);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(firstDay);
		int days = 0;
		int day_week = 0;

		if (month_int == 0 || month_int == 2 || month_int == 4 || month_int == 6 || month_int == 7 || month_int == 9
				|| month_int == 11) {
			days = 31;
		} else if (month_int == 3 || month_int == 5 || month_int == 8 || month_int == 10) {
			days = 30;
		} else {
			if (cal.isLeapYear(year_sel)) {
				days = 29;
			} else {
				days = 28;
			}
		}

		day_week = firstDay.getDay();
		int count = 1;

		for (int i = day_week; i < day_week + days; count++, i++) {
			if (i % 7 == 0 || (i + 1) % 7 == 0) {
				if ((i == day_week + now_date.getDate() - 1) && month_int == now_month
						&& (year_sel == now_year - 1900)) {
					button_day[i].setForeground(Color.BLUE);
					button_day[i].setText(count + "");
				} else {
					button_day[i].setForeground(Color.RED);
					button_day[i].setText(count + "");
				}
			} else {
				if ((i == day_week + now_date.getDate() - 1) && month_int == now_month
						&& (year_sel == now_year - 1900)) {
					button_day[i].setForeground(Color.BLUE);
					button_day[i].setText(count + "");
				} else {
					button_day[i].setForeground(Color.BLACK);
					button_day[i].setText(count + "");
				}
			}

		}
		if (day_week == 0) {
			for (int i = days; i < 42; i++) {
				button_day[i].setText("");
			}
		} else {
			for (int i = 0; i < day_week; i++) {
				button_day[i].setText("");
			}
			for (int i = day_week + days; i < 42; i++) {
				button_day[i].setText("");
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_ok) {
			todayFlag = false;
			this.paintDay();
		} else if (e.getSource() == button_today) {
			// System.out.println( e.getSource() );
			todayFlag = true;
			YearBox.setSelectedIndex(0);
			MonthBox.setSelectedIndex(now_month);
			this.paintDay();
		} else if (e.getSource() == button_report) {
			DetailReportView detailReportView = new DetailReportView(YearBox.getSelectedItem().toString(),
					MonthBox.getSelectedItem().toString());
			detailReportView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else {
			for (int i = 0; i < 42; i++) {
				if (e.getSource() == button_day[i]) {
					System.out.println(
							YearBox.getSelectedItem().toString() + " " + MonthBox.getSelectedItem().toString());
					Object source = e.getSource();
					if (source instanceof JButton) {
						JButton btn = (JButton) source;
						final String butSrcTxt = btn.getText();
						System.out.println("i = " + butSrcTxt);
						if (butSrcTxt != "") {
							try {
								FunctionChosenView frame = new FunctionChosenView(YearBox.getSelectedItem().toString(),
										MonthBox.getSelectedItem().toString(), butSrcTxt);
								frame.setVisible(true);
								frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								//
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} // if
				}
			} // for
		} // else

	}

	public static void main(String[] args) {
		final IndexPage ct = new IndexPage();
		ct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ct.setVisible(true);

	}

}