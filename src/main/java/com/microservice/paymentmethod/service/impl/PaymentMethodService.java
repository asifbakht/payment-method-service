package com.microservice.paymentmethod.service.impl;

import com.microservice.paymentmethod.dto.paymentmethod.PaymentMethodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * payment method related business logic resides here
 *
 * @author Asif Bakht
 * @since 2024
 */

public interface PaymentMethodService {

    public PaymentMethodDTO add(final PaymentMethodDTO customerDTO);

    public PaymentMethodDTO update(final String id, final PaymentMethodDTO customerDTO);

    public PaymentMethodDTO get(final String id);

    public void delete(final String id);

    public Page<PaymentMethodDTO> getAll(final Pageable pageable, final String id);
}
