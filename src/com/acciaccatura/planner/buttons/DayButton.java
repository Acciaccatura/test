package com.acciaccatura.planner.buttons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.calendar.Times;
import com.acciaccatura.planner.frame.CalendarFrame;

public class DayButton extends Button implements Comparable<DayButton> {

	private static final long serialVersionUID = 1L;
	
	public int day;
	public int dow;
	
	public boolean hasEvent;

	public DayButton(int day, int dow) {
		super();
		this.day = day;
		this.dow = dow + 1;
		hasEvent = false;
		setPreferredSize(new Dimension(60, 60));
		setBackground(Color.WHITE);
	}
	
	@Override
	public void clicked(MouseEvent e) {
		Times.day = day;
		Times.dayw = dow;
		System.out.println(Times.getTimes());
		Times.removeDayEvents();
		Times.loadDayEvents(day, Times.month, Times.year);
		CalendarFrame.pane2.scroll.updateScrollbar();
		CalendarFrame.pane2.repaint();
		System.out.println(CalendarFrame.pane2.pane.getSize() + " " + CalendarFrame.pane2.empty.getSize() + " " + CalendarFrame.pane2.empty.getComponentCount());
		for (Component c: CalendarFrame.pane.getComponents())
			c.repaint();
		Frame.frame.pack();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(Frame.alph);
		g2d.drawImage(CalendarFrame.image.getSubimage(getLocation().x, getLocation().y, 60, 60), 0, 0, null);
		if (clicking)
			g2d.setColor(new Color(200, 200, 200));
		else if (hovering)
			g2d.setColor(new Color(220, 220, 220));
		else
			g2d.setColor(new Color(240, 240, 240));
		g2d.fillRect(2, 2, 56, 56);
		g2d.setColor(Frame.prim1);
		if (Times.day == day)
			g2d.drawRect(2, 2, 56, 56);
		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		g2d.drawString("" + day, 6, 20);
		g2d.setColor(new Color(180, 180, 180));
		if (hasEvent)
			g2d.fillRect(8, 40, 44, 12);
	}

	@Override
	public int compareTo(DayButton arg0) {
		return arg0.day - day;
	}

}
