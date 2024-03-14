package com.microservice.paymentmethod.helper.cardvalidator;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Different type of card validator regex pattern resides here
 *
 * @author Asif Bakht
 * @since 2024
 */
@Getter
public enum CardValidatorMap {
    AMEX("^3[47][0-9]{13}$"),
    DISCOVER("^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$"),
    MASTERCARD("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$"),
    VISA("^4[0-9]{12}(?:[0-9]{3})?$"),
    DUMMY("^[0-9]{12}?$"),
    VISA_MASTER("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$");

    private final String regex;

    /**
     * Constructor with dependecy
     *
     * @param regex {@link String} regular expression
     */
    CardValidatorMap(String regex) {
        this.regex = regex;
    }

    /**
     * return regular expression based on card type provided
     *
     * @param cardType {@link String} type of card
     * @return {@link String} regular expression of provided card
     */
    public static String getCardRegex(final String cardType) {
        return Stream.of(CardValidatorMap.values())
                .filter(eachCardType -> eachCardType.name().equalsIgnoreCase(cardType))
                .findFirst()
                .map(CardValidatorMap::getRegex)
                .get();
    }

}
