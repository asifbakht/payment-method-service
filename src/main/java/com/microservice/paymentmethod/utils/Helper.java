package com.microservice.paymentmethod.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static com.microservice.paymentmethod.utils.Constants.ZONE_UTC;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

/**
 * Helper class resides common functions that is accessible
 * through application
 *
 * @author Asif Bakht
 * @since 2024
 */
public class Helper {

    public static String getProcessingDateTime(final int processInDays) {
        return LocalDateTime
                .of(
                        LocalDate
                                .now(ZoneId.of(ZONE_UTC))
                                .plusDays(processInDays),
                        LocalTime.MIDNIGHT)
                .format(ISO_LOCAL_DATE_TIME);

    }

    public static String getCurrentUTCDateTime() {
        return LocalDateTime
                .of(
                        LocalDate.now(ZoneId.of(ZONE_UTC)),
                        LocalTime.now()).format(ISO_LOCAL_DATE_TIME);

    }

}
