package net.charukak.sudoku.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CellTest {
    @Test
    public void testCellValue() throws SudokuError {
        int value = 3;
        Cell cell = new Cell(value);

        assertEquals(cell.getValue(), value);
    }

    @Test
    public void testPrintableValue() throws SudokuError {
        int value = 3;
        Cell cell = new Cell(value);

        assertEquals(cell.getPrintableString(), "3");
    }

    @Test
    public void testPrintableValueForZero() throws SudokuError {
        Cell cell = new Cell(0);
        assertEquals(cell.getPrintableString(), ".");
    }

    @Test(expected = SudokuError.class)
    public void testPreFilledValueEditing() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setPreFilled(true);
        assertTrue(cell.isPreFilled());
        cell.setValue(5);
    }

    @Test(expected = SudokuError.class)
    public void testSettingInvalidValue() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setValue(12);
    }

    @Test(expected = SudokuError.class)
    public void testSettingInvalidNegativeValue() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setValue(-1);
    }

    @Test
    public void testSettingBoundaryValues() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setValue(9);
        assertEquals(cell.getValue(), 9);

        cell.setValue(0);
        assertEquals(cell.getValue(), 0);
    }

    @Test(expected = SudokuError.class)
    public void testConstructorRejectsInvalidHighValue() throws SudokuError {
        new Cell(12);
    }

    @Test(expected = SudokuError.class)
    public void testConstructorRejectsInvalidNegativeValue() throws SudokuError {
        new Cell(-1);
    }
}
