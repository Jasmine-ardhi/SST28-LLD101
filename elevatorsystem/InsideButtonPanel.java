public class InsideButtonPanel {

    private ElevatorCar car;

    public InsideButtonPanel(ElevatorCar car) {
        this.car = car;
    }

    public void pressFloor(int floor) {
        car.getController().addRequest(floor);
    }

    public void pressOpen() {
        car.getDoor().open();
    }

    public void pressClose() {
        if (!car.getWeightSensor().isOverWeight()) {
            car.getDoor().close();
        }
    }

    public void pressAlarm(ElevatorSystem system) {
        system.triggerAlarm();
    }
}