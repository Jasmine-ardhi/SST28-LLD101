import java.util.*;

public class Game {
    private Queue<Player> players;
    private Board board;
    private DiceStrategy dice;

    public Game(int n, int playerCount, String difficulty) {
        this.board = BoardFactory.createBoard(n, difficulty);
        this.dice = new NormalDice();
        this.players = new LinkedList<>();

        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("P" + i));
        }
    }

    public void start() {
        while (players.size() > 1) {
            Player current = players.poll();

            int roll = dice.roll();
            System.out.println(current.getName() + " rolled: " + roll);

            int newPos = current.getPosition() + roll;

            if (newPos > board.getSize()) {
                System.out.println("Move exceeds board.");
                players.add(current);
                continue;
            }

            newPos = board.checkJump(newPos);
            current.setPosition(newPos);

            System.out.println(current.getName() + " at " + newPos);

            if (newPos == board.getSize()) {
                System.out.println(current.getName() + " WON!");
            } else {
                players.add(current);
            }

            System.out.println("----------------");
        }

        System.out.println("Game Over");
    }
}