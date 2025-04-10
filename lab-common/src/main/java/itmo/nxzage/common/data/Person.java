package itmo.nxzage.common.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import itmo.nxzage.common.util.CSVSerializable;

/**
 * Person data class (main data container)
 */
public final class Person implements Comparable<Person>, CSVSerializable {
    private static final Float MIN_HEIGHT = 1f;
    private static final Long MIN_WEIGHT = 1L;
    private static final Integer PASSPORT_ID_MIN_LENGTH = 5;
    private static final Integer PASSPORT_ID_MAX_LENGHT = 24;
    private static Integer nextID = 1;
    private static Pattern CSV_DESERIALIZATION_PATTERN;
    private static String CSV_SERIALIZATION_PATTERN;

    public static final DateFormat CREATION_DATE_FORMAT =
            new SimpleDateFormat("<dd.MM.yyyy HH:mm:ss z>");
    // сериализация может пойти по пизде из-за \n

    static {
        CSV_SERIALIZATION_PATTERN = "%d" + CSVSerializable.DELIMETER + // id
                "%s" + CSVSerializable.DELIMETER + // creationDate
                "%s" + CSVSerializable.DELIMETER + // name
                "%s" + CSVSerializable.DELIMETER + // coordinates
                "%f" + CSVSerializable.DELIMETER + // height
                "%d" + CSVSerializable.DELIMETER + // weight
                "%s" + CSVSerializable.DELIMETER + // passportID
                "%s" + CSVSerializable.DELIMETER + // nationality
                "%s" // location
        ;

        CSV_DESERIALIZATION_PATTERN = Pattern.compile(
                "^(.+)" + DELIMETER_ESCAPE + "(.+)" + DELIMETER_ESCAPE + "(.+)"
                        + DELIMETER_ESCAPE + "(.+)" + DELIMETER_ESCAPE + "(.+)"
                        + DELIMETER_ESCAPE + "(.+)" + DELIMETER_ESCAPE + "(.+)"
                        + DELIMETER_ESCAPE + "(.+)" + DELIMETER_ESCAPE + "(.+)$");
    }


    private Integer id; // can't be null, unique, autogenerate, >0
    private Date creationDate; // can be null, autogenerate
    private String name; // can't be null, can't be blank
    private Coordinates coordinates; // can't be null
    private Float height; // can't be null, >0
    private Long weight; // >0
    private String passportID; // 5 <= lenght <= 24, can be null
    private Country nationality; // can't be null
    private Location location; // can't be null

    private Person(Integer id, Date creationDate, String name,
            Coordinates coordinates, Float height, Long weight,
            String passportID, Country nationality, Location location) {
        if (id == null) {
            throw new IllegalArgumentException("ID can\'t be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date can\'t be null");
        }
        if (creationDate.compareTo(new Date()) > 0) {
            throw new IllegalArgumentException("Creation date can\'t be further than now");
        }

        this.id = id;
        this.creationDate = creationDate;
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setHeight(height);
        this.setWeight(weight);
        this.setPassportID(passportID);
        this.setNationality(nationality);
        this.setLocation(location);
    }

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

    public static Person deserializeCSV(String element) throws ParseException {
        Matcher matcher = CSV_DESERIALIZATION_PATTERN.matcher(element);
        if (matcher.find()) {
            Integer id = new Integer(matcher.group(1));
            Date creationDate = CREATION_DATE_FORMAT.parse(matcher.group(2));
            String name = matcher.group(3);
            Coordinates coordinates =
                    Coordinates.deserializeCSV(matcher.group(4));
            Float height = new Float(matcher.group(5));
            Long weight = new Long(matcher.group(6));
            String passportID = matcher.group(7);
            Country nationality = Country.deserializeCSV(matcher.group(8));
            Location location = Location.deserializeCSV(matcher.group(9));
            return new Person(id, creationDate, name, coordinates, height,
                    weight, passportID, nationality, location);
        } else
            throw new ParseException("[Person.deserializeCSV()]Argument doesn't match the pattern", 0);
    }

    private void increaseID() {
        ++nextID;
    }

    public void update(Person element) {
        this.setName(element.name);
        this.setCoordinates(element.coordinates);
        this.setHeight(element.height);
        this.setWeight(element.weight);
        this.setPassportID(element.passportID);
        this.setNationality(element.nationality);
        this.setLocation(element.location);
    }

    public static void updateNextID(Collection<Person> collection) {
        Integer maxID = 0;
        for (Person element : collection) {
            maxID = Integer.max(maxID, element.id);
        }
        Person.nextID = maxID + 1;
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

        if (value.contains(CSVSerializable.DELIMETER)) {
            String message = String.format("Name can\'t contains \"%s\" symbol",
                    CSVSerializable.DELIMETER);
            throw new IllegalArgumentException(message);
        }

        if (value.contains(CSVSerializable.STRING_DELIMETER)) {
            String message = String.format("Name can\'t contains \"%s\" symbol",
                    CSVSerializable.STRING_DELIMETER);
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

        if (value.contains(CSVSerializable.DELIMETER)) {
            String message =
                    String.format("PassportID can\'t contains \"%s\" symbol",
                            CSVSerializable.DELIMETER);
            throw new IllegalArgumentException(message);
        }

        if (value.contains(CSVSerializable.STRING_DELIMETER)) {
            String message =
            String.format("PassportID can\'t contains \"%s\" symbol",
                    CSVSerializable.STRING_DELIMETER);
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

        if (value.getName().contains(CSVSerializable.DELIMETER)) {
            String message =
                    String.format("Location.name can\'t contains \"%s\" symbol",
                            CSVSerializable.DELIMETER);
            throw new IllegalArgumentException(message);
        }

        if (value.getName().contains(CSVSerializable.STRING_DELIMETER)) {
            String message =
                    String.format("Location.name can\'t contains \"%s\" symbol",
                            CSVSerializable.STRING_DELIMETER);
            throw new IllegalArgumentException(message);
        }

        this.location = value;
    }

    public Integer getID() {
        return this.id;
    }

    public Date getCreationDate() {
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

    @Override
    public int compareTo(Person other) {
        final int moreValue = 1;
        final int lessValue = -1;
        final int equalsValue = 0;
        if (this.id > other.id) {
            return moreValue;
        }
        if (this.id < other.id) {
            return lessValue;
        }
        return equalsValue;
    }

    @Override
    public String serializeCSV() {
        return String.format(CSV_SERIALIZATION_PATTERN, id,
                CREATION_DATE_FORMAT.format(creationDate), name, coordinates.serializeCSV(),
                height, weight, passportID, nationality.serializeCSV(),
                location.serializeCSV());
    }

    @Override
    public String toString() {
        String formattedCreationDate =
                Person.CREATION_DATE_FORMAT.format(this.creationDate);
        String result = String.format(
                "OBJECT PERSON [\n" + "  ID: %d\n" + "  Creation date: %s\n"
                        + "  Name: %s\n" + "  Coordinates: %s\n"
                        + "  Height: %f sm\n" + "  Weight: %d kg\n"
                        + "  Passport ID: %s\n" + "  Nationality: %s\n"
                        + "  Location: %s\n" + "]",
                this.id, formattedCreationDate, this.name,
                this.coordinates.toString(), this.height, this.weight,
                this.passportID, this.nationality.toString(),
                this.location.toString());
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
        Person person = (Person) other;
        return (this.id.equals(person.id)
                && this.creationDate.equals(person.creationDate)
                && this.name.equals(person.name)
                && this.coordinates.equals(person.coordinates)
                && this.height.equals(person.height)
                && this.weight.equals(person.weight)
                && this.passportID.equals(person.passportID)
                && this.nationality.equals(person.nationality)
                && this.location.equals(person.location));
    }

    @Override
    public int hashCode() {
        final int mod = 31;
        int hash = mod;
        hash = hash * mod + (id == null ? 0 : id.hashCode());
        hash = hash * mod
                + (creationDate == null ? 0 : creationDate.hashCode());
        hash = hash * mod + (name == null ? 0 : name.hashCode());
        hash = hash * mod + (coordinates == null ? 0 : coordinates.hashCode());
        hash = hash * mod + (height == null ? 0 : height.hashCode());
        hash = hash * mod + (weight == null ? 0 : weight.hashCode());
        hash = hash * mod + (passportID == null ? 0 : passportID.hashCode());
        hash = hash * mod + (nationality == null ? 0 : nationality.hashCode());
        hash = hash * mod + (location == null ? 0 : location.hashCode());
        return hash;
    }
}
