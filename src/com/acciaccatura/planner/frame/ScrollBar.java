package com.acciaccatura.planner.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JViewport;

import com.acciaccatura.planner.buttons.Button;

public class ScrollBar extends Button {

	private static final long serialVersionUID = 1L;
	
	private JViewport port;
	private EventPanel pane;
	private int at;
	private int length;
	private int mousey;
	
	public ScrollBar(EventPanel pane1, JViewport pane2) {
		super();
		setPreferredSize(new Dimension(20, pane1.getPreferredSize().height));
		port = pane2;
		pane = pane1;
		at = 0;
		length = 200;
		mousey = 0;
		this.removeMouseListener(this.getMouseListeners()[0]);
		addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getY() >= at && e.getY() <= at + length)
					hovering = true;
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				hovering = false;
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				clicking = true;
				if (e.getY() >= at && e.getY() <= at + length)
					mousey = e.getY() - at;
				else {
					if (e.getY() - mousey <= 0)
						at = 0;
					else if (e.getY() - mousey + length >= getPreferredSize().height)
						at = getPreferredSize().height - length;
					else
						at = e.getY() - mousey;
					mousey = length/2;
				}
				repaint();
				scroll((int) (((double) at*pane.pane.getSize().getHeight())/getPreferredSize().getHeight()));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				clicking = false;
				repaint();
			}
			
		});
		addMouseMotionListener(new MouseAdapter(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (e.getY() - mousey <= 0)
					at = 0;
				else if (e.getY() - mousey + length >= getPreferredSize().height)
					at = getPreferredSize().height - length;
				else
					at = e.getY() - mousey;
				repaint();
				scroll((int) (((double) at*pane.pane.getSize().getHeight())/getPreferredSize().getHeight()));
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(CalendarFrame.image.getSubimage(700, 0, 20, 420), 0, 0, null);
		if (clicking)
			g.setColor(new Color(200, 200, 200));
		else if (hovering)
			g.setColor(new Color(220, 220, 220));
		else
			g.setColor(new Color(240, 240, 240));
		g.fillRect(2, at+2, 16, length-4);
	}
	
	private void scroll(int enter) {
		port.setViewPosition(new Point(0,enter));
		//System.out.println((((double) at*pane.pane.getSize().getHeight())/getPreferredSize().getHeight()));
	}

	public void updateScrollbar() {
		pane.empty.setSize(280, 60*(pane.empty.getComponentCount()));
		pane.pane.setSize(new Dimension(280, 60*(pane.empty.getComponentCount())+420));
		double ratio = (port.getPreferredSize().getHeight())/(port.getView().getSize().getHeight());
		if (ratio >= 1)
			length = this.getPreferredSize().height;
		else {
			length = (int) (ratio*this.getPreferredSize().height);
			if (at + length >= this.getPreferredSize().height)
				at = this.getPreferredSize().height - length;
		}
		repaint();
		//perform updates to port
	}
	
	@Override
	public void clicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
