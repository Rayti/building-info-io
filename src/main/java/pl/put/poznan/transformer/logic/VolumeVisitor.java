package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class VolumeVisitor implements Visitor {

    private float volume = -1;

    @Override
    public void visitRoom(Room room) {
        volume = room.calculateVolume();
    }

    @Override
    public void visitLevel(Level level) {
        volume = level.calculateVolume();
    }

    @Override
    public void visitBuilding(Building building) {
        volume = building.calculateVolume();
    }

    public float getVolume() {
        return volume;
    }
}
