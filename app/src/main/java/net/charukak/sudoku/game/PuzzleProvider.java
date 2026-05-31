package net.charukak.sudoku.game;

import net.charukak.sudoku.ui.Board;

public interface PuzzleProvider {
    Board getPuzzleBoard();
    Board getSolutionBoard();
}
