package javadeveloper.module9.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class ZoneOffsetValidator {
    public static boolean isZoneOffsetValid(String timeZoneOffset) {
        Set<String> allAvailableZoneOffsets = new HashSet<>();
        LocalDateTime localDateTime = LocalDateTime.now();

        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId id = ZoneId.of(zoneId);
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);
            ZoneOffset zoneOffset = zonedDateTime.getOffset();
            String offset = zoneOffset.getId()
                    .replaceAll(":00", "")
                    .replaceAll("Z", "")
                    .replaceAll("\\+0", "+")
                    .replaceAll("-0", "-");
            allAvailableZoneOffsets.add("UTC" + offset);
        }

        return allAvailableZoneOffsets.contains(timeZoneOffset);
    }
}
