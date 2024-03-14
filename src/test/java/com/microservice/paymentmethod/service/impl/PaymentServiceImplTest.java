//package com.microservice.payment.service.impl;
//
//import com.microservice.payment.dto.payment.PaymentDTO;
//import com.microservice.payment.dto.payment.PaymentStatus;
//import com.microservice.payment.exception.NotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Optional;
//
//import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.greaterThan;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.AdditionalAnswers.returnsFirstArg;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.when;
//
///**
// * unit test cases for payment service class
// *
// * @author Asif Bakht
// * @since 2024
// */
//@ExtendWith(SpringExtension.class)
//public class PaymentServiceImplTest {
//
//    @Spy
//    @InjectMocks
//    private PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);
//    @Mock
//    private PaymentRepository paymentRepository;
//
//    @InjectMocks
//    private PaymentServiceImpl underTest;
//
//    @BeforeEach
//    public void setUp() {
//        ReflectionTestUtils.setField(underTest, "processInDays", 2);
//        ReflectionTestUtils.setField(underTest, "totalModification", 1);
//    }
//
//
//    @Test
//    void shouldAddPayment() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO(null,
//                "customerId",
//                320d,
//                "paymentId",
//                null,
//                null);
//
//        // mock repository
//        doAnswer(returnsFirstArg()).when(paymentRepository).save(any(Payment.class));
//
//        //execute actual service unit test
//        final PaymentDTO responseDTO = underTest.pay(requestPaymentDTO);
//
//        //verify
//        assertThat(responseDTO.getStatus(), equalTo(PaymentStatus.PENDING.name()));
//        assertThat(responseDTO.getProcessingTime(), greaterThan(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME)));
//
//    }
//
//    /**
//     * throw error with minimum amount setup
//     */
//    @Test
//    void shouldNotAddPayment() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO(null,
//                "customerId",
//                0.2d,
//                "paymentId",
//                null,
//                null);
//
//        //execute actual service unit test
//        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
//                () -> underTest.pay(requestPaymentDTO));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Minimum amount 0.5 is required"));
//    }
//
//
//    /**
//     * update payment
//     */
//    @Test
//    void shouldUpdatePayment() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO("id1",
//                "customerId1",
//                950d,
//                "paymentMethod2",
//                null,
//                null);
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PENDING.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//
//        //mock repository
//        when(paymentRepository.findById(requestPaymentDTO.getId())).thenReturn(Optional.of(oldPaymentEntity));
//        doAnswer(returnsFirstArg()).when(paymentRepository).save(any(Payment.class));
//
//        //execute actual service unit test
//        final PaymentDTO responseDTO = underTest.update("id1", requestPaymentDTO);
//
//        //verify
//        assertThat(responseDTO.getId(), equalTo("id1"));
//        assertThat(responseDTO.getCustomerId(), equalTo("customerId1"));
//        assertThat(responseDTO.getPaymentMethodId(), equalTo("paymentMethod2"));
//        assertThat(responseDTO.getStatus(), equalTo(PaymentStatus.PENDING.name()));
//        assertThat(responseDTO.getAmount(), equalTo(950d));
//    }
//
//
//    /**
//     * throw not found exception when fetch payment by id
//     */
//    @Test
//    void shouldFailUpdatePaymentAndThrowNotFoundException() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO(null,
//                "customerId",
//                0.2d,
//                "paymentId",
//                null,
//                null);
//
//        //mock
//        when(paymentRepository.findById(requestPaymentDTO.getId())).thenReturn(Optional.empty());
//
//        //execute actual service unit test
//        final NotFoundException thrown = assertThrows(NotFoundException.class,
//                () -> underTest.update("id1", requestPaymentDTO));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Payment not found"));
//    }
//
//
//    /**
//     * throw illegal argument exception when update payment lower than minimum amount threshold
//     */
//    @Test
//    void shouldFailUpdatePaymentAndThrowExceptionWithLowAmount() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO("id1",
//                "customerId",
//                0.2d,
//                "paymentId",
//                null,
//                null);
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PENDING.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//
//        //mock repository
//        when(paymentRepository.findById(requestPaymentDTO.getId())).thenReturn(Optional.of(oldPaymentEntity));
//
//        //execute actual service unit test
//        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
//                () -> underTest.update("id1", requestPaymentDTO));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Minimum amount 0.5 is required"));
//    }
//
//    /**
//     * throw illegal argument exception when update payment maximum no of
//     * record modification exhausted
//     */
//    @Test
//    void shouldFailUpdatePaymentAndThrowExceptionWithMaxModification() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO("id1",
//                "customerId",
//                95d,
//                "paymentId",
//                null,
//                null);
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PENDING.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(4)
//                .build();
//
//        //mock repository
//        when(paymentRepository.findById(requestPaymentDTO.getId())).thenReturn(Optional.of(oldPaymentEntity));
//
//        //execute actual service unit test
//        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
//                () -> underTest.update("id1", requestPaymentDTO));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Payment modification exhausted"));
//    }
//
//
//    /**
//     * throw illegal argument exception when update payment that
//     * is not in pending state
//     */
//    @Test
//    void shouldFailUpdatePaymentAndThrowExceptionIfNotPending() {
//        // create payload
//        final PaymentDTO requestPaymentDTO = new PaymentDTO("id1",
//                "customerId",
//                95d,
//                "paymentId",
//                null,
//                null);
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PROCESSED.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//
//        //mock repository
//        when(paymentRepository.findById(requestPaymentDTO.getId())).thenReturn(Optional.ofNullable(oldPaymentEntity));
//
//        //execute actual service unit test
//        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
//                () -> underTest.update("id1", requestPaymentDTO));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Payment cannot be updated"));
//    }
//
//
//    /**
//     * should successfully cancel payment
//     */
//    @Test
//    void shouldCancelPayment() {
//        // create payload
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PENDING.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//
//        //mock
//        when(paymentRepository.findById(oldPaymentEntity.getId())).thenReturn(Optional.of(oldPaymentEntity));
//        doAnswer(returnsFirstArg()).when(paymentRepository).save(any(Payment.class));
//
//        //execute actual service unit test
//        final PaymentDTO responseDTO = underTest.cancel(oldPaymentEntity.getId());
//
//        //verify
//        assertThat(responseDTO.getId(), equalTo("id1"));
//        assertThat(responseDTO.getCustomerId(), equalTo("customerId1"));
//        assertThat(responseDTO.getPaymentMethodId(), equalTo("paymentMethod1"));
//        assertThat(responseDTO.getStatus(), equalTo(PaymentStatus.CANCELLED.name()));
//        assertThat(responseDTO.getAmount(), equalTo(320d));
//    }
//
//    /**
//     * When cancelling payment by ID, the system should return
//     * empty and throws a "not found" exception
//     */
//    @Test
//    void shouldFailCancelPaymentAndThrowNotFoundException() {
//
//        //mock
//        when(paymentRepository.findById("id1")).thenReturn(Optional.empty());
//
//        //execute actual service unit test
//        final NotFoundException thrown = assertThrows(NotFoundException.class,
//                () -> underTest.cancel("id1"));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Payment not found"));
//    }
//
//
//    /**
//     * The cancellation of payment should fail and return an exception
//     * when the payment status is not pending.
//     */
//    @Test
//    void shouldFailCancelPaymentAndThrowIfPaymentNotPending() {
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PROCESSED.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//
//        //mock
//        when(paymentRepository.findById(oldPaymentEntity.getId())).thenReturn(Optional.of(oldPaymentEntity));
//
//        //execute actual service unit test
//        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
//                () -> underTest.cancel(oldPaymentEntity.getId()));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Payment cannot be updated"));
//    }
//
//
//    /**
//     * When fetch payment by ID, the system should return
//     * payment record
//     */
//    @Test
//    void shouldGetPayment() {
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PROCESSED.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//
//        //mock
//        when(paymentRepository.findById(oldPaymentEntity.getId())).thenReturn(Optional.of(oldPaymentEntity));
//
//        //execute actual service unit test
//        final PaymentDTO responseDTO = underTest.get(oldPaymentEntity.getId());
//
//        //verify
//        assertThat(responseDTO.getId(), equalTo("id1"));
//        assertThat(responseDTO.getCustomerId(), equalTo("customerId1"));
//        assertThat(responseDTO.getPaymentMethodId(), equalTo("paymentMethod1"));
//        assertThat(responseDTO.getStatus(), equalTo(PaymentStatus.PROCESSED.name()));
//        assertThat(responseDTO.getAmount(), equalTo(320d));
//    }
//
//
//    /**
//     * When fetching payment by ID, the system should return
//     * empty and throws a "not found" exception
//     */
//    @Test
//    void shouldFailGetPaymentAndThrowNotFoundException() {
//
//        //mock
//        when(paymentRepository.findById("id1")).thenReturn(Optional.empty());
//
//        //execute actual service unit test
//        final NotFoundException thrown = assertThrows(NotFoundException.class,
//                () -> underTest.get("id1"));
//        //verify
//        assertThat(thrown.getMessage(), equalTo("Payment not found"));
//    }
//
//    /**
//     * When fetching payment by customerId, the system should return
//     * paginated properties with payments list
//     */
//    @Test
//    void shouldGetPaymentByCustomerId() {
//
//        final Payment oldPaymentEntity = Payment.builder()
//                .id("id1")
//                .customerId("customerId1")
//                .paymentMethodId("paymentMethod1")
//                .amount(320d)
//                .status(PaymentStatus.PROCESSED.name())
//                .processingTime(LocalDateTime.now().format(ISO_LOCAL_DATE_TIME))
//                .version(0)
//                .build();
//        final Page<Payment> pagePayments = new PageImpl<>(Collections.singletonList(oldPaymentEntity));
//
//        final Pageable pageable = PageRequest.of(0, 5);
//
//        //mock
//        when(paymentRepository
//                .findAllByCustomerId(eq(oldPaymentEntity.getCustomerId()), eq(pageable)))
//                .thenReturn(pagePayments);
//
//        //execute actual service unit test
//        final Page<PaymentDTO> responsePageDTO = underTest
//                .getAllPayments(
//                        pageable,
//                        oldPaymentEntity.getCustomerId()
//                );
//
//        final PaymentDTO paymentDTO = responsePageDTO.getContent().get(0);
//        //verify
//        assertThat(paymentDTO.getId(), equalTo("id1"));
//        assertThat(paymentDTO.getCustomerId(), equalTo("customerId1"));
//        assertThat(paymentDTO.getPaymentMethodId(), equalTo("paymentMethod1"));
//        assertThat(paymentDTO.getStatus(), equalTo(PaymentStatus.PROCESSED.name()));
//        assertThat(paymentDTO.getAmount(), equalTo(320d));
//
//    }
//
//}
