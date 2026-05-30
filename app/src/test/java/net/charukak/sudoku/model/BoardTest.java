package net.charukak.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import net.charukak.sudoku.utils.SudokuConstants;

@DisplayName("Board")
public class BoardTest {

    @Test
    @DisplayName("Initializes a 9x9 board with all values set to Zero")
    void testCreatingAValidBoard() throws SudokuError {
        Board board = new Board();

        // implementation is 1 index
        for (int row = 1; row <= SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 1; col <= SudokuConstants.BOARD_LENGTH; col++) {
                assertEquals(0, board.getValue(row, col));
            }
        }
    }

    @Test
    @DisplayName("Set/Get value methods are working properly")
    void testValueSetterAndGetter() throws SudokuError {
        Board board = new Board();
        board.setValue(3, 3, 7);
        assertEquals(7, board.getValue(3, 3));
    }

    @ParameterizedTest
    @DisplayName("Test out of bounds value getter")
    @MethodSource("getOutOfBoundTestCases")
    void testOutOfBoundValueGetter(int row, int col) {
        Board board = new Board();
        assertThrows(SudokuError.class, () -> board.getValue(row, col));
    }

    @ParameterizedTest
    @DisplayName("Test out of bounds value setter")
    @MethodSource("getOutOfBoundTestCases")
    void testOutOfBoundValueSetter(int row, int col) {
        Board board = new Board();
        assertThrows(SudokuError.class, () -> board.setValue(row, col, 5));
    }

    static Stream<Arguments> getOutOfBoundTestCases() {
        return Stream.of(
                Arguments.of(0, 12),
                Arguments.of(-12, 0),
                Arguments.of(0, 1),
                Arguments.of(1, 0),
                Arguments.of(10, 1),
                Arguments.of(1, 10),
                Arguments.of(-1, 1),
                Arguments.of(1, -1));
    }

    @Test
    @DisplayName("Set/Get at boundary positions")
    void testBoundaryPositions() throws SudokuError {
        Board board = new Board();
        board.setValue(1, 1, 5);
        assertEquals(5, board.getValue(1, 1));
        board.setValue(9, 9, 3);
        assertEquals(3, board.getValue(9, 9));
    }

    // @Test
    // @DisplayName("Validate Board printed to the console")
    // void testPrintableString() {
    //     Board board = new Board();
    //     String output = board.getPrintableString();
    //     String[] rows = output.split("\n");
    //
    //     assertEquals("  123 456 789", rows[0]);
    //     assertEquals("A 123 456 789", rows[0]);
    // }

}
