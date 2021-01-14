package pl.put.poznan.transformer.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.AreaVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    private static Level level;


    @BeforeAll
    static void beforeAll() {
        level = new Level(4545, "Sample Level");
    }

    @BeforeEach
    void setUp() {
        level = new Level(4545, "Sample level");
        level.addRoom(new Room(1234, "Sample Room", 20.0f, 30.0f, 40.0f, 50.0f));
        level.addRoom(new Room(4321, "Second room", 100.0f, 200.0f, 300.0f, 400.0f));
    }

    @Test
    void addRoom() {
        Level level = new Level(4545, "Sample level");
        Room room = new Room(1234, "Sample Room", 20.0f, 30.0f, 40.0f, 50.0f);
        level.addRoom(room);
        List<Room> rooms = level.getRoomList();
        assertEquals(rooms.get(0), room);
    }

    @Test
    void calculateArea() {
        assertEquals(level.calculateArea(), 120.0f);
    }

    @Test
    void calculateVolume() {
        assertEquals(level.calculateVolume(), 230.0f);
    }

    @Test
    void calculateLight() {
        assertEquals(level.calculateLight(), (50.0f/20.0f + 400.0f/100.0f)/2);
    }

    @Test
    void calculateHeating() {
        assertEquals(level.calculateHeating(), (40.0f/30.0f + 300.0f/200.0f)/2);
    }

    @Test
    void accept() {
        AreaVisitor visitor = new AreaVisitor(4545);
        level.accept(visitor);
        assertEquals(visitor.getArea(), 120.0f);
        visitor = new AreaVisitor(1234);
        level.accept(visitor);
        assertEquals(visitor.getArea(), 20.0f);
    }

    @Test
    void getRoomList() {
        assertEquals(level.getRoomList().size(), 2);
        assertTrue(level.getRoomList().get(0) instanceof Room);
        assertTrue(level.getRoomList().get(1) instanceof Room);
    }
}