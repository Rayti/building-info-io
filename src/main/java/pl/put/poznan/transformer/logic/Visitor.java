package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Location;
import pl.put.poznan.transformer.model.Room;

public interface Visitor {
    void visitRoom(Room room);

    void visitLevel(Level level);

    void visitBuilding(Building building);
}
