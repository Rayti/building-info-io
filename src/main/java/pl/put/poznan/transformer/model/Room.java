package pl.put.poznan.transformer.model;

public class Room extends Location {

    //in m^2
    private float area;

    //in m^3
    private float cube;

    private float heating;

    private float light;

    public Room(long id, String name) {
        super(id, name);
    }

    @Override
    public void process() {

    }
}
