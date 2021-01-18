package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.paymentcomponents.libraries.rest.sdk.wrapper.TestConstants;
import com.paymentcomponents.libraries.rest.sdk.wrapper.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTests {

    @Test
    public void convertXmlToJsonTest() {
        //GIVEN
        String expected = TestConstants.VALID_JSON_SEPA_PACS_008;

        //WHEN
        String result = Utils.convertXmlToJson(TestConstants.VALID_SEPA_PACS_008, "Document");

        //THEN
        assertEquals(expected, result);
    }

    @Test
    public void escapeSpecialRegexCharsTest() {
        String text = "{}()[].+*?^$\\|/";
        Assertions.assertEquals("\\{\\}\\(\\)\\[\\]\\.\\+\\*\\?\\^\\$\\\\\\|\\/", TestUtils.escapeSpecialRegexChars(text));
    }

}
