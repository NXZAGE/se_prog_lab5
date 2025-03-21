package itmo.nxzage.common.data;

/**
 * Class of 2D coordinates with properties X and Y
 */
public final class Coordinates {
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
        this.setX(x);
        this.setY(y);
    }

    public Double getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    public void setX(Double value) {
        if (value == null) {
            throw new IllegalArgumentException(X_NULL_MESSAGE);
        }
        if (value <= MIN_X) {
            throw new IllegalArgumentException(X_OUT_OF_RANGE_MESSAGE);
        }

        this.x = value;
    }

    public void setY(Float value) {
        if (value == null) {
            throw new IllegalArgumentException(Y_NULL_MESSAGE);
        }

        this.y = value;
    }

    @Override
    public String toString() {
        String result = String.format("Coordinates(%f, %f)", this.x, this.y);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Coordinates coordinates = (Coordinates) other;
        return (this.x.equals(coordinates.x) && this.y.equals(coordinates.y));
    }

    @Override
    public int hashCode() {
        final int mod = 31;
        int hash = mod;
        hash = hash * mod + x.hashCode();
        hash = hash * mod + y.hashCode();
        return hash;
    }
}
