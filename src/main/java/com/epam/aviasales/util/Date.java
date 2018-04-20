package com.epam.aviasales.util;

import java.time.LocalDateTime;

public final class Date {
    private Date() {}

    public static boolean isBefore(LocalDateTime localDateTime) {
        return LocalDateTime.now().isBefore(localDateTime);
    }
}
