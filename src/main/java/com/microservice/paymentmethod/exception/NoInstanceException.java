package com.microservice.paymentmethod.exception;

/**
 * Exception that will be used across application whose
 * class object instantiate is restricted
 *
 * @author Asif Bakht
 * @since 2024
 */
public class NoInstanceException extends RuntimeException {

    /**
     * Constructor class with dependency
     *
     * @param message {@link String} exception message
     */
    public NoInstanceException(final String message) {
        super(message);
    }
}
