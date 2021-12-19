package implementation;

public class BmiCategory {
	public static String assembleBmiCategory(int height, float weight) {
		float heightInMetres = (float) height / 100;
		float bmiIndicator = weight / (heightInMetres*heightInMetres);
//		System.out.println(bmiIndicator);
//		System.out.println("wysokosc:" + heightInMetres + " waga: " + weight);
		if(bmiIndicator <= 16f) {
			return "Wyg³odzenie";
		}
		else if (bmiIndicator <= 16.99f) {
			return "Wychudzenie";
		}
		else if (bmiIndicator <= 18.49f) {
			return "Niedowaga";
		}
		else if (bmiIndicator <= 24.99f) {
			return "Waga prawid³owa";
		}
		else if (bmiIndicator <= 29.9f) {
			return "Nadwaga";
		}
		else if (bmiIndicator <= 34.99f) {
			return "I stopieñ oty³oœci";
		}
		else if (bmiIndicator <= 39.99f) {
			return "II stopieñ oty³oœci";
		}
		else {
			return "Oty³oœæ skrajna";
		}

		
	}
}
