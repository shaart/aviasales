package com.epam.aviasales.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public final class Dates {

  private Dates() {
  }

  public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
    return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String getLocalizedDateTime(LocalDateTime localDateTime, String locale) {
    if (localDateTime == null) {
      return "";
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(new Locale(locale));

    return localDateTime.format(formatter);
  }

  public static Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
