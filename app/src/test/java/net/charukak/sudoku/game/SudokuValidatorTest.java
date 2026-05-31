package net.charukak.sudoku.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.charukak.sudoku.ui.Board;
import net.charukak.sudoku.ui.SudokuError;

@DisplayName("Sudoku Validator")
public class SudokuValidatorTest {

    @Test
    @DisplayName("Empty board is valid")
    void testEmptyBoardIsValid() throws SudokuError {
        Board board = new Board();
        SudokuValidator validator = new SudokuValidator();
        SudokuValidationResult result = validator.validateBoard(board);
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Valid filled board passes")
    void testValidBoard() throws SudokuError {
        Board board = new Board();
        int[][] grid = {
                { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 }
        };
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board.setValue(r, c, grid[r][c]);
            }
        }
        SudokuValidator validator = new SudokuValidator();
        SudokuValidationResult result = validator.validateBoard(board);
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Detects duplicate value in a row")
    void testDuplicateInRow() throws SudokuError {
        Board board = new Board();
        board.setValue(0, 0, 5);
        board.setValue(0, 1, 5);
        SudokuValidator validator = new SudokuValidator();
        SudokuValidationResult result = validator.validateBoard(board);
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Detects duplicate value in a column")
    void testDuplicateInColumn() throws SudokuError {
        Board board = new Board();
        board.setValue(0, 0, 5);
        board.setValue(1, 0, 5);
        SudokuValidator validator = new SudokuValidator();
        SudokuValidationResult result = validator.validateBoard(board);
        assertFalse(result.isValid());
    }

    @Test
    @DisplayName("Detects duplicate value in a box")
    void testDuplicateInBox() throws SudokuError {
        Board board = new Board();
        board.setValue(0, 0, 5);
        board.setValue(1, 1, 5);
        SudokuValidator validator = new SudokuValidator();
        SudokuValidationResult result = validator.validateBoard(board);
        assertFalse(result.isValid());
    }

}
