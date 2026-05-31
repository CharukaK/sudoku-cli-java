package net.charukak.sudoku.launcher;

import java.util.Scanner;

import net.charukak.sudoku.controller.CommandParser;
import net.charukak.sudoku.model.Board;
import net.charukak.sudoku.model.Command;

public class GameLoop {
    void Run() {
        Board b = new Board();
        boolean showGreeting = true;
        Scanner scanner = new Scanner(System.in);
        CommandParser parser = new CommandParser();
        Command cmd = null;

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (showGreeting) {
                System.out.println("Welcome to Sudoku!\n");
                showGreeting = false;
            }
            System.out.println(b.getPrintableString());
            if (cmd != null) {
                System.out.println("The command was:" + cmd.getType() + "\n\n"); // TODO: Add messages and hints and
            }

            System.out.print("\nEnter new command: ");
            String cmdStr = scanner.nextLine();
            cmd = parser.parse(cmdStr);

            if (cmd.getType() == Command.Type.QUIT) {
                break;
            }
        }

        scanner.close();

    }
}
