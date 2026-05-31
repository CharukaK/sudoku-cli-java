package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.Position;
import net.charukak.sudoku.model.SudokuError;
import net.charukak.sudoku.utils.SudokuConstants;

public class SudokuGame {
    private Board puzzleBoard;
    private Board solutionBoard;

    public SudokuGame(Board puzzleBoard, Board solutionBoard) {
        this.puzzleBoard = puzzleBoard;
        this.solutionBoard = solutionBoard;
    }

    public CommandResult apply(Command cmd) {
        if (cmd == null) {
            return CommandResult.invalid("Empty command.");
        }

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

    public boolean isComplete() throws SudokuError {
        return this.puzzleBoard.isFilled() && areBoardsSimilar();
    }

    public String getBoardString() {
        return puzzleBoard.getPrintableString();
    }

    private CommandResult handleSet(Command cmd) throws SudokuError {
        Position p = cmd.getPosition();
        int value = cmd.getValue();

        if (value < 1 || value > SudokuConstants.BOARD_LENGTH) {
            return CommandResult.invalid("Invalid number. Value must be between 1 and 9.");
        }

        if (this.puzzleBoard.isPreFilled(p.row(), p.col())) {
            return CommandResult.invalid(String.format("Invalid move. %s is pre-filled.", p.toString().toUpperCase()));
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
            return CommandResult.invalid(String.format("Invalid move. %s is pre-filled.", p.toString().toUpperCase()));
        }

        this.puzzleBoard.setValue(p.row(), p.col(), 0);

        return CommandResult.ok("Move accepted.");
    }

    private CommandResult handleHint() throws SudokuError {
        Position hintPosition = getHintTarget();

        if (hintPosition == null) {
            return CommandResult.ok("No hint available");
        }

        int value = solutionBoard.getValue(hintPosition.row(), hintPosition.col());

        return CommandResult.ok(
                String.format(
                        "Hint: Cell %s = %d",
                        hintPosition.toString().toUpperCase(),
                        value));

    }

    private CommandResult handleCheck() throws SudokuError {
        SudokuValidationResult svr = new SudokuValidator().validateBoard(puzzleBoard);
        return svr.isValid() ? CommandResult.ok("No rule violations detected.")
                : CommandResult.violation(svr.getMessage());
    }

    private boolean areBoardsSimilar() throws SudokuError {
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                if (puzzleBoard.getValue(row, col) != solutionBoard.getValue(row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    private Position getHintTarget() throws SudokuError {
        for (int row = 0; row < SudokuConstants.BOARD_LENGTH; row++) {
            for (int col = 0; col < SudokuConstants.BOARD_LENGTH; col++) {
                if (!puzzleBoard.isPreFilled(row, col)
                        && puzzleBoard.getValue(row, col) != solutionBoard.getValue(row, col)) {
                    return Position.fromCoordinates(row, col);
                }
            }
        }

        return null;
    }
}
