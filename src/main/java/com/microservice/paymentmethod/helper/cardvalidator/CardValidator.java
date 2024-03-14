package com.microservice.paymentmethod.helper.cardvalidator;

import com.microservice.paymentmethod.dto.paymentmethod.PaymentMethodDTO;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microservice.paymentmethod.utils.Constants.CARD_EXPIRY_INVALID;
import static com.microservice.paymentmethod.utils.Constants.ZONE_UTC;

/**
 * Card information validator function resides here
 *
 * @author Asif Bakht
 * @since 2024
 */
@Log4j2
public class CardValidator {

    /**
     * validates card expiry
     *
     * @param paymentMethodDTO {@link PaymentMethodDTO} payment method payload
     * @return {@link Boolean} indicates card is expired or not
     */

    public static boolean isExpired(final PaymentMethodDTO paymentMethodDTO) {
        log.info("Validating card expiry");
        Objects.requireNonNull(paymentMethodDTO.getExpirationMonth(), CARD_EXPIRY_INVALID);
        Objects.requireNonNull(paymentMethodDTO.getExpirationYear(), CARD_EXPIRY_INVALID);
        final LocalDate cardExpiryDate = LocalDate
                .of(paymentMethodDTO.getExpirationYear(),
                        paymentMethodDTO.getExpirationMonth(),
                        1);
        final boolean isExpired = cardExpiryDate.isBefore(LocalDate.now(ZoneId.of(ZONE_UTC)));
        log.info("Card is expired: {}", !isExpired);
        return isExpired;
    }

    /**
     * validates card number using different card map
     *
     * @param paymentMethodDTO {@link PaymentMethodDTO} payment method payload
     * @return {@link Boolean} indicates card number is valid or not
     */
    public static boolean isInvalid(final PaymentMethodDTO paymentMethodDTO) {
        log.info("Validating card number");
        final String cardRegex = CardValidatorMap.getCardRegex(paymentMethodDTO.getCardType());
        final Pattern cardPattern = Pattern.compile(cardRegex);
        final Matcher cardMatcher = cardPattern.matcher(paymentMethodDTO.getCardNumber());
        log.info("Card number valid: {}", cardMatcher.matches());
        return !cardMatcher.matches();
    }
}
