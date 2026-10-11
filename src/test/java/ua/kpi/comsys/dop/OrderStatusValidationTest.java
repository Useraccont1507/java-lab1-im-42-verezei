package ua.kpi.comsys.dop;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderStatusValidationTest {

    private static final LocalDate DATE = LocalDate.of(2026, 10, 11);

    @Test
    void pendingCreatesWithoutArguments() {
        assertDoesNotThrow(() -> new Pending());
    }

    @Test
    void paidKeepsValues() {
        Paid paid = new Paid("PAY-1", new BigDecimal("10.50"));

        assertEquals("PAY-1", paid.paymentId());
        assertEquals(new BigDecimal("10.50"), paid.amount());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void paidRejectsBlankPaymentId(String paymentId) {
        assertThrows(IllegalArgumentException.class, () -> new Paid(paymentId, BigDecimal.TEN));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "0.00", "-1", "-0.01"})
    void paidRejectsNonPositiveAmount(String amount) {
        assertThrows(IllegalArgumentException.class, () -> new Paid("PAY-1", new BigDecimal(amount)));
    }

    @Test
    void paidRejectsNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Paid("PAY-1", null));
    }

    @Test
    void paidAcceptsSmallestPositiveAmount() {
        assertDoesNotThrow(() -> new Paid("PAY-1", new BigDecimal("0.01")));
    }

    @Test
    void shippedKeepsValues() {
        Shipped shipped = new Shipped("TRK-1", DATE);

        assertEquals("TRK-1", shipped.trackingCode());
        assertEquals(DATE, shipped.dispatchDate());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void shippedRejectsBlankTrackingCode(String trackingCode) {
        assertThrows(IllegalArgumentException.class, () -> new Shipped(trackingCode, DATE));
    }

    @Test
    void shippedRejectsNullDispatchDate() {
        assertThrows(IllegalArgumentException.class, () -> new Shipped("TRK-1", null));
    }

    @Test
    void cancelledKeepsReason() {
        assertEquals("Out of stock", new Cancelled("Out of stock").reason());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void cancelledRejectsBlankReason(String reason) {
        assertThrows(IllegalArgumentException.class, () -> new Cancelled(reason));
    }

    @Test
    void recordsWithSameValuesAreEqual() {
        assertEquals(new Paid("PAY-1", BigDecimal.ONE), new Paid("PAY-1", BigDecimal.ONE));
        assertEquals(new Shipped("TRK-1", DATE), new Shipped("TRK-1", DATE));
        assertEquals(new Cancelled("r"), new Cancelled("r"));
        assertEquals(new Pending(), new Pending());
    }
}
