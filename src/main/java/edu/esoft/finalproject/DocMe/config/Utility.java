package edu.esoft.finalproject.DocMe.config;

import org.apache.log4j.Logger;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Utility {
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    private static final Logger LOGGER = Logger.getLogger(Utility.class);

    private static final String strMonths[] = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    private static final String dayStr[] = {"st", "nd", "rd", "th", "th",
            "th", "th", "th", "th", "th",
            "th", "th", "th", "th", "th",
            "th", "th", "th", "th", "th",
            "st", "nd", "rd", "th", "th",
            "th", "th", "th", "th", "th",
            "st"};


    private static final String shortStrMonths[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};


    public static long getDateDiff(Date maxDate, Date minDate) {

        Period p = new Period(maxDate.getTime(), minDate.getTime(), PeriodType.days());
        long days = p.getDays();
        if (days == 0 && maxDate.compareTo(minDate) == -1) {
            days = 1;
        }

        return days;
    }

    public static long getNoDaysDateDiff(String passDate, String dateFormat) {
        long days = 0;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Date dateCurrent;
        Date datePasss;

        try {
            dateCurrent = new Date();
            datePasss = df.parse(passDate);
            long diff = dateCurrent.getTime() - datePasss.getTime();
            days = (diff / (1000 * 60 * 60 * 24));
        } catch (ParseException pe) {
            LOGGER.error(pe);
        }
        return days;

    }

    public static Date getCustomizeDate(String dateInString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        return date;
    }

    public static String convertToEntityAttributeToDbFieldName(String entityAttributeName) {
        String s = entityAttributeName.replaceAll("([A-Z])", "_$1").toLowerCase();
        LOGGER.debug(s);
        return s;
    }

    private static String javaDate(String fmt) {
        String dd;
        TimeZone gmt530 = TimeZone.getTimeZone("GMT");
        gmt530.setRawOffset((11 * 30) * 60 * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        formatter.setTimeZone(gmt530);
        dd = formatter.format(new Date());
        return dd;
    }

    public static String sysDateDiff(int years) {
        Calendar now = Calendar.getInstance();
        now = Calendar.getInstance();
        now.add(Calendar.YEAR, -(years));
        int month = now.get(Calendar.MONTH) + 1;
        String sMonth = "";
        if (month <= 9) {
            sMonth = "0" + month + "";
        }

        return now.get(Calendar.YEAR) + "-" + sMonth + "-" + now.get(Calendar.DATE);
    }


    public static String sysDate(String dateFormat) {
        return javaDate(dateFormat);
    }

    public static String getCurrentYearMonth() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(date);
    }

    public static String getDateString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String getFileExtentionUsingMultipart(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf('.');
        return originalFilename.substring(index);

    }

    public static int getCurrentMonth(String bussinessDate) {
        SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = dt.parse(bussinessDate);
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;

    }

    public static int getCurrentYear(String bussinessDate) {

        SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = dt.parse(bussinessDate);
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static String getStringDate(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String reportDate = null;
        try {
            reportDate = df.format(date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return reportDate;
    }

    public static Date getDateFromString(String sDate, String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dt.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }


    public static int monthsBetweenDates(Date startDate, Date endDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int monthsBetween = 0;
        int dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);

        if (dateDiff < 0) {
            int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (end.get(Calendar.DAY_OF_MONTH) + borrrow) - start.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if (dateDiff > 0) {
                monthsBetween++;
            }
        } else {
            monthsBetween++;
        }
        monthsBetween += end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        monthsBetween += (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return monthsBetween;
    }

    public static String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("###,##0.00;(###,##0.00)");
        return df.format(value);
    }

    public static String getDateCustomerDescribeHTML(String date) {
        //2011-10-02
        String m_date = "";//02nd of October 2011
        String dataArray[] = date.split("-");
        int m_day;

        try {
            m_day = Integer.parseInt(dataArray[2]);
            m_date = dataArray[2] + "<sup>" + dayStr[m_day - 1] + "</sup> of " + Utility.getMonthFullName(dataArray[1]) + " " + dataArray[0];
        } catch (NumberFormatException e) {
            LOGGER.error(e);
        }
        return m_date;
    }

    public static String getMonthFullName(int month) {
        return strMonths[month - 1];
    }

    public static String getMonthFullName(int month, int language) {
        return strMonths[month - 1];
    }

    public static String getMonthFullName(String month) {
        return getMonthFullName(Integer.parseInt(month));
    }

    public static boolean getIsGreaterThan(String dateOne, String dateTwo, String dateFormat) {
        //dateOne == appointmentDate, dateTwo=fromDate
        boolean greaterThan = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(dateOne);
            Date date2 = sdf.parse(dateTwo);


            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            if (cal1.after(cal2)) {
                greaterThan = true;
            } else if (cal1.before(cal2)) {
                greaterThan = false;

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return greaterThan;
    }

    public static String getCustomizeDateFormat(String currentDate, String currentFormat, String requiredFormat) {
        DateFormat formatter = new SimpleDateFormat(currentFormat);
        SimpleDateFormat parse = new SimpleDateFormat(requiredFormat);
        Date date = null;
        if (currentDate.equals("")) {
            LOGGER.info("INVALID DATE 1 : " + currentDate);
            return "";
        }
        try {
            date = (Date) formatter.parse(currentDate);
        } catch (ParseException e) {
            LOGGER.error("Date Value 1 :" + currentDate + " -> " + e);
        }
        String parsed = parse.format(date);
        return (parsed);
    }

    public static String getCustomizeDateFormat(Date currentDate, String requiredFormat) {
        SimpleDateFormat parse = new SimpleDateFormat(requiredFormat);
        String parsed = "";

        try {
            parsed = parse.format(currentDate);
        } catch (Exception e) {
            LOGGER.error("Date Value 1 :" + currentDate + " -> " + e);
        }

        return parsed;
    }

    public static String getCusormerDateFormat(String date, String cust_date_format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(cust_date_format);
        String m_date = "1980-01-01";
        if (date.equals("")) {
            LOGGER.info("INVALID DATE 2 : " + date);
            return "";
        }
        try {
            m_date = dateFormat.format(dateFormat.parse(date));
        } catch (ParseException e) {
            LOGGER.error("Date Value 2 :" + date + " -> " + e);
        }
        return m_date;
    }


    public static String getDateCustomerFormatDescribeHTML(String date) {
        //2011-10-02
        String m_date = "";//02nd of October 2011

        String dataArray[] = date.split("-");
        int m_day;

        try {
            m_day = Integer.parseInt(dataArray[2]);
            m_date = dataArray[2] + " " + Utility.getMonthShortName(dataArray[1]) + " " + dataArray[0];
        } catch (NumberFormatException e) {
            LOGGER.error(e);
        }
        return m_date;
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    public static String getMonthShortName(int month) {
        return shortStrMonths[month - 1];
    }

    public static String getMonthShortName(String month) {
        return getMonthShortName(Integer.parseInt(month));
    }

    public static boolean isLeapYear(int year) {
        boolean result = false;
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
            result = true;
        }

        return result;
    }

    public static long getDiffDays(Date fromDate, Date toDate) {
        long diff = toDate.getTime() - fromDate.getTime();
        long d = diff / (24 * 60 * 60 * 1000);

        return d;
    }
    

}
