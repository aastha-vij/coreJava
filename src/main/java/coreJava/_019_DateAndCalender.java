package coreJava;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class _019_DateAndCalender {

	public static void main(String[] args) {

		Date date = new Date();
		System.out.println(date.toString());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/d/YYYY");
		System.out.println(dateFormat.format(date));
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
		System.out.println(dateFormat2.format(date));
		
		
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat2.format(cal.getTime()));
		
		System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		System.out.println(cal.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println(cal.get(Calendar.AM_PM));
		System.out.println(cal.get(Calendar.MINUTE));


	}

}
