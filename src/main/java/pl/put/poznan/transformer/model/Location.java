package pl.put.poznan.transformer.model;

public abstract class Location {

    private final long id;
    private final String name;

    protected Location(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void process();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
