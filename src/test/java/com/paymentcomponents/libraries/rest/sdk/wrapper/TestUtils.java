package com.paymentcomponents.libraries.rest.sdk.wrapper;

import gr.datamation.swift.common.InvalidMessageFormatException;
import gr.datamation.swift.common.SwiftMessage;
import gr.datamation.swift.processor.SwiftMsgProcessor;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<String> returnResourcePathFilesContentAsList(String path) throws URISyntaxException, IOException {
        List<String> filesContent = new ArrayList<>();

        URL url = TestUtils.class.getResource(path);
        Path dirPath = Paths.get(url.toURI());
        Stream<Path> paths = Files.list(dirPath);
        List<File> files = paths
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File file : files) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                String sampleMessage = convertInputStreamToString(inputStream);
                filesContent.add(sampleMessage.trim());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return filesContent;
    }

    public static String convertInputStreamToString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }


}
