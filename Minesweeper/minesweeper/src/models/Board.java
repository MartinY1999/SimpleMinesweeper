package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int boardLength;
    private int minesCount;
    private Cell[][] matrix;

    public Board(String difficulty) {
        //Setting fixed difficulties
        if (difficulty.equals("BEGINNER")) {
            this.boardLength = 9;
            this.minesCount = 10;
        }
        if (difficulty.equals("INTERMEDIATE")) {
            this.boardLength = 16;
            this.minesCount = 40;
        }
        if (difficulty.equals("ADVANCED")) {
            this.boardLength = 24;
            this.minesCount = 99;
        }

        //creating the grid
        this.matrix = make2dArray();
    }

    public Board(Board other) {
        this.boardLength = other.boardLength;
        this.minesCount = other.minesCount;

        try {
            for (int row = 0; row < other.matrix.length; row++) {
                for (int col = 0; col < other.matrix.length; col++) {
                    this.matrix[col][row] = other.matrix[row][col];
                }
            }
        }
        catch (Exception message) {
            throw new ArrayIndexOutOfBoundsException("Incompatible matrices");
        }
    }

    public int getBoardLength() {
        return boardLength;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    private Cell[][] make2dArray() {
        Cell[][] matrix = new Cell[this.boardLength][this.boardLength];
        // first we're filling the board matrix with mines
        fillMines(matrix);

        // then we fill the matrix with the remaining empty elements
        for (int row = 0; row < this.boardLength; row++) {
            for (int col = 0; col < this.boardLength; col++) {
                if (matrix[row][col] == null)
                    matrix[row][col] = new Cell(row, col);
            }
        }

        return matrix;
    }

    private void fillMines(Cell[][] matrix) {
        List<Integer>[] positions = new ArrayList[this.boardLength]; //storing mine positions (row, col)
        for (int i = 0; i < this.boardLength; i++) {
            positions[i] = new ArrayList<>();
        }

        Random generator = new Random();

        for (int i = 0; i < this.minesCount; i++) {
            int generatedX = generator.nextInt(this.boardLength);
            int generatedY = generator.nextInt(this.boardLength);

            if (positions[generatedX].contains(generatedY))
                continue;
            else
                positions[generatedX].add(generatedY);
        }

        //inserting mines in to the grid
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].size() == 0)
                continue;
            else {
                for (int col : positions[i]) {
                    matrix[i][col] = new Cell(i, col);
                    matrix[i][col].setRealIcon("\033[0;31m" + "*" + "\033[0m"); //Applying red color and resetting after so that only '*' symbol is affected.
                    matrix[i][col].setMine(true);
                }
            }
        }
    }

    //method for counting the surrounding cells to see if some of them are mines
    public int countNeighbours(Cell cell) {
        int total = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (this.matrix[cell.getX() + i][cell.getY() + j].isMine())
                        total++;
                }
                catch (Exception message) {
                    continue;
                }
            }
        }

        return total;
    }

    //method used when the game has ended
    public void revealMines() {
        for (int i = 0; i < this.boardLength; i++) {
            for (int j = 0; j < this.boardLength; j++) {
                if (!this.matrix[i][j].isRevealed() && this.matrix[i][j].isMine())
                    this.matrix[i][j].setRevealed(true);
            }
        }
    }

    //There is mismatch of index and element of the matrix when the index is higher than 10. Needs some optimization.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Current status of Board: \n");

        //appending starting row
        builder.append("   ");
        for (int i = 0; i < this.boardLength; i++) {
            builder.append(i);
            if (i != this.boardLength - 1)
                builder.append(" ");
        }

        builder.append("\n");

        //appending rest
        for (int i = 0; i < this.boardLength; i++) {
            if (i < 10)
                builder.append(i + "  ");
            else
                builder.append(i + " ");
            for (int j = 0; j < this.boardLength; j++) {
                builder.append(this.matrix[i][j].isRevealed() ? this.matrix[i][j].getRealIcon() : this.matrix[i][j].getHiddenIcon());
                if (j != this.boardLength - 1) {
                    builder.append(" ");
                }
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}
