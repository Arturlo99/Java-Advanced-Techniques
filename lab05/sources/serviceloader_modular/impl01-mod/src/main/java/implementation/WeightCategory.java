package implementation;

public class WeightCategory {
	static public String assembleWeightCategory(float weight) {
		if(weight <= 48f) {
			return "Papierowa";
		}
		else if (weight <= 51f) {
			return "Musza";
		}
		else if (weight <= 54f) {
			return "Kogucia";
		}
		else if (weight <= 57f) {
			return "Piórkowa";
		}
		else if (weight <= 60f) {
			return "Lekka";
		}
		else if (weight <= 64f) {
			return "Lekkopó³œrednia";
		}
		else if (weight <= 69f) {
			return "Pó³œrednia";
		}
		else if (weight <= 75f) {
			return "Œrednia";
		}
		else if (weight <= 81f) {
			return "Pó³ciê¿ka";
		}
		else if (weight <= 91f) {
			return "Ciê¿ka";
		}
		else {
			return "Superciê¿ka";
		}
		
	}
	
}
