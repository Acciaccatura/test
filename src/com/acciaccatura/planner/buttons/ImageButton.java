package com.acciaccatura.planner.buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.acciaccatura.planner.blocks.EventBlock;

public abstract class ImageButton extends Button {

	private static final long serialVersionUID = 1L;
	
	protected BufferedImage image;
	
	public ImageButton(String file) {
		try {
			image = ImageIO.read(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (clicking)
			g.drawImage(image.getSubimage(40, 0, 20, 20), 0, 0, null);
		else if (hovering)
			g.drawImage(image.getSubimage(20, 0, 20, 20), 0, 0, null);
		else
			g.drawImage(image.getSubimage(0, 0, 20, 20), 0, 0, null);
	}

}
