package net.charukak.sudoku.model;

public class Position {
    private int row;
    private int col;

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

    public static Position fromRef(String ref) throws SudokuError {
        if (ref.length() == 0 || ref.length() > 2) {
            throw new SudokuError("Invalid position reference provided");
        }

        if (ref.charAt(0) < 'a' || ref.charAt(0) > 'z' || ref.charAt(1) < '1' || ref.charAt(1) > '9') {
            throw new SudokuError("Malformed input position");
        }

        int row = ref.charAt(0) - 'a' + 1;
        int col = ref.charAt(1) - '1' + 1;

        return new Position(row, col);
    }
}
