package com.acciaccatura.planner.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.buttons.Button;
import com.acciaccatura.planner.buttons.ImageButton;
import com.acciaccatura.planner.calendar.Event;
import com.acciaccatura.planner.calendar.Times;

public class AlertFrame extends JWindow {

	private static final long serialVersionUID = 1L;
	
	private static BufferedImage img;
	private JPanel pane;
	private Button butt;
	private List<Event> nearEvents;
	public static List<String> dates = new ArrayList<String>();

	public AlertFrame() {
		super();
		try {
			System.out.println("hey");
			if (img == null)
				img = ImageIO.read(new File("res/alert_background.png"));
			nearEvents = Times.getCloseEvents(7);
			setPreferredSize(new Dimension(480, 40*nearEvents.size()+120));
			setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width-getPreferredSize().width)/2, Frame.frame.getLocation().y + 500));
			FlowLayout layout = new FlowLayout();
			layout.setHgap(0);
			layout.setVgap(0);
			setLayout(layout);
			pane = new JPanel(){

				private static final long serialVersionUID = 1L;

				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(img.getSubimage(0, 0, 480, 60), 0, 0, null);
					g.drawImage(img.getSubimage(0, 60, 480, 60).getScaledInstance(480, 40*nearEvents.size()+60, BufferedImage.TYPE_4BYTE_ABGR), 0, 60, null);
					g.setColor(Frame.prim1);
					g.setFont(new Font("Arial", Font.ITALIC, 36));
					g.drawString("Coming up: " + nearEvents.size() + " event" + ((nearEvents.size() == 1) ? "" : "s"), 12, 42);
					g.setFont(new Font("Arial", Font.PLAIN, 16));
					for (int a = 0; a < nearEvents.size(); a++) {
						g.drawString(nearEvents.get(a).msg, 10, 78 + a*40);
						g.drawString(dates.get(a), 10, 94 + a*40);
						g.drawLine(10, 60 + 40*a, 400, 60 + 40*a);
					}
				}

			};
			pane.setPreferredSize(new Dimension(480, 60+40*nearEvents.size()));
			System.out.println(pane.getPreferredSize().height);
			add(pane);
			butt = new ImageButton("res/alert_background.png"){

				private static final long serialVersionUID = 1L;

				@Override
				public void clicked(MouseEvent e) {
					AlertFrame.this.setVisible(false);
					
				}
				
				@Override
				public void paintComponent(Graphics g) {
					if (clicking)
						g.drawImage(image.getSubimage(0, 240, 480, 60), 0, 0, null);
					else if (hovering)
						g.drawImage(image.getSubimage(0, 120, 480, 60), 0, 0, null);
					else
						g.drawImage(image.getSubimage(0, 180, 480, 60), 0, 0, null);
					g.setFont(new Font("Arial", Font.PLAIN, 36));
					g.setColor(Frame.prim1);
					g.drawString("Close", 200, 52);
				}
				
			};
			butt.setPreferredSize(new Dimension(480, 60));
			add(butt);
			pack();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setVisible(boolean arg0) {
		super.setVisible(arg0);
		if (arg0) {
			nearEvents = Times.getCloseEvents(7);
			setPreferredSize(new Dimension(480, 40*nearEvents.size()+120));
			pane.setPreferredSize(new Dimension(480, 40*nearEvents.size()+60));
			repaint();
			pack();
		}
	}
	
	
}
