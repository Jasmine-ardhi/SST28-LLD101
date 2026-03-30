import java.util.*;

public interface ElevatorSchedulingStrategy {
    ElevatorCar selectElevator(int floor, Direction dir, List<ElevatorCar> cars);
}