package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Location;
import pl.put.poznan.transformer.model.Room;

import java.util.ArrayList;
import java.util.List;

public class OverheatingLocationsVisitor implements Visitor {

    private float heatingPivot = -1;

    List<Location> overheatedLocations;

    public OverheatingLocationsVisitor(float heatingPivot) {
        this.heatingPivot = heatingPivot;
        overheatedLocations = new ArrayList<>();
    }

    @Override
    public void visitRoom(Room room) {
        if (room.calculateHeating() > heatingPivot) {
            overheatedLocations.add(room);
        }
    }

    @Override
    public void visitLevel(Level level) {
        if (level.calculateHeating() > heatingPivot) {
            overheatedLocations.add(level);
        }
        for (Room room : level.getRoomList()) {
            visitRoom(room);
        }

    }

    @Override
    public void visitBuilding(Building building) {
        if (building.calculateHeating() > heatingPivot) {
            overheatedLocations.add(building);
        }
        for (Level level : building.getLevelList()) {
            visitLevel(level);
        }
    }

    public Location[] getOverheatedLocations() {
        Location[] locations = new Location[overheatedLocations.size()];
        overheatedLocations.toArray(locations);
        return locations;
    }
}
