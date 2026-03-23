import java.util.*;

public class Board {
    private int size;
    private Map<Integer, Jump> jumps;

    public Board(int size) {
        this.size = size;
        this.jumps = new HashMap<>();
    }

    public int getSize() {
        return size;
    }

    public void addJump(Jump jump) {
        jumps.put(jump.getStart(), jump);
    }

    public int checkJump(int position) {
        if (jumps.containsKey(position)) {
            Jump j = jumps.get(position);

            if (j instanceof Snake) {
                System.out.println("Bitten by Snake!");
            } else {
                System.out.println("Climbed Ladder!");
            }

            return j.getEnd();
        }
        return position;
    }
}