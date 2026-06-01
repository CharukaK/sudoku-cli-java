package net.charukak.sudoku.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.SudokuError;

@DisplayName("Puzzle Provider Contract")
public class PuzzleProviderContractTest {

    private static final int SIZE = 9;
    private static final int EXPECTED_PREFILLED_COUNT = 30;
    private static final PuzzleProvider PROVIDER = new DynamicPuzzleProvider();

    @Test
    @DisplayName("Returns non-null puzzle and solution boards")
    void returnsNonNullPuzzleAndSolutionBoards() {
        Board puzzleBoard = PROVIDER.getPuzzleBoard();
        Board solutionBoard = PROVIDER.getSolutionBoard();

        assertNotNull(puzzleBoard);
        assertNotNull(solutionBoard);
    }

    @Test
    @DisplayName("Solution board is fully filled and valid")
    void solutionBoardIsFullyFilledAndValid() throws SudokuError {
        Board solutionBoard = PROVIDER.getSolutionBoard();

        assertTrue(solutionBoard.isFilled());

        SudokuValidationResult validationResult = new SudokuValidator().validateBoard(solutionBoard);
        assertTrue(validationResult.isValid());
    }

    @Test
    @DisplayName("Puzzle has exactly 30 pre-filled cells")
    void puzzleHasExactlyThirtyPreFilledCells() throws SudokuError {
        Board puzzleBoard = PROVIDER.getPuzzleBoard();

        int preFilledCount = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (puzzleBoard.isPreFilled(row, col)) {
                    preFilledCount++;
                }
            }
        }

        assertEquals(EXPECTED_PREFILLED_COUNT, preFilledCount);
    }

    @Test
    @DisplayName("All puzzle pre-filled values match solution values")
    void puzzlePreFilledValuesMatchSolutionValues() throws SudokuError {
        Board puzzleBoard = PROVIDER.getPuzzleBoard();
        Board solutionBoard = PROVIDER.getSolutionBoard();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (puzzleBoard.isPreFilled(row, col)) {
                    assertEquals(solutionBoard.getValue(row, col), puzzleBoard.getValue(row, col));
                }
            }
        }
    }

}
