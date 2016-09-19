package com.acciaccatura.planner.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CalendarFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String imglink = "res/cal_background.png";
	public static BufferedImage image;
	
	public static MonthPanel pane = new MonthPanel();
	public static EventPanel pane2 = new EventPanel();

	public CalendarFrame() {
		super();
		try {
			image = ImageIO.read(new File(imglink));
			setBackground(new Color(255, 255, 255));
			FlowLayout layout = new FlowLayout();
			layout.setHgap(0);
			layout.setVgap(0);
			setLayout(layout);
			add(pane);
			add(pane2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
}
