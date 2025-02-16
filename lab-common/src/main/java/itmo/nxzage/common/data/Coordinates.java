package itmo.nxzage.common.data;

/**
 * Class of 2D coordinates with properties X and Y
 */
public class Coordinates {
    private static final Double MIN_X = -727d;
    private static final String X_NULL_MESSAGE;
    private static final String X_OUT_OF_RANGE_MESSAGE;
    private static final String Y_NULL_MESSAGE;

    private Double x; // more than -727, can't be null
    private Float y; // can't be null

    static {
        X_NULL_MESSAGE = "X can\'t be null.";
        X_OUT_OF_RANGE_MESSAGE = String.format("X must be more than %f", MIN_X);
        Y_NULL_MESSAGE = "Y can\'t be null.";
    }

    public Coordinates() {
        this.x = 0d;
        this.y = 0f;
    }

    public Coordinates(Double x, Float y) {
        if (x == null) {
            throw new IllegalArgumentException(X_NULL_MESSAGE);
        }
        if (x <= MIN_X) {
            throw new IllegalArgumentException(X_OUT_OF_RANGE_MESSAGE);
        }
        if (y == null) {
            throw new IllegalArgumentException(Y_NULL_MESSAGE);
        }

        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    public void setX(Double value) {
        if (x == null) {
            throw new IllegalArgumentException(X_NULL_MESSAGE);
        }
        if (x <= MIN_X) {
            throw new IllegalArgumentException(X_OUT_OF_RANGE_MESSAGE);
        }

        this.x = value;
    }

    public void setY(Float value) {
        if (y == null) {
            throw new IllegalArgumentException(Y_NULL_MESSAGE);
        }

        this.y = value;
    }

    @Override
    public String toString() {
        return String.format("Coordinates(%f, %f)", this.x, this.y);
    }
}
