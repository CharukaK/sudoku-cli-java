package net.charukak.sudoku.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.charukak.sudoku.utils.SudokuConstants;

public class PuzzleGenerator {
    private int[][] solution = new int[SudokuConstants.BOARD_LENGTH][SudokuConstants.BOARD_LENGTH];
    private int[][] puzzle = new int[SudokuConstants.BOARD_LENGTH][SudokuConstants.BOARD_LENGTH];

    public int[][] generateSolutionBoard() {
        preSeedBoardForPuzzleGen(this.solution);
        genSolutionBoard(this.solution);
        return solution;
    }

    public int[][] generatePuzzleBoardFromSolution() {
        this.puzzle = deepCopyBoard(this.solution);
        genPuzzleBoard(this.puzzle);
        return puzzle;
    }

    private void preSeedBoardForPuzzleGen(int[][] board) {
        Random random = new Random();
        for (int boxStartRow = 0; boxStartRow < SudokuConstants.BOARD_LENGTH; boxStartRow += 3) {
            for (int boxStartCol = 0; boxStartCol < SudokuConstants.BOARD_LENGTH; boxStartCol += 3) {
                if (boxStartCol == boxStartRow) {
                    Set<Integer> inserted = new HashSet<>();
                    for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
                        for (int colOffset = 0; colOffset < 3; colOffset++) {
                            int num;
                            do {
                                num = random.nextInt(9) + 1;
                            } while (inserted.contains(num));

                            inserted.add(num);
                            board[boxStartRow + rowOffset][boxStartCol + colOffset] = num;
                        }
                    }
                }
            }
        }
    }

    private boolean genPuzzleBoard(int[][] board) {
        List<int[]> positions = new ArrayList<>();
        Random random = new Random();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                positions.add(new int[] { row, col });
            }
        }

        int targetClues = SudokuConstants.TOTAL_CLUES;
        int currentClues = board.length * board.length;

        while (currentClues > targetClues && !positions.isEmpty()) {
            int positionIndex = random.nextInt(positions.size());
            int[] position = positions.remove(positionIndex);

            int row = position[0];
            int col = position[1];

            int currentValue = board[row][col];
            board[row][col] = 0;

            if (countSolutions(board, 2) == 1) {
                currentClues--;
            } else {
                board[row][col] = currentValue;
            }

        }

        return true;
    }

    private int countSolutions(int[][] board, int limit) {
        // find first empty cell
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                if (board[row][col] == 0) {
                    int total = 0;
                    for (int num = 1; num <= SudokuConstants.BOARD_LENGTH; num++) {
                        if (canInsertValue(board, row, col, num)) {
                            board[row][col] = num;
                            total += countSolutions(board, limit - total);
                            board[row][col] = 0;
                            if (total >= limit)
                                return total;
                        }
                    }
                    return total;
                }
            }
        }
        return 1; // no empty cells → found a complete solution
    }

    private boolean genSolutionBoard(int[][] board) {
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SudokuConstants.BOARD_LENGTH; num++) {
                        if (canInsertValue(board, row, col, num)) {
                            board[row][col] = num;
                            if (genSolutionBoard(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    private boolean canInsertValue(int[][] board, int row, int col, int value) {
        // check number exist in row
        for (int i = 0; i < SudokuConstants.BOARD_LENGTH; i++) {
            if (value == board[row][i]) {
                return false;
            }
        }

        // check number exist in column
        for (int i = 0; i < SudokuConstants.BOARD_LENGTH; i++) {
            if (value == board[i][col]) {
                return false;
            }
        }

        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;
        for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
            for (int colOffset = 0; colOffset < 3; colOffset++) {
                if (value == board[boxStartRow + rowOffset][boxStartCol + colOffset]) {
                    return false;
                }
            }
        }

        return true;
    }

    private int[][] deepCopyBoard(int[][] b) {
        int[][] copy = new int[b.length][b.length];

        for (int row = 0; row < b.length; row++) {
            copy[row] = b[row].clone();
        }

        return copy;
    }

}
