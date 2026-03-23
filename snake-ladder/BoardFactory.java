import java.util.Random;

public class BoardFactory {

    public static Board createBoard(int n, String difficulty) {
        Board board = new Board(n * n);
        Random rand = new Random();

        int count = n;

        if (difficulty.equalsIgnoreCase("hard")) {
            count = n + n / 2;
        }

        // Snakes
        int i = 0;
        while (i < count) {
            int head = rand.nextInt(board.getSize() - 1) + 2;
            int tail = rand.nextInt(head - 1) + 1;

            board.addJump(new Snake(head, tail));
            i++;
        }

        // Ladders
        i = 0;
        while (i < count) {
            int start = rand.nextInt(board.getSize() - 1) + 1;
            int end = rand.nextInt(board.getSize() - start) + start + 1;

            board.addJump(new Ladder(start, end));
            i++;
        }

        return board;
    }
}