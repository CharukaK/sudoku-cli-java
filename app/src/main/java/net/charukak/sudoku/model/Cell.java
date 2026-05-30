package net.charukak.sudoku.model;

public class Cell implements PrintableElement {
    private int value;
    private boolean isFixed;

    public Cell(int value) {
        this.value = value;
    }

    @Override
    public String getPrintableString() {
        return this.value == 0 ? "." : "" + this.value;
    }

    public int getValue() {
        return this.value;
    }

    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public void setValue(int name) {
        this.value = name;
    }

    public Object getIsFixed() throws SudokuError {
        if (isFixed) {
            throw new SudokuError("you cannot edit a fixed value string");
        }
        return this.isFixed;
    }
}
