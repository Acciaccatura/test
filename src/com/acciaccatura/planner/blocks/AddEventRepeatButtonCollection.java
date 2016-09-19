package com.acciaccatura.planner.blocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.buttons.Button;

public class AddEventRepeatButtonCollection extends JPanel {

	private static final long serialVersionUID = 1L;
	public static enum RepeatType {NONE, DAILY, WEEKLY, MONTHLY, YEARLY};
	
	public RepeatType type;

	public AddEventRepeatButtonCollection() {
		super();
		System.out.println("wow");
		setBackground(Color.RED);
		type = RepeatType.NONE;
		setPreferredSize(new Dimension(260, 100));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (int a = 0; a < RepeatType.values().length; a++) {
			final int c = a;
			Button b = new Button(){
				
				private static final long serialVersionUID = 1L;
				
				private String str = RepeatType.values()[c].toString();
				private int set = c;
				
				@Override
				public void clicked(MouseEvent e) {
					AddEventRepeatButtonCollection.this.type = RepeatType.values()[set];
				}
				
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					if (clicking)
						g.setColor(new Color(getBackground().getRed()-20, getBackground().getRed()-20, getBackground().getRed()-20));
					else if (hovering)
						g.setColor(new Color(getBackground().getRed()-10, getBackground().getRed()-10, getBackground().getRed()-10));
					else
						g.setColor(getBackground());
					g.fillRect(0, 0, 260, 20);
					g.setColor(Frame.prim2);
					g.setFont(new Font("Arial", Font.PLAIN, 12));
					g.drawString(str, 2, 16);
				}
				
			};
			b.setBackground(((a % 2 == 0) ? new Color(220, 220, 220) : new Color(200, 200, 200)));
			add(b);
		}
	}
	
}
