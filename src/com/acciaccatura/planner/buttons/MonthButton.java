package com.acciaccatura.planner.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.calendar.Times;
import com.acciaccatura.planner.frame.CalendarFrame;

public class MonthButton extends Button {

	private static final long serialVersionUID = 1L;
	
	private int textAnimSize = 24;
	private boolean isMoving = false;

	public MonthButton() {
		super();
		setPreferredSize(new Dimension(420, 60));
		setBackground(Color.WHITE);
		setOpaque(false);
		setLayout(new GridBagLayout());
	}
	
	@Override
	public void clicked(MouseEvent e) {
		if (!isMoving) {
			isMoving = true;
			Thread motion = new Thread(){
				
				@Override
				public void run() {
					try {
						if (textAnimSize < 24) {
							removeAll();
							while (textAnimSize < 24) {
								textAnimSize++;
								repaint();
								sleep(20);
							}
						} else {
							while (textAnimSize > 16) {
								textAnimSize--;
								repaint();
								sleep(20);
							}
							Button upbutton = new ImageButton("res/side_buttons.png"){

								@Override
								public void clicked(MouseEvent e) {
									if (Times.month == 11)
										CalendarFrame.pane.setMonth(0, Times.year+1);
									else
										CalendarFrame.pane.setMonth(Times.month+1, Times.year);
									MonthButton.this.repaint();
								}
								
								@Override
								public void paintComponent(Graphics g) {
									g.drawImage(this.image.getSubimage(0, 60*(this.hovering ? 1 : 0), 40, 30), 0, 0, null);
								}
								
							};
							Button downbutton = new ImageButton("res/side_buttons.png"){

								@Override
								public void clicked(MouseEvent e) {
									if (Times.month == 0)
										CalendarFrame.pane.setMonth(11, Times.year-1);
									else
										CalendarFrame.pane.setMonth(Times.month-1, Times.year);
									MonthButton.this.repaint();
								}
								
								@Override
								public void paintComponent(Graphics g) {
									g.drawImage(this.image.getSubimage(0, 30+60*(this.hovering ? 1 : 0), 40, 30), 0, 0, null);
								}
								
							};
							upbutton.setPreferredSize(new Dimension(40, 30));
							downbutton.setPreferredSize(new Dimension(40, 30));
							GridBagConstraints c = new GridBagConstraints();
							c.gridx = 0;
							c.gridy = 0;
							c.insets.left = MonthButton.this.getPreferredSize().width-40;
							MonthButton.this.add(upbutton, c);
							c.gridy = 1;
							MonthButton.this.add(downbutton, c);
							Frame.frame.pack();
						}
						System.out.println("done. " + MonthButton.this.getComponentCount());
						isMoving = false;
						interrupt();
					} catch (InterruptedException e) {
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
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(CalendarFrame.image.getSubimage(getLocation().x, getLocation().y, 420, 60), 0, 0, null);
		g2d.setComposite(Frame.alph);
		g2d.setFont(new Font("Arial", Font.PLAIN, textAnimSize*2));
		if (clicking)
			g2d.setColor(Frame.prim3);
		else if (hovering)
			g2d.setColor(Frame.prim2);
		else
			g2d.setColor(Frame.prim1);
		g2d.drawString(Times.getMonth(Times.month) + " " + Times.year, 8, 48 - (24-textAnimSize));
	}
	
}
