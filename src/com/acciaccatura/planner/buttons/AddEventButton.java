package com.acciaccatura.planner.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.blocks.AddEventRepeatButtonCollection;
import com.acciaccatura.planner.calendar.Event;
import com.acciaccatura.planner.calendar.Times;
import com.acciaccatura.planner.frame.EventPanel;

public class AddEventButton extends Button {

	private static final long serialVersionUID = 1L;

	private boolean activated, moving;
	private int activatel;
	private JTextArea memo;
	private AddEventRepeatButtonCollection coll;
	private boolean hovering2, clicking2;
	
	public AddEventButton(EventPanel pane) {
		super();
		setOpaque(false);
		setPreferredSize(new Dimension(280, 420));
		setBackground(new Color(240, 240, 240));
		setLayout(new GridBagLayout());
		activated = false;
		moving = false;
		hovering2 = false;
		clicking2 = false;
		activatel = 0;
		this.removeMouseListener(this.getMouseListeners()[0]);
		coll = new AddEventRepeatButtonCollection();
		memo = new JTextArea();
		memo.setPreferredSize(new Dimension(260, 140));
		memo.setFont(new Font("Arial", Font.PLAIN, 16));
		memo.setForeground(Frame.prim1);
		memo.setBackground(Color.WHITE);
		memo.setEditable(true);
		memo.setEnabled(true);
		memo.setLineWrap(true);
		addMouseListener(new MouseAdapter(){

			@Override
			public void mouseExited(MouseEvent e) {
				hovering = false;
				hovering2 = false;
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				if ((arg0.getY() <= 60 && !activated) || (arg0.getY() >= 360 && activated)) {
					clicking = true;
					clicked(arg0);
					repaint();
				} else if (arg0.getY() <= 60){
					clicking2 = true;
					clicked(arg0);
					repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				clicking = false;
				clicking2 = false;
				repaint();
			}
			
		});
		addMouseMotionListener(new MouseAdapter(){

			@Override
			public void mouseMoved(MouseEvent e) {
				if ((e.getY() <= 60 && !activated) || (e.getY() >= 360 && activated)) {
					hovering = true;
				} else if (e.getY() <= 60) {
					hovering2 = true;
				} else {
					hovering = false;
					hovering2 = false;
				}
				repaint();
			}
			
		});
	}
	
	@Override
	public void clicked(MouseEvent e) {
		if (!moving) {
			moving = true;
			final int pos = e.getY();
			Thread motion = new Thread(){
				
				@Override
				public void run() {
					try {
						if (!activated) {
							activated = true;
							activatel = 5;
							sleep(20);
							repaint();
							for (int a = 26; a >= 0; a--) {
								activatel += a;
								sleep(10);
								repaint();
							}
							activatel = 356;
							GridBagConstraints c = new GridBagConstraints();
							c.insets = new Insets(80, 10, 20, 10);
							c.gridy = 0;
							AddEventButton.this.add(memo, c);
							c.insets = new Insets(10, 10, 70, 10);
							c.gridy = 1;
							AddEventButton.this.add(coll, c);
							activated = true;
							Frame.frame.pack();
						} else {
							if (pos >= 360 && !memo.getText().equals(""))
								Times.addNewEvent(new Event(Times.year, Times.month, Times.day, Times.dayw, coll.type, memo.getText()));
							AddEventButton.this.removeAll();
							activatel = 351;
							sleep(10);
							repaint();
							activated = false;
							for (int a = 26; a >= 0; a--) {
								activatel -= a;
								sleep(10);
								repaint();
							}
							activatel = 0;
							repaint();
						}
						moving = false;
						this.interrupt();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			motion.start();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (clicking || clicking2)
			g.setColor(new Color(200, 200, 200));
		else if (hovering || hovering2)
			g.setColor(new Color(220, 220, 220));
		else
			g.setColor(new Color(240, 240, 240));
		if (clicking || hovering)
			g.fillRect(4, 4 + activatel, 272, 56);
		else if (clicking2 || hovering2)
			g.fillRect(4, activatel - 352, 272, 56);
		g.setColor(Frame.prim1);
		g.setFont(new Font("Arial", Font.PLAIN, 36));
		g.drawString("Add event", 64, 44 + activatel);
		g.drawRect(4, 4, 272, 55 + activatel);
		if (activated) {
			g.drawString("Close", 100, activatel - 312);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			g.setColor(Frame.prim1);
			g.drawRect(9, activatel-107, 261, 102);
			g.drawRect(9, activatel-277, 261, 142);
			g.drawString("Repeat:", 10, activatel - 110);
			g.drawString("Memo:", 10, activatel - 280);
		}
	}

}
