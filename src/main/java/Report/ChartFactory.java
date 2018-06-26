package Report;

public class ChartFactory {
  
	public Chart getChart( String chartType, String year, String month){
		
	  if( chartType == null ) return null;
	  else {		  
		 if( chartType.equalsIgnoreCase("PieChart") ) 
		    return new PieChart(year, month);
		 else if( chartType.equalsIgnoreCase("BarChart") )
			return new BarChart(year, month);
	  }
	  
	  return null;
	  
	}
}
