public interface ElevatorMovementStrategy {
    void addRequest(int floor);
    void process(ElevatorCar car);
}