package pl.put.poznan.transformer.model;

import java.util.ArrayList;
import java.util.List;

public class Level extends Location {

    private List<Room> roomList;

    public Level(long id, String name, Room[] rooms) {
        super(id, name);
        roomList = new ArrayList<>();
        roomList.addAll(List.of(rooms));
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    @Override
    public void process() {
        roomList.forEach(location -> location.process());
    }
}
