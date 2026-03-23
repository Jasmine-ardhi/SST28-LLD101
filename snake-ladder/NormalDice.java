import java.util.Random;

public class NormalDice implements DiceStrategy {
    private Random rand = new Random();

    public int roll() {
        return rand.nextInt(6) + 1;
    }
}