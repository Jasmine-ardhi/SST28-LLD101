import java.util.*;

/* ===========================
   ENUMS & LEGACY TYPES
   =========================== */

enum AddOn {
    MESS, LAUNDRY, GYM
}

class LegacyRoomTypes {
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int DELUXE = 4;

    public static String nameOf(int t) {
        return switch (t) {
            case SINGLE -> "SINGLE";
            case DOUBLE -> "DOUBLE";
            case TRIPLE -> "TRIPLE";
            default -> "DELUXE";
        };
    }
}



class BookingRequest {
    public final int roomType;
    public final List<AddOn> addOns;

    public BookingRequest(int roomType, List<AddOn> addOns) {
        this.roomType = roomType;
        this.addOns = addOns;
    }
}

class Money {
    public final double amount;

    public Money(double amount) {
        this.amount = round2(amount);
    }

    public Money plus(Money other) {
        return new Money(this.amount + other.amount);
    }

    private static double round2(double x) {
        return Math.round(x * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }
}



interface PricingComponent {
    Money monthlyFee();
}

/* Room Pricing */

class SingleRoom implements PricingComponent {
    public Money monthlyFee() { return new Money(14000.0); }
}

class DoubleRoom implements PricingComponent {
    public Money monthlyFee() { return new Money(15000.0); }
}

class TripleRoom implements PricingComponent {
    public Money monthlyFee() { return new Money(12000.0); }
}

class DeluxeRoom implements PricingComponent {
    public Money monthlyFee() { return new Money(16000.0); }
}

/* AddOn Pricing */

class MessAddOn implements PricingComponent {
    public Money monthlyFee() { return new Money(1000.0); }
}

class LaundryAddOn implements PricingComponent {
    public Money monthlyFee() { return new Money(500.0); }
}

class GymAddOn implements PricingComponent {
    public Money monthlyFee() { return new Money(300.0); }
}

/*  REPO*/

class FakeBookingRepo {
    public void save(String id, BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Saved booking: " + id);
    }
}

/*  RECEIPT (UNCHANGED)*/

class ReceiptPrinter {
    public static void print(BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Room: " + LegacyRoomTypes.nameOf(req.roomType) + " | AddOns: " + req.addOns);
        System.out.println("Monthly: " + monthly);
        System.out.println("Deposit: " + deposit);
        System.out.println("TOTAL DUE NOW: " + monthly.plus(deposit));
    }
}

/* REFACTORED CALCULATOR*/

class HostelFeeCalculator {

    private final FakeBookingRepo repo;
    private final Map<Integer, PricingComponent> roomPricing = new HashMap<>();
    private final Map<AddOn, PricingComponent> addOnPricing = new HashMap<>();

    public HostelFeeCalculator(FakeBookingRepo repo) {
        this.repo = repo;

        
        roomPricing.put(LegacyRoomTypes.SINGLE, new SingleRoom());
        roomPricing.put(LegacyRoomTypes.DOUBLE, new DoubleRoom());
        roomPricing.put(LegacyRoomTypes.TRIPLE, new TripleRoom());
        roomPricing.put(LegacyRoomTypes.DELUXE, new DeluxeRoom());

        
        addOnPricing.put(AddOn.MESS, new MessAddOn());
        addOnPricing.put(AddOn.LAUNDRY, new LaundryAddOn());
        addOnPricing.put(AddOn.GYM, new GymAddOn());
    }

    public void process(BookingRequest req) {

        List<PricingComponent> components = new ArrayList<>();

        components.add(roomPricing.get(req.roomType));

        for (AddOn a : req.addOns) {
            components.add(addOnPricing.get(a));
        }

        Money monthly = new Money(0);
        for (PricingComponent c : components) {
            monthly = monthly.plus(c.monthlyFee());
        }

        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }
}


public class Demo04 {
    public static void main(String[] args) {

        System.out.println("=== Hostel Fee Calculator ===");

        BookingRequest req =
                new BookingRequest(
                        LegacyRoomTypes.DOUBLE,
                        List.of(AddOn.LAUNDRY, AddOn.MESS)
                );

        HostelFeeCalculator calc =
                new HostelFeeCalculator(new FakeBookingRepo());

        calc.process(req);
    }
}