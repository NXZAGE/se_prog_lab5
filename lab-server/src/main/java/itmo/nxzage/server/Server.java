package itmo.nxzage.server;

import java.util.Date;
import java.text.ParseException;
import itmo.nxzage.common.commands.server.ServerCommand;
import itmo.nxzage.common.data.Coordinates;
import itmo.nxzage.common.data.Country;
import itmo.nxzage.common.data.Location;
import itmo.nxzage.common.data.Person;
import itmo.nxzage.server.tests.DumpManagerTester;

public final class Server {
    private Storage storage;
    private ControlPanel mainControlPanel;

    public Server() {
        storage = new Storage();
        mainControlPanel = storage.getControlPanel();
    }

    public ExecutionResponse execute(ServerCommand command) {
        // translate ServerCommand -> Executable
        // exe.execute(mainControlPanel);
        return null;
    }

    public static void main(String[] args) {
        // Server.testPerson();
        // Server.testDumpManager();
        Server.testStorage();
    }

    private static void testPerson() {
        Person person1 = new Person("Иван", new Coordinates(10D, 20F), 190F,
                90L, "5783293823984", Country.USA,
                new Location(10F, 20, 30L, "Дом какой то там"));
        Person person2 = new Person("Марина", new Coordinates(12D, 30F), 160F,
                40L, "12121212", Country.FRANCE,
                new Location(1230F, 1277, 30L, "Эйфелева башня"));

        System.out.println(person1.toString());
        System.out.println(person2.toString());

        // testing date convertion
        String dateString = "<22.03.2025 13:34:37 MSK>";
        Date date;
        try {
            date = Person.CREATION_DATE_FORMAT.parse(dateString);
            System.out.println("Original:" + dateString);
            System.out.println(".toString():" + date.toString());
            System.out.println(
                    "DateFormat:" + Person.CREATION_DATE_FORMAT.format(date));
        } catch (ParseException exc) {
            System.out.println(exc.getMessage());
        }

        // testing csv comversion

        System.out.println("Serialized persons:");
        System.out.println("^" + person1.serializeCSV() + "$");
        System.out.println("^" + person2.serializeCSV() + "$");
        System.out.println("Deseriilized persons:");
        try {
            System.out.println(Person.deserializeCSV(person1.serializeCSV()));
            System.out.println(Person.deserializeCSV(person2.serializeCSV()));
        } catch (ParseException exc) {
            System.out.println(
                    String.format("Unable to parse: %s", exc.getMessage()));
            System.out.println(exc.getStackTrace());
            System.out.println(exc.getCause());
        }
    }

    private static void testStorage() {
        Storage storage = new Storage();
        Person.updateNextID(storage.getAll(false));
        Person[] data = new Person[] {
                new Person("Kirill", new Coordinates(12332D, 2230F), 199F, 99L,
                        "5783293sdf sdsd4", Country.FRANCE,
                        new Location(1430F, 2110, 3012L, "Будка для собаки")),
                new Person("Kim Kim", new Coordinates(242D, 230F), 1440F, 420L,
                        "1210823277777", Country.JAPAN,
                        new Location(12230F, 12277, 3023L, "Банный комплекс")),
                new Person("Ahmed Bulba", new Coordinates(3444D, 843F),
                        223F, 69L, "473734737", Country.USA,
                        new Location(1555F, 123333, 45L, "Spaceship")),
                new Person("Leeeeee Banks", new Coordinates(558D, 5553F), 78F,
                        7L, "99938383", Country.USA,
                        new Location(710F, 8444, 133L, "Coffix"))};

        storage.dump();

        for (Person element : storage.getAll(true)) {
            System.out.println(element);;
        }
    }

    private static void testDumpManager() {
        String readFilePath = "./lab-server/target/local_dir/forRead.txt";
        String writeFilePath = "./lab-server/target/local_dir/forWrite.csv";
        String writeData =
                "some data that i want to write in this file\n\n\n\n\nn\ni\ng\ng\ne\nr\n\n\npup!";
        DumpManagerTester.tryRead(new DumpManager(readFilePath));
        DumpManagerTester.tryWrite(new DumpManager(writeFilePath), writeData);
    }
}
