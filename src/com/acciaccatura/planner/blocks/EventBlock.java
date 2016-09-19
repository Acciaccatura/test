package com.acciaccatura.planner.blocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.buttons.ImageButton;
import com.acciaccatura.planner.calendar.Event;
import com.acciaccatura.planner.calendar.Times;
import com.acciaccatura.planner.frame.CalendarFrame;

public class EventBlock extends JPanel {

	private static final long serialVersionUID = 1L;

	public Event event;
	private boolean editing;
	private JTextArea area;
	
	public EventBlock(Event e) {
		super();
		setPreferredSize(new Dimension(280, 60));
		setBackground(new Color(240, 240, 240));
		setLayout(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(8, 8, 16, 0);
		c.gridheight = 2;
		area = new JTextArea();
		area.setPreferredSize(new Dimension(244, 36));
		area.setBackground(Color.WHITE);
		area.setForeground(Frame.prim1);
		area.setFont(new Font("Arial", Font.PLAIN, 16));
		area.setEditable(false);
		area.setLineWrap(true);
		event = e;
		editing = false;
		area.setText(event.msg);
		add(area, c);
		c.gridx = 1;
		c.gridheight = 1;
		c.insets = new Insets(5, 0, 0, 8);
		ImageButton button = new ImageButton("res/edit_button.png"){

			private static final long serialVersionUID = 1L;

			@Override
			public void clicked(MouseEvent e) {
				if (!editing)
					area.setBackground(new Color(240, 240, 240));
				else {
					area.setBackground(Color.WHITE);
					event.msg = area.getText();
					System.out.println(Times.events);
				}
				editing = !editing;
				area.setEditable(editing);
				area.getCaret().setVisible(editing);
				repaint();
			}
			
		};
		button.setPreferredSize(new Dimension(20, 20));
		button.setBackground(Color.WHITE);
		add(button, c);
		c.gridy = 1;
		c.insets = new Insets(0, 0, 15, 8);
		ImageButton button2 = new ImageButton("res/exit_button.png"){

			private static final long serialVersionUID = 1L;

			@Override
			public void clicked(MouseEvent e) {
				CalendarFrame.pane2.empty.remove(EventBlock.this);
				CalendarFrame.pane2.scroll.updateScrollbar();
				Times.events.remove(event);
				Times.updateCalendar();
				CalendarFrame.pane.repaint();
				event = null;
				Times.updateData();
			}
			
		};
		button2.setBackground(Color.WHITE);
		button2.setPreferredSize(new Dimension(20, 20));
		add(button2, c);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.setColor(Frame.prim1);
		g.drawString(Times.getMonth(Times.month) + " " + Times.day + ", " + Times.year, 6, 56);
		if (editing)
			g.setColor(Color.BLACK);
		g.drawRect(4, 4, 272, 55);
	}
	
}
