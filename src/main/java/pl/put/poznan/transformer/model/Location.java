package pl.put.poznan.transformer.model;

import pl.put.poznan.transformer.logic.Visitor;

/**
 * Abstract class which helps with creating complex Buildings
 */
public abstract class Location {

    /**
     * Id number of Location
     */
    private final long id;

    /**
     * Name of Location
     */
    private final String name;

    protected Location(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Calculates area of Location
     * @return area
     */
    public abstract float calculateArea();

    /**
     * Calculates volume of Location
     * @return volume
     */
    public abstract float calculateVolume();

    /**
     * Calculates Location's light power divided by it's area
     * @return light power
     */
    public abstract float calculateLight();

    /**
     * Calculates Location's heating power divided by it's volume
     * @return heating power
     */
    public abstract float calculateHeating();

    /**
     * Enables Visitor to use this class methods
     * @param visitor specific Visitor that calculates something
     */
    public abstract void accept(Visitor visitor);

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
