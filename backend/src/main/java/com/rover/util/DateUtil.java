package com.rover.util;

import java.text.SimpleDateFormat;

public class DateUtil {
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_PATTERN_MATCHER = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_PATTERN);
}
