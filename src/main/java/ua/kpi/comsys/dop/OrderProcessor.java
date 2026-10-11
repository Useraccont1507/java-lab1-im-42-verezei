package ua.kpi.comsys.dop;

public class OrderProcessor {

    public String process(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        return switch (status) {
            case Pending pending -> """
                    Order Status: PENDING
                    Details: Order is currently pending processing.
                    """;
            case Paid(String paymentId, var amount) -> """
                    Order Status: PAID
                    Details: Payment ID: %s, Amount: $%s
                    """.formatted(paymentId, amount);
            case Shipped(String trackingCode, var dispatchDate) -> """
                    Order Status: SHIPPED
                    Details: Tracking Code: %s, Dispatch Date: %s
                    """.formatted(trackingCode, dispatchDate);
            case Cancelled(String reason) -> """
                    Order Status: CANCELLED
                    Details: Reason: %s
                    """.formatted(reason);
        };
    }
}
