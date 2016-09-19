package com.acciaccatura.planner.calendar;

import com.acciaccatura.planner.blocks.AddEventRepeatButtonCollection.RepeatType;

public class Event {
	
	public RepeatType type;
	public int dow;
	public int year, month, day;
	public String msg;

	public Event(int yar, int munf, int deh, int dow, RepeatType type, String mess) {
		year = yar;
		month = munf;
		day = deh;
		msg = mess;
		this.type = type;
		this.dow = dow;
	}
	
	public String toString(){
		return month + "/" + day + "/" + year + ", " + dow + ". Repeat: " + type + ".\r" + msg;
	}
	
}
