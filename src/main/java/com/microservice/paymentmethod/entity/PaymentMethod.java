package com.microservice.paymentmethod.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * entity class that have payment method
 * related attributes
 *
 * @author Asif Bakht
 * @since 2024
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_method")
@EqualsAndHashCode(callSuper = true)
public class PaymentMethod extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String customerId;
    private String paymentName;
    private String paymentType;

    String cardHolderName;
    String cardNumber;
    Integer expirationMonth;
    Integer expirationYear;
    String cvv;
    String cardType;

    private String accountNumber;
    private String routingNumber;
    private String accountHolderName;
    private String bankName;

}
