package pl.put.poznan.transformer.model;

import pl.put.poznan.transformer.logic.Visitor;

public class Room extends Location {

    //in m^2
    private final float area;

    //in m^3
    private final float cube;

    private final float heating;

    private final float light;

    public Room(long id, String name, float area, float cube, float heating, float light) {
        super(id, name);
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
    }

    @Override
    public float calculateArea() {
        return area;
    }

    @Override
    public float calculateVolume() {
        return cube;
    }

    @Override
    public float calculateLight() {
        return area != 0.0f ? light/area : 0.0f;
    }

    @Override
    public float calculateHeating() {
        return cube != 0.0f ? heating/cube : 0.0f;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitRoom(this);
    }

    public boolean isOverHeating(float heating) {
        return calculateHeating() > heating;
    }

    public float getArea() {
        return area;
    }

    public float getCube() {
        return cube;
    }

    public float getHeating() {
        return heating;
    }

    public float getLight() {
        return light;
    }
}
