class ParkingLot {

    SlotManager slotManager;
    PricingStrategy pricing;

    ParkingLot() {
        slotManager = new SlotManager();
        pricing = new HourlyPricingStrategy();
    }

   
    public ParkingTicket park(Vehicle vehicle,long entryTime,SlotType requestedSlotType,int entryGateId) {

        ParkingSlot slot = slotManager.getNearestSlot(requestedSlotType, entryGateId);

        if (slot == null) {
            System.out.println("No slot available");
            return null;
        }

        slot.isOccupied = true;
        slot.vehicle = vehicle;

        return new ParkingTicket(vehicle, slot, entryTime, entryGateId);
    }

    
    public double exit(ParkingTicket ticket, long exitTime) {

        long duration = exitTime - ticket.entryTime;
        long hours = (long) Math.ceil(duration / 3600.0);

        double amount =
                pricing.calculate(hours, ticket.slot.slotType);

        ticket.slot.isOccupied = false;
        ticket.slot.vehicle = null;

        return amount;
    }

    
    public void status() {

        java.util.Map<SlotType, Integer> map =
                slotManager.getAvailableSlots();

        System.out.println("Available Slots:");
        for (SlotType type : map.keySet()) {
            System.out.println(type + " : " + map.get(type));
        }
    }
}