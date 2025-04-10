package itmo.nxzage.common.data;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import itmo.nxzage.common.util.CSVSerializable;

public enum Country implements CSVSerializable {
    UNITED_KINGDOM(1, "The United Kingdom of Great Britan and Northen Irland"),
    USA(2, "The United States of America"),
    FRANCE(3, "France"),
    SPAIN(4, "Spain"),
    JAPAN(5, "Japanese Empire");

    private String title;
    private Integer id;

    private Country(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Country deserializeCSV(String code) throws ParseException {
        Pattern pattern = Pattern.compile("^Country#(\\d+)$");
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()){
            Integer id = new Integer(matcher.group(1));   
            for (Country country : Country.class.getEnumConstants()) {
                if (country.id.equals(id)) return country;
                System.out.println("Country searching... Current country: " + country.title + " " + country.id.toString());
            }
            throw new IllegalArgumentException(String.format("No such countryID (%d)", id));
        } else throw new ParseException("[Country.deserializeCSV()]Argument doesn\'t match the pattern", 0);
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getID() {
        return this.id;
    }

    @Override
    public String serializeCSV() {
        String pattern = "Country#%d";
        return String.format(pattern, this.id);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
