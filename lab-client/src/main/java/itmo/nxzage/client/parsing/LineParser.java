package itmo.nxzage.client.parsing;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

public final class LineParser {
    private final int CURSOR_MIN_VALUE = -1;

    private String line;
    private int cursor;
    private boolean endReached;
    private ArrayList<Lexem> lexemBuffer;

    public LineParser(String line) {
        this.line = line;
        cursor = -1;
        endReached = line.isBlank();
    }

    public ArrayList<Lexem> parse() {
        lexemBuffer = new ArrayList<Lexem>();
        while(endReached == false) {
            lexemBuffer.add(getNextLexem());
        }

        return lexemBuffer;
    }

    private Lexem getNextLexem() {
        if (endReached) {
            return null;
        }
        update();

        while (space() && endReached == false) {
            update();
        }

        if (quote()) {
            return parseQuotedString();
        }

        if (digit()) {
            return parseNumber();
        }

        return parseKeyword();
    }

    private Lexem parseQuotedString() {
        StringBuilder buffer = new StringBuilder("\"");
        do {
            update();
            buffer.append(line.charAt(cursor));
        } while (endReached == false && quote() == false);

        return new Lexem(buffer.toString());
    }

    private Lexem parseNumber() {
        StringBuilder buffer = new StringBuilder();
        while (digit()) {
            buffer.append(line.charAt(cursor));
            if (endReached == false) {
                update();
            } else {
                break;
            }
        }


        StringBuffer floatBuffer = new StringBuffer();
        if (dot()) {
            do {
                floatBuffer.append(line.charAt(cursor));
                if (endReached == false) {
                    update();
                } else {
                    break;   
                }
            } while (digit());

            if (floatBuffer.length() == 1) {
                stepBack();
            } else {
                buffer.append(floatBuffer);
            }
        }

        return new Lexem(buffer.toString());
    }

    private Lexem parseKeyword() {
        StringBuilder buffer = new StringBuilder();
        while(space() == false) {
            buffer.append(line.charAt(cursor));
            if (endReached == false) {
                update();
            } else {
                break;
            }
        }

        if (endReached == false) {
            stepBack();
        }

        return new Lexem(buffer.toString());
    }

    private boolean space() {
        return (Character.isSpaceChar(line.charAt(cursor)));
    }

    private boolean dot() {
        return (line.charAt(cursor) == '.');
    }

    private boolean digit() {
        return Character.isDigit(line.charAt(cursor));
    }

    private boolean quote() {
        return (line.charAt(cursor) == '\"');
    }

    private void update() {
        if (endReached) {
            return;
        }

        ++cursor;
        endReached = (cursor + 1 == line.length());
    }

    private void stepBack() {
        if (cursor <= CURSOR_MIN_VALUE) {
            return;
        }

        --cursor;
        endReached = (cursor + 1 == line.length());
    }
}
