package Model;

public enum Status {
    DECLINED(-1),
    PENDING(0),
    ACCEPTED(1),
    MOVED(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static Status getByValue(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid BillStatus value: " + value);
    }
}
