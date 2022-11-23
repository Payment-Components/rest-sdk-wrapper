package com.paymentcomponents.libraries.rest.sdk.wrapper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentcomponents.libraries.rest.sdk.wrapper.exception.InvalidMessageException;
import gr.datamation.converter.common.exceptions.InvalidMtMessageException;
import gr.datamation.converter.common.exceptions.InvalidMxMessageException;
import gr.datamation.converter.common.exceptions.StopTranslationException;
import gr.datamation.converter.common.exceptions.TranslationUnhandledException;
import gr.datamation.converter.common.utils.MtMessageValidationUtils;
import gr.datamation.converter.rtgs.RtgsTranslator;
import gr.datamation.converter.rtgs.utils.RtgsMessageValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class RtgsTranslatorService {

    public String translateMtToMx(String mtMessage) throws InvalidMessageException, JsonProcessingException {
        try {
            MtMessageValidationUtils.parseAndValidateMtMessage(mtMessage); //Input is not validated from sdk for target2, we validate it in order to prevent NullPointers
            String translatedMessage = RtgsTranslator.translateMtToMx(mtMessage); //throws InvalidMtMessageException
            RtgsMessageValidationUtils.autoParseAndValidateRtgsMessage(translatedMessage); //throws InvalidMxMessageException
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

    public String translateMxToMt(String mxMessage) throws InvalidMessageException, JsonProcessingException {
        try {
            return RtgsTranslator.translateMxToMt(mxMessage); //output should not validated for Target2
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

}
