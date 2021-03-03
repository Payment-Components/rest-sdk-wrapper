package com.paymentcomponents.libraries.rest.sdk.wrapper;

import gr.datamation.swift.common.InvalidMessageFormatException;
import gr.datamation.swift.common.SwiftMessage;
import gr.datamation.swift.processor.SwiftMsgProcessor;

import java.util.regex.Pattern;

public class TestUtils {

    public static SwiftMessage constructSwiftMessage(String mtMessage) throws InvalidMessageFormatException {
        mtMessage = mtMessage.replaceAll("\r\n", "\r");
        mtMessage = mtMessage.replaceAll("\n", "\r");
        mtMessage = mtMessage.replaceAll("\r", "\r\n");
        SwiftMsgProcessor smp = new SwiftMsgProcessor("\r\n");
        return smp.ParseMsgStringToObject(mtMessage);
    }

    public static String escapeSpecialRegexChars(String text) {
        Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|/]");

        return SPECIAL_REGEX_CHARS.matcher(text).replaceAll("\\\\$0");
    }

    public static String replaceLindEndings(String s) {
        return replaceLindEndings(s, System.lineSeparator());
    }

    public static String replaceLindEndings(String s, String lineSeparator) {
        return s.replaceAll("\r\n", "\n").replaceAll("\n", lineSeparator);
    }

}
