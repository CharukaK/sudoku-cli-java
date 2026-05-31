package net.charukak.sudoku.game;

import net.charukak.sudoku.model.Board;

public interface PuzzleProvider {
    Board getPuzzleBoard();
    Board getSolutionBoard();
}
