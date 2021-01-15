package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.model.Room;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AreaVisitorTest {

    @Test
    void acceptRoomWithMock() {
        Room mockRoom = mock(Room.class);

        when(mockRoom.calculateArea()).thenReturn(50.0f);
        when(mockRoom.getId()).thenReturn(1234L);

        AreaVisitor visitor = new AreaVisitor(1234L);
        visitor.visitRoom(mockRoom);

        float result = visitor.getArea();

        verify(mockRoom).getId();
        verify(mockRoom).calculateArea();

        assertEquals(50.0f, result);
    }
}