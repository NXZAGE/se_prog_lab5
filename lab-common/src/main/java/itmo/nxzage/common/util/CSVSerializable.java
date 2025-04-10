package itmo.nxzage.common.util;

public interface CSVSerializable {
    public static final String DELIMETER = "$";
    public static final String DELIMETER_ESCAPE = "\\$";
    public static final String STRING_DELIMETER = "\n"; 
    public String serializeCSV();
}
