package net.charukak.sudoku.model;

import net.charukak.sudoku.utils.SudokuConstants;

public class Board implements PrintableElement {
    private Cell[][] board;

    public Board() {
        board = new Cell[SudokuConstants.BOARD_LENGTH][SudokuConstants.BOARD_LENGTH];
        initializeBoard();
    }

    @Override
    public String getPrintableString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrintableString'");
    }

    public Integer getValue(int row, int col) {
        return board[row][col].getValue();
    }

    private void initializeBoard() {
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                try {
                    board[row][col] = new Cell(0);
                } catch (Exception e) {
                    throw new IllegalStateException("failed initialize empty cell");
                }
            }
        }
    }
}
