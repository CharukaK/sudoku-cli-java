package net.charukak.sudoku.model;

public class Command {
    public static enum Type {
        SET_VALUE, CLEAR_VALUE, HINT, CHECK, QUIT, INVALID
    }

    private Type type;
    private Position position;
    private Integer value;
    private String errorMessage;

    // public static Command set(Position p, int value) {
    // // return new Command
    // }

    private Command(Type type, Position position, Integer value, String err) {
        this.type = type;
        this.position = position;
        this.value = value;
        this.errorMessage = err;
    }

    public static Command set(Position p, int value) {
        return new Command(Type.SET_VALUE, p, value, null);
    }

    public static Command clear(Position p) {
        return new Command(Type.CLEAR_VALUE, p, null, null);
    }

    public static Command hint() {
        return new Command(Type.HINT, null, null, null);
    }

    public static Command check() {
        return new Command(Type.CHECK, null, null, null);
    }

    public static Command quite() {
        return new Command(Type.QUIT, null, null, null);
    }

    public static Command invalid(String err) {
        return new Command(Type.INVALID, null, null, err);
    }

    public Type getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public Integer getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
