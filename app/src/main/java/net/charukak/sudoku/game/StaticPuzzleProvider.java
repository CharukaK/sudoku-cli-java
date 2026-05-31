package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.SudokuError;

public class StaticPuzzleProvider implements PuzzleProvider {

    private static final int[][] PUZZLE = {
        {5,3,0,0,7,0,0,0,0},
        {6,0,0,1,9,5,0,0,0},
        {0,9,8,0,0,0,0,6,0},
        {8,0,0,0,6,0,0,0,3},
        {4,0,0,8,0,3,0,0,1},
        {7,0,0,0,2,0,0,0,6},
        {0,6,0,0,0,0,2,8,0},
        {0,0,0,4,1,9,0,0,5},
        {0,0,0,0,8,0,0,7,9}
    };

    private static final int[][] SOLUTION = {
        {5,3,4,6,7,8,9,1,2},
        {6,7,2,1,9,5,3,4,8},
        {1,9,8,3,4,2,5,6,7},
        {8,5,9,7,6,1,4,2,3},
        {4,2,6,8,5,3,7,9,1},
        {7,1,3,9,2,4,8,5,6},
        {9,6,1,5,3,7,2,8,4},
        {2,8,7,4,1,9,6,3,5},
        {3,4,5,2,8,6,1,7,9}
    };

    @Override
    public Board getPuzzleBoard() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = PUZZLE[row][col];
                try {
                    board.setValue(row, col, value);
                    if (value != 0) {
                        board.getCell(row, col).setPreFilled(true);
                    }
                } catch (SudokuError e) {
                    throw new IllegalStateException("Failed to set puzzle cell (" + row + "," + col + ")", e);
                }
            }
        }
        return board;
    }

    @Override
    public Board getSolutionBoard() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                try {
                    board.setValue(row, col, SOLUTION[row][col]);
                } catch (SudokuError e) {
                    throw new IllegalStateException("Failed to set solution cell (" + row + "," + col + ")", e);
                }
            }
        }
        return board;
    }
}
