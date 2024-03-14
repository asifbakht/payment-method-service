package com.microservice.paymentmethod.exception;

/**
 * not found exception when user try to access resource
 * that does not exists
 *
 * @author Asif Bakht
 * @since 2024
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor class with dependency
     *
     * @param message {@link String} exception message
     */
    public NotFoundException(String message) {
        super(message);
    }

}
