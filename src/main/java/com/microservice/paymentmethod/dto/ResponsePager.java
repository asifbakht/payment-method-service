package com.microservice.paymentmethod.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * class that resides response with pagination properties
 *
 * @author Asif Bakht
 * @since 2024
 */
public record ResponsePager<T>(
        @Schema(description = "Content") List<T> content,
        @Schema(description = "currentPage") int currentPage,
        @Schema(description = "totalRecords") long totalRecords,
        @Schema(description = "totalRecords") int totalPages) {
}