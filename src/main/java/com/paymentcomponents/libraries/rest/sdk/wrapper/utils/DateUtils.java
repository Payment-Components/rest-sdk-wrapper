package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static XMLGregorianCalendar toXMLGregorianCalendarDate(LocalDate localDate) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
    }

    public static XMLGregorianCalendar toXMLGregorianCalendarDate(ZonedDateTime zonedDateTime) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    public static LocalDate convertToLocalDate(String value, String format) {
        DateTimeFormatter dtf = (new DateTimeFormatterBuilder()).appendPattern(format).toFormatter();
        return LocalDate.parse(value, dtf);
    }

    public static XMLGregorianCalendar fromDate(String value, String format) throws DatatypeConfigurationException {
        return toXMLGregorianCalendarDate(convertToLocalDate(value, format));
    }

    public static String createTimestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return LocalDateTime.now().format(dtf);
    }

}
