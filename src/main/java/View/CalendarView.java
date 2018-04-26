package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class CalendarView extends JFrame{
	
	private String year;
	private String month;
	private String day;
	
	public CalendarView(String year, String month, String day) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        String[] columns = {"ID", "Time", "Schedule"};
 
        Object[][] data = {
            {"1", "10:00-12:00", "timelog"},
            {"2", "14:00-18:00", "prepare demo"}
        };
 
        JTable table = new JTable(data, columns);
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
        JButton addButton = new JButton("ADD Schedule");
        JButton okButton = new JButton("EDIT/DELEE Schedule");
        addButton.setFont(new Font("Arial", Font.PLAIN, 24));
        okButton.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(addButton);
        panel.add(okButton);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CalendarSettingView calendarSettingView = new CalendarSettingView(year,month,day);
            	calendarSettingView.setVisible(true);
            	calendarSettingView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        
	}
	
//
//	public void init(){
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        final JFrame frame = new JFrame("MySchedule");
//        
//        String[] columns = {"Date", "Time", "Schedule"};
// 
//        Object[][] data = {
//            {"20XX/4/16", "10:00-12:00", "timelog"},
//            {"20XX/4/17", "14:00-18:00", "prepare demo"}
//        };
// 
//        JTable table = new JTable(data, columns);
//        JScrollPane scrollPane = new JScrollPane(table);
//        table.setFillsViewportHeight(true);
//        table.setRowHeight(30);
//        table.setFont(new Font("Serif", Font.BOLD, 20));
// 
//        JLabel lblHeading = new JLabel("current day " + year + "/"+ month + "/" + day);
//        lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT,24));
// 
//        frame.getContentPane().setLayout(new BorderLayout());
// 
//        frame.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
//        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
// 
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 500);
//        frame.setVisible(true);
//        
//        JPanel panel=new JPanel();
//        frame.add(panel, BorderLayout.SOUTH);
//        JButton addButton = new JButton("ADD Schedule");
//        JButton okButton = new JButton("OK");
//        addButton.setFont(new Font("Arial", Font.PLAIN, 24));
//        okButton.setFont(new Font("Arial", Font.PLAIN, 24));
//        panel.add(addButton);
//        panel.add(okButton);
//        
//	}
 
    public static void main(String[] args) {
    	CalendarView calendarView = new CalendarView("2018","12","5");
    	
        
    }
}