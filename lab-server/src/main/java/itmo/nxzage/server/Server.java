package itmo.nxzage.server;

import itmo.nxzage.common.data.Coordinates;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException(
                "This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        final Double x = 100d;
        final Double y = 100d;
        Coordinates coordinates = new Coordinates(x, y);
        System.out.println(coordinates);
    }
}
