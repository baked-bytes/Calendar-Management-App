package CalendarView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Model.Schedule;
import Model.ScheduleBuilder;

public class WindowsReminder {
	private String year;
	private String month;
	private String day;
	private Schedule needtoremind;
	public WindowsReminder(Schedule needtoremind) {
		  this.year = needtoremind.getYear();
		  this.month = needtoremind.getMonth();
		  this.day = needtoremind.getDay();
		  this.needtoremind = needtoremind;
	}
	public void setReminder() {
		File baiscfile=new File("fortesting.bat"); 
		String path=baiscfile.getAbsolutePath();
		FileWriter writer = null;
		String[] parts = null;
		try {
			if(needtoremind.getMonth().length() < 2){
				needtoremind.setMonth("0" + needtoremind.getMonth());
			}
			if(needtoremind.getDay().length() < 2){
				needtoremind.setDay("0" + needtoremind.getDay());
			}
			parts = needtoremind.getTime().split("-");
			parts[0] = parts[0].substring(0, 2) + ":" + parts[0].substring(2, 4) + ":00";
			writer = new FileWriter("test.bat");
			writer.write("SCHTASKS /Create /SC ONCE /TN forTesting"+ needtoremind.getId() +" /TR "+path+" /ST "+ parts[0] +" /SD " + needtoremind.getYear() + "/" + needtoremind.getMonth() + "/" + needtoremind.getDay());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		    if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		}
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String data = needtoremind.getYear() + "/" + needtoremind.getMonth() + "/" + needtoremind.getDay() + " " +parts[0] + " " + needtoremind.getContent() + "\n";
			File file = new File("reminderrecord.txt");
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(data);
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			File baiscfile2=new File("test.bat"); 
			String path2=baiscfile2.getAbsolutePath();
			Process p =  Runtime.getRuntime().exec("cmd /c start  " + path2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				TimeUnit.MILLISECONDS.sleep(70);
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void editReminder() {
		File baiscfile=new File("fortesting.bat"); 
		String path=baiscfile.getAbsolutePath();
		FileWriter writer = null;
		try {
			writer = new FileWriter("testdelete.bat");
			writer.write("SCHTASKS /Delete /TN fortesting" + needtoremind.getId() + " /F");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		    if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		}
		try {
			File baiscfile2=new File("testdelete.bat"); 
			String path2=baiscfile2.getAbsolutePath();
			Process p =  Runtime.getRuntime().exec("cmd /c start  " + path2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				TimeUnit.MILLISECONDS.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String[] parts = null;
		try {
			if(needtoremind.getMonth().length() < 2){
				needtoremind.setMonth("0" + needtoremind.getMonth());
			}
			if(needtoremind.getDay().length() < 2){
				needtoremind.setDay("0" + needtoremind.getDay());
			}
			parts = needtoremind.getTime().split("-");
			parts[0] = parts[0].substring(0, 2) + ":" + parts[0].substring(2, 4) + ":00";
			writer = new FileWriter("test.bat");
			writer.write("SCHTASKS /Create /SC ONCE /TN forTesting"+ needtoremind.getId() +" /TR "+ path +" /ST "+ parts[0] +" /SD " + needtoremind.getYear() + "/" + needtoremind.getMonth() + "/" + needtoremind.getDay());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		    if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		}
	    try {
	    	File baiscfile2=new File("test.bat"); 
			String path2=baiscfile2.getAbsolutePath();
	    	Process p =  Runtime.getRuntime().exec("cmd /c start  " + path2);
	    } catch (IOException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }finally {
	    	try {
	    		TimeUnit.MILLISECONDS.sleep(70);
	    		Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
	    	} catch (InterruptedException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
	    String searching = needtoremind.getYear() + "/" + needtoremind.getMonth() + "/" + needtoremind.getDay() + " " +parts[0] ;
	    String data = needtoremind.getYear() + "/" + needtoremind.getMonth() + "/" + needtoremind.getDay() + " " +parts[0] + " " + needtoremind.getContent() + "\n";
	    List<String> lines = new ArrayList<String>();
	    String line = null;
		try {
            File f1 = new File("reminderrecord.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            Boolean flag = false;
            while ((line = br.readLine()) != null) {
                if (line.contains(searching)){
                	line = data;
                	flag = true;
                }
                lines.add(line + "\n");
            }
            if(!flag) {
            	lines.add(data);
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                 out.write(s);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	public void deleteReminder() {
		FileWriter writer = null;
		try {
			writer = new FileWriter("testdelete.bat");
			writer.write("SCHTASKS /Delete /TN fortesting" + needtoremind.getId() + " /F");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		    if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		}
		try {
			File baiscfile2=new File("testdelete.bat"); 
			String path2=baiscfile2.getAbsolutePath();
			Process p =  Runtime.getRuntime().exec("cmd /c start  " + path2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				TimeUnit.MILLISECONDS.sleep(70);
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Schedule schedule = new ScheduleBuilder().year("2018").month("5").day("18").isNotify("true").time("1506-1510").content("code demo").id("16").build();
		WindowsReminder testing = new WindowsReminder(schedule);
	}
}
