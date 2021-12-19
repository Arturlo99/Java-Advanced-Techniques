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
			return "Pi�rkowa";
		}
		else if (weight <= 60f) {
			return "Lekka";
		}
		else if (weight <= 64f) {
			return "Lekkop�rednia";
		}
		else if (weight <= 69f) {
			return "P�rednia";
		}
		else if (weight <= 75f) {
			return "�rednia";
		}
		else if (weight <= 81f) {
			return "P�ci�ka";
		}
		else if (weight <= 91f) {
			return "Ci�ka";
		}
		else {
			return "Superci�ka";
		}
		
	}
	
}
