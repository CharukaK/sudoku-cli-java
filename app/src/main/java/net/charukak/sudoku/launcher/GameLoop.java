package net.charukak.sudoku.launcher;

import java.util.Scanner;

import net.charukak.sudoku.controller.CommandParser;
import net.charukak.sudoku.game.Command;
import net.charukak.sudoku.game.CommandResult;
import net.charukak.sudoku.game.DynamicPuzzleProvider;
import net.charukak.sudoku.game.PuzzleProvider;
import net.charukak.sudoku.game.SudokuGame;

public class GameLoop {
    void run() {
        boolean showGreeting = true;
        Scanner scanner = new Scanner(System.in);
        CommandParser parser = new CommandParser();
        PuzzleProvider provider = new DynamicPuzzleProvider();
        SudokuGame sudokuGame = new SudokuGame(provider.getPuzzleBoard(), provider.getSolutionBoard());
        String message = null;
        boolean gameWon = false;

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (showGreeting) {
                System.out.println("Welcome to Sudoku!\n");
                showGreeting = false;
            }
            System.out.println(sudokuGame.getBoardString());
            if (message != null ) {
                System.out.println("\n" + message);
            }

            if (gameWon) {
                break;
            }

            System.out.print("\nEnter command (e.g., A3 4, C5 clear, hint, check): ");
            String cmdStr = scanner.nextLine();
            Command cmd = parser.parse(cmdStr.toLowerCase());
            CommandResult result = sudokuGame.apply(cmd);
            message = result.getMessage();

            if (result.getStatus() == CommandResult.Status.QUIT) {
                System.out.println("\n" + result.getMessage());
                break;
            }
            if (result.getStatus() == CommandResult.Status.GAME_WON) {
                gameWon = true;
            }
        }

        scanner.close();

    }
}
