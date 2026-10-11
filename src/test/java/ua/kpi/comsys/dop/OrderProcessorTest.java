package ua.kpi.comsys.dop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void processPending() {
        String expected = """
                Order Status: PENDING
                Details: Order is currently pending processing.
                """;

        assertEquals(expected, processor.process(new Pending()));
    }

    @Test
    void processPaid() {
        String expected = """
                Order Status: PAID
                Details: Payment ID: PAY-123, Amount: $99.99
                """;

        assertEquals(expected, processor.process(new Paid("PAY-123", new BigDecimal("99.99"))));
    }

    @Test
    void processPaidKeepsAmountScale() {
        String expected = """
                Order Status: PAID
                Details: Payment ID: PAY-1, Amount: $10.00
                """;

        assertEquals(expected, processor.process(new Paid("PAY-1", new BigDecimal("10.00"))));
    }

    @Test
    void processShipped() {
        String expected = """
                Order Status: SHIPPED
                Details: Tracking Code: UA123456789, Dispatch Date: 2026-10-11
                """;

        Shipped shipped = new Shipped("UA123456789", LocalDate.of(2026, 10, 11));

        assertEquals(expected, processor.process(shipped));
    }

    @Test
    void processCancelled() {
        String expected = """
                Order Status: CANCELLED
                Details: Reason: Customer request
                """;

        assertEquals(expected, processor.process(new Cancelled("Customer request")));
    }

    @Test
    void processNullThrows() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));

        assertEquals("Status cannot be null", e.getMessage());
    }
}
