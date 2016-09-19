package com.acciaccatura.planner.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JPanel;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.buttons.DayButton;
import com.acciaccatura.planner.buttons.MonthButton;
import com.acciaccatura.planner.calendar.Times;

public class MonthPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static final int firstday = 4; //Thursday, January 1, 1970

	private GridBagConstraints c;
	private MonthButton button = new MonthButton();
	
	public static List<DayButton> buttons = new ArrayList<DayButton>();
	
	public MonthPanel() {
		super();
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		setPreferredSize(new Dimension(420, 420));
		setBackground(Color.WHITE);
		c.gridwidth = 7;
		add(button, c);
		c.gridwidth = 1;
	}
	
	public void setMonth(int month, int year) {
		Times.refresh();
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 1;
		if (month == Times.cal.get(Calendar.MONTH) && year == Times.cal.get(Calendar.YEAR))
			Times.day = Times.cal.get(Calendar.DAY_OF_MONTH);
		else
			Times.day = 1;
		Times.month = month;
		Times.year = year;
		int getdown = firstday;
		for (int a = 1970; a < year; a++) {
			if (Times.isLeapYear(a)) {
				getdown+=366;
			} else
				getdown+=365;
		}
		for (int a = 0; a < month; a++)
			getdown += Times.getNumbDays(a, year);
		c.gridx = getdown %= 7;
		int yes = Times.getNumbDays(month, year);
		for (DayButton b: buttons)
			remove(b);
		buttons.clear();
		for (int a = 1; a <= yes; a++) {
			if (c.gridx == 0 && yes - a < 7)
				c.insets.bottom = 60*(6 - c.gridy);
			addDay(new DayButton(a, c.gridx),c);
			c.gridx++;
			if (c.gridx >= 7) {
				c.gridx = 0;
				c.gridy++;
			}
		}
		Times.updateCalendar();
		for (DayButton d: buttons) {
			System.out.println(d.hasEvent);
		}
		repaint();
		Frame.frame.pack();
	}

	public void addDay(DayButton arg0, Object arg1) {
		add(arg0, arg1);
		buttons.add(arg0);
	}
	
}
