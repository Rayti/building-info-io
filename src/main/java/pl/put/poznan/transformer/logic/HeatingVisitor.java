package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class HeatingVisitor implements Visitor {

    private float heating = -1;

    private long searchingId = -1;

    public HeatingVisitor(long searchingId) {
        this.searchingId = searchingId;
    }

    @Override
    public void visitRoom(Room room) {
        if (room.getId() == searchingId) {
            heating = room.calculateHeating();
        }
    }

    @Override
    public void visitLevel(Level level) {
        if (level.getId() == searchingId) {
            heating = level.calculateHeating();
        } else {
            for (Room room : level.getRoomList()){
                visitRoom(room);
            }
        }
    }

    @Override
    public void visitBuilding(Building building) {
        if (building.getId() == searchingId) {
            heating = building.calculateHeating();
        } else {
            for (Level level : building.getLevelList()) {
                visitLevel(level);
            }
        }
    }

    public float getHeating() {
        return heating;
    }
}
