package Report;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import DB.AccountManager;
import Model.CostRecord;

public class BarChart extends Chart {

	AccountManager accountManager;
	
	public BarChart(String year, String month) {
	  accountManager = new AccountManager(year, month, "");
	}

	private JFreeChart createChart() {
		JFreeChart barChart = ChartFactory.createBarChart("", "Category", "Cost", createDataset(),
				PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}
	
	public int getContentTypeCost(String searchType) {
		ArrayList<CostRecord> montlyCostList = accountManager.getMonthlyCost();
		int partCost = 0;
		for (CostRecord costRecord : montlyCostList) {
			if (costRecord.gettype().equals(searchType))
				partCost += Integer.parseInt(costRecord.getcost());
		}
		return partCost;
	}

	private CategoryDataset createDataset() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(getContentTypeCost("Food"), "Food", "");
		dataset.addValue(getContentTypeCost("Clothing"), "Clothing", "");
		dataset.addValue(getContentTypeCost("Housing"), "Housing", "");
		dataset.addValue(getContentTypeCost("Transportation"), "Transportation", "");
		dataset.addValue(getContentTypeCost("Entertainment"), "Entertainment", "");
		return dataset;
	}

	@Override
	JPanel generate() {
		ChartPanel chartPanel = new ChartPanel(createChart());
		return chartPanel;
	}
}