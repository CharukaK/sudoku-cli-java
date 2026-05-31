package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.Command;
import net.charukak.sudoku.model.SudokuError;

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
        SudokuValidator validator = new SudokuValidator();
        try {
            validator.validateBoard(this.puzzleBoard);
        } catch (SudokuError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public boolean isComplete() {
        return false;
    }

    public String getBoardString() {
        return puzzleBoard.getPrintableString();
    }
}
