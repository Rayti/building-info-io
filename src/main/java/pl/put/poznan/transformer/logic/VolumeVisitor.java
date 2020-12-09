package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class VolumeVisitor implements Visitor {

    private float volume = -1;

    private long searchingId = -1;

    public VolumeVisitor(long searchingId) {
        this.searchingId = searchingId;
    }

    @Override
    public void visitRoom(Room room) {
        if(room.getId() == searchingId){
            volume = room.calculateVolume();
        }
    }

    @Override
    public void visitLevel(Level level) {
        if (level.getId() == searchingId) {
            volume = level.calculateVolume();
        } else {
            for (Room room : level.getRoomList()) {
                visitRoom(room);
            }
        }
    }

    @Override
    public void visitBuilding(Building building) {
        if (building.getId() == searchingId) {
            volume = building.calculateVolume();
        } else {
            for (Level level : building.getLevelList()) {
                visitLevel(level);
            }
        }
    }

    public float getVolume() {
        return volume;
    }
}
