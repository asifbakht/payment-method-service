package com.microservice.paymentmethod.exception;

/**
 * duplicate exception is used when same resources
 * are trying to create
 *
 * @author Asif Bakht
 * @since 2024
 */
public class DuplicateException extends RuntimeException {

    /**
     * Constructor class with dependency
     *
     * @param message {@link String} exception message
     */
    public DuplicateException(String message) {
        super(message);
    }

}
