package net.charukak.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Cell")
public class CellTest {
    @Test
    @DisplayName("returns the initialized value")
    void testCellValue() throws SudokuError {
        int value = 3;
        Cell cell = new Cell(value);

        assertEquals(cell.getValue(), value);
    }

    @Test
    @DisplayName("formats non-zero value as digits")
    void testPrintableValue() throws SudokuError {
        int value = 3;
        Cell cell = new Cell(value);

        assertEquals(cell.getPrintableString(), "3");
    }

    @Test
    @DisplayName("formats zero value as dot")
    void testPrintableValueForZero() throws SudokuError {
        Cell cell = new Cell(0);
        assertEquals(cell.getPrintableString(), "_");
    }

    @Test
    @DisplayName("throws when editing a pre-filled cell")
    void testPreFilledValueEditing() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setPreFilled(true);
        assertTrue(cell.isPreFilled());
        assertThrows(SudokuError.class, () -> cell.setValue(5));
    }

    @Test
    @DisplayName("throws when setting value above range")
    void testSettingInvalidValue() {
        assertThrows(
                SudokuError.class,
                () -> {
                    Cell cell = new Cell(1);
                    cell.setValue(12);
                });
    }

    @Test
    @DisplayName("throws when setting negative value")
    void testSettingInvalidNegativeValue() {
        assertThrows(
                SudokuError.class,
                () -> {
                    Cell cell = new Cell(1);
                    cell.setValue(-1);
                });
    }

    @Test
    @DisplayName("accepts boundary values zero and nine")
    void testSettingBoundaryValues() throws SudokuError {
        Cell cell = new Cell(1);
        cell.setValue(9);
        assertEquals(cell.getValue(), 9);

        cell.setValue(0);
        assertEquals(cell.getValue(), 0);
    }

    @Test
    @DisplayName("constructor rejects value above range")
    void testConstructorRejectsInvalidHighValue() {
        assertThrows(SudokuError.class, () -> new Cell(12));
    }

    @Test
    @DisplayName("constructor rejects negative value")
    void testConstructorRejectsInvalidNegativeValue() {
        assertThrows(SudokuError.class, () -> new Cell(-1));
    }
}
