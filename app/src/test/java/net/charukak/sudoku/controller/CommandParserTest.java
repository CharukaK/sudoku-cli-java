package net.charukak.sudoku.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import net.charukak.sudoku.game.Command;

@DisplayName("CommandParser")
public class CommandParserTest {
    private final CommandParser parser = new CommandParser();

    @ParameterizedTest
    @DisplayName("returns invalid for null or blank input")
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    void testNullOrBlankInput(String input) {
        Command cmd = parser.parse(input);
        assertEquals(Command.Type.INVALID, cmd.getType());
        assertEquals("Empty command.", cmd.getErrorMessage());
    }

    @ParameterizedTest
    @DisplayName("returns expected type for known single-word commands")
    @MethodSource("singleWordCommandProvider")
    void testKnownSingleWordCommands(String input, Command.Type expectedType) {
        assertEquals(expectedType, parser.parse(input).getType());
    }

    static Stream<Arguments> singleWordCommandProvider() {
        return Stream.of(
                Arguments.of("hint", Command.Type.HINT),
                Arguments.of("HINT", Command.Type.HINT),
                Arguments.of("Hint", Command.Type.HINT),
                Arguments.of("check", Command.Type.CHECK),
                Arguments.of("CHECK", Command.Type.CHECK),
                Arguments.of("Check", Command.Type.CHECK),
                Arguments.of("quit", Command.Type.QUIT),
                Arguments.of("QUIT", Command.Type.QUIT),
                Arguments.of("Quit", Command.Type.QUIT));
    }

    @ParameterizedTest
    @DisplayName("returns SET_VALUE for valid position and value")
    @MethodSource("validSetProvider")
    void testValidSet(String input, int expectedRow, int expectedCol, int expectedValue) {
        Command cmd = parser.parse(input);
        assertEquals(Command.Type.SET_VALUE, cmd.getType());
        assertEquals(expectedRow, cmd.getPosition().row());
        assertEquals(expectedCol, cmd.getPosition().col());
        assertEquals(expectedValue, cmd.getValue());
    }

    static Stream<Arguments> validSetProvider() {
        return Stream.of(
                Arguments.of("a1 5", 0, 0, 5),
                Arguments.of("c3 9", 2, 2, 9),
                Arguments.of("h8 1", 7, 7, 1));
    }

    @ParameterizedTest
    @DisplayName("returns CLEAR_VALUE for valid position with clear")
    @MethodSource("validClearProvider")
    void testValidClear(String input, int expectedRow, int expectedCol) {
        Command cmd = parser.parse(input);
        assertEquals(Command.Type.CLEAR_VALUE, cmd.getType());
        assertEquals(expectedRow, cmd.getPosition().row());
        assertEquals(expectedCol, cmd.getPosition().col());
    }

    static Stream<Arguments> validClearProvider() {
        return Stream.of(
                Arguments.of("a1 clear", 0, 0),
                Arguments.of("d5 clear", 3, 4),
                Arguments.of("i9 clear", 8, 8));
    }

    @ParameterizedTest
    @DisplayName("returns invalid for malformed command format")
    @ValueSource(strings = { "foo", "unknown", "x", "a1 5 extra" })
    void testInvalidFormat(String input) {
        Command cmd = parser.parse(input);
        assertEquals(Command.Type.INVALID, cmd.getType());
        assertEquals("Invalid command format", cmd.getErrorMessage());
    }

    @Test
    @DisplayName("returns invalid for bad position reference")
    void testInvalidPosition() {
        Command cmd = parser.parse("11 5");
        assertEquals(Command.Type.INVALID, cmd.getType());
        assertNotNull(cmd.getErrorMessage());
    }

    @Test
    @DisplayName("returns invalid for non-numeric value")
    void testNonNumericValue() {
        Command cmd = parser.parse("a1 abc");
        assertEquals(Command.Type.INVALID, cmd.getType());
        assertEquals("Invalid number. Value must be between 1 and 9.", cmd.getErrorMessage());
    }
}
