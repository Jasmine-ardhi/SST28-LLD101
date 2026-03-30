
public class OutsideButton {

    private int floor;

    public OutsideButton(int floor) {
        this.floor = floor;
    }

    public void pressUp(ElevatorSystem system) {
        system.requestElevator(floor, Direction.UP);
    }

    public void pressDown(ElevatorSystem system) {
        system.requestElevator(floor, Direction.DOWN);
    }
} 
