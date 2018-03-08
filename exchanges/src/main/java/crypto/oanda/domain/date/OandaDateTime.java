package crypto.oanda.domain.date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class OandaDateTime {

    public static String rfc3339Plus2Days() {
        DateTime dateTime = new DateTime(System.currentTimeMillis() + (2 * 84600000), DateTimeZone.UTC);
        DateTimeFormatter dateFormatter = ISODateTimeFormat.dateTime();

        return dateFormatter.print(dateTime);
    }

    public static String rfc3339(Object date) {
        DateTime dateTime = new DateTime(date, DateTimeZone.UTC);
        DateTimeFormatter dateFormatter = ISODateTimeFormat.dateTime();

        return dateFormatter.print(dateTime);
    }
}
