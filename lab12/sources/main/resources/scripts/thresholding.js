var BufferedImage = Java.type('java.awt.image.BufferedImage');
var Graphics2D = Java.type('java.awt.Graphics2D');
var Color = Java.type('java.awt.Color');

function process (img) {
		var height = img.getHeight();
		var width = img.getWidth();
		var result = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		for (var x = 0; x < width; x++) {

			for (var y = 0; y < height; y++) {
				var color = new Color(img.getRGB(x, y));
				var red = color.getRed();
				var green = color.getGreen();
				var blue = color.getBlue();

				if((red+green+blue)/3 < 107) {
						result.setRGB(x,y,new Color(0x000000).getRGB());
					}
					else {
						result.setRGB(x,y,new Color(0xffffff).getRGB());
					}
				
			}
		}
		return result;
	}