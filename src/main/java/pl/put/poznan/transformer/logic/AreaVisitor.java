package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

public class AreaVisitor implements Visitor {

    private float area = -1;

    private long searchingId = -1;

    public AreaVisitor(long searchingId) {
        this.searchingId = searchingId;
    }

    @Override
    public void visitRoom(Room room) {
        if(room.getId() == searchingId){
            area = room.calculateArea();
        }
    }

    @Override
    public void visitLevel(Level level) {
        if (level.getId() == searchingId) {
            area = level.calculateArea();
        } else {
            for (Room room : level.getRoomList()) {
                visitRoom(room);
            }
        }
    }

    @Override
    public void visitBuilding(Building building) {
        if (building.getId() == searchingId) {
            area = building.calculateArea();
        } else {
            for (Level level : building.getLevelList()) {
                visitLevel(level);
            }
        }
    }

    public long getSearchingId() {
        return searchingId;
    }

    public void setSearchingId(long searchingId) {
        this.searchingId = searchingId;
    }

    public float getArea() {
        return area;
    }
}
