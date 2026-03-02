public class TransportBookingService {

    private final DistanceService distanceService;
    private final DriverService driverService;
    private final PaymentService paymentService;
    private final PricingService pricingService;

    public TransportBookingService(
            DistanceService distanceService,
            DriverService driverService,
            PaymentService paymentService,
            PricingService pricingService) {

        this.distanceService = distanceService;
        this.driverService = driverService;
        this.paymentService = paymentService;
        this.pricingService = pricingService;
    }

    public void book(TripRequest req) {

        double km = distanceService.km(req.from, req.to);
        System.out.println("DistanceKm=" + km);

        String driver = driverService.allocate(req.studentId);
        System.out.println("Driver=" + driver);

        double fare = pricingService.calculateFare(km);

        String txn = paymentService.charge(req.studentId, fare);
        System.out.println("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        System.out.println("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}