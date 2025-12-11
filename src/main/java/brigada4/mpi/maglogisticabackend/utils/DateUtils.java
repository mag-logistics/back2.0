package brigada4.mpi.maglogisticabackend.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final Date MAX_DATE = getMaxDate();
    public static final Date MIN_DATE = getMinDate();

    public static Date getStartDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getEndDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getTomorrowDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    private static Date getMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2200);
        calendar.add(Calendar.MONTH, 11);
        calendar.add(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 99);
        return calendar.getTime();
    }

    private static Date getMinDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1991);
        calendar.add(Calendar.MONTH, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return getDateWithoutTime(calendar.getTime());
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
    }


    public static Date toDate(LocalDate date) {
        return java.util.Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}

