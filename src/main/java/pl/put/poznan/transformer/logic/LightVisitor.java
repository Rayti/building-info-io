package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class LightVisitor implements Visitor {

    private float light = -1;

    @Override
    public void visitRoom(Room room) {
        light = room.calculateLight();
    }

    @Override
    public void visitLevel(Level level) {
        light = level.calculateLight();
    }

    @Override
    public void visitBuilding(Building building) {
        light = building.calculateLight();
    }
}
