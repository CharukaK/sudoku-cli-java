# Sudoku CLI

A command-line Sudoku game written in Java

## Build from source

### Prerequisites

- JDK 21+ - required to build and run

### Steps to build

Clone the repository into your local environment

```bash
git clone https://github.com/CharukaK/sudoku-cli-java.git
```

Run the command `./gradlew clean build` from the repository root

## Running the App

After building the app you can then run the application using 

```bash
java -jar app/build/libs/app.jar
```

You will be prompted with the game as follows
![game_greet](./docs/game_greeting.png)

## Supported Commands

The following commands are available once the game has started:

| Command | Syntax | Description |
|---------|--------|-------------|
| Set value | `<ref> <1-9>` (e.g., `A3 4`) | Enter a value into a cell |
| Clear | `<ref> clear` (e.g., `A3 clear`) | Clear a cell's value |
| Hint | `hint` | Reveal the correct value for the next incorrect or empty cell |
| Check | `check` | Validate the board for rule violations |
| Quit | `quit` | Exit the game |

Cell references use standard Sudoku notation: a letter `A`–`I` for the row and a digit `1`–`9` for the column (e.g., `A1`, `C3`, `H8`).

## Design & Assumptions

The app is split into four packages:
- **model** — Board, Cell, Position (game state)
- **game** — SudokuGame, PuzzleGenerator, SudokuValidator (game logic)
- **controller** — CommandParser (input parsing)
- **launcher** — App, GameLoop (entry point and UI loop)

### Design Decisions

- **30 clues per puzzle** — the generator removes cells until 30 remain,
verifying a unique solution each time
- **Hint scans left-to-right, top-to-bottom** — returns the first empty or
wrong cell's correct value
- **Validation is on-demand** — type `check` to find rule violations;
individual moves only check range and pre-filled status
- **Dynamic puzzle generation by default** — the game generates a fresh puzzle
every session using backtracking seeded with diagonal 3×3 blocks

### Assumptions

- The terminal supports ANSI escape codes (Linux and macOS tested; Windows not guaranteed)
- Single-user, single-threaded CLI with no save/load

### Board generation flow

```mermaid
flowchart TD
    G[GameLoop startup] --> P[DynamicPuzzleProvider]
    P --> PG[PuzzleGenerator]
    PG --> S1[Seed 3 diagonal 3×3 boxes\nwith random 1-9 permutations]
    S1 --> S2[Backtrack fill remaining cells\nto produce a valid solution]
    S2 --> S3[Clone solved board as reference]
    S3 --> S4[Remove cells until 30 clues remain,\nverifying unique solution each time]
    S4 --> S5[Return puzzle Board + solution Board]
```
