package com.paymentcomponents.libraries.rest.sdk.wrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public static String convertXmlToJson(String xml, String additionalRootElement) {
        String json = "";
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new SimpleModule().addDeserializer(
                    JsonNode.class,
                    new DuplicateToArrayJsonNodeDeserializer()
            ));
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

    public static XMLGregorianCalendar convertDateToXmlGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

    public static Calendar convertDateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    public static String createTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

}
