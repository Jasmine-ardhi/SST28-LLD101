import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.print("Enter players: ");
        int p = sc.nextInt();

        System.out.print("Enter difficulty: ");
        String d = sc.next();

        Game game = new Game(n, p, d);
        game.start();
    }
}