package reservation;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CreateLargeCan extends Canvas{
	BufferedImage img;
	ReservationMain main;
	
	public CreateLargeCan(BufferedImage img, ReservationMain main) {
		this.img = img;
		this.main = main;
		
		this.setPreferredSize(new Dimension(500, 450));
//		this.paint(main.getGraphics());
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, 500, 450, main.p_south_center);
	}

}
