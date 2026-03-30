import java.util.*;

public class SimpleMovementStrategy implements ElevatorMovementStrategy {

    private PriorityQueue<Integer> pq = new PriorityQueue<>();

    public void addRequest(int floor) {
        pq.offer(floor);
    }

    public void process(ElevatorCar car) {

        if (car.getState() == ElevatorState.MAINTENANCE) return;

        if (pq.isEmpty()) {
            car.setState(ElevatorState.IDLE);
            return;
        }

        int target = pq.peek();

        if (car.getCurrentFloor() < target) {
            car.setState(ElevatorState.UP);
            car.setCurrentFloor(car.getCurrentFloor() + 1);
        } else if (car.getCurrentFloor() > target) {
            car.setState(ElevatorState.DOWN);
            car.setCurrentFloor(car.getCurrentFloor() - 1);
        } else {
            if (car.getWeightSensor().isOverWeight()) {
                System.out.println("Overweight! Cannot close door");
                return;
            }

            car.getDoor().open();
            System.out.println("Elevator " + car.getId() + " reached floor " + target);
            pq.poll();
            car.getDoor().close();
        }
    }
}