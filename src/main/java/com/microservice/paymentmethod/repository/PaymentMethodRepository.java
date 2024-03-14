package com.microservice.paymentmethod.repository;

import com.microservice.paymentmethod.entity.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * payment method related query resides here
 *
 * @author Asif Bakht
 * @since 2024
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {

    @Query(value = """
            SELECT * FROM `payment_method` pm
            WHERE pm.customer_id = :customerId
            AND pm.card_number = :cardNumber
            AND pm.is_deleted = :isDeleted LIMIT 0,1"""
            , nativeQuery = true)
    Optional<PaymentMethod> findByCustomerIdAndCardNumber(@Param("customerId") final String customerId,
                                                          @Param("cardNumber") final String cardNumber,
                                                          @Param("isDeleted") final boolean isDeleted);

    @Query(value = """
            SELECT * FROM `payment_method` pm WHERE pm.customer_id = :customerId
            AND pm.account_number = :accountNumber 
            AND pm.is_deleted = :isDeleted LIMIT 0,1""",
            nativeQuery = true)
    Optional<PaymentMethod> findByCustomerIdAndAccountNumber(@Param("customerId") final String customerId,
                                                             @Param("accountNumber") final String accountNumber,
                                                             @Param("isDeleted") final boolean isDeleted);


    @Query(value = """
            SELECT * FROM `payment_method` pm
            WHERE pm.customer_id = :customerId
            AND pm.is_deleted = :isDeleted
            """,
            nativeQuery = true)
    Page<PaymentMethod> findAllByCustomerId(@Param("customerId") final String customerId,
                                            @Param("isDeleted") final boolean isDeleted,
                                            final Pageable pageable);

}
