package net.charukak.sudoku.launcher;

import java.util.Scanner;

import net.charukak.sudoku.controller.CommandParser;
import net.charukak.sudoku.game.PuzzleProvider;
import net.charukak.sudoku.game.StaticPuzzleProvider;
import net.charukak.sudoku.game.SudokuGame;
import net.charukak.sudoku.game.Command;
import net.charukak.sudoku.game.CommandResult;

public class GameLoop {
    void Run() {
        boolean showGreeting = true;
        Scanner scanner = new Scanner(System.in);
        CommandParser parser = new CommandParser();
        PuzzleProvider provider = new StaticPuzzleProvider();
        SudokuGame sudokuGame = new SudokuGame(provider.getPuzzleBoard(), provider.getSolutionBoard());

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (showGreeting) {
                System.out.println("Welcome to Sudoku!\n");
                showGreeting = false;
            }
            System.out.println(sudokuGame.getBoardString());

            System.out.print("\nEnter command (e.g., A3 4, C5 clear, hint, check): ");
            String cmdStr = scanner.nextLine();
            Command cmd = parser.parse(cmdStr.toLowerCase());
            CommandResult result = sudokuGame.apply(cmd);

            if (result.getMessage() != null && !result.getMessage().isBlank()) {
                System.out.println("\n" + result.getMessage());
            }

            if (result.getStatus() == CommandResult.Status.QUIT
                    || result.getStatus() == CommandResult.Status.GAME_WON) {
                break;
            }
        }

        scanner.close();

    }
}
