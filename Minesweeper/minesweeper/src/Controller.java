import models.Board;
import models.Cell;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Board grid;
    private List<Cell> visitedCells;
    private int safeLocationsCount;
    private boolean gameLost;
    private boolean gameWon;

    public Controller(Board grid) {
        this.grid = grid;
        this.visitedCells = new ArrayList<>();
        this.safeLocationsCount = (int)(Math.pow(this.grid.getBoardLength(), 2) - this.grid.getMinesCount());
        this.gameLost = false;
        this.gameWon = false;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void showMessage() {
        if (isGameLost()) {
            System.out.println("You lost!");
            this.grid.revealMines();
        }

        if (isGameWon())
            System.out.println("You won!");

        System.out.println(this.grid.toString());
    }

    public void click(int row, int col) {
        Cell current = this.grid.getMatrix()[row][col];

        if (this.visitedCells.contains(current)) {
            System.out.println("Already been here!");
            return;
        }

        if (current.isMine()) {
            this.gameLost = true;
            return;
        }

        current.setRevealed(true);

        int neighbours = this.grid.countNeighbours(current);

        if (neighbours != 0) {
            this.visitedCells.add(current);
            current.setRealIcon(String.valueOf(neighbours));
        }
        else {
            current.setRealIcon("\033[0;34m" + "." + "\033[0m"); //Applying blue color and resetting after so that only '.' symbol is affected.
            floodFill(current.getX(), current.getY(), current);
        }

        // if we visited all safe locations - game is won
        if (this.visitedCells.size() == safeLocationsCount && this.gameLost == false) {
            this.gameWon = true;
            return;
        }
    }

    // using Flood fill algorithm to find all safe adjacent cells
    private void floodFill(int x, int y, Cell cell) {
        if (x < 0 || x >= this.grid.getMatrix().length || y < 0 || y >= this.grid.getMatrix().length)
            return;
        if (this.grid.countNeighbours(this.grid.getMatrix()[x][y]) != 0
                || this.visitedCells.contains(this.grid.getMatrix()[x][y]))
            return;

        // We reach this point if the cell has no neighbouring mines and the cell has not been visited already
        this.grid.getMatrix()[x][y] = cell;
        this.visitedCells.add(this.grid.getMatrix()[x][y]);

        floodFill(x+1, y, cell);
        floodFill(x-1, y, cell);
        floodFill(x, y+1, cell);
        floodFill(x, y-1, cell);
    }
}
