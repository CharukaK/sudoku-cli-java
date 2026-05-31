package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;

public class SudokuGame {
    private Board puzzleBoard;
    private Board solutionBoard;

    public SudokuGame(Board puzzleBoard, Board solutionBoard) {
        this.puzzleBoard = puzzleBoard;
        this.solutionBoard = solutionBoard;
    }

    public void apply(Command cmd) {
        // TODO: logic to mutate puzzle Board
        System.out.println(cmd.getType());
        
    }

    public boolean isComplete() {
        return false;
    }

    public String getBoardString() {
        return puzzleBoard.getPrintableString();
    }
}
