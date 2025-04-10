package itmo.nxzage.common.data;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import itmo.nxzage.common.util.CSVSerializable;

/**
 * Class of location which contains name of location and its position in 3D space
 */
public final class Location implements CSVSerializable {
    private static final String DEFAULT_NAME = "noname";
    private static final Integer NAME_MAX_LENGTH = 210;
    private static final String X_NULL_MESSAGE;
    private static final String NAME_NULL_MESSAGE;
    private static final String NAME_LENGTH_MESSAGE;
    private static final String NAME_CONTAINS_CSV_DELIMETER_MESSAGE;
    private static final String NAME_CONTAINS_CSV_STRING_DELIMETER_MESSAGE;
    private static final String CSV_DESERIALIZATION_PATTERN;

    private Float x; // can't be null
    private Integer y;
    private Long z;
    private String name; // can't be null, can't be longer than 210

    static {
        X_NULL_MESSAGE = "x can\'t be null.";
        NAME_NULL_MESSAGE = "name can\'t be null.";
        NAME_LENGTH_MESSAGE = "name must be no longer than 210";
        NAME_CONTAINS_CSV_DELIMETER_MESSAGE =
                "name can\'t contain CSV delimeter";
        NAME_CONTAINS_CSV_STRING_DELIMETER_MESSAGE = 
                "name can\'t contain CSV string delimeter";
        String floatPattern = "\\d+(?:\\.\\d+)?";
        String intPattern = "\\d+";
        CSV_DESERIALIZATION_PATTERN =
                String.format("^Location\\((%s),(%s),(%s)\\)#(.+)$",
                        floatPattern, intPattern, intPattern);
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

    public static Location deserializeCSV(String code) throws ParseException {
        Pattern pattern = Pattern.compile(CSV_DESERIALIZATION_PATTERN);
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            Float x = new Float(matcher.group(1));
            Integer y = new Integer(matcher.group(2));
            Long z = new Long(matcher.group(3));
            String name = matcher.group(4);
            return new Location(x, y, z, name);
        } else throw new ParseException("[Location.deserializeCSV()]Argument doesn\'t match the pattern", 0);
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

        if (value.contains(CSVSerializable.DELIMETER)) {
            throw new IllegalArgumentException(
                    NAME_CONTAINS_CSV_DELIMETER_MESSAGE);
        }

        if (value.contains(CSVSerializable.STRING_DELIMETER)) {
            throw new IllegalArgumentException(
                    NAME_CONTAINS_CSV_STRING_DELIMETER_MESSAGE);
        }

        this.name = value;
    }

    @Override
    public String serializeCSV() {
        String pattern = "Location(%f,%d,%d)#%s";
        return String.format(pattern, x, y, z, name);
    }

    @Override
    public String toString() {
        String result = String.format("Location[\"%s\"](%f, %d, %d)", this.name,
                this.x, this.y, this.z);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Location location = (Location) other;
        return (this.x.equals(location.x) && this.y.equals(location.y)
                && this.z.equals(location.z));
    }

    @Override
    public int hashCode() {
        final int mod = 31;
        int hash = mod;
        hash = hash * mod + x.hashCode();
        hash = hash * mod + y.hashCode();
        hash = hash * mod + z.hashCode();
        return hash;
    }
}
