package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.converter.cbpr.CbprTranslator;
import gr.datamation.converter.cbpr.utils.CbprMessageValidationUtils;
import gr.datamation.converter.common.exceptions.InvalidMtMessageException;
import gr.datamation.converter.common.exceptions.InvalidMxMessageException;
import gr.datamation.converter.common.exceptions.StopTranslationException;
import gr.datamation.converter.common.exceptions.TranslationUnhandledException;
import gr.datamation.converter.common.utils.MtMessageValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class CbprTranslatorService {

    public String translateMtToMx(String mtMessage) throws InvalidMessageException, JsonProcessingException {
        try {
            String translatedMessage = CbprTranslator.translateMtToMx(mtMessage); //throws InvalidMtMessageException
            CbprMessageValidationUtils.autoParseAndValidateCbprMessage(translatedMessage); //throws InvalidMxMessageException
            return translatedMessage;
        } catch (InvalidMtMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (InvalidMxMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (StopTranslationException e) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(e.getTranslationErrorList()));
        } catch (TranslationUnhandledException e) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(e.getMessage()));
        }
    }

    public String translateMxToMt(String mxMessage, String direction) throws InvalidMessageException, JsonProcessingException {
        try {
            String translatedMessage = CbprTranslator.translateMxToMt(mxMessage, direction); //throws InvalidMxMessageException
            MtMessageValidationUtils.parseAndValidateMtMessage(translatedMessage); //throws InvalidMtMessageException
            return translatedMessage;
        } catch (InvalidMxMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (InvalidMtMessageException ex) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ex.getValidationErrorList()));
        } catch (StopTranslationException e) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(e.getTranslationErrorList()));
        } catch (TranslationUnhandledException e) {
            throw new InvalidMessageException(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(e.getMessage()));
        }
    }

}
