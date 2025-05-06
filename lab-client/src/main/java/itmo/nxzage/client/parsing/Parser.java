package itmo.nxzage.client.parsing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import itmo.nxzage.client.input.tools.ConsoleInput;
import itmo.nxzage.client.input.tools.FileInput;
import itmo.nxzage.client.input.tools.InputSource;
import itmo.nxzage.client.input.tools.InputSourceType;
import itmo.nxzage.common.commands.Command;
import itmo.nxzage.common.commands.CommandType;
import itmo.nxzage.common.commands.client.ExecuteScript;
import itmo.nxzage.common.commands.client.Exit;
import itmo.nxzage.common.commands.client.Help;
import itmo.nxzage.common.commands.server.Add;
import itmo.nxzage.common.commands.server.AddIfMax;
import itmo.nxzage.common.commands.server.AddIfMin;
import itmo.nxzage.common.commands.server.Clear;
import itmo.nxzage.common.commands.server.FilterStartsWithPassportID;
import itmo.nxzage.common.commands.server.GetAll;
import itmo.nxzage.common.commands.server.GetAllAscendingNationality;
import itmo.nxzage.common.commands.server.GetAllDescendingNationality;
import itmo.nxzage.common.commands.server.RemoveByID;
import itmo.nxzage.common.commands.server.RemoveLower;
import itmo.nxzage.common.commands.server.Save;
import itmo.nxzage.common.commands.server.Update;
import itmo.nxzage.common.data.Coordinates;
import itmo.nxzage.common.data.Country;
import itmo.nxzage.common.data.Location;
import itmo.nxzage.common.data.Person;

public final class Parser {
    private Stack<InputSource> sourceStack;
    private InputSourceType inputMode;
    private InputSource currentSource;
    private ArrayList<Lexem> lexemBuffer;
    private Iterator<Lexem> nextLexemIt;
    private Lexem currentLexem;
    public Parser() {
        currentSource = new ConsoleInput();
        inputMode = InputSourceType.CONSOLE;
        lexemBuffer = new ArrayList<Lexem>();
        nextLexemIt = lexemBuffer.iterator();
    }

    public Response getCommand() {
        // инвариант: во время вызова команды поинтер стоит перед именем команды
        // (то есть next() указывает за пределы LexemBuffer или на первый элемент)
        updateLexem();
        CommandType commandType = CommandType.parse(currentLexem.getValue());
        if (commandType == null) {
            Exception exception = new ParseException(currentLexem.getValue(), 0);
            Response response = new ErrorResponse(exception);
            recover();
            return response;
        }
        try {
            Command command = parseCommand(commandType);

            if (nextLexemIt.hasNext()) {
                ParseException exception = new ParseException(currentLexem.getValue(), 0);
                recover();
                throw exception;
            }

            return new SuccessfulResponse(command);
        } catch (ParseException exception) {
            recover();
            return new ErrorResponse(exception);
        } catch (IllegalArgumentException exception) {
            recover();
            return new ErrorResponse(exception);
        }
    }

    private Command parseCommand(CommandType commandType)
            throws ParseException, IllegalArgumentException {
        return switch (commandType) {
            case GET_ALL -> new GetAll();
            case FILTER_STARTS_WITH_PASSPORT_ID -> parseFilterStartsWithPassportID();
            case GET_ALL_ASCENDING_NATIONALITY -> new GetAllAscendingNationality();
            case GET_ALL_DESCENDING_NATIONALITY -> new GetAllDescendingNationality();
            case ADD -> parseAdd();
            case ADD_IF_MIN -> parseAddIfMin();
            case ADD_IF_MAX -> parseAddIfMax();
            case UPDATE -> parseUpdate();
            case REMOVE_BY_ID -> parseRemoveByID();
            case REMOVE_LOWER -> parseRemoveLower();
            case CLEAR -> new Clear();
            case SAVE -> new Save();
            case EXECUTE_SCRIPT -> parseExecuteScript();
            case EXIT -> new Exit();
            case HELP -> new Help();
        };
    }

    private FilterStartsWithPassportID parseFilterStartsWithPassportID() throws ParseException {
        updateLexem();
        if (currentLexem.getType() != LexemType.STRING) {
            recover();
            throw new ParseException("passportID должен быть строкой в кавычках", 0);
        }
        String value = currentLexem.getValue();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после filter_starts_with_passport_id", 0);
        }
        return new FilterStartsWithPassportID(value);
    }

    private Add parseAdd() throws ParseException {
        Person person = getPerson();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после add", 0);
        }
        return new Add(person);
    }

    private AddIfMin parseAddIfMin() throws ParseException {
        Person person = getPerson();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после add_if_min", 0);
        }
        return new AddIfMin(person);
    }

    private AddIfMax parseAddIfMax() throws ParseException {
        Person person = getPerson();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после add_if_max", 0);
        }
        return new AddIfMax(person);
    }

    private Update parseUpdate() throws ParseException {
        updateLexem();
        String idStr = currentLexem.getValue();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            recover();
            throw new ParseException("ID должен быть числом", 0);
        }

        Person person = getPerson();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после update", 0);
        }
        return new Update(id, person);
    }

    private RemoveByID parseRemoveByID() throws ParseException {
        updateLexem();
        String idStr = currentLexem.getValue();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            recover();
            throw new ParseException("ID должен быть числом", 0);
        }
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после remove_by_id", 0);
        }
        return new RemoveByID(id);
    }

    private RemoveLower parseRemoveLower() throws ParseException {
        Person person = getPerson();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после remove_lower", 0);
        }
        return new RemoveLower(person);
    }

    private ExecuteScript parseExecuteScript() throws ParseException {
        updateLexem();
        if (currentLexem.getType() != LexemType.STRING) {
            recover();
            throw new ParseException("Имя файла должно быть строкой в кавычках", 0);
        }
        String filename = currentLexem.getValue();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы после execute_script", 0);
        }
        return new ExecuteScript(filename);
    }

    private Person getPerson() throws ParseException, IllegalArgumentException {
        String name;
        Coordinates coordinates;
        Float height;
        Long weight;
        String passportID;
        Country nationality;
        Location location;

        if (inputMode == InputSourceType.CONSOLE) {
            name = readNameFromConsole();
            coordinates = readCoordinatesFromConsole();
            height = readHeightFromConsole();
            weight = readWeightFromConsole();
            passportID = readPassportIDFromConsole();
            nationality = readCountryFromConsole();
            location = readLocationFromConsole();
        } else if (inputMode == InputSourceType.SCRIPT) {
            name = readNameFromScript();
            coordinates = readCoordinatesFromScript();
            height = readHeightFromScript();
            weight = readWeightFromScript();
            passportID = readPassportIDFromScript();
            nationality = readCountryFromScript();
            location = readLocationFromScript();
        } else {
            throw new IllegalStateException("Unsupported input mode: " + inputMode);
        }

        return new Person(name, coordinates, height, weight, passportID, nationality, location);
    }

    private String readNameFromConsole() {
        while (true) {
            System.out.print("Введите имя в кавычках: ");
            updateLexem();
            if (currentLexem.getType() != LexemType.STRING) {
                recover();
                System.out.println("[Ошибка] Имя должно быть строкой в кавычках.");
                continue;
            }

            String name = currentLexem.getValue();
            if (nextLexemIt.hasNext()) {
                recover();
                System.out.println("[Ошибка] Имя должно быть единственным аргументом.");
                continue;
            }
            return name;
        }
    }

    private String readNameFromScript() throws ParseException {
        updateLexem();
        if (currentLexem.getType() != LexemType.STRING || nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Имя должно быть строкой и только одним аргументом", 0);
        }
        return currentLexem.getValue();
    }

    private Coordinates readCoordinatesFromConsole() {
        while (true) {
            System.out.print("Введите координаты в форматe x y: ");
            updateLexem();
            String xStr = currentLexem.getValue();
            updateLexem();
            String yStr = currentLexem.getValue();

            if (nextLexemIt.hasNext()) {
                recover();
                System.out.println("[Ошибка] Координаты должны содержать только X и Y.");
                continue;
            }

            try {
                Double x = Double.parseDouble(xStr);
                Float y = Float.parseFloat(yStr);
                return new Coordinates(x, y);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                recover();
                System.out.println("[Ошибка] Некорректный формат координат. Ожидаются два числа.");
            }
        }
    }

    private Coordinates readCoordinatesFromScript() throws ParseException {
        try {
            updateLexem();
            String xStr = currentLexem.getValue();
            updateLexem();
            String yStr = currentLexem.getValue();
            if (nextLexemIt.hasNext())
                throw new ParseException("Лишние аргументы для Coordinates", 0);
            return new Coordinates(Double.parseDouble(xStr), Float.parseFloat(yStr));
        } catch (Exception e) {
            recover();
            throw new ParseException("Некорректный формат координат", 0);
        }
    }

    private Float readHeightFromConsole() {
        while (true) {
            System.out.print("Введите высоту: ");
            updateLexem();
            String str = currentLexem.getValue();
            if (nextLexemIt.hasNext()) {
                recover();
                System.out.println("[Ошибка] Высота должна быть одиночным числом.");
                continue;
            }
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException e) {
                recover();
                System.out.println("[Ошибка] Неверный формат высоты.");
            }
        }
    }

    private Float readHeightFromScript() throws ParseException {
        updateLexem();
        String value = currentLexem.getValue();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы для height", 0);
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            recover();
            throw new ParseException("Некорректный формат height", 0);
        }
    }

    private Long readWeightFromConsole() {
        while (true) {
            System.out.print("Введите вес: ");
            updateLexem();
            String str = currentLexem.getValue();
            if (nextLexemIt.hasNext()) {
                recover();
                System.out.println("[Ошибка] Вес должен быть одиночным числом.");
                continue;
            }
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e) {
                recover();
                System.out.println("[Ошибка] Неверный формат веса.");
            }
        }
    }

    private Long readWeightFromScript() throws ParseException {
        updateLexem();
        String value = currentLexem.getValue();
        if (nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Лишние аргументы для weight", 0);
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            recover();
            throw new ParseException("Некорректный формат weight", 0);
        }
    }

    private String readPassportIDFromConsole() {
        while (true) {
            System.out.print("Введите passportID в кавычках: ");
            updateLexem();
            if (currentLexem.getType() != LexemType.STRING) {
                recover();
                System.out.println("[Ошибка] PassportID должен быть строкой в кавычках.");
                continue;
            }

            String value = currentLexem.getValue();
            if (nextLexemIt.hasNext()) {
                recover();
                System.out.println("[Ошибка] PassportID должен быть одним аргументом.");
                continue;
            }
            return value;
        }
    }

    private String readPassportIDFromScript() throws ParseException {
        updateLexem();
        if (currentLexem.getType() != LexemType.STRING || nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("PassportID должен быть строкой и единственным аргументом", 0);
        }
        return currentLexem.getValue();
    }

    private Country readCountryFromConsole() {
        while (true) {
            System.out.print("Введите страну (код большими буквами без кавычек): ");
            updateLexem();
            if (currentLexem.getType() != LexemType.COUNTRY_ENUM) {
                recover();
                System.out.println("[Ошибка] Неизвестное значение страны.");
                continue;
            }

            String str = currentLexem.getValue();
            if (nextLexemIt.hasNext()) {
                recover();
                System.out.println("[Ошибка] Страна должна быть одним словом.");
                continue;
            }
            try {
                return Country.valueOf(str);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }
    }

    private Country readCountryFromScript() throws ParseException {
        updateLexem();
        if (currentLexem.getType() != LexemType.COUNTRY_ENUM || nextLexemIt.hasNext()) {
            recover();
            throw new ParseException("Некорректный формат или лишние аргументы для nationality", 0);
        }
        try {
            return Country.valueOf(currentLexem.getValue());
        } catch (IllegalArgumentException e) {
            recover();
            throw new ParseException("Неизвестная страна", 0);
        }
    }

    private Location readLocationFromConsole() {
        while (true) {
            System.out.print("Введите локацию в формате x y z \"name\": ");
            try {
                updateLexem();
                String xStr = currentLexem.getValue();
                updateLexem();
                String yStr = currentLexem.getValue();
                updateLexem();
                String zStr = currentLexem.getValue();
                updateLexem();
                String name = currentLexem.getValue();

                if (nextLexemIt.hasNext()) {
                    recover();
                    System.out
                            .println("[Ошибка] Локация должна содержать ровно 4 поля: x y z name.");
                    continue;
                }

                Float x = Float.parseFloat(xStr);
                Integer y = Integer.parseInt(yStr);
                Long z = Long.parseLong(zStr);
                return new Location(x, y, z, name);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                recover();
                System.out.println("[Ошибка] Некорректный формат для полей Location.");
            }
        }
    }

    private Location readLocationFromScript() throws ParseException {
        try {
            updateLexem();
            String xStr = currentLexem.getValue();
            updateLexem();
            String yStr = currentLexem.getValue();
            updateLexem();
            String zStr = currentLexem.getValue();
            updateLexem();
            String name = currentLexem.getValue();
            if (nextLexemIt.hasNext())
                throw new ParseException("Лишние аргументы для Location", 0);
            return new Location(Float.parseFloat(xStr), Integer.parseInt(yStr),
                    Long.parseLong(zStr), name);
        } catch (Exception e) {
            recover();
            throw new ParseException("Ошибка парсинга Location", 0);
        }
    }

    private void recover() {
        updateLexemBuffer();
    }

    private void updateLexem() {
        if (nextLexemIt.hasNext() == false) {
            updateLexemBuffer();
        }

        currentLexem = nextLexemIt.next();
    }

    private void updateLexemBuffer() {
        try {
            String line = currentSource.readLine();
            System.out.println("[Buffer updating:]" + line);
            if (line != null) {
                lexemBuffer = new LineParser(line).parse();                
                nextLexemIt = lexemBuffer.iterator();
            } else if (inputMode == InputSourceType.SCRIPT) {
                unbindCurentSource();
                updateLexemBuffer();
            }
        } catch (IOException exception) {
            // write to log
            System.err.println(exception.getMessage());
        }
    }

    private void unbindCurentSource() {
        if (inputMode == InputSourceType.CONSOLE) {
            return;
        }

        if (inputMode == InputSourceType.SCRIPT) {
            try {
                currentSource.close();
                currentSource = sourceStack.pop();
                inputMode = currentSource.getType();
            } catch (IOException exception) {
                // write to log
                System.err.println("Unable to close stream: ");
                System.err.println(exception.getMessage());
            }
        }
    }

    public void switchToNewSource(String fileName) {
        try {
            InputSource source = new FileInput(fileName);
            sourceStack.push(currentSource);
            currentSource = source;
            inputMode = source.getType();
        } catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
