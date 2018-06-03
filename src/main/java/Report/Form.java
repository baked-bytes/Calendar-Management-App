package Report;

import java.util.ArrayList;

import DB.AccountManager;
import Model.CostRecord;

public class Form {
	
	AccountManager accountManager ;
	
	public Form(String year, String month) {
		accountManager = new AccountManager( year , month, "");
	}
	
	public String[][] getDataFromManager() {		
		
		ArrayList<CostRecord> montlyCostList = accountManager.getMonthlyCost();
		String[][] mydata = new String[montlyCostList.size()][3];

		for (int i = 0; i < montlyCostList.size(); i++) {
			mydata[i][0] = montlyCostList.get(i).gettype();
			mydata[i][1] = montlyCostList.get(i).getcost();		
			mydata[i][2] = montlyCostList.get(i).getContent();				
		}
		return mydata;
	}
	
	public int getMontlyTotalCost(){
	  return accountManager.getMontlyTotalCost();
	}

}
