package net.charukak.sudoku.controller;

import net.charukak.sudoku.game.Command;
import net.charukak.sudoku.model.Position;

public class CommandParser {
    public Command parse(String cmd) {
        if (cmd == null || cmd.isBlank()) {
            return Command.invalid("Empty command.");
        }

        switch (cmd.trim().toLowerCase()) {
            case "hint":
                return Command.hint();
            case "check":
                return Command.check();
            case "quit":
                return Command.quite();
            default: {
                String[] parts = cmd.trim().split("\s+");

                if (parts.length != 2) {
                    return Command.invalid("Invalid command format");
                }

                try {
                    Position p = Position.fromRef(parts[0]);

                    if (parts[1].equals("clear")) {
                        return Command.clear(p);
                    }

                    Integer val = Integer.parseInt(parts[1]);

                    return Command.set(p, val);
                } catch (Exception e) {
                    return Command.invalid(e.getMessage());
                }
            }
        }
    }
}
