package net.charukak.sudoku.model;

import net.charukak.sudoku.utils.SudokuConstants;

public class Cell implements PrintableElement {
    private int value;
    private boolean isPreFilled;

    public Cell(int value) throws SudokuError {
        validateValue(value);
        this.value = value;
    }

    @Override
    public String getPrintableString() {
        return this.value == 0 ? "_" : "" + this.value;
    }

    public int getValue() {
        return this.value;
    }

    public void setPreFilled(boolean isPreFilled) {
        this.isPreFilled = isPreFilled;
    }

    public void setValue(int value) throws SudokuError {
        if (isPreFilled) {
            throw new SudokuError("Cannot modify a pre-filled cell");
        }

        validateValue(value);
        this.value = value;
    }

    public boolean isPreFilled() {
        return this.isPreFilled;
    }

    private void validateValue(int value) throws SudokuError {
        if (value < 0 || value > SudokuConstants.BOARD_LENGTH) {
            throw new SudokuError(
                    "Invalid number. Only accepts numbers from 0-"
                            + SudokuConstants.BOARD_LENGTH);
        }
    }
}
