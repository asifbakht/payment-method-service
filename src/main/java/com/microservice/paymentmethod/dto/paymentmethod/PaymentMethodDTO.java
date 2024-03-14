package com.microservice.paymentmethod.dto.paymentmethod;


import com.microservice.paymentmethod.helper.enumvalidator.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * class resides payment method payload properties
 *
 * @author Asif Bakht
 * @since 2024
 */
@Value
public class PaymentMethodDTO {

    String id;
    @NotBlank(message = "customerId is required")
    String customerId;
    @NotBlank(message = "paymentName is required")
    String paymentName;
    @EnumValidator(enumClazz = PayType.class, message = "Invalid payment type")
    String paymentType;
    String cardHolderName;
    String cardNumber;
    Integer expirationMonth;
    Integer expirationYear;
    String cvv;
    String cardType;
    String accountNumber;
    String routingNumber;
    String accountHolderName;
    String bankName;
}