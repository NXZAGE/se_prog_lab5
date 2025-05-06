package itmo.nxzage.client.parsing;

public final class Lexem {
    private String value;
    private LexemType type;

    public Lexem(String value) {
        this.value = value;
        this.type = LexemType.recognize(value);
    }

    public Boolean isKeyword() {
        return type == LexemType.KEYWORD;
    }

    public Boolean isString() {
        return type == LexemType.STRING;
    }

    public Boolean isNumber() {
        return type == LexemType.NUMBER;
    }

    public String getValue() {
        if (isString()) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    public LexemType getType() {
        return type;
    }
}
