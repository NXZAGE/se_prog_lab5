package itmo.nxzage.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import itmo.nxzage.common.data.Person;
import itmo.nxzage.server.csv.Converter;

public final class Storage {
    private DumpManager dumpManager;
    private TreeSet<Person> collection;

    public Storage() {
        this.dumpManager = new DumpManager(
                "./lab-server/target/storage_dir/store_file.csv");
        if (this.load()) {
            System.out.println("Stroage was successfully loaded");
        } else {
            this.collection = new TreeSet<Person>();
            System.out.println(
                    "Storage wasn\'t loaded successfully. Collection is empty");
        }
    }

    public Boolean load() {
        return this.load(this.dumpManager);
    }

    // make private
    public Boolean load(DumpManager dumpManager) {
        DumpManager.ReadingResponse response = dumpManager.read();
        if (response.successful()) {
            collection = new TreeSet<Person>(
                    Converter.deserialize(response.getData()));
            return true;
        }
        return false;
    }

    public Boolean dump() {
        return this.dump(this.dumpManager);
    }

    // make private
    public Boolean dump(DumpManager dumpManager) {
        String serializedCollection = Converter.serialize(collection);
        DumpManager.WritingResponse response =
                dumpManager.write(serializedCollection);
        return response.successful();
    }

    public void add(Person element) {
        collection.add(element);
    }

    public void addIfMin(Person element) {
        if (collection.isEmpty() || collection.first().compareTo(element) > 0) {
            collection.add(element);
        }
    }

    public void addIfMax(Person element) {
        if (collection.isEmpty() || collection.last().compareTo(element) < 0) {
            collection.add(element);
        }
    }

    public ArrayList<Person> filterByPassportID(String prefix) {
        ArrayList<Person> matches = new ArrayList<Person>();
        Iterator<Person> iter = collection.iterator();
        while (iter.hasNext()) {
            Person element = iter.next();
            if (element.getPassportID().regionMatches(0, prefix, 0,
                    prefix.length())) {
                matches.add(element);
            }
        }

        return matches;
    }

    public Person get(Integer id) {
        Iterator<Person> iter = collection.iterator();
        while (iter.hasNext()) {
            Person elem = iter.next();
            if (elem.getID() == id) {
                return elem;
            }
        }
        return null;
    }

    public ArrayList<Person> getAll(Boolean reversed) {
        ArrayList<Person> list = new ArrayList<Person>();
        Iterator<Person> iter = collection.iterator();
        while (iter.hasNext()) {
            Person element = iter.next();
            if (reversed) {
                list.add(0, element);
            } else {
                list.add(element);
            }
        }

        return list;
    }

    public void remove(Integer id) {
        Person element = this.get(id);
        if (element == null) {
            return;
        }

        collection.remove(element);
    }

    public void removeLower(Person value) {
        while ((collection.isEmpty() == false)
                && collection.first().compareTo(value) < 0) {
            collection.pollFirst();
        }
    }

    public void clear() {
        this.collection.clear();
    }

    public boolean update(Integer id, Person newElement) {
        Person element = this.get(id);
        if (element == null) {
            return false;
        }

        element.update(newElement);
        return true;
    }
}
