package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class HeatingVisitor implements Visitor {

    private float heating = -1;

    @Override
    public void visitRoom(Room room) {
        heating = room.calculateHeating();
    }

    @Override
    public void visitLevel(Level level) {
        heating = level.calculateHeating();
    }

    @Override
    public void visitBuilding(Building building) {
        heating = building.calculateHeating();
    }
}
