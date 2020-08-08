import models.Board;

import java.util.*;

public class Main {
    public static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        String difficulty = reader.nextLine();
        Board grid = new Board(difficulty);
        Controller gameController = new Controller(grid);

        System.out.println(grid.toString());

        while (!gameController.isGameLost() && !gameController.isGameWon()) {
            System.out.println("Enter your move, (row, column)\n ->");

            String[] line = reader.nextLine().split(" ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);

            gameController.click(x, y);
            gameController.showMessage();
        }
    }
}
