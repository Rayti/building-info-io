package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Location;
import pl.put.poznan.transformer.model.Room;


/**
 * Interface which implements part of Visitor design pattern
 */
public interface Visitor {

    /**
     * Makes calculations based on Room class
     * @param room Room object for calculations
     */
    void visitRoom(Room room);

    /**
     * Makes calculations based on Level class
     * @param level Level object for calculations
     */
    void visitLevel(Level level);

    /**
     * Makes calculations based on Building class
     * @param building Building object for calculations
     */
    void visitBuilding(Building building);
}
