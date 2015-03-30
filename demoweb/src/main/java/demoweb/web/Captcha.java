package demoweb.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class Captcha {
	private int width = 100;
	private int height = 30;
	private Random random = new Random();
	private BufferedImage image;
	private String code;
	private int twistLevel = 1;
	private int noiseLineNumber = 3;
	private Color backgroundColor = Color.WHITE;
	// private Color foregroundColor = Color.BLACK;
	private Color[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.BLACK, Color.CYAN, Color.MAGENTA };

	private Color getRandomColor(Color notThisColor) {
		Color c = colors[random.nextInt(colors.length)];
		if (null == notThisColor) {
			return c;
		}
		while (c.equals(notThisColor)) {
			c = colors[random.nextInt(colors.length)];
		}
		return c;
	}

	public Captcha generate(int width, int height, String randomStr) {
		this.width = width;
		this.height = height;
		this.code = randomStr;
		if (StringUtils.isBlank(code)) {
			throw new RuntimeException("randomStr can not be empty.");
		}
		int xWidth = width / (code.length() + 2);
		int yIndex = height - 4;
		Graphics2D graphics = graphicsInit();
		Color c = null;
		for (int i = 0; i < code.length(); i++) {
			// graphics.setColor(foregroundColor);
			c = getRandomColor(c);
			graphics.setColor(c);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.drawString(code.charAt(i) + "", (i + 1) * xWidth, yIndex);
		}
		setBuffImg(disturb());
		return this;
	}

	private Graphics2D graphicsInit() {
		Graphics2D graphics = buffImgInit().createGraphics();
		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(new Font("Fixedsys", Font.ITALIC, height - 2));
		graphics.drawRect(0, 0, width - 1, height - 1);
		return graphics;
	}

	private BufferedImage buffImgInit() {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		return image;
	}

	private BufferedImage disturb() {
		drawNoiseLine(image.createGraphics());
		return twistImage();
	}

	private void drawNoiseLine(Graphics2D graphics) {
		int x = 0;
		int y = 0;
		int xl = 0;
		int yl = 0;
		Color c = null;
		for (int i = 0; i < noiseLineNumber; i++) {
			x = random.nextInt(width / 2);
			y = random.nextInt(height / 2);
			xl = random.nextInt(width / 2);
			yl = random.nextInt(height / 2);
			// graphics.setColor(foregroundColor);
			c = getRandomColor(c);
			graphics.setColor(c);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
	}

	private BufferedImage twistImage() {
		double dMultValue = random.nextInt(9) + twistLevel;
		double dPhase = random.nextInt(6);
		BufferedImage destBi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = destBi.createGraphics();
		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, width, height);
		for (int i = 0; i < destBi.getWidth(); i++) {
			for (int j = 0; j < destBi.getHeight(); j++) {
				int nOldX = getXPosition4Twist(dPhase, dMultValue, destBi.getHeight(), i, j);
				int nOldY = j;
				if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0 && nOldY < destBi.getHeight()) {
					destBi.setRGB(nOldX, nOldY, image.getRGB(i, j));
				}
			}
		}
		return destBi;
	}

	private int getXPosition4Twist(double dPhase, double dMultValue, int height, int xPosition, int yPosition) {
		double PI = Math.PI;
		double dx = (double) (PI * yPosition) / height + dPhase;
		double dy = Math.sin(dx);
		return xPosition + (int) (dy * dMultValue);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setBuffImg(BufferedImage buffImg) {
		this.image = buffImg;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
