package pl.put.poznan.transformer.service;

import org.springframework.stereotype.Service;
import pl.put.poznan.transformer.model.Building;
import pl.put.poznan.transformer.model.Level;
import pl.put.poznan.transformer.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("buildingService")
public class BuildingService {

    private Building building;

    public void addLevel(Level level){
        building.addLevel(level);
    }

    public void addRoom(long levelId, Room room){
        Optional<Level> level = building.getLevelList()
                .stream()
                .filter(lvl -> lvl.getId() == levelId)
                .findFirst();
        level.ifPresent(value -> value.addRoom(room));
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Level> getLevels() {
        return building.getLevelList();
    }

    public Level getLevel(long id) {
        return getLevels()
                .stream()
                .filter(level -> level.getId() == id)
                .findFirst()
                .get();
    }

    public List<Room> getRooms(long levelId){
        Optional<Level> level =  building.getLevelList()
                .stream()
                .filter(lvl -> lvl.getId() == levelId)
                .findFirst();
        return level.isPresent() ? level.get().getRoomList() : new ArrayList<>();
    }

    public boolean idExistsInStructure(long id) {
        return idExistsForBuilding(id)
                || idExistsInLevelsOrRooms(id);
    }

    private boolean idExistsForBuilding(long id) {
        return building != null ? building.getId() == id : false;
    }

    private boolean idExistsInLevelsOrRooms(long id) {
        if(!getLevels().isEmpty()){
            return getLevels()
                    .stream()
                    .anyMatch(level -> level.getId() == id
                            || idExistsInRooms(level.getId(), id));
        }
        else return false;
    }

    private boolean idExistsInRooms(long levelId, long id){
        return getRooms(levelId)
                .stream()
                .anyMatch(room -> room.getId() == id);
    }

}
