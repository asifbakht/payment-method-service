package com.microservice.paymentmethod.mapper;

import com.microservice.paymentmethod.dto.paymentmethod.PaymentMethodDTO;
import com.microservice.paymentmethod.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;

/**
 * payment method mapper class to avoid boilerplate code
 * this class transform dto to entity and vice versa
 *
 * @author Asif Bakht
 * @since 2024
 */
@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    /**
     * converts dto to payment method entity class
     *
     * @param paymentMethodDTO {@link PaymentMethodDTO} payment method object
     * @return {@link PaymentMethod} payment method entity
     */
    PaymentMethod toEntity(final PaymentMethodDTO paymentMethodDTO);


    /**
     * converts payment method entity to dto class, this will ignore
     * cvv to be populated in dto class and also mask account number
     * and card number
     *
     * @param paymentMethod {@link PaymentMethod} payment method entity
     * @return {@link PaymentMethodDTO} payment method dto class
     */
    @Mapping(target = "cvv", ignore = true)
    @Mapping(target = "accountNumber", qualifiedByName = "maskAccountNumber")
    @Mapping(target = "cardNumber", qualifiedByName = "maskCardNumber")
    PaymentMethodDTO toDTO(final PaymentMethod paymentMethod);


    /**
     * function that mask account number
     *
     * @param accountNumber {@link String} account number
     * @return {@link String} masked account number
     */
    @Named("maskAccountNumber")
    default String mastAccountNumber(final String accountNumber) {
        if (Objects.nonNull(accountNumber))
            return accountNumber.replaceAll(".(?=.{4})", "#");
        return accountNumber;
    }

    /**
     * function that mask card number
     *
     * @param cardNumber {@link String} original card number
     * @return {@link String} masked card number
     */
    @Named("maskCardNumber")
    default String maskCardNumber(final String cardNumber) {
        if (Objects.nonNull(cardNumber))
            return cardNumber.replaceAll(".(?=.{4})", "#");
        return cardNumber;
    }
}
