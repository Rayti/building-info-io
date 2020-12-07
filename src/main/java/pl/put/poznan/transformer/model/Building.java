package pl.put.poznan.transformer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

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

    private float calculate(ToDoubleFunction<Level> levelToDoubleFunction) {
        return (float)levelList.stream()
                .mapToDouble(levelToDoubleFunction)
                .sum();
    }

    @Override
    public float calculateArea() {
        return calculate(Level::calculateArea);
    }

    @Override
    public float calculateVolume() {
        return calculate(Level::calculateVolume);
    }

    @Override
    public float calculateLight() {
        return calculate(Level::calculateLight);
    }

    @Override
    public float calculateHeating() {
        return calculate(Level::calculateHeating);
    }
}
