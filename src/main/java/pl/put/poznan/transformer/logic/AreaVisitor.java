package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class AreaVisitor implements Visitor {

    private float area = -1;

    @Override
    public void visitRoom(Room room) {
        area = room.calculateArea();
    }

    @Override
    public void visitLevel(Level level) {
        area = level.calculateArea();
    }

    @Override
    public void visitBuilding(Building building) {
        area = building.calculateArea();
    }

    public float getArea() {
        return area;
    }
}
