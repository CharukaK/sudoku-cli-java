package net.charukak.sudoku.ui;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    @Override
    public String toString() {
        return String.format("%c%d", (char) ('a' + row), col + 1);
    }

    public static Position fromCoordinates(int row, int col) throws SudokuError {
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            throw new SudokuError("Invalid position coordinates: row and col must be between 0 and 8");
        }
        return new Position(row, col);
    }

    public static Position fromRef(String ref) throws SudokuError {
        if (ref.length() == 0 || ref.length() > 2) {
            throw new SudokuError("Invalid position reference provided");
        }

        if (ref.charAt(0) < 'a' || ref.charAt(0) > 'z' || ref.charAt(1) < '1' || ref.charAt(1) > '9') {
            throw new SudokuError("Malformed input position");
        }

        int row = ref.charAt(0) - 'a';
        int col = ref.charAt(1) - '1';

        return new Position(row, col);
    }
}
