class ParkingTicket {
    static int counter = 1;

    int ticketId;
    Vehicle vehicle;
    ParkingSlot slot;
    long entryTime;
    int gateId;

    ParkingTicket(Vehicle v, ParkingSlot s, long time, int gateId) {
        this.ticketId = counter++;
        this.vehicle = v;
        this.slot = s;
        this.entryTime = time;
        this.gateId = gateId;
    }
}