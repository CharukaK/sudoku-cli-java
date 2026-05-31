package net.charukak.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Command")
public class CommandTest {

    @Test
    @DisplayName("set returns SET_VALUE with position and value")
    void testSet() {
        Position p = new Position(3, 5);
        Command cmd = Command.set(p, 7);
        assertEquals(Command.Type.SET_VALUE, cmd.getType());
        assertEquals(p, cmd.getPosition());
        assertEquals(7, cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("clear returns CLEAR_VALUE with position")
    void testClear() {
        Position p = new Position(1, 9);
        Command cmd = Command.clear(p);
        assertEquals(Command.Type.CLEAR_VALUE, cmd.getType());
        assertEquals(p, cmd.getPosition());
        assertNull(cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("hint returns HINT with null fields")
    void testHint() {
        Command cmd = Command.hint();
        assertEquals(Command.Type.HINT, cmd.getType());
        assertNull(cmd.getPosition());
        assertNull(cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("check returns CHECK with null fields")
    void testCheck() {
        Command cmd = Command.check();
        assertEquals(Command.Type.CHECK, cmd.getType());
        assertNull(cmd.getPosition());
        assertNull(cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("quite returns QUIT with null fields")
    void testQuite() {
        Command cmd = Command.quite();
        assertEquals(Command.Type.QUIT, cmd.getType());
        assertNull(cmd.getPosition());
        assertNull(cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("invalid returns INVALID with error message")
    void testInvalid() {
        Command cmd = Command.invalid("bad input");
        assertEquals(Command.Type.INVALID, cmd.getType());
        assertEquals("bad input", cmd.getErrorMessage());
        assertNull(cmd.getPosition());
        assertNull(cmd.getValue());
    }
}
