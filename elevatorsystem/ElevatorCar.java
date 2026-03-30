public class ElevatorCar {

    private int id;
    private int currentFloor;
    private ElevatorState state;

    private Door door;
    private WeightSensor weightSensor;
    private ElevatorController controller;

    public ElevatorCar(int id, int floor) {
        this.id = id;
        this.currentFloor = floor;
        this.state = ElevatorState.IDLE;

        this.door = new Door();
        this.weightSensor = new WeightSensor();
        this.controller = new ElevatorController(this);
    }

    public int getId() { return id; }

    public int getCurrentFloor() { return currentFloor; }

    public void setCurrentFloor(int floor) { this.currentFloor = floor; }

    public ElevatorState getState() { return state; }

    public void setState(ElevatorState state) { this.state = state; }

    public Door getDoor() { return door; }

    public WeightSensor getWeightSensor() { return weightSensor; }

    public ElevatorController getController() { return controller; }

    public boolean isAvailable() {
        return state != ElevatorState.MAINTENANCE;
    }

    public void stop() {
        System.out.println("Elevator " + id + " STOPPED!");
    }
}