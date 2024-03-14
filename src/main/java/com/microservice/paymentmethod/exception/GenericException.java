package com.microservice.paymentmethod.exception;

/**
 * generic exception is used within application
 * when unknown exception occurred
 *
 * @author Asif Bakht
 * @since 2024
 */
public class GenericException extends RuntimeException {

    /**
     * Constructor class with dependency
     *
     * @param message {@link String} exception message
     */
    public GenericException(String message) {
        super(message);
    }

}
