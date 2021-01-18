package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.paymentcomponents.libraries.rest.sdk.wrapper.Constants;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtUtilsTests {

    @Test
    void addTrailingCharactersTest() {
        //GIVEN
        String textToTest = "TESTBICA";

        //WHEN
        String result = MtUtils.addTrailingCharacters(textToTest, "X", 12);

        //THEN
        assertEquals(textToTest + "XXXX", result);
    }

    @Test
    void convertMtCreateRequestDateToMtFormatTest() throws ParseException {
        //GIVEN
        Date dateToTest = new SimpleDateFormat(Constants.MT_CREATE_REQUEST_DATE_FORMAT).parse("2020-09-22");

        //WHEN
        String result = MtUtils.convertMtCreateRequestDateToMtFormat(dateToTest, "yyMMdd");

        //THEN
        assertEquals("200922", result);
    }

    @Test
    void convertBigDecimaltoMtFieldTest() {
        //GIVEN
        BigDecimal amountToTest = new BigDecimal("2004.08");

        //WHEN
        String result = MtUtils.convertBigDecimaltoMtField(amountToTest);

        //THEN
        assertEquals("2004,08", result);

    }

}
