package net.charukak.sudoku.game;

public class CommandResult {
    public static enum Status {
        OK,
        INVALID,
        GAME_WON,
        QUIT
    }

    private Status status;
    private String message;

    private CommandResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static CommandResult ok(String message) {
        return new CommandResult(Status.OK, message);
    }

    public static CommandResult invalid(String message) {
        return new CommandResult(Status.INVALID, message);
    }

    public static CommandResult gameWon(String message) {
        return new CommandResult(Status.GAME_WON, message);
    }

    public static CommandResult quit() {
        return new CommandResult(Status.QUIT, "Good bye!");
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
