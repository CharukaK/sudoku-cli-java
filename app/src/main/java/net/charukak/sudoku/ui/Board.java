package net.charukak.sudoku.ui;

import net.charukak.sudoku.utils.SudokuConstants;

public class Board implements PrintableElement {
    private Cell[][] board;

    public Board() {
        board = new Cell[SudokuConstants.BOARD_LENGTH][SudokuConstants.BOARD_LENGTH];
        initializeBoard();
    }

    @Override
    public String getPrintableString() {
        StringBuilder builder = new StringBuilder();
        int boardLength = SudokuConstants.BOARD_LENGTH;
        String suffix = "   ";
        String rowSuffix = "  ";
        String colSeperator = " │ ";

        builder.append(suffix);
        for (int col = 1; col <= boardLength; col++) {
            builder.append(col).append(suffix);
        }
        builder.append("\n\n");

        for (int row = 0; row < boardLength; row++) {
            builder.append((char) ('A' + row)).append(rowSuffix);
            for (int col = 0; col < boardLength; col++) {
                builder.append(board[row][col].getPrintableString());
                if ((col + 1) % 3 == 0 && col < boardLength - 1) {
                    builder.append(colSeperator);
                } else {
                    builder.append(suffix);
                }
            }
            builder.append("\n");
            if ((row + 1) % 3 == 0 && row < boardLength - 1) {
                builder.append("  ───────────┼───────────┼───────────  ");
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public int getValue(int row, int col) throws SudokuError {
        validatePosition(row, col);
        return this.board[row][col].getValue();
    }

    public void setValue(int row, int col, int value) throws SudokuError {
        validatePosition(row, col);
        this.board[row][col].setValue(value);
    }

    private void initializeBoard() {
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                try {
                    this.board[row][col] = new Cell(0);
                } catch (Exception e) {
                    throw new IllegalStateException("failed initialize empty cell");
                }
            }
        }
    }

    public Cell getCell(int row, int col) throws SudokuError {
        validatePosition(row, col);
        return this.board[row][col];
    }

    private void validatePosition(int row, int col) throws SudokuError {
        if (row < 0 || row >= SudokuConstants.BOARD_LENGTH
                || col < 0 || col >= SudokuConstants.BOARD_LENGTH) {
            throw new SudokuError("Invalid board position: (" + row + "," + col + ")");
        }
    }

}
