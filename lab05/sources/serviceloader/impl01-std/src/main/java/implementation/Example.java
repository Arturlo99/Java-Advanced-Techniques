package implementation;

import com.google.auto.service.AutoService;

import ex.api.ClusterAnalysisService;
import ex.api.ClusteringException;
import ex.api.DataSet;

@AutoService(ClusterAnalysisService.class)
public class Example implements ClusterAnalysisService, Runnable {

	private DataSet input;
	private DataSet output = new DataSet();
	String[] options;

	@Override
	public void setOptions(String[] options) throws ClusteringException {
		this.options = options;

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Algorytm do przydzielania kategorii wagowych/BMI";
	}

	@Override
	public void submit(DataSet ds) throws ClusteringException {
		input = ds;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public DataSet retrieve(boolean clear) throws ClusteringException {
		// TODO Auto-generated method stub
		return output;
	}

	@Override
	public void run() {
		String outDataSet[][] = input.getData();
		if (options[0].equals("0")) {
			output.setData(assembleWeightCategories(outDataSet));
		} else {
			output.setData(assembleBmiCategories(outDataSet));
		}
	}

	private String[][] assembleWeightCategories(String[][] outDataSet) {
		for (int i = 0; i < outDataSet.length; i++) {
			float weight = Float.parseFloat(outDataSet[i][3]);
			outDataSet[i][5] = WeightCategory.assembleWeightCategory(weight);
		}
		return outDataSet;
	}

	private String[][] assembleBmiCategories(String[][] outDataSet) {
		for (int i = 0; i < outDataSet.length; i++) {
			float weight = Float.parseFloat(outDataSet[i][3]);
			int height = Integer.parseInt(outDataSet[i][4]);
			outDataSet[i][5] = BmiCategory.assembleBmiCategory(height, weight);
		}
		return outDataSet;
	}

}
