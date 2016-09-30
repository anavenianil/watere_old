package com.callippus.web.controller.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class CPSUtils {
	public static HashMap<String, String> monthMap = new HashMap<String, String>();
	public static HashMap<String, String> daysMap = new HashMap<String, String>();

	/**
	 * Checks if is null.
	 * 
	 * @param value
	 *            the value
	 * @return true, if is null
	 * @throws Exception
	 *             the exception
	 */
	public static boolean isNull(Object value) throws Exception {
		if (value == null)
			return true;
		return false;
	}

	/**
	 * Compare two strings.
	 * 
	 * @param firstValue
	 *            the first value
	 * @param secondValue
	 *            the second value
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public static boolean compareStrings(String firstValue, String secondValue) throws Exception {
		if (firstValue != null && secondValue != null && firstValue.equalsIgnoreCase(secondValue))
			return true;
		return false;
	}
	public static boolean compareString(String firstValue, String secondValue){
		if (firstValue != null && secondValue != null && firstValue.equalsIgnoreCase(secondValue))
			return true;
		return false;
	}
	
	public static boolean checkList(List<?> list) throws Exception {
		if (list != null && list.size() > 0)
			return true;
		return false;
	}

	
	public static boolean checkMap(HashMap<?, ?> map) throws Exception {
		if (map != null && map.size() > 0)
			return true;
		return false;
	}

	public static boolean isNullOrEmpty(Object value) throws Exception {
		if (value != null && !value.toString().equalsIgnoreCase("")&& !value.toString().equalsIgnoreCase("null"))
			return false;
		return true;
	}

	public static String getCurrentDate() throws Exception {
		String dateNow = null;
		Calendar currentDate = null;
		SimpleDateFormat formatter = null;
		try {
			currentDate = Calendar.getInstance();
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
			dateNow = formatter.format(currentDate.getTime());
		} catch (Exception e) {
			throw e;
		}
		return dateNow;
	}

	public static String getCurrentYear() throws Exception {
		String currentYear = null;
		Calendar currentDate = null;
		SimpleDateFormat formatter = null;
		try {
			currentDate = Calendar.getInstance();
			formatter = new SimpleDateFormat("yyyy");
			currentYear = formatter.format(currentDate.getTime());
		} catch (Exception e) {
			throw e;
		}
		return currentYear;
	}

	public static Date getCurrentDateWithTime() throws Exception {
		Date today = null;
		try {
			today = Calendar.getInstance().getTime();
		} catch (Exception e) {
			throw e;
		}
		return today;
	}
	public static String formatDate(Date date) throws Exception{
		String formatDate = null;
		SimpleDateFormat df = null;
		try {
			if (!isNull(date)) {
				df = new SimpleDateFormat("dd-MMM-yyyy");
				formatDate = df.format(date);
			}
		} catch (Exception e) {
			throw e;
		}
		return formatDate;
	}
	public static String formatDate1(Date date) throws Exception{
		String formatDate = null;
		SimpleDateFormat df = null;
		try {
			if (!isNull(date)) {
				df = new SimpleDateFormat("yyyy-MM-dd");
				formatDate = df.format(date);
			}
		} catch (Exception e) {
			throw e;
		}
		return formatDate;
	}
	public static String formattedDate(String date) throws Exception {
		String dateFormatted = null;
		SimpleDateFormat df = null;
		SimpleDateFormat df1 = null;
		try {
			if (!isNull(date)) {
				df = new SimpleDateFormat("yyyy-MM-dd");
				df1 = new SimpleDateFormat("dd-MMM-yyyy");
				dateFormatted = df1.format(df.parse(date));
			}
		} catch (Exception e) {
			throw e;
		}
		return dateFormatted;
	}
	public static String formattedDateWithTime(String date) throws Exception {
		String dateFormatted = null;
		SimpleDateFormat df = null;
		SimpleDateFormat df1 = null;
		try {
			if (!isNull(date)) {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				df1 = new SimpleDateFormat("dd-MMM-yyyy HH-mm");
				dateFormatted = df.format(df1.parse(date));
			}
		} catch (Exception e) {
			throw e;
		}
		return dateFormatted;
	}

	public static String currentYear() throws Exception {
		Calendar currentDate = null;
		String year = null;
		try {
			currentDate = Calendar.getInstance();
			year = String.valueOf(currentDate.get(Calendar.YEAR));

		} catch (Exception e) {
			throw e;
		}
		return year;
	}

	public static Date critFormattedDate(String date) throws Exception {
		DateFormat format = null;
		Date formattedDate = null;
		try {
			if (!isNull(date)) {
				format = new SimpleDateFormat("dd-MMM-yyyy");
				formattedDate = (Date) format.parse(date);
			}
		} catch (Exception e) {
			throw e;
		}
		return formattedDate;
	}

	public static String setEmptyIfNull(String value) throws Exception {
		String result = null;
		try {
			if (isNullOrEmpty(value)) {
				result = " ";
			} else {
				result = value;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public static String setNullIfEmpty(String value) throws Exception {
		String result = null;
		try {
			if (compareStrings(value, " ")) {
				result = null;
			} else {
				result = value;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public static boolean bothNullOrNot(String firstValue, String secondValue) throws Exception {
		boolean status = false;
		try {
			if (isNullOrEmpty(firstValue) && isNullOrEmpty(secondValue)) {
				status = true;
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	public static boolean isNumeric(String value) throws Exception {
		boolean status = true;
		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public static String previousDate(String currentDate) throws Exception {
		String prevDate = null;
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			prevDate = formatter.format(((Date) formatter.parse(currentDate)).getTime() - MILLIS_IN_DAY);
		} catch (Exception e) {
			throw e;
		}
		return prevDate;
	}

	public static String nextDate(String currentDate) throws Exception {
		String nextDate = null;
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			nextDate = formatter.format(((Date) formatter.parse(currentDate)).getTime() + MILLIS_IN_DAY);
		} catch (Exception e) {
			throw e;
		}
		return nextDate;
	}

	public static void monthMap() throws Exception {
		try {
			monthMap.put("Jan", "1");
			monthMap.put("Feb", "2");
			monthMap.put("Mar", "3");
			monthMap.put("Apr", "4");
			monthMap.put("May", "5");
			monthMap.put("Jun", "6");
			monthMap.put("Jul", "7");
			monthMap.put("Aug", "8");
			monthMap.put("Sep", "9");
			monthMap.put("Oct", "10");
			monthMap.put("Nov", "11");
			monthMap.put("Dec", "12");
		} catch (Exception e) {
			throw e;
		}
	}

	public static String convertMonthDate(String date) throws Exception {
		String convertedDate = null;
		try {
			String[] dateArr = date.split("-");
			convertedDate = dateArr[0] + "-" + monthMap.get(dateArr[1]) + "-" + dateArr[2];
		} catch (Exception e) {
			throw e;
		}
		return convertedDate;
	}

	public static String daysDifference(String startDate, String endDate) throws Exception {
		String days = "0";
		Calendar scal = Calendar.getInstance();
		Calendar ecal = Calendar.getInstance();
		String[] startDateArr = new String[3];
		String[] endDateArr = new String[3];
		try {
			// build Month
			if(!CPSUtils.isNull(startDate) && !CPSUtils.isNull(endDate)) {
				monthMap();
				startDate = convertMonthDate(startDate);
				endDate = convertMonthDate(endDate);
				startDateArr = startDate.split("-");
				endDateArr = endDate.split("-");

				scal.set(Integer.valueOf(startDateArr[2]) - 1900, Integer.valueOf(startDateArr[1]) - 1, Integer.valueOf(startDateArr[0]));
				ecal.set(Integer.valueOf(endDateArr[2]) - 1900, Integer.valueOf(endDateArr[1]) - 1, Integer.valueOf(endDateArr[0]));
				days = String.valueOf(((ecal.getTime().getTime() - scal.getTime().getTime()) / (1000 * 60 * 60 * 24)) - 1);
			}
		} catch (Exception e) {
			throw e;
		}
		return days;
	}
	public static String daysDifferenceWithTimeWithOutMonthString(String startDate, String endDate) throws Exception {
		String days = "0";
		Calendar scal = Calendar.getInstance();
		Calendar ecal = Calendar.getInstance();
		String[] startDateArr = new String[3];
		String[] endDateArr = new String[3];
		try {
			// build Month
			if(!CPSUtils.isNull(startDate) && !CPSUtils.isNull(endDate)) {
				startDateArr = startDate.split(" ")[0].split("-");
				endDateArr = endDate.split(" ")[0].split("-");
				scal.set(Integer.valueOf(startDateArr[2]), Integer.valueOf(startDateArr[1]) - 1, Integer.valueOf(startDateArr[0]));
				ecal.set(Integer.valueOf(endDateArr[2]), Integer.valueOf(endDateArr[1]) - 1, Integer.valueOf(endDateArr[0]));
				days = String.valueOf(((ecal.getTime().getTime() - scal.getTime().getTime()) / (1000 * 60 * 60 * 24)));
			}
		} catch (Exception e) {
			throw e;
		}
		return days;
	}
	public static int yearsDifference(String date) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date date1 = null;
		Date date2 = null;
		Calendar cal1 = null;
		Calendar cal2 = null;
		int yearDiff = 0;
		try {
			date1 = df.parse(date);
			date2 = df.parse(getCurrentDate());
			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();

			// different date might have different offset
			cal1.setTime(date1);
			long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);

			cal2.setTime(date2);
			long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);

			// Use integer calculation, truncate the decimals
			int hr1 = (int) (ldate1 / 3600000); // 60*60*1000
			int hr2 = (int) (ldate2 / 3600000);

			int days1 = (int) hr1 / 24;
			int days2 = (int) hr2 / 24;

			int dateDiff = days2 - days1;
			int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
			int weekDiff = dateDiff / 7 + weekOffset;
			yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
			int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

			// System.out.println("Year difference : " + yearDiff);

		} catch (ParseException pe) {
			throw pe;
		}
		return yearDiff;
	}

	public static boolean isGreaterOrNot(String value1, int value2) throws Exception {
		boolean status = false;
		try {
			if (!isNullOrEmpty(value1)) {
				if (Integer.parseInt(value1) > value2)
					status = true;
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	public static String getEncodedHTML(String content) {
		if (content == null) {
			return "";
		}
		content = content.replaceAll("&", "&amp;");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		content = content.replaceAll("\"", "&quot;");
		content = content.replaceAll("'", "&apos;");
		return content;
	}

	@SuppressWarnings("deprecation")
	public static Date convertStringToDate(String date) throws Exception {
		Date convertedDate = null;
		try {
			if (date != null) {
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MMM-yy");
				convertedDate = (Date) formatter.parse(date);
				Date today = Calendar.getInstance().getTime();
				convertedDate.setHours(today.getHours());
				convertedDate.setMinutes(today.getMinutes());
				convertedDate.setSeconds(today.getSeconds());
			}
		} catch (Exception e) {
			throw e;
		}
		return convertedDate;

	}

	
	
//ADDED BY SURESH 09/07/2016
	@SuppressWarnings("deprecation")
	public static Date convertStringToDate1(String date) throws Exception {
		Date convertedDate = null;
		try {
			if (date != null) {
				DateFormat formatter;
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				convertedDate = formatter.parse(date);
			// convertedDate=new Date(date);
				Date today = Calendar.getInstance().getTime();
				convertedDate.setHours(today.getHours());
				convertedDate.setMinutes(today.getMinutes());
				convertedDate.setSeconds(today.getSeconds());
			}
		} catch (Exception e) {
			throw e;
		}
		return convertedDate;

	}
	//ADDED BY SURESH 09/07/2016
		@SuppressWarnings("deprecation")
		public static Date convertStringToDate2(String date) throws Exception {
			Date convertedDate = null;
			try {
				if (date != null) {
					DateFormat formatter;
					formatter = new SimpleDateFormat("dd-MMM-yy");
					convertedDate = formatter.parse(date);
				// convertedDate=new Date(date);
					Date today = Calendar.getInstance().getTime();
					convertedDate.setHours(today.getHours());
					convertedDate.setMinutes(today.getMinutes());
					convertedDate.setSeconds(today.getSeconds());
				}
			} catch (Exception e) {
				throw e;
			}
			return convertedDate;

		}
	
	public static boolean isInteger(String input) throws Exception {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static float convertToFloat(String value) throws Exception {
		try {
			return Float.valueOf(value);
		} catch (Exception e) {
			return 0.0f;
		}
	}

	public static int convertToInteger(String value) throws Exception {
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String addNoOfDays(String fromDate, String noOfDays) throws Exception {
		String result = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
			Date date = (Date) formatter.parse(fromDate);
			Calendar now = Calendar.getInstance();
			now.setTime(date);

			// add days to from date using Calendar.add method
			now.add(Calendar.DATE, Integer.valueOf(noOfDays));
			result = formatter.format(now.getTime());
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public static String addDays(String inputDate, String days) throws Exception {
		String result = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = (Date) formatter.parse(inputDate);
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			//Add days to input date using add() of Calendar
			now.add(Calendar.DATE, Integer.valueOf(days));
			result = formatter.format(now.getTime());
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public static String addNoOfMonths(String fromDate, String noOfMonths) throws Exception {
		String result = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
			Date date = (Date) formatter.parse(fromDate);
			Calendar now = Calendar.getInstance();
			now.setTime(date);

			// add months to from date using Calendar.add method
			now.add(Calendar.MONTH, Integer.valueOf(noOfMonths));
			now.add(Calendar.DATE, -1);
			result = formatter.format(now.getTime());
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public static float round(float number) throws Exception {
		try {
			number = (float) Math.round(number);

			// float p = (float) Math.pow(10, 2);
			// number = (float) Math.round(number * p) / p;

			// DecimalFormat df = new DecimalFormat("#.##");
			// String[] val = String.valueOf(df.format(number)).split("\\.");
			// if (val.length ==1 || Integer.valueOf(val[1]) == 5 || Integer.valueOf(val[1]) == 0) {
			// return number;
			// } else if (Integer.valueOf(val[1]) < 50) {
			// return Float.valueOf(val[0]);
			// } else if (Integer.valueOf(val[1]) > 50) {
			// return Float.valueOf(val[0]) + 1.0f;
			// }
		} catch (Exception e) {
			throw e;
		}
		return number;
	}

	public static String appendValue(String oldValue, String newValue, String delimeter) throws Exception {
		if (isNullOrEmpty(oldValue)) {
			if (isNullOrEmpty(newValue)) {
				return "";
			} else {
				return newValue;
			}
		} else {
			if (isNullOrEmpty(newValue)) {
				return oldValue;
			} else {
				return oldValue + delimeter + " " + newValue;
			}
		}
	}

	public static int getNoofDaysInMonth(String month) throws Exception {

		daysMap.put("Jan", "31");
		daysMap.put("Feb", "28");
		daysMap.put("Mar", "31");
		daysMap.put("Apr", "30");
		daysMap.put("May", "31");
		daysMap.put("Jun", "30");
		daysMap.put("Jul", "31");
		daysMap.put("Aug", "31");
		daysMap.put("Sep", "30");
		daysMap.put("Oct", "31");
		daysMap.put("Nov", "30");
		daysMap.put("Dec", "31");
		return Integer.parseInt(daysMap.get(month));
	}
	public static boolean compareTwoDates(Date d1, Date d2) throws Exception{
		boolean b=false;
			if(d1.getYear()==d2.getYear())
			{
				if(d1.getMonth()==d2.getMonth()){
					if(d1.getDay()==d2.getDay()){
						b=true;
					}
				}
			}
		return b;
	}
	public static boolean compareTwoDate(Date d1, Date d2){
		boolean b=false;
			if(d1.getYear()==d2.getYear())
			{
				if(d1.getMonth()==d2.getMonth()){
					if(d1.getDay()==d2.getDay()){
						b=true;
					}
				}
			}
		return b;
	}
	public static int compareTwoDatesUptoYear(Date targetDate,Date baseDate)throws Exception{
		int returnValue=0;
		if(targetDate.getYear()<baseDate.getYear())
			returnValue=-1;
		else if(targetDate.getYear()>baseDate.getYear())
			returnValue=1;
		else if(targetDate.getYear()==baseDate.getYear())
			returnValue=0;
		
		return  returnValue;
	}
	@SuppressWarnings("deprecation")
	public static int compareTwoDatesUptoMonth(Date targetDate,Date baseDate)throws Exception{
		int	returnValue=compareTwoDatesUptoYear(targetDate,baseDate);
		if(returnValue==0){
			if(targetDate.getMonth()==baseDate.getMonth()){
				returnValue=0;
			}else if(targetDate.getMonth()>baseDate.getMonth()){
				returnValue=1;
			}else if(targetDate.getMonth()<baseDate.getMonth()){
				returnValue=-1;
			}
		}
		return  returnValue;
	}
	@SuppressWarnings("deprecation")
	public static int compareTwoDatesUptoDate(Date targetDate,Date baseDate)throws Exception{
		int	returnValue=compareTwoDatesUptoMonth(targetDate,baseDate);
		if(returnValue==0){
			if(targetDate.getDate()==baseDate.getDate()){
				returnValue=0;
			}else if(targetDate.getDate()>baseDate.getDate()){
				returnValue=1;
			}else if(targetDate.getDate()<baseDate.getDate()){
				returnValue=-1;
			}
		}
		return  returnValue;
	}
	
	@SuppressWarnings("deprecation")
	public static int compareTwoDatesUptoDateWithTime(String targetDate,String baseDate)throws Exception{
		Date tDate = new SimpleDateFormat("yyyy-MM-dd").parse(targetDate.toString().split(" ")[0]);
		Date bDate = new SimpleDateFormat("yyyy-MM-dd").parse(baseDate.toString().split(" ")[0]);
		int	returnValue=compareTwoDatesUptoDate(tDate,bDate);
		
		int thrs = Integer.parseInt(targetDate.toString().split(" ")[1].split(":")[0]);
		int tmin = Integer.parseInt(targetDate.toString().split(" ")[1].split(":")[1]);
		int bhrs = Integer.parseInt(baseDate.toString().split(" ")[1].split(":")[0]);
		int bmin = Integer.parseInt(baseDate.toString().split(" ")[1].split(":")[1]);
		if(returnValue==0){
			
			if(thrs == bhrs){
				returnValue=0;
			}else if(thrs > bhrs){
				returnValue=1;
			}else if(thrs < bhrs){
				returnValue=-1;
			}	
		}
		if(returnValue==0){
			if(tmin == bmin){
				returnValue=0;
			}else if(tmin > bmin){
				returnValue=1;
			}else if(tmin < bmin){
				returnValue=-1;
			}	
		}
		
		return  returnValue;
	}
	/*public static int compareDatesInBetweenDateWithTime(Date startDate,Date midDate, Date endDate) throws Exception{
		int returnValue=0;
		int	returnValue1 = compareTwoDatesUptoDateWithTime(midDate,startDate);
		int returnValue2 = compareTwoDatesUptoDateWithTime(endDate,midDate);
		 if(returnValue1==1 && returnValue2==1){
			 return returnValue=1;	
		 }else if(returnValue1==0 || returnValue2==0){
			 return returnValue=0;
		 }else{
			 return returnValue=-1;
		 }
	}*/
	public static int getNoofDaysInMonth(int month) throws Exception {
		final HashMap<Integer, Integer> daysMapInInteger = new HashMap<Integer, Integer>();
		daysMapInInteger.put(1, 31);
		daysMapInInteger.put(2, 28);
		daysMapInInteger.put(3, 31);
		daysMapInInteger.put(4, 30);
		daysMapInInteger.put(5, 31);
		daysMapInInteger.put(6, 30);
		daysMapInInteger.put(7, 31);
		daysMapInInteger.put(8, 31);
		daysMapInInteger.put(9, 30);
		daysMapInInteger.put(10, 31);
		daysMapInInteger.put(11, 30);
		daysMapInInteger.put(12, 31);
		return daysMapInInteger.get(month);
	}
	public static String convertFirstLetterToUpperCase(String s)throws Exception{
		s=s.toLowerCase();
		String nst="";
		StringTokenizer st=new StringTokenizer(s, " ");
		while(st.hasMoreTokens()){
			String token=st.nextToken();
		    token=(token.substring(0,1).toUpperCase()).concat(token.substring(1));
			nst=nst+token+" "; 
		}
		nst=nst.substring(0,nst.length()-1);
		return nst;
	}
	public static String timeDifference(String maxTime,String minTime)throws Exception{
		String timeDiff=null;
		int hrDiff=Integer.parseInt(maxTime.split(":")[0].trim())-Integer.parseInt(minTime.split(":")[0].trim());
		int minDiff=Integer.parseInt(maxTime.split(":")[1].trim())-Integer.parseInt(minTime.split(":")[1].trim());
		if(minDiff<0){
			hrDiff=hrDiff-1;
			minDiff=60+minDiff;
		}else if(minDiff>60){
			hrDiff=hrDiff+1;
			minDiff=minDiff-60;
		}else if(minDiff==60){
			hrDiff=hrDiff+1;
			minDiff=minDiff-60;
		}
		timeDiff=hrDiff+":"+minDiff;
		return timeDiff;
	}
	public static boolean compareTwoDatesDays(Date d1, Date d2) throws Exception{
		boolean b=false;
			if(d1.getYear()==d2.getYear())
			{
				if(d1.getMonth()==d2.getMonth()){
					if(d1.getDate()==d2.getDate()){
						b=true;
					}
				}
			}
		return b;
	}
}
