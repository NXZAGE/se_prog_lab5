package itmo.nxzage.common.data;

import java.util.Date;

/**
 * Person data class (main data container)
 */
public final class Person {
    private static Integer nextID = 1;

    private static final Float MIN_HEIGHT = 1f;
    private static final Long MIN_WEIGHT = 1L;
    private static final Integer PASSPORT_ID_MIN_LENGTH = 5;
    private static final Integer PASSPORT_ID_MAX_LENGHT = 24;

    private Integer id; // can't be null, unique, autogenerate, >0
    private Date creationDate; // can be null, autogenerate
    private String name; // can't be null, can't be blank
    private Coordinates coordinates; // can't be null
    private Float height; // can't be null, >0
    private Long weight; // >0
    private String passportID; // 5 <= lenght <= 24, can be null
    private Country nationality; // can't be null
    private Location location; // can't be null

    public Person(String name, Coordinates coordinates, Float height,
            Long weight, String passportID, Country nationality,
            Location location) {
        this.id = Person.nextID;
        this.creationDate = new Date();
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setHeight(height);
        this.setWeight(weight);
        this.setPassportID(passportID);
        this.setNationality(nationality);
        this.setLocation(location);
        this.increaseID();
    }

    private void increaseID() {
        ++nextID;
    }

    public void setName(String value) {
        if (value == null) {
            String message = "Name can\'t be null";
            throw new IllegalArgumentException(message);
        }
        if (value.length() == 0) {
            String message = "Name can\'t be blank";
            throw new IllegalArgumentException(message);
        }

        this.name = value;
    }

    public void setCoordinates(Coordinates value) {
        if (value == null) {
            String message = "Coordinates can\'t be null";
            throw new IllegalArgumentException(message);
        }

        this.coordinates = value;
    }

    public void setHeight(Float value) {
        if (value == null) {
            String message = "Height can\'t be null";
            throw new IllegalArgumentException(message);
        }

        if (value <= MIN_HEIGHT) {
            String message =
                    String.format("Height must be more than %f.0", MIN_HEIGHT);
            throw new IllegalArgumentException(message);
        }

        this.height = value;
    }

    public void setWeight(Long value) {
        if ((value != null) && (value <= MIN_WEIGHT)) {
            String message =
                    String.format("Weight must be more than %d", MIN_WEIGHT);
            throw new IllegalArgumentException(message);
        }

        this.weight = value;
    }

    public void setPassportID(String value) {
        if (value == null) {
            this.passportID = null;
            return;
        }

        if (value.length() < PASSPORT_ID_MIN_LENGTH) {
            String message = String.format(
                    "PassportID can\'t bw shorter than %d symbols",
                    PASSPORT_ID_MIN_LENGTH);
            throw new IllegalArgumentException(message);
        }

        if (value.length() > PASSPORT_ID_MAX_LENGHT) {
            String message =
                    String.format("PassportID can\'t be longer than %d symbols",
                            PASSPORT_ID_MAX_LENGHT);
            throw new IllegalArgumentException(message);
        }
        this.passportID = value;
    }

    public void setNationality(Country value) {
        if (value == null) {
            String message = "Nationality can\'t be null";
            throw new IllegalArgumentException(message);
        }

        this.nationality = value;
    }

    public void setLocation(Location value) {
        if (value == null) {
            String message = "Location can\'t be null";
            throw new IllegalArgumentException(message);
        }

        this.location = value;
    }

    public Integer getID() {
        return this.id;
    }

    public java.util.Date getCreationDate() {
        return this.creationDate;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public Float getHeight() {
        return this.height;
    }

    public Long getWeight() {
        return this.weight;
    }

    public String getPassportID() {
        return this.passportID;
    }

    public Country getNationality() {
        return this.nationality;
    }

    public Location getLocation() {
        return this.location;
    }
}
