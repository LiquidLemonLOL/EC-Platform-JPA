package se.lexicon.entity;

public enum OrderStatus {
    CREATED,
    PAID,
    SHIPPED,
    CANCELLED;

    public static OrderStatus fromString(String status) {
        if (status == null || status.isBlank()) {
            return CREATED;
        }
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Order Status: " + status);
        }
    }
}
