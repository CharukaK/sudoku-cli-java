package net.charukak.sudoku.game;

import java.util.HashSet;
import java.util.Set;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.Position;
import net.charukak.sudoku.model.SudokuError;
import net.charukak.sudoku.utils.SudokuConstants;

public class SudokuValidator {

    public SudokuValidationResult validateBoard(Board b) throws SudokuError {
        Set<Integer> rowSet = new HashSet<>();
        Set<Integer> colSet = new HashSet<>();
        Set<Integer> boxSet = new HashSet<>();

        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                int rowNumber = b.getValue(row, col);
                int colNumber = b.getValue(col, row);
                int boxRowBand = (int) Math.floor(row / 3);
                int boxColBand = (int) Math.floor(row % 3);
                int cellRowInBox = (int) Math.floor(col / 3);
                int cellColInBox = (int) Math.floor(col % 3);

                int boxRow = boxRowBand * 3 + cellRowInBox;
                int boxCol = boxColBand * 3 + cellColInBox;

                int boxNumber = b.getValue(boxRow, boxCol);

                if (rowNumber != 0) {
                    if (rowSet.contains(rowNumber)) {
                        return SudokuValidationResult
                                .invalid("Duplicate value found in cell" + Position.fromCoordinates(row, col));
                    }
                    rowSet.add(rowNumber);
                }

                if (colNumber != 0) {
                    if (colSet.contains(colNumber)) {
                        return SudokuValidationResult
                                .invalid("Duplicate value found in cell" + Position.fromCoordinates(row, col));
                    }

                    colSet.add(colNumber);
                }

                if (boxNumber != 0) {
                    if (boxSet.contains(boxNumber)) {
                        return SudokuValidationResult
                                .invalid("Duplicate value found in cell" + Position.fromCoordinates(row, col));
                    }

                    boxSet.add(boxNumber);
                }

            }

            boxSet.clear();
            rowSet.clear();
            colSet.clear();
        }

        return SudokuValidationResult.valid();
    }
}
