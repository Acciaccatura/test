package com.acciaccatura.planner;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.util.Calendar;

import javax.swing.JDialog;

import com.acciaccatura.planner.buttons.ImageButton;
import com.acciaccatura.planner.calendar.Times;
import com.acciaccatura.planner.frame.AlertFrame;
import com.acciaccatura.planner.frame.CalendarFrame;


public class Frame extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public static final Dimension size = new Dimension(720, 440);
	public static final Color prim1 = new Color(0, 153, 255);
	public static final Color prim2 = new Color(0, 133, 235);
	public static final Color prim3 = new Color(0, 113, 215);
	public static final AlphaComposite alph = AlphaComposite.getInstance(AlphaComposite.SRC);
	
	public static Frame frame;
	public static AlertFrame frame2;
	public static CalendarFrame calendar;
	public static GridBagConstraints c = new GridBagConstraints();
	public static ImageButton close;
	public static Toolkit kit = Toolkit.getDefaultToolkit();
	public static boolean activated = true;
	
	public Frame() {
		super();
		setPreferredSize(size);
		setLocation(new Point((kit.getScreenSize().width-720)/2, 0));
		setAlwaysOnTop(true);
		setLayout(new GridBagLayout());
		c.gridy = 0;
		calendar = new CalendarFrame();
		calendar.setPreferredSize(new Dimension(720, 420));
		setUndecorated(true);
		add(calendar, c);
		c.gridy = 1;
		close = new ImageButton("res/dropdown_button.png"){

			private static final long serialVersionUID = 1L;

			@Override
			public void clicked(MouseEvent e) {
				calendar.setVisible(!calendar.isVisible());
				Frame.this.setAlwaysOnTop(!Frame.this.isAlwaysOnTop());
				if (activated) {
					frame2.setVisible(false);
					Frame.this.setPreferredSize(new Dimension(720, 20));
					Frame.this.pack();
				} else {
					Times.cal = Calendar.getInstance();
					frame2.setVisible(true);
					Frame.this.setPreferredSize(new Dimension(720, 440));
					Frame.this.pack();
				}
				activated = !activated;
			}
			
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(image.getSubimage(0, 0, 720, 20), 0, 0, null);
			}
			
		};
		close.setPreferredSize(new Dimension(size.width, 20));
		add(close, c);
		Times.loadEvents();
		pack();
	}

	public static void main(String[] args) {
		frame = new Frame();
		frame.setVisible(true);
		CalendarFrame.pane.setMonth(Times.cal.get(Calendar.MONTH), Times.cal.get(Calendar.YEAR));
		frame2 = new AlertFrame();
		frame2.setAlwaysOnTop(true);
		frame2.setVisible(true);
	}

}
