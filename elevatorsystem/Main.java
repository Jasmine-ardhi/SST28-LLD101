import java.util.*;

public class Main {

    public static void main(String[] args) {

        ElevatorCar e1 = new ElevatorCar(1, 0);
        ElevatorCar e2 = new ElevatorCar(2, 5);

        List<ElevatorCar> cars = Arrays.asList(e1, e2);

        ElevatorSystem system = new ElevatorSystem(cars);

        OutsideButton floor3 = new OutsideButton(3);
        floor3.pressUp(system);

        InsideButtonPanel panel = new InsideButtonPanel(e1);
        panel.pressFloor(7);

        system.setMaintenance(2);

        panel.pressAlarm(system);
    }
}