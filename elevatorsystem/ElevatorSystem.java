import java.util.*;

public class ElevatorSystem {

    private List<ElevatorCar> cars;
    private ElevatorSchedulingStrategy strategy;

    public ElevatorSystem(List<ElevatorCar> cars) {
        this.cars = cars;
        this.strategy = new NearestElevatorStrategy(); // simple
    }

    public void requestElevator(int floor, Direction dir) {

        ElevatorCar car = strategy.selectElevator(floor, dir, cars);

        if (car == null) {
            System.out.println("No elevator available");
            return;
        }

        System.out.println("Elevator " + car.getId() + " assigned to floor " + floor);
        car.getController().addRequest(floor);

        process();
    }

    public void process() {
        for (int i = 0; i < 8; i++) {
            for (ElevatorCar car : cars) {
                car.getController().process();
            }
        }
    }

    public void triggerAlarm() {
        System.out.println("ALARM!");
        for (ElevatorCar car : cars) {
            car.stop();
        }
    }

    public void setMaintenance(int id) {
        for (ElevatorCar car : cars) {
            if (car.getId() == id) {
                car.setState(ElevatorState.MAINTENANCE);
                System.out.println("Elevator " + id + " under maintenance");
            }
        }
    }
}