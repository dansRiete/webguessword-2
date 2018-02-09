package com.oleksii.kuzko.dao;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author The Weather Company, An IBM Business
 */
public class DateTimeUtils {

    public static ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
    }
}
