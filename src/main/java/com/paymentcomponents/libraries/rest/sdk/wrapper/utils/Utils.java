package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Utils {

    public static String convertXmlToJson(String xml, String additionalRootElement) {
        String json = "";
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xml.getBytes());
            json = node.toString();
            if (additionalRootElement != null && !additionalRootElement.isEmpty()) {
                json = "{ \"" + additionalRootElement + "\": " + json + " } ";
            }
            json = json.replaceAll("\"\":", "\"Value\":");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
