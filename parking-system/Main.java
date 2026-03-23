
public class Main {

    public static void main(String[] args) {

        // Create Parking Lot
        ParkingLot parkingLot = new ParkingLot();

        // -------------------------------
        // Create Slots
        // -------------------------------
        ParkingSlot s1 = new ParkingSlot(1, SlotType.SMALL);
        ParkingSlot s2 = new ParkingSlot(2, SlotType.MEDIUM);
        ParkingSlot s3 = new ParkingSlot(3, SlotType.LARGE);
        ParkingSlot s4 = new ParkingSlot(4, SlotType.MEDIUM);

        // -------------------------------
        // Set distance from gates
        // -------------------------------
        // Gate 1
        s1.distanceFromGate.put(1, 10);
        s2.distanceFromGate.put(1, 5);
        s3.distanceFromGate.put(1, 20);
        s4.distanceFromGate.put(1, 15);

        // Gate 2
        s1.distanceFromGate.put(2, 25);
        s2.distanceFromGate.put(2, 10);
        s3.distanceFromGate.put(2, 5);
        s4.distanceFromGate.put(2, 30);

        // -------------------------------
        // Add slots to SlotManager
        // -------------------------------
        parkingLot.slotManager.addSlot(s1, 1);
        parkingLot.slotManager.addSlot(s2, 1);
        parkingLot.slotManager.addSlot(s3, 1);
        parkingLot.slotManager.addSlot(s4, 1);

        parkingLot.slotManager.addSlot(s1, 2);
        parkingLot.slotManager.addSlot(s2, 2);
        parkingLot.slotManager.addSlot(s3, 2);
        parkingLot.slotManager.addSlot(s4, 2);

        // -------------------------------
        // Create Vehicles
        // -------------------------------
        Vehicle v1 = new Vehicle("V1", "Bike");
        Vehicle v2 = new Vehicle("V2", "Car");
        Vehicle v3 = new Vehicle("V3", "Truck");

        // -------------------------------
        // Park Vehicles
        // -------------------------------
        ParkingTicket t1 = parkingLot.park(v1, 0, SlotType.SMALL, 1);
        ParkingTicket t2 = parkingLot.park(v2, 0, SlotType.MEDIUM, 1);
        ParkingTicket t3 = parkingLot.park(v3, 0, SlotType.SMALL, 2); // upgrade case

        // -------------------------------
        // Status
        // -------------------------------
        parkingLot.status();

        // -------------------------------
        // Exit Vehicles
        // -------------------------------
        double bill1 = parkingLot.exit(t1, 7200); // 2 hours
        double bill2 = parkingLot.exit(t2, 3600); // 1 hour
        double bill3 = parkingLot.exit(t3, 10800); // 3 hours

        // -------------------------------
        // Print Bills
        // -------------------------------
        System.out.println("Bill1: " + bill1);
        System.out.println("Bill2: " + bill2);
        System.out.println("Bill3: " + bill3);

        // -------------------------------
        // Final Status
        // -------------------------------
        parkingLot.status();
    }
} 