package net.charukak.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.charukak.sudoku.utils.SudokuConstants;

@DisplayName("Board")
public class BoardTest {

    @Test
    @DisplayName("Initializes a 9x9 board with all values set to Zero")
    void createAValidBoard() {
        Board board = new Board();

        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                assertEquals(0, board.getValue(row, col));
            }
        }
    }
}
