package javadeveloper.module9.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FormattedZonedDateTime {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentFormattedZonedDateTime() {
        OffsetDateTime now = OffsetDateTime.now();
        return DATE_TIME_FORMATTER.format(now) + " UTC" + now.getOffset();
    }

    public static String getCurrentFormattedZonedDateTime(String timezone) {
        String time = timezone.replaceAll("UTC", "");
        int minutes = 0;
        String[] timeArr = time.split(":");
        int hours = Integer.parseInt(timeArr[0]);

        if (timeArr.length > 1)
            minutes = Integer.parseInt(timeArr[1]);

        String offsetId = (hours > 0 ? "+" : "-" )
                + String.format("%02d", Math.abs(hours)) + ":"
                + String.format("%02d", minutes);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of(offsetId));

        return DATE_TIME_FORMATTER.format(now) + " UTC" + now.getOffset();
    }
}
