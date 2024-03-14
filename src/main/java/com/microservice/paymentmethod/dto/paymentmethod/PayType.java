package com.microservice.paymentmethod.dto.paymentmethod;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * class that provides different type of payment types
 *
 * @author Asif Bakht
 * @since 2024
 */
@Getter
public enum PayType {

    CARD("CARD"),
    BANK_ACCOUNT("BANK_ACCOUNT");

    final String payType;

    private PayType(final String payType) {
        this.payType = payType;
    }

    public static PayType paymentType(final String cardType) {
        return Stream.of(PayType.values())
                .filter(eachCardType -> eachCardType.name().equalsIgnoreCase(cardType))
                .findFirst()
                .get();
    }

}
