package Report;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import DB.AccountManager;
import Model.CostRecord;

import java.util.ArrayList;

import javax.swing.JPanel;

public class PieChart implements Chart {

	AccountManager accountManager;

	public PieChart(String year, String month) {
		accountManager = new AccountManager(year, month, "");
	}

	public double getContentScale(String searchType) {
		ArrayList<CostRecord> montlyCostList = accountManager.getMonthlyCost();
		int totalCost = accountManager.getMontlyTotalCost();
		int partCost = 0;
		for (CostRecord costRecord : montlyCostList) {
			if (costRecord.gettype().equals(searchType))
				partCost += Integer.parseInt(costRecord.getcost());
		}
		return (partCost * 100) / totalCost;
	}

	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Food", getContentScale("Food"));
		dataset.setValue("Clothing", getContentScale("Clothing"));
		dataset.setValue("Housing", getContentScale("Housing"));
		dataset.setValue("Transportation", getContentScale("Transportation"));
		dataset.setValue("Entertainment", getContentScale("Entertainment"));
		return dataset;
	}

	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("", // chart // title
				dataset, // data
				true, // include legend
				true, false);

		return chart;
	}
	
	public JPanel generate() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

}