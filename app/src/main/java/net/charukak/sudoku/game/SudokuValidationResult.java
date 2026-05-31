package net.charukak.sudoku.game;

public class SudokuValidationResult {
    private boolean isValid;
    private String message;

    public SudokuValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public static SudokuValidationResult valid() {
        return new SudokuValidationResult(true, "No rule violations found.");
    }

    public static SudokuValidationResult invalid(String message) {
        return new SudokuValidationResult(false, message);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }
}

