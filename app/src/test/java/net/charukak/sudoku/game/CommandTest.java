package net.charukak.sudoku.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.Supplier;
import java.util.stream.Stream;

import net.charukak.sudoku.model.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Command")
public class CommandTest {

    @Test
    @DisplayName("set returns SET_VALUE with position and value")
    void testSet() {
        Position p = new Position(2, 4);
        Command cmd = Command.set(p, 7);
        assertEquals(Command.Type.SET_VALUE, cmd.getType());
        assertEquals(p, cmd.getPosition());
        assertEquals(7, cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("clear returns CLEAR_VALUE with position")
    void testClear() {
        Position p = new Position(0, 8);
        Command cmd = Command.clear(p);
        assertEquals(Command.Type.CLEAR_VALUE, cmd.getType());
        assertEquals(p, cmd.getPosition());
        assertNull(cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }

    @ParameterizedTest
    @DisplayName("no-arg factories return command with null payload")
    @MethodSource("noArgCommandProvider")
    void testNoArgFactories(Command.Type expectedType, Supplier<Command> factory) {
        Command cmd = factory.get();
        assertEquals(expectedType, cmd.getType());
        assertNoPayload(cmd);
    }

    static Stream<Arguments> noArgCommandProvider() {
        return Stream.of(
                Arguments.of(Command.Type.HINT, (Supplier<Command>) Command::hint),
                Arguments.of(Command.Type.CHECK, (Supplier<Command>) Command::check),
                Arguments.of(Command.Type.QUIT, (Supplier<Command>) Command::quite));
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

    private void assertNoPayload(Command cmd) {
        assertNull(cmd.getPosition());
        assertNull(cmd.getValue());
        assertNull(cmd.getErrorMessage());
    }
}
