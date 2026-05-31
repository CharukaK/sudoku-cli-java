package net.charukak.sudoku.game;

import java.util.HashSet;
import java.util.Set;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.SudokuError;
import net.charukak.sudoku.utils.SudokuConstants;

public class SudokuValidator {

    public SudokuValidationResult validateBoard(Board b) throws SudokuError {
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            Set<Integer> seen = new HashSet<>();
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                int value = b.getValue(row, col);
                if (value == 0) {
                    continue;
                }

                if (!seen.add(value)) {
                    char rowLetter = (char) ('A' + row);
                    return SudokuValidationResult.invalid(
                            String.format("Number %d already exists in Row %c.", value, rowLetter));
                }
            }
        }

        for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
            Set<Integer> seen = new HashSet<>();
            for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
                int value = b.getValue(row, col);
                if (value == 0) {
                    continue;
                }

                if (!seen.add(value)) {
                    return SudokuValidationResult.invalid(
                            String.format("Number %d already exists in Column %d.", value, col + 1));
                }
            }
        }

        for (int boxStartRow = 0; boxStartRow < SudokuConstants.BOARD_LENGTH; boxStartRow += 3) {
            for (int boxStartCol = 0; boxStartCol < SudokuConstants.BOARD_LENGTH; boxStartCol += 3) {
                Set<Integer> seen = new HashSet<>();
                for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
                    for (int colOffset = 0; colOffset < 3; colOffset++) {
                        int value = b.getValue(boxStartRow + rowOffset, boxStartCol + colOffset);
                        if (value == 0) {
                            continue;
                        }

                        if (!seen.add(value)) {
                            return SudokuValidationResult
                                    .invalid(String.format("Number %d already exists in the same 3×3 subgrid.", value));
                        }
                    }
                }
            }
        }

        return SudokuValidationResult.valid();
    }
}
