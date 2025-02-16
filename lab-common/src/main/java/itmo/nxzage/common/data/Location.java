package itmo.nxzage.common.data;

/**
 * Class of location which contains name of location and its position in 3D space
 */
public final class Location {
    private static final String DEFAULT_NAME = "noname";
    private static final int NAME_MAX_LENGTH = 210;
    private static final String X_NULL_MESSAGE;
    private static final String NAME_NULL_MESSAGE;
    private static final String NAME_LENGTH_MESSAGE;

    private Float x; // can't be null
    private Integer y;
    private Long z;
    private String name; // can't be null, can't be longer than 210

    static {
        X_NULL_MESSAGE = "x can\'t be null.";
        NAME_NULL_MESSAGE = "name can\'t be null.";
        NAME_LENGTH_MESSAGE = "name must be no longer than 210";
    }

    public Location() {
        this.x = 0f;
        this.y = 0;
        this.z = 0L;
        this.name = DEFAULT_NAME;
    }

    public Location(Float x, Integer y, Long z, String name) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setName(name);
    }

    public Float getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Long getZ() {
        return this.z;
    }

    public String getName() {
        return this.name;
    }

    public void setX(Float value) {
        if (value == null) {
            throw new IllegalArgumentException(X_NULL_MESSAGE);
        }
        this.x = value;
    }

    public void setY(Integer value) {
        this.y = value;
    }

    public void setZ(Long value) {
        this.z = value;
    }

    public void setName(String value) {
        if (value == null) {
            throw new IllegalArgumentException(NAME_NULL_MESSAGE);
        }
        if (value.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(NAME_LENGTH_MESSAGE);
        }

        this.name = value;
    }

    @Override
    public String toString() {
        String result = String.format("Location[\"%s\"](%f, %d, %d)", this.name,
                this.x, this.y, this.z);
        return result;
    }
}
