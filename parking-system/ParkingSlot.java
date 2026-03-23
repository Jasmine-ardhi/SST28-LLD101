import java.util.*;

class ParkingSlot {
    int slotId;
    SlotType slotType;
    boolean isOccupied;
    Vehicle vehicle;

    Map<Integer, Integer> distanceFromGate;

    ParkingSlot(int id, SlotType type) {
        this.slotId = id;
        this.slotType = type;
        this.isOccupied = false;
        this.distanceFromGate = new HashMap<>();
    }
}