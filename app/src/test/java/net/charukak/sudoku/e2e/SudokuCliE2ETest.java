package net.charukak.sudoku.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import net.charukak.sudoku.controller.CommandParser;
import net.charukak.sudoku.game.Command;
import net.charukak.sudoku.game.CommandResult;
import net.charukak.sudoku.game.PuzzleProvider;
import net.charukak.sudoku.game.StaticPuzzleProvider;
import net.charukak.sudoku.game.SudokuGame;
import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.Position;
import net.charukak.sudoku.model.SudokuError;

@DisplayName("Sudoku CLI E2E")
class SudokuCliE2ETest {

    private static class Fixture {
        final SudokuGame game;
        final CommandParser parser;
        final Board puzzle;
        final Board solution;

        Fixture() {
            PuzzleProvider provider = new StaticPuzzleProvider();
            this.puzzle = provider.getPuzzleBoard();
            this.solution = provider.getSolutionBoard();
            this.game = new SudokuGame(puzzle, solution);
            this.parser = new CommandParser();
        }

        CommandResult exec(String raw) {
            Command cmd = parser.parse(raw.toLowerCase());
            return game.apply(cmd);
        }
    }

    @ParameterizedTest(name = "[{index}] {0} -> {1}")
    @MethodSource("singleCommandCases")
    @DisplayName("Single command flows return expected status and message")
    void singleCommandFlows(String input, CommandResult.Status expectedStatus, String expectedMessage) {
        Fixture fixture = new Fixture();

        CommandResult result = fixture.exec(input);

        assertEquals(expectedStatus, result.getStatus());
        assertEquals(expectedMessage, result.getMessage());
    }

    static Stream<Arguments> singleCommandCases() {
        return Stream.of(
                Arguments.of("A3 4", CommandResult.Status.OK, "Move accepted."),
                Arguments.of("A1 6", CommandResult.Status.INVALID, "Invalid move. A1 is pre-filled."),
                Arguments.of("check", CommandResult.Status.OK, "No rule violations detected."),
                Arguments.of("hint", CommandResult.Status.OK, "Hint: Cell A3 = 4"),
                Arguments.of("quit", CommandResult.Status.QUIT, "Good bye!"));
    }

    @ParameterizedTest(name = "[{index}] {0} -> {1}")
    @MethodSource("violationCases")
    @DisplayName("check reports expected violation after an accepted move")
    void checkReportsViolationAfterMove(String moveCommand, String expectedViolationMessage) {
        Fixture fixture = new Fixture();

        CommandResult moveResult = fixture.exec(moveCommand);
        assertEquals(CommandResult.Status.OK, moveResult.getStatus());
        assertEquals("Move accepted.", moveResult.getMessage());

        CommandResult checkResult = fixture.exec("check");

        assertEquals(CommandResult.Status.VIOLATION, checkResult.getStatus());
        assertEquals(expectedViolationMessage, checkResult.getMessage());
    }

    static Stream<Arguments> violationCases() {
        return Stream.of(
                Arguments.of("A3 3", "Number 3 already exists in Row A."),
                Arguments.of("C1 5", "Number 5 already exists in Column 1."),
                Arguments.of("B3 8", "Number 8 already exists in Column 3."));
    }

    @ParameterizedTest(name = "[{index}] ''{0}'' invalid input")
    @MethodSource("invalidInputCases")
    @DisplayName("Invalid raw inputs return INVALID status with expected message behavior")
    void invalidInputsReturnInvalidStatus(
            String input,
            String expectedMessage,
            boolean containsMatch) {
        Fixture fixture = new Fixture();

        CommandResult result = fixture.exec(input);

        assertEquals(CommandResult.Status.INVALID, result.getStatus());
        if (containsMatch) {
            assertTrue(result.getMessage().contains(expectedMessage));
        } else {
            assertEquals(expectedMessage, result.getMessage());
        }
    }

    static Stream<Arguments> invalidInputCases() {
        return Stream.of(
                Arguments.of("", "Empty command.", false),
                Arguments.of("   ", "Empty command.", false),
                Arguments.of("foo", "Invalid command format", false),
                Arguments.of("a1 5 extra", "Invalid command format", false),
                Arguments.of("a1 abc", "For input string: \"abc\"", true),
                Arguments.of("j1 4", "Malformed input position", false),
                Arguments.of("aa 4", "Malformed input position", false));
    }

    @Test
    @DisplayName("set then clear on editable cell restores zero")
    void clearAfterSetRestoresZero() throws SudokuError {
        Fixture fixture = new Fixture();

        CommandResult setResult = fixture.exec("a3 4");
        assertEquals(CommandResult.Status.OK, setResult.getStatus());
        assertEquals(4, fixture.puzzle.getValue(0, 2));

        CommandResult clearResult = fixture.exec("a3 clear");
        assertEquals(CommandResult.Status.OK, clearResult.getStatus());
        assertEquals("Move accepted.", clearResult.getMessage());
        assertEquals(0, fixture.puzzle.getValue(0, 2));
    }

    @Test
    @DisplayName("final correct move triggers GAME_WON")
    void finalMoveTriggersGameWon() throws SudokuError {
        Fixture fixture = new Fixture();

        Position finalPosition = null;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!fixture.puzzle.isPreFilled(row, col)) {
                    Position pos = Position.fromCoordinates(row, col);
                    int value = fixture.solution.getValue(row, col);

                    if (finalPosition == null) {
                        finalPosition = pos;
                        continue;
                    }

                    CommandResult stepResult = fixture.exec(pos.toString().toUpperCase() + " " + value);
                    assertEquals(CommandResult.Status.OK, stepResult.getStatus());
                }
            }
        }

        int finalValue = fixture.solution.getValue(finalPosition.row(), finalPosition.col());
        CommandResult finalResult = fixture.exec(finalPosition.toString().toUpperCase() + " " + finalValue);

        assertEquals(CommandResult.Status.GAME_WON, finalResult.getStatus());
        assertEquals("You have successfully completed the Sudoku puzzle!", finalResult.getMessage());
    }
}
