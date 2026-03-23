import java.util.*;

class SlotManager {

    Map<SlotType, Map<Integer, PriorityQueue<ParkingSlot>>> slotMap;

    SlotManager() {
        slotMap = new HashMap<>();
    }

    void addSlot(ParkingSlot slot, int gateId) {

        
        if (!slotMap.containsKey(slot.slotType)) {
            slotMap.put(slot.slotType, new HashMap<Integer, PriorityQueue<ParkingSlot>>());
        }
    
        Map<Integer, PriorityQueue<ParkingSlot>> gateMap = slotMap.get(slot.slotType);
    
       
        if (!gateMap.containsKey(gateId)) {
    
            PriorityQueue<ParkingSlot> pq =
                    new PriorityQueue<ParkingSlot>(new Comparator<ParkingSlot>() {
                        public int compare(ParkingSlot a, ParkingSlot b) {
                            return a.distanceFromGate.get(gateId)
                                    - b.distanceFromGate.get(gateId);
                        }
                    });
    
            gateMap.put(gateId, pq);
        }    
       
        gateMap.get(gateId).add(slot);
    }

    ParkingSlot getNearestSlot(SlotType requestedType, int gateId) {

        List<SlotType> allowed = SlotType.getUpgradePath(requestedType);

        for (int i = 0; i < allowed.size(); i++) {

            SlotType type = allowed.get(i);

            Map<Integer, PriorityQueue<ParkingSlot>> gateMap =
                    slotMap.get(type);

            if (gateMap == null || gateMap.get(gateId) == null) continue;

            PriorityQueue<ParkingSlot> pq = gateMap.get(gateId);

            while (!pq.isEmpty()) {
                ParkingSlot slot = pq.peek();

                if (!slot.isOccupied) return slot;
                pq.poll(); // remove occupied (lazy cleanup)
            }
        }

        return null;
    }

    Map<SlotType, Integer> getAvailableSlots() {

        Map<SlotType, Integer> result = new HashMap<>();

        for (SlotType type : slotMap.keySet()) {
            int count = 0;

            Map<Integer, PriorityQueue<ParkingSlot>> gateMap =
                    slotMap.get(type);

            for (int gateId : gateMap.keySet()) {
                for (ParkingSlot slot : gateMap.get(gateId)) {
                    if (!slot.isOccupied) count++;
                }
            }

            result.put(type, count);
        }

        return result;
    }
}