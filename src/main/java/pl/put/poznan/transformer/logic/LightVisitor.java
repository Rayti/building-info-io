package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class LightVisitor implements Visitor {

    private float light = -1;

    private long searchingId = -1;

    public LightVisitor(long searchingId) {
        this.searchingId = searchingId;
    }

    @Override
    public void visitRoom(Room room) {
        if (room.getId() == searchingId) {
            light = room.calculateLight();
        }
    }

    @Override
    public void visitLevel(Level level) {
        if (level.getId() == searchingId) {
            light = level.calculateLight();
        } else {
            for (Room room : level.getRoomList()) {
                visitRoom(room);
            }
        }
    }

    @Override
    public void visitBuilding(Building building) {
        if (building.getId() == searchingId) {
            light = building.calculateLight();
        } else {
            for (Level level : building.getLevelList()) {
                visitLevel(level);
            }
        }
    }

    public float getLight() {
        return light;
    }
}
