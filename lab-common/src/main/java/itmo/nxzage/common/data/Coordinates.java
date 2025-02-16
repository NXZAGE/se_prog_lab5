package itmo.nxzage.common.data;

public class Coordinates {
    private static final Double MIN_X = -727d;
    private static final Double MAX_X = Double.POSITIVE_INFINITY;
    private static final Double MIN_Y = Double.NEGATIVE_INFINITY;
    private static final Double MAX_Y = Double.POSITIVE_INFINITY;
    private static final String X_NULL_MESSAGE;
    private static final String X_OUT_OF_RANGE_MESSAGE;
    private static final String Y_NULL_MESSAGE;
    private static final String Y_OUT_OF_RANGE_MESSAGE;

    private Double x; // Значение поля должно быть больше -727, Поле не может быть null
    private Double y; // Поле не может быть null

    static {
        X_NULL_MESSAGE = "X can\'t be null.";
        X_OUT_OF_RANGE_MESSAGE =
                String.format("X must be within (%f.2, %f.2).", MIN_X, MAX_X);
        Y_NULL_MESSAGE = "Y can\'t be null.";
        Y_OUT_OF_RANGE_MESSAGE =
                String.format("Y must be within (%f.2, %f.2).", MIN_Y, MAX_Y);
    }

    public Coordinates() {
        this.x = 0d;
        this.y = 0d;
    }

    public Coordinates(Double x, Double y) {
        if (x == null) {
            throw new IllegalArgumentException(X_NULL_MESSAGE);
        }
        if (x >= MAX_X || x <= MIN_X) {
            throw new IllegalArgumentException(X_OUT_OF_RANGE_MESSAGE);
        }
        if (y == null) {
            throw new IllegalArgumentException(Y_NULL_MESSAGE);
        }
        if (y >= MAX_X || x <= MIN_X) {
            throw new IllegalArgumentException(Y_OUT_OF_RANGE_MESSAGE);
        }
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public void setX(Double value) {
        if (x == null) {
            throw new IllegalArgumentException(X_NULL_MESSAGE);
        }
        if (x >= MAX_X || x <= MIN_X) {
            throw new IllegalArgumentException(X_OUT_OF_RANGE_MESSAGE);
        }

        this.x = value;
    }

    public void setY(Double value) {
        if (y == null) {
            throw new IllegalArgumentException(Y_NULL_MESSAGE);
        }
        if (y >= MAX_X || x <= MIN_X) {
            throw new IllegalArgumentException(Y_OUT_OF_RANGE_MESSAGE);
        }

        this.y = value;
    }

    @Override
    public String toString() {
        return String.format("Coordinates(%f.2, %f.2)", this.x, this.y);
    }
}
