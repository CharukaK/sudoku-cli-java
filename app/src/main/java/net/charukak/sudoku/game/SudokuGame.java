package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.Position;
import net.charukak.sudoku.model.SudokuError;

public class SudokuGame {
    private Board puzzleBoard;
    private Board solutionBoard;

    public SudokuGame(Board puzzleBoard, Board solutionBoard) {
        this.puzzleBoard = puzzleBoard;
        this.solutionBoard = solutionBoard;
    }

    public CommandResult apply(Command cmd) {
        // TODO: logic to mutate puzzle Board
        if (cmd == null) {
            return CommandResult.invalid("Empty command.");
        }

        System.out.println(cmd.getType());
        try {
            return switch (cmd.getType()) {
                case INVALID -> CommandResult.invalid(cmd.getErrorMessage());
                case QUIT -> CommandResult.quit();
                case CHECK -> handleCheck();
                case HINT -> handleHint();
                case SET_VALUE -> handleSet(cmd);
                case CLEAR_VALUE -> handleClear(cmd);
            };
        } catch (SudokuError e) {
            return CommandResult.invalid(e.getMessage());
        }
    }

    public boolean isComplete() {
        // TODO: Check if complete and is valid
        return false;
    }

    public String getBoardString() {
        return puzzleBoard.getPrintableString();
    }

    private CommandResult handleSet(Command cmd) throws SudokuError {
        Position p = cmd.getPosition();
        int value = cmd.getValue();

        if (this.puzzleBoard.isPreFilled(p.row(), p.col())) {
            return CommandResult.invalid(String.format("%s is prefilled.", p.toString()));
        }

        this.puzzleBoard.setValue(p.row(), p.col(), value);

        if (isComplete()) {
            return CommandResult.gameWon("You have successfully completed the Sudoku puzzle!");
        }

        return CommandResult.ok("Move accepted.");
    }

    private CommandResult handleClear(Command cmd) throws SudokuError {
        Position p = cmd.getPosition();

        if (this.puzzleBoard.isPreFilled(p.row(), p.col())) {
            return CommandResult.invalid(String.format("%s is prefilled.", p.toString()));
        }

        this.puzzleBoard.setValue(p.row(), p.col(), 0);

        return CommandResult.ok("Move accepted.");
    }

    private CommandResult handleHint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleHint'");
    }

    private CommandResult handleCheck() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCheck'");
    }
}
