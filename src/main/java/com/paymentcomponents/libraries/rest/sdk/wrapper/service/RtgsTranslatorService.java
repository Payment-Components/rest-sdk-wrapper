package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.translator.common.exceptions.InvalidMtMessageException;
import gr.datamation.translator.common.exceptions.InvalidMxMessageException;
import gr.datamation.translator.rtgs.RtgsTranslator;
import gr.datamation.translator.rtgs.utils.RtgsMessageValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class RtgsTranslatorService {

    public String translateMtToMx(String mtMessage) throws InvalidMessageException, JsonProcessingException {
        try {
            String translatedMessage = RtgsTranslator.translateMtToMx(mtMessage); //throws InvalidMtMessageException
            RtgsMessageValidationUtils.autoParseAndValidateRtgsMessage(translatedMessage); //throws InvalidMxMessageException
            return translatedMessage;
        } catch (InvalidMtMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (InvalidMxMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (Exception ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("Message could not be translated"));
        }
    }

    public String translateMxToMt(String mxMessage) throws InvalidMessageException, JsonProcessingException {
        try {
            return RtgsTranslator.translateMxToMt(mxMessage); //output should not validated for now
        } catch (InvalidMxMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (InvalidMtMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (Exception ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString("Message could not be translated"));
        }
    }

}
