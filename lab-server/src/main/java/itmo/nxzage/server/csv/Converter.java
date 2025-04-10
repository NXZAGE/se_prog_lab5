package itmo.nxzage.server.csv;

import java.util.Collection;
import java.text.ParseException;
import java.util.ArrayList;
import itmo.nxzage.common.data.Person;
import itmo.nxzage.common.util.CSVSerializable;
import itmo.nxzage.common.util.Pair;

public class Converter {
    public static String serialize(Collection<Person> collection) {
        StringBuilder result = new StringBuilder();
        for (Person element : collection) {
            result.append(element.serializeCSV());
            result.append(CSVSerializable.STRING_DELIMETER);
        }
        return result.toString();
    }

    public static ArrayList<Person> deserialize(String data) {
        ArrayList<Person> collection = new ArrayList<Person>();
        ArrayList<Pair<String, String>> failedStrings = new ArrayList<Pair<String, String>>(); // Debug tool
        for (String encodedElement : data.split(CSVSerializable.STRING_DELIMETER)) {
            try {
                Person element = Person.deserializeCSV(encodedElement);
                collection.add(element);
            } catch (ParseException exc) {
                failedStrings.add(new Pair<String, String>(encodedElement, exc.getMessage()));
            }
        }
        return collection;
    }
}
