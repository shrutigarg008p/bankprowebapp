package com.bankpro.app.dto.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * Custom exception thrown when it was not possible to deserialize a time field,
 * @see com.bankpro.app.dto.serialization.CustomTimeDeserializer
 *
 */
public class TimeDeserializationException extends JsonProcessingException {

    protected TimeDeserializationException(Throwable rootCause) {
        super(rootCause);
    }

}
