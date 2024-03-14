package com.microservice.paymentmethod.controller;

import com.microservice.paymentmethod.dto.Response;
import com.microservice.paymentmethod.dto.ResponsePager;
import com.microservice.paymentmethod.dto.paymentmethod.PaymentMethodDTO;
import com.microservice.paymentmethod.exception.DuplicateException;
import com.microservice.paymentmethod.exception.GenericException;
import com.microservice.paymentmethod.exception.NotFoundException;
import com.microservice.paymentmethod.service.impl.PaymentMethodService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.microservice.paymentmethod.utils.Constants.CACHE_PAYMENT_METHOD;
import static com.microservice.paymentmethod.utils.Constants.PAYMENT_METHOD_SERVICE;
import static com.microservice.paymentmethod.utils.Constants.REQUIRE_ID;
import static com.microservice.paymentmethod.utils.Constants.SUCCESS_DELETE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * apis related to payment
 *
 * @author Asif Bakht
 * @since 2024
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "payment-method", produces = APPLICATION_JSON_VALUE)
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;


    /**
     * add payment method to database
     *
     * @return {@link PaymentMethodDTO}
     */
    @Operation(summary = "add payment method")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            examples = {
                                    @ExampleObject(name = "addPaymentMethod",
                                            summary = "Adding payment method and returns error if customerId is not present",
                                            value = """
                                                        {
                                                            "statusCode": 400,
                                                            "content": "customerId is required"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "409",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(name = "addPaymentMethod",
                                            summary = "Adding payment method that already exists returns conflict error",
                                            value = """
                                                        {
                                                            "statusCode": 409,
                                                            "content": "Payment with same card number already exists"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(name = "addPaymentMethod",
                                            summary = "When adding payment method and occurs unexpected error",
                                            value = """
                                                        {
                                                            "statusCode": 500,
                                                            "content": "Unknown error occurred please try again later"
                                                        }
                                                    """
                                    )
                            }))
    })
    @PostMapping
    @CircuitBreaker(name = PAYMENT_METHOD_SERVICE, fallbackMethod = "serviceUnavailable")
    public ResponseEntity<Response<?>> addPaymentMethod(@Valid @RequestBody final PaymentMethodDTO paymentmethodDTO) {
        try {
            log.info("Add payment method api initiated");
            final PaymentMethodDTO responseDTO = paymentMethodService.add(paymentmethodDTO);
            log.info("Add payment method api completed");
            return ResponseEntity
                    .status(CREATED)
                    .body(new Response<>(responseDTO, CREATED.value()));
        } catch (final GenericException | IllegalArgumentException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new Response<>(e.getMessage(), BAD_REQUEST.value()));
        } catch (final DuplicateException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new Response<>(e.getMessage(), CONFLICT.value()));
        } catch (final NotFoundException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new Response<>(e.getMessage(), NOT_FOUND.value()));
        }
    }

    /**
     * @return {@link PaymentMethodDTO} response with status
     */
    @Operation(summary = "update payment method")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "updatePaymentMethod",
                                            summary = "Updating payment method and returns error if customerId is not present",
                                            value = """
                                                        {
                                                            "statusCode": 400,
                                                            "content": "customerId is required"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(
                                            name = "updatePaymentMethod",
                                            summary = "Updating payment method that does not exists",
                                            value = """
                                                        {
                                                            "statusCode": 404,
                                                            "content": "Payment not found"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "409",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(
                                            name = "updatePaymentMethod",
                                            summary = "Updating payment method that already exists returns conflict error",
                                            value = """
                                                        {
                                                            "statusCode": 409,
                                                            "content": "Payment with same card number already exists"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(name = "updatePaymentMethod",
                                            summary = "When updating payment method and occurs unexpected error",
                                            value = """
                                                        {
                                                            "statusCode": 500,
                                                            "content": "Unknown error occurred please try again later"
                                                        }
                                                    """
                                    )
                            }))
    })
    @CachePut(value = CACHE_PAYMENT_METHOD, key = "#id")
    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updatePaymentMethod(
            @PathVariable("id") final String id,
            @Valid @RequestBody final PaymentMethodDTO paymentMethodDTO) {

        try {
            log.info("Update payment api initiated: {}", id);
            Objects.requireNonNull(id, REQUIRE_ID);
            final PaymentMethodDTO responseDTO = paymentMethodService.update(id, paymentMethodDTO);
            log.info("Update payment api completed: {}", id);
            return ResponseEntity
                    .status(OK)
                    .body(new Response<>(responseDTO, OK.value()));
        } catch (final GenericException | IllegalArgumentException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new Response<>(e.getMessage(), BAD_REQUEST.value()));
        } catch (final DuplicateException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new Response<>(e.getMessage(), CONFLICT.value()));
        } catch (final NotFoundException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new Response<>(e.getMessage(), NOT_FOUND.value()));
        }
    }

    /**
     * @return {@link PaymentMethodDTO} response with status
     */
    @Operation(summary = "get payment method by providing id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "getPaymentMethod",
                                            summary = "Get payment method and returns error if id is not provided",
                                            value = """
                                                        {
                                                            "statusCode": 400,
                                                            "content": "id is required"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(
                                            name = "getPaymentMethod",
                                            summary = "Get payment method that does not exists",
                                            value = """
                                                        {
                                                            "statusCode": 404,
                                                            "content": "Payment not found"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(name = "getPaymentMethod",
                                            summary = "When updating payment method and occurs unexpected error",
                                            value = """
                                                        {
                                                            "statusCode": 500,
                                                            "content": "Unknown error occurred please try again later"
                                                        }
                                                    """
                                    )
                            }))
    })
    @GetMapping("/{id}")
    @CachePut(value = CACHE_PAYMENT_METHOD, key = "#id")
    public ResponseEntity<Response<?>> getPaymentMethod(@PathVariable("id") final String id) {
        try {
            log.info("Get payment method initiated: {}", id);
            Objects.requireNonNull(id, REQUIRE_ID);
            final PaymentMethodDTO responseDTO = paymentMethodService.get(id);
            log.info("Get payment method completed: {}", id);
            return ResponseEntity
                    .status(OK)
                    .body(new Response<>(responseDTO, OK.value()));
        } catch (final GenericException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new Response<>(e.getMessage(), BAD_REQUEST.value()));
        } catch (final NotFoundException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new Response<>(e.getMessage(), NOT_FOUND.value()));
        }
    }

    /**
     * @return {@link PaymentMethodDTO} response with status
     */
    @Operation(summary = "delete payment method information")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "deletePaymentMethod",
                                            summary = "Delete payment method and returns success message",
                                            value = """
                                                        {
                                                            "statusCode": 200,
                                                            "content": "Payment method deleted"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "deletePaymentMethod",
                                            summary = "Get payment method and returns error if id is not provided",
                                            value = """
                                                        {
                                                            "statusCode": 400,
                                                            "content": "id is required"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(
                                            name = "deletePaymentMethod",
                                            summary = "Get payment method that does not exists",
                                            value = """
                                                        {
                                                            "statusCode": 404,
                                                            "content": "Payment not found"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(name = "deletePaymentMethod",
                                            summary = "When updating payment method and occurs unexpected error",
                                            value = """
                                                        {
                                                            "statusCode": 500,
                                                            "content": "Unknown error occurred please try again later"
                                                        }
                                                    """
                                    )
                            }))
    })
    @DeleteMapping("/{id}")
    @CacheEvict(value = CACHE_PAYMENT_METHOD, key = "#id", allEntries = true)
    public ResponseEntity<Response<String>> deletePaymentMethod(@PathVariable("id") final String id) {
        try {
            log.info("Delete payment method api initiated: {}", id);
            Objects.requireNonNull(id, REQUIRE_ID);
            paymentMethodService.delete(id);
            log.info("Delete payment method api completed: {}", id);
            return ResponseEntity
                    .status(OK)
                    .body(new Response<>(SUCCESS_DELETE, OK.value()));
        } catch (final GenericException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new Response<>(e.getMessage(), BAD_REQUEST.value()));
        } catch (final NotFoundException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new Response<>(e.getMessage(), NOT_FOUND.value()));
        }
    }

    @Operation(summary = "Returns a paginated list of payment method")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "getCustomerPayments",
                                            summary = "Returns a payment method lists with pagination",
                                            value = """
                                                        {
                                                            "statusCode": 200,
                                                            "content": ["{payment method object}",
                                                                         "{payment method object}"],
                                                            "currentPage": 1,
                                                            "totalRecords": 5,
                                                            "totalPage": 10
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "getCustomerPayments",
                                            summary = "Get customers method and returns error if customer id is not provided",
                                            value = """
                                                        {
                                                            "statusCode": 400,
                                                            "content": "id is required"
                                                        }
                                                    """
                                    )
                            })),
            @ApiResponse(responseCode = "500",
                    content = @Content(
                            schema = @Schema(implementation = Response.class),
                            examples = {
                                    @ExampleObject(name = "getCustomerPayments",
                                            summary = "When updating payment method and occurs unexpected error",
                                            value = """
                                                        {
                                                            "statusCode": 500,
                                                            "content": "Unknown error occurred please try again later"
                                                        }
                                                    """
                                    )
                            }))
    })
    @GetMapping("/customer/{customerId}")
    @Cacheable(value = CACHE_PAYMENT_METHOD, key = "#customerId")
    public ResponseEntity<?> getCustomerPayments(final Pageable pageRequest,
                                                 @PathVariable("customerId") final String customerId) {
        try {
            log.info("Search customer payment method api initiated: {}", customerId);
            Objects.requireNonNull(customerId, REQUIRE_ID);
            final Page<PaymentMethodDTO> pageCustomers = paymentMethodService.getAll(pageRequest, customerId);
            final ResponsePager<?> responsePage = new ResponsePager<>(pageCustomers.getContent(),
                    pageCustomers.getNumber(),
                    pageCustomers.getTotalElements(),
                    pageCustomers.getTotalPages()
            );
            log.info("Search customer payment method completed");
            return ResponseEntity
                    .status(OK)
                    .body(responsePage);
        } catch (final GenericException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new Response<>(e.getMessage(), BAD_REQUEST.value()));
        } catch (final NotFoundException e) {
            log.error("Error occurred: {}", e.getMessage());
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new Response<>(e.getMessage(), NOT_FOUND.value()));
        }
    }
}
