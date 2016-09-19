package com.acciaccatura.planner.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JViewport;

import com.acciaccatura.planner.buttons.AddEventButton;
import com.acciaccatura.planner.calendar.Event;
import com.acciaccatura.planner.calendar.Times;

public class EventPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JViewport port;
	public ScrollBar scroll;
	public JPanel pane,empty;

	public EventPanel() {
		super();
		setPreferredSize(new Dimension(300, 420));
		setBackground(Color.WHITE);
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
		pane = new JPanel();
		pane.setSize(new Dimension(280, 420));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.setBackground(Color.WHITE);
		pane.setOpaque(false);
		//addEvents();
		empty = new JPanel();
		empty.setSize(new Dimension(280,0));
		empty.setBackground(Color.WHITE);
		empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));
		empty.setOpaque(false);
		pane.add(empty);
		pane.add(new AddEventButton(this));
		
		port = new JViewport(){

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(CalendarFrame.image.getSubimage(420, 0, 280, 420), 0, 0, null);
			}
			
		};
		port.setPreferredSize(new Dimension(280, getPreferredSize().height));
		port.setView(pane);
		add(port);
		scroll = new ScrollBar(this, port);
		add(scroll);
		scroll.updateScrollbar();
	}
	
	public void addEvent(Event e) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
