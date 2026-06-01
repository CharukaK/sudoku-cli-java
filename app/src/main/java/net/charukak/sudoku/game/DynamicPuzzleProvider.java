package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.SudokuError;

public class DynamicPuzzleProvider implements PuzzleProvider {
    private int[][] solutionBoard;
    private int[][] puzzleBoard;

    public DynamicPuzzleProvider() {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator();
        this.solutionBoard = puzzleGenerator.generateSolutionBoard();
        this.puzzleBoard = puzzleGenerator.generatePuzzleBoardFromSolution();
    }

    @Override
    public Board getPuzzleBoard() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = puzzleBoard[row][col];
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
                    board.setValue(row, col, solutionBoard[row][col]);
                } catch (SudokuError e) {
                    throw new IllegalStateException("Failed to set solution cell (" + row + "," + col + ")", e);
                }
            }
        }
        return board;
    }

}
