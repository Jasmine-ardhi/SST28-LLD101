public class ElevatorController {

    private ElevatorCar car;
    private ElevatorMovementStrategy strategy;

    public ElevatorController(ElevatorCar car) {
        this.car = car;
        this.strategy = new SimpleMovementStrategy(); // simple hardcoded
    }

    public void addRequest(int floor) {
        strategy.addRequest(floor);
    }

    public void process() {
        strategy.process(car);
    }
}