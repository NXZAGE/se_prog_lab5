package itmo.nxzage.client.parsing;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import itmo.nxzage.common.data.Country;

public enum LexemType {
    KEYWORD, COUNTRY_ENUM, STRING, NUMBER, UNKNOWN;

    private static final Set<String> KEYWORDS;
    private static final Set<String> COUNTRY_ENUMS;
    private static final Pattern STRING_PATTERN;
    private static final Pattern NUMBER_PATTERN;

    static {
        // брать из CommandType констант
        KEYWORDS = Stream.of("get_all", "filter_starts_with_passport_id",
                "get_all_ascending_nationality", "get_all_descending_nationality", "add",
                "add_if_min", "add_if_max", "update", "remove_by_id", "remove_lower", "clear",
                "save", "execute_script", "exit", "help").collect(Collectors.toCollection(TreeSet::new));
        COUNTRY_ENUMS = Stream.of(Country.values()).map(value -> value.name())
                .collect(Collectors.toCollection(TreeSet::new));
        // WORD_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\!\\?]*$");
        STRING_PATTERN = Pattern.compile(
                "^\\\"[a-zA-Z0-9\\_\\+\\-\\=\\*\\.\\%\\(\\)\\[\\]\\{\\}\\@\\!\\#\\№\\&\\^\\$ ]*\\\"$");
        NUMBER_PATTERN = Pattern.compile("^[0-9]+(?:\\.[0-9]+)?$");

        // COUNTRY_ENUMS.stream().forEach(value -> System.out.println(value));
    }

    public static LexemType recognize(String value) {
        if (KEYWORDS.contains(value)) {
            return KEYWORD;
        }
        if (COUNTRY_ENUMS.contains(value)) {
            return COUNTRY_ENUM;
        }
        if (STRING_PATTERN.matcher(value).matches()) {
            return STRING;
        }
        if (NUMBER_PATTERN.matcher(value).matches()) {
            return NUMBER;
        }
        return UNKNOWN;
    }
}
