package pl.put.poznan.transformer.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.AreaVisitor;
import pl.put.poznan.transformer.logic.Visitor;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private static Room room;

    @BeforeAll
    static void beforeAll() {
        room = new Room(1234, "Sample Room", 20.0f, 30.0f, 40.0f, 50.0f);
    }

    @Test
    void calculateArea() {
        assertEquals(room.calculateArea(), 20.0f);
    }

    @Test
    void calculateVolume() {
        assertEquals(room.calculateVolume(), 30.0f);
    }

    @Test
    void calculateLight() {
        assertEquals(room.calculateLight(), 2.5f);
    }

    @Test
    void calculateHeating() {
        assertEquals(room.calculateHeating(), 4.0f/3.0f);
    }

    @Test
    void accept() {
        AreaVisitor visitor = new AreaVisitor(1234);
        room.accept(visitor);
        assertEquals(visitor.getArea(), 20.0f);
    }

    @Test
    void getArea() {
        assertEquals(room.getArea(), 20.0f);
    }

    @Test
    void getCube() {
        assertEquals(room.getCube(), 30.0f);
    }

    @Test
    void getHeating() {
        assertEquals(room.getHeating(), 40.0f);
    }

    @Test
    void getLight() {
        assertEquals(room.getLight(), 50.0f);
    }
}