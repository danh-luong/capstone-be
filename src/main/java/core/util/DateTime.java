package core.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTime {

    public static Date convertStringToDate(String sDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = formatter.parse(sDate);
        Date result = new Date(date.getTime());
        return result;
    }
}
