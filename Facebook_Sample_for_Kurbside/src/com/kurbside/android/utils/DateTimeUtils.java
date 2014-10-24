package com.kurbside.android.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils
{
	public static final String MESSAGING_DATE_FORMAT = "dd MMM";
	public static final String MESSAGING_TIME_FORMAT = "hh:mm a";
	
	public static String formatShortDate(Date date)
	{
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		return dateFormat.format(date);
	}
	
	
	public static String formatMediumDate(Date date)
	{
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		return dateFormat.format(date);
	}
	
	
	public static String formatLongDate(Date date)
	{
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
		return dateFormat.format(date);
	}
	
	public static String formatShortDateTime(Date date)
	{
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
		return dateFormat.format(date);
	}
	
	
	public static String formatMediumDateTime(Date date)
	{
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault());
		return dateFormat.format(date);
	}
	
	
	public static String formatLongDateTime(Date date)
	{
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.getDefault());
		return dateFormat.format(date);
	}
	
	
	public static String formatCustomDate(String format, Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	
	public static Date parseStr(String format, String dateStr)
	{
		DateFormat dateFormat = new SimpleDateFormat(format);
		
		Date date;
  	try
  	{
  		date = dateFormat.parse(dateStr);
  	}
  	catch(ParseException e)
  	{
  		e.printStackTrace();
  		return null;
  	}
  	
  	return date;
	}
}
