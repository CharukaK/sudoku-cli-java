package net.charukak.sudoku.launcher;

import java.util.Scanner;

import net.charukak.sudoku.controller.CommandParser;
import net.charukak.sudoku.game.PuzzleProvider;
import net.charukak.sudoku.game.StaticPuzzleProvider;
import net.charukak.sudoku.game.SudokuGame;
import net.charukak.sudoku.model.Command;

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

            System.out.print("\nEnter new command: ");
            String cmdStr = scanner.nextLine();
            Command cmd = parser.parse(cmdStr.toLowerCase());

            if (cmd.getType() == Command.Type.QUIT) {
                break;
            } else {
                sudokuGame.apply(cmd);
            }
        }

        scanner.close();

    }
}
