package itmo.nxzage.server;

import itmo.nxzage.common.data.Coordinates;
import itmo.nxzage.common.data.Location;
import itmo.nxzage.common.data.Person;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException(
                "This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        final Double cx = 100d;
        final Float cy = 100f;
        Coordinates coordinates = new Coordinates(cx, new Float(cy));

        final Float lx = 23f;
        final Integer ly = 1232;
        final Long lz = 123412L;
        final String name = "DonDon";
        Location location = new Location(lx, ly, lz, name);

        System.out.println(coordinates);
        System.out.println(location);
    }
}
