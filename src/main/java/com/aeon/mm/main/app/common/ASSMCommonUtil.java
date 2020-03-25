/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/

package com.aeon.mm.main.app.common;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Utility class for Fast project.
 * <p>
 * 
 * <pre>
 * In this class, it includes the common methods for whole Fast project.
 * </pre>
 * 
 * </p>
 * 
 */
public final class ASSMCommonUtil {

    /**
     * 
     * Default Constructor.
     * <p>
     * 
     * <pre>
     * </pre>
     * 
     * </p>
     * 
     * @return CommonUtil
     */
    private ASSMCommonUtil() {

    }

    /**
     * 
     * Compare two BigDecimal values.
     * <p>
     * 
     * <pre>
     * </pre>
     * 
     * </p>
     * 
     * @param amount
     *            the value to compare
     * @param compareAmount
     *            the value to compare
     * @return the status of greater than or not
     */
    public static boolean isGreaterThan(BigDecimal amount, BigDecimal compareAmount) {

        if (amount.compareTo(compareAmount) < 1) {
            return false;
        }
        return true;
    }

    /**
     * 
     * Get current Timestamp.
     * 
     * @return current Timestamp
     */
    public static Timestamp getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        return timestamp;
    }

    /**
     * 
     * Get Timestamp From String.
     * 
     * @return Timestamp
     */
    public static Timestamp getChangeStringToTimeStamp(String date) {

        Timestamp timestamp = null;
        try {
            // Long time = System.currentTimeMillis();
            date = date.replace("/", "-");
            date = date.concat(" 00:00:00");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new Timestamp(parsedDate.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }

    public static String getChangeTimestampToString (Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String string = dateFormat.format(timestamp);
        return string;
    }
    /**
     * 
     * Get current Year
     * 
     * @return year
     */
    public static String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        int year;

        // if month = 2 => March
        if (now.get(Calendar.MONTH) < 3) {
            year = now.get(Calendar.YEAR) - 1;
        } else {
            year = now.get(Calendar.YEAR);
        }
        String currentYear = String.valueOf(year);

        return currentYear;
    }

    /**
     * 
     * Convert String to Double
     * 
     * @return double (after convertion)
     * 
     * @return null (error occur)
     */
    public static double convertDouble(String strValue) {

        if (strValue == null || strValue.equals("")) {
            Double doubleValue = Double.parseDouble(strValue);
            if (doubleValue > -1.00) {
                return doubleValue;
            }
        }
        return 0.0;
    }

    /**
     * 
     * convert String to int
     * 
     * @return int (after convertion)
     * 
     * @return null (error occur)
     */
    public static int convertInteger(String strValue) {
        int intValue = Integer.parseInt(strValue);
        if (intValue < 0) {
            return -1;
        }
        return intValue;
    }

    /**
     * 
     * check startPeriod is less than endPeriod
     * 
     * @return true (if startPeriod is less than endPeriod)
     */
    public static boolean isBeforeEndPeriod(Date startPeriod, Date endPeriod) {
        boolean isBefore = true;
        if (startPeriod.compareTo(endPeriod) <= 0) {
            isBefore = false;
        }
        return isBefore;
    }

    /**
     * 
     * get month 0 for January- 11 for December
     * 
     * @return int (index of month)
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    /**
     * 
     * get year depand on inputting date
     * 
     * @return int (index of year)
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static Date changeYMDSlashStringToDate(String dateStr) {

        try {
            DateFormat format = new SimpleDateFormat(ASSMCommonConstant.YYYYMMDD_SLASH);
            return format.parse(dateStr);
        } catch (ParseException e) {
           e.printStackTrace();
        }
        return null;
    }


    public static Date changeTimestampToYMDdate(Timestamp timestamp) {

        try {
            DateFormat format = new SimpleDateFormat(ASSMCommonConstant.YYYYMMDD_SLASH);
            return format.parse(format.format(timestamp));
            
        } catch (ParseException e) {
           e.printStackTrace();
        }
        return null;
    }

    public static String changeDateToYMDSlashString(Date date) {

        if (date == null) {
            return null;
        } else {
            DateFormat format = new SimpleDateFormat(ASSMCommonConstant.YYYYMMDD_SLASH);
            return format.format(date);
        }
    }

    public static boolean isUnitPrice(String unitPrice) {
        Pattern p = Pattern.compile("^[0-9]{1,4}(\\.[0-9]*)?$");
        Matcher m = p.matcher(unitPrice);

        return m.matches();
    }

    public static boolean isNumeric(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,4}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidAmount(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,10}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidManMonth(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,6}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidBudgetTotal(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,16}(\\.[0-9]*)?$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidInteger(String inputStr) {
        Pattern p = Pattern.compile("^[0-9]{1,2}$");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidDirId(String dirId) {

        Pattern p = Pattern.compile("[a-zA-Z0-9]{7}");
        Matcher m = p.matcher(dirId);

        return m.matches();
    }

    public static String getCurrentStringDate() {
        DateFormat dateFormat = new SimpleDateFormat(ASSMCommonConstant.YYYYMMDD_SLASH);
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public static Date getCurrentDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(ASSMCommonConstant.YYYYMMDD_SLASH);
        Date date = new Date();
        return dateFormat.parse(dateFormat.format(date));
    }

    public static String getFormattedNumberfor2Digit(String number) {
        return String.format("%.2f", Double.parseDouble(number));
    }

    public static String getFormattedNumberFor4Digit(String number) {
        return String.format("%.4f", Double.parseDouble(number));
    }

    public static String getFormattedNumber(String number, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        String numberAsString = decimalFormat.format(Double.parseDouble(number));

        return numberAsString;
    }

    /**
     * 
     * Get current Month
     * 
     * @return month(int)
     */
    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1;

        return currentMonth;
    }

    public static String increaseYear(String year) {
        int currentYear = Integer.parseInt(year) + 1;
        return Integer.toString(currentYear);
    }

    public static boolean hasDuplicateYear(String year, ArrayList<String> yearList) {
        int count = 0;
        if (yearList != null && yearList.size() > 1) {
            for (String tempYear : yearList) {
                if (tempYear == year) {
                    count++;
                }
            }
            if (count > 1) {
                return true;
            }
        }
        return false;
    }

    public static String getTotalManMonth(ArrayList<Double> totalPlanList) {
        double totalManMonth = 0.00;
        for (int i = 0; i < totalPlanList.size(); i++) {
            totalManMonth += totalPlanList.get(i);
        }
        return Double.toString(totalManMonth);

    }

    /**
     * 
     * check two dates difference
     * 
     * @return days (difference days)
     */
    public static long getDifferenceDays(String sendDate, String currentDate) {
        long diffDays = 0;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date send = format.parse(sendDate);
            Date current = format.parse(currentDate);

            long diffTime = current.getTime() - send.getTime();
            diffDays = diffTime / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffDays;
    }

    public static boolean isValidPhoneNo(String inputStr) {
        Pattern p = Pattern.compile("[09][0-9]{7,9}");
        Matcher m = p.matcher(inputStr);

        return m.matches();
    }

    public static boolean isValidDMYDate(String dateString) {
        Pattern p = Pattern.compile("[0-9]{1,2}(/|-)[0-9]{1,2}(/|-)[0-9]{4}");
        Matcher m = p.matcher(dateString);

        return m.matches();
    }

    public static boolean isValidAgreementNo(String agreementNo) {
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{1}-[0-9]{10}-[0-9]{1}");
        Matcher m = p.matcher(agreementNo);

        return m.matches();
    }
    
    public static Map<Integer, String> getTargetMap() {
        Map<Integer,String> targetMap = new HashMap<>();
        targetMap.put(1, "Mobile");
        targetMap.put(2, "Non-mobile");
        targetMap.put(3, "Personal Loan");        
        
        return targetMap;
    }

    public static boolean isWithinPeriod(Timestamp startDate, Timestamp endDate) {
    	
    	if(startDate.before(getCurrentTimeStamp()) && endDate.after(getCurrentTimeStamp())){
    		return true;
    	}
    	return false;
    }
    
    public static String getCurrentTimeInString() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String string = dateFormat.format(getCurrentTimeStamp());
        return string;
    }
    
    /*
     * check version for update.
     * */  	
    public static boolean isUpgradeNeeded(String reqVersion, String latestVersion) {
		int reqCode[] = new int[3];
		int latestCode[] = new int[3];
		int i=0;
		try {
			StringTokenizer st = new StringTokenizer(reqVersion.trim(), ".");
			while (st.hasMoreElements()) {
				reqCode[i] = Integer.parseInt(st.nextElement().toString());
				i++;
			}
			i=0;
			StringTokenizer st2 = new StringTokenizer(latestVersion.trim(), ".");
			while (st2.hasMoreElements()) {
				latestCode[i] = Integer.parseInt(st2.nextElement().toString());
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		for(int j=0; j<3; j++) {
			if(reqCode[j] > latestCode[j]) {
				return false;
			} else if (reqCode[j] < latestCode[j]) {
				return true;
			}
		}
		
		return false;
  	}
}
