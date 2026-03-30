import java.util.*;

public class NearestElevatorStrategy implements ElevatorSchedulingStrategy {

    public ElevatorCar selectElevator(int floor, Direction dir, List<ElevatorCar> cars) {

        ElevatorCar best = null;
        int min = Integer.MAX_VALUE;

        for (ElevatorCar car : cars) {
            if (!car.isAvailable()) continue;

            int dist = Math.abs(car.getCurrentFloor() - floor);

            if (dist < min) {
                min = dist;
                best = car;
            }
        }

        return best;
    }
}