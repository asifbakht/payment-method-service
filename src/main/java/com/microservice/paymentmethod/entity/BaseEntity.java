package com.microservice.paymentmethod.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * base entity class properties
 *
 * @author Asif Bakht
 * @since 2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "date_created", updatable = false, nullable = false)
    private String dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private String dateUpdated;
}
