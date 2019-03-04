/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zw.android.framework.db.core;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public final class DateUtils {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PARRERN = "\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}";
	
	public static final String DATE_TIME_PATTERN = "\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}";
	public static final String PART_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	private DateUtils() {
	}

	public static boolean isEmpty(String source) {

		if (source == null || "".equals(source))
			return true;

		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	
	public static Date toDate(String dateString) {

		if (matches(dateString, DATE_PARRERN)) {
			return toDate(dateString, DATE_FORMAT);
		}
		
		if (matches(dateString, DATE_TIME_PATTERN)) {
			return toDate(dateString, DATE_TIME_FORMAT);
		}
		
		return null;
	}

	public static Date toDate(String dateString, String pattern) {
		
		if (dateString == null) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		ParsePosition position = new ParsePosition(0);
		Date strtodate = formatter.parse(dateString, position);
		return strtodate;
	}

	public static String toDateString(Date date) {
		return toDateString(date, DATE_FORMAT);
	}
	
	public static String toDateString(Date date, String pattern) {
		
		if (date == null) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		return formatter.format(date);
	}

	public static long compare(Date d1, Date d2) {

		if (d1 == null || d2 == null) {
			return -1 ;
		}
		
		return d1.getTime() - d2.getTime();
	}

	public static Date set(Date date, int type, int num) {

		if (num < 1) {
			return date;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, num);
		return calendar.getTime();
	}
	
	public static boolean matches(String source, String pattern) {

		if (source == null || !source.matches(pattern)) {
			return false;
		}
		
		return true;
	}
}
