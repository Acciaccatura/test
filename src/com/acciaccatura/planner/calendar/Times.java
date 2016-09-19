package com.acciaccatura.planner.calendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.acciaccatura.planner.Frame;
import com.acciaccatura.planner.blocks.AddEventRepeatButtonCollection.RepeatType;
import com.acciaccatura.planner.blocks.EventBlock;
import com.acciaccatura.planner.buttons.DayButton;
import com.acciaccatura.planner.frame.AlertFrame;
import com.acciaccatura.planner.frame.CalendarFrame;
import com.acciaccatura.planner.frame.MonthPanel;

public class Times {
	
	public static Calendar cal = Calendar.getInstance();
	public static List<Event> events = new ArrayList<Event>();
	
	public static int year = cal.get(Calendar.YEAR);
	public static int month = cal.get(Calendar.MONTH);
	public static int day = cal.get(Calendar.DAY_OF_MONTH);
	public static int dayw = cal.get(Calendar.DAY_OF_WEEK);

	public static void refresh() {
		cal = Calendar.getInstance();
	}
	
	public static void loadEvents() {
		try {
			File f = new File("res/data.dat");
			BufferedReader read = new BufferedReader(new FileReader(f));
			String in = "";
			while (!((in = read.readLine()) == null)) {
				String[] split = in.split(" ");
				events.add(new Event(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]),RepeatType.valueOf(split[4]),split[5].replace("_", " ")));
			}
			read.close();
			loadDayEvents(Times.day, Times.month, Times.year);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getNumbDays(int month, int year) {
		switch (month) {
			case 0: case 2: case 4: case 6: case 7: case 9: case 11:
				return 31;
			case 3: case 5: case 8: case 10:
				return 30;
			case 1:
				if (isLeapYear(year))
					return 29;
				else
					return 28;
			default:
				return -1;
		}
	}
	
	public static boolean isLeapYear(int year) {
		return year%4 == 0 && !(year%100 == 0 && year%400 != 0);
	}
	
	public static boolean isEventToday(Event e) {
		return e.day == Times.day && e.month == Times.month && e.year == Times.year;
	}
	
	public static void addNewEvent(Event arg0){
		events.add(arg0);
		try {
			File f = new File("res/data.dat");
			BufferedWriter write = new BufferedWriter(new FileWriter(f, true));
			write.write(arg0.year + " " + arg0.month + " " + arg0.day + " " + arg0.dow + " " + arg0.type + " " + arg0.msg.replace(" ", "_") + "\r");
			write.close();
			if (isEventToday(arg0)) {
				CalendarFrame.pane2.empty.add(new EventBlock(arg0));
				Frame.frame.pack();
			}
			CalendarFrame.pane2.scroll.updateScrollbar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeDayEvents(){
		CalendarFrame.pane2.empty.removeAll();
	}

	public static void updateData() {
		try {
			File f = new File("res/data.dat");
			BufferedWriter write = new BufferedWriter(new FileWriter(f, false));
			for (Event arg0: events) {
				write.write(arg0.year + " " + arg0.month + " " + arg0.day + " " + arg0.dow + " " + arg0.type + " " + arg0.msg.replace(" ", "_") + "\r");
			}
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean updateCalendar(){
		System.out.println(events);
		for (DayButton d: MonthPanel.buttons)
			d.hasEvent = false;
		for (Event e: events) {
			switch (e.type) {
				case WEEKLY:
					for (DayButton d: MonthPanel.buttons) {
						if (d.dow == e.dow)
							d.hasEvent = true;
					}
					break;
				case DAILY:
					for (DayButton d: MonthPanel.buttons)
						d.hasEvent = true;
					return true;
				case MONTHLY:
					if (e.day <= MonthPanel.buttons.size())
						MonthPanel.buttons.get(e.day-1).hasEvent = true;
					break;
				case YEARLY:
					if (e.month == month && e.day <= MonthPanel.buttons.size())
						MonthPanel.buttons.get(e.day-1).hasEvent = true;
					break;
				case NONE:
					if (e.month == month && e.year == year)
						MonthPanel.buttons.get(e.day-1).hasEvent = true;
					break;
			}
		}
			return true;
	}
	
	public static void loadDayEvents(int day, int month, int year){
		for (Event e: events) {
			boolean isThere = false;
			for (int a = 0; a < CalendarFrame.pane2.empty.getComponentCount(); a++) {
				if (((EventBlock) CalendarFrame.pane2.empty.getComponents()[a]).event.equals(e)) {
					isThere = true;
					break;
				}
			}
			if (!isThere) {
				switch (e.type) {
					case NONE:
						if (e.day == day && e.month == month && e.year == year)
							CalendarFrame.pane2.empty.add(new EventBlock(e));
						break;
					case DAILY:
						CalendarFrame.pane2.empty.add(new EventBlock(e));
						break;
					case WEEKLY:
						if (e.dow == dayw)
							CalendarFrame.pane2.empty.add(new EventBlock(e));
						break;
					case MONTHLY:
						if (e.day == day)
							CalendarFrame.pane2.empty.add(new EventBlock(e));
						break;
					case YEARLY:
						if (e.day == day && e.month == month)
							CalendarFrame.pane2.empty.add(new EventBlock(e));
						break;
				}
			}
		}
	}
	
	public static List<Event> getCloseEvents(int dist){
		List<Event> events = new ArrayList<Event>();
		AlertFrame.dates.clear();
		for (int a = 0; a < dist; a++) {
			int day = cal.get(Calendar.DAY_OF_MONTH) + a;
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int dow = (cal.get(Calendar.DAY_OF_WEEK) + a)%7;
			if (dow == 0)
				dow = 7;
			if (day > Times.getNumbDays(month, year)) {
				day -= (Times.getNumbDays(month, year) - 1);
				month++;
				if (month >= 12) {
					year++;
					month = 0;
				}
			}
			for (Event e: Times.events) {
				switch (e.type) {
					case NONE:
						if (e.day == day && e.month == month && e.year == year) {
							events.add(e);
							AlertFrame.dates.add(dateFormat(day, month, year));
						}
						break;
					case DAILY:
						events.add(e);
						AlertFrame.dates.add(dateFormat(day, month, year));
						break;
					case WEEKLY:
						if (e.dow == dow) {
							events.add(e);
							AlertFrame.dates.add(dateFormat(day, month, year));
						}
						break;
					case MONTHLY:
						if (e.day == day) {
							events.add(e);
							AlertFrame.dates.add(dateFormat(day, month, year));
						}
						break;
					case YEARLY:
						if (e.day == day && e.month == month) {
							events.add(e);
							AlertFrame.dates.add(dateFormat(day, month, year));
						}
						break;
				}
			}
		}
		return events;
	}
	
	public static String getTimes() {
		return getMonth(month) + " " + day + ", " + year;
	}
	
	public static String getMonth(int month) {
		switch (month) {
			case 0:return "January";
			case 1:return "February";
			case 2:return "March";
			case 3:return "April";
			case 4:return "May";
			case 5:return "June";
			case 6:return "July";
			case 7:return "August";
			case 8:return "September";
			case 9:return "October";
			case 10:return "November";
			case 11:return "December";
			default:return "";
		}
	}
	
	public static String getDayOfWeek(int day) {
		switch (day) {
			case 1: return "Sunday";
			case 2: return "Monday";
			case 3: return "Tuesday";
			case 4: return "Wednesday";
			case 5: return "Thursday";
			case 6: return "Friday";
			case 7: return "Saturday";
			default: return "";
		}
	}
	
	public static String dateFormat(int day, int month, int year) {
		return getMonth(month) + " " + day + ", " + year;
	}
	
}
