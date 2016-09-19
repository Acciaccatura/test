package com.acciaccatura.planner.buttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public abstract class Button extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected boolean hovering;
	protected boolean clicking;

	public Button() {
		super();
		addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent e) {
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
				repaint();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				clicking = false;
				clicked(e);
				repaint();
			}
			
		});
	}
	
	public abstract void clicked(MouseEvent e);
	
}
