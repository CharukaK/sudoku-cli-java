package net.charukak.sudoku.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Position")
public class PositionTest {

    @ParameterizedTest
    @DisplayName("converts valid references to correct row and column")
    @MethodSource("validReferenceProvider")
    void testValidReferences(String ref, int expectedRow, int expectedCol) throws SudokuError {
        Position pos = Position.fromRef(ref);
        assertEquals(expectedRow, pos.row());
        assertEquals(expectedCol, pos.col());
    }

    static Stream<Arguments> validReferenceProvider() {
        return Stream.of(
                Arguments.of("a1", 0, 0),
                Arguments.of("a9", 0, 8),
                Arguments.of("i1", 8, 0),
                Arguments.of("i9", 8, 8),
                Arguments.of("c5", 2, 4),
                Arguments.of("e8", 4, 7),
                Arguments.of("b2", 1, 1),
                Arguments.of("h7", 7, 6));
    }

    @Test
    @DisplayName("throws for empty string")
    void testEmptyString() {
        SudokuError e = assertThrows(SudokuError.class, () -> Position.fromRef(""));
        assertEquals("Invalid position reference provided", e.getMessage());
    }

    @ParameterizedTest
    @DisplayName("throws for references longer than 2 characters")
    @ValueSource(strings = { "a12", "ab1", "abc", "aaaa" })
    void testTooLongReferences(String ref) {
        SudokuError e = assertThrows(SudokuError.class, () -> Position.fromRef(ref));
        assertEquals("Invalid position reference provided", e.getMessage());
    }

    @ParameterizedTest
    @DisplayName("throws for malformed first character (not A-Z)")
        @ValueSource(strings = { "11", "@1", "[1", "1a" })
    void testInvalidFirstChar(String ref) {
        assertThrows(SudokuError.class, () -> Position.fromRef(ref));
    }

    @ParameterizedTest
    @DisplayName("throws for malformed second character (not 1-9)")
    @ValueSource(strings = { "a0", "a:", "a/", "a ", "a." })
    void testInvalidSecondChar(String ref) {
        assertThrows(SudokuError.class, () -> Position.fromRef(ref));
    }
}
