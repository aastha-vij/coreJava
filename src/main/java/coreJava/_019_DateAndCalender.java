package coreJava;

import java.text.SimpleDateFormat;
import java.util.Date;

public class _019_DateAndCalender {

	public static void main(String[] args) {

		Date date = new Date();
		System.out.println(date.toString());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/d/YYYY");
		System.out.println(dateFormat.format(date));
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
		System.out.println(dateFormat2.format(date));
	}

}
