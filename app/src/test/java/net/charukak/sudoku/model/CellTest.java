package net.charukak.sudoku.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CellTest {
    @Test
    public void testCellValue() {
        int value = 3;
        Cell cell = new Cell(value);

        assertEquals(cell.getValue(), value);
    }

    @Test
    public void testPrintableVlaue() {
        int value = 3;
        Cell cell = new Cell(value);

        assertEquals(cell.getPrintableString(), "3");
    }

    @Test
    public void testPrintableVlaueForZero() {
        Cell cell = new Cell(0);
        assertEquals(cell.getPrintableString(), ".");
    }

    @Test(expected = SudokuError.class)
    public void testFixedValueEditing() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setIsFixed(true);
        assertEquals(cell.getIsFixed(), true);
        cell.setValue(5);
    }
}
