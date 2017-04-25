package reservation;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class CreateSmallCan extends Canvas{
	URL url;
	BufferedImage img;
	CreateLargeCan large;
	ReservationMain main;
	
	public CreateSmallCan(URL url, ReservationMain main) {
		this.url = url;
		this.main = main;
		
		this.setPreferredSize(new Dimension(120, 120));
		
		try {
			img = ImageIO.read(url);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				main.p_south_center.removeAll();
				
				large = new CreateLargeCan(img, main);
				main.p_south_center.add(large);
				main.p_south.updateUI();
			}
		});
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 0, 30, 110, 110, main);
		System.out.println("CreateSmallCan.java " + url.toString());
	}
}
