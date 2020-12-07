package pl.put.poznan.transformer.model;

import java.util.ArrayList;
import java.util.List;

public class Building extends Location {

    private List<Level> levelList;

    public Building(long id, String name, Level[] levels) {
        super(id, name);
        levelList = new ArrayList<>();
        levelList.addAll(List.of(levels));
    }

    public void addLevel(Level level) {
        levelList.add(level);
    }

    @Override
    public void process() {
        levelList.forEach(level -> level.process());
    }
}
