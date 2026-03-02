import java.util.*;

public class Demo02 {

    public static void main(String[] args) {

        System.out.println("=== Cafeteria Billing ===");

        InvoiceStore store = new FileStore();
        TaxPolicy taxPolicy = new DefaultTaxPolicy();
        DiscountPolicy discountPolicy = new DefaultDiscountPolicy();
        PricingEngine engine = new PricingEngine(taxPolicy, discountPolicy);
        InvoiceFormatter formatter = new InvoiceFormatter();

        CafeteriaSystem sys = new CafeteriaSystem(store, engine, formatter);

        sys.addToMenu(new MenuItem("M1", "Veg Thali", 80.00));
        sys.addToMenu(new MenuItem("C1", "Coffee", 30.00));
        sys.addToMenu(new MenuItem("S1", "Sandwich", 60.00));

        List<OrderLine> order = List.of(new OrderLine("M1", 2),new OrderLine("C1", 1) );

        sys.checkout("student", order);
    }
}


class MenuItem {
    public final String id;
    public final String name;
    public final double price;

    public MenuItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class OrderLine {
    public final String itemId;
    public final int qty;

    public OrderLine(String itemId, int qty) {
        this.itemId = itemId;
        this.qty = qty;
    }
}

/*TAX POLICY*/

interface TaxPolicy {
    double taxPercent(String customerType);
}

class DefaultTaxPolicy implements TaxPolicy {

    @Override
    public double taxPercent(String customerType) {
        if ("student".equalsIgnoreCase(customerType)) return 5.0;
        if ("staff".equalsIgnoreCase(customerType)) return 2.0;
        return 8.0;
    }
}

/* DISCOUNT POLICY*/

interface DiscountPolicy {
    double discount(String customerType,double subtotal,int distinctLines);
}

class DefaultDiscountPolicy implements DiscountPolicy {

    @Override
    public double discount(String customerType,double subtotal,int distinctLines) {

        if ("student".equalsIgnoreCase(customerType)) {
            if (subtotal >= 180.0) return 10.0;
            return 0.0;
        }

        if ("staff".equalsIgnoreCase(customerType)) {
            if (distinctLines >= 3) return 15.0;
            return 5.0;
        }

        return 0.0;
    }
}



class InvoiceSummary {

    public final double subtotal;
    public final double taxPercent;
    public final double tax;
    public final double discount;
    public final double total;

    public InvoiceSummary(double subtotal,double taxPercent,double tax,double discount,double total) {

        this.subtotal = subtotal;
        this.taxPercent = taxPercent;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }
}

class PricingEngine {

    private final TaxPolicy taxPolicy;
    private final DiscountPolicy discountPolicy;

    public PricingEngine(TaxPolicy taxPolicy,DiscountPolicy discountPolicy) {
        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
    }

    public InvoiceSummary calculate(String customerType, double subtotal,int distinctLines) {

        double taxPct = taxPolicy.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountPolicy.discount(customerType,subtotal,distinctLines);

        double total = subtotal + tax - discount;

        return new InvoiceSummary(subtotal,taxPct,tax,discount,total);
    }
}

/* INVOICE FORMATTER*/

class InvoiceFormatter {

    public String format(String invId,List<String> itemLines,InvoiceSummary summary) {

        StringBuilder out = new StringBuilder();

        out.append("Invoice# ").append(invId).append("\n");

        for (String line : itemLines) {
            out.append(line).append("\n");
        }

        out.append(String.format("Subtotal: %.2f\n", summary.subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n",summary.taxPercent, summary.tax));
        out.append(String.format("Discount: -%.2f\n",summary.discount));
        out.append(String.format("TOTAL: %.2f\n", summary.total));

        return out.toString();
    }
}

/* PERSISTENCE ABSTRACTION*/

interface InvoiceStore {
    void save(String id, String text);
    int countLines(String id);
}

class FileStore implements InvoiceStore {

    private final Map<String, String> storage =
            new HashMap<>();

    @Override
    public void save(String id, String text) {
        storage.put(id, text);
    }

    @Override
    public int countLines(String id) {
        String content = storage.get(id);
        if (content == null) return 0;
        return content.split("\n").length;
    }
}

/*Cafeteria System */

class CafeteriaSystem {

    private final Map<String, MenuItem> menu =
            new LinkedHashMap<>();

    private final InvoiceStore store;
    private final PricingEngine pricingEngine;
    private final InvoiceFormatter formatter;

    private int invoiceSeq = 1000;

    public CafeteriaSystem(InvoiceStore store,PricingEngine pricingEngine,InvoiceFormatter formatter) {

        this.store = store;
        this.pricingEngine = pricingEngine;
        this.formatter = formatter;
    }

    public void addToMenu(MenuItem i) {
        menu.put(i.id, i);
    }

    public void checkout(String customerType,List<OrderLine> lines) {

        String invId = "INV-" + (++invoiceSeq);

        double subtotal = 0.0;
        List<String> itemLines = new ArrayList<>();

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;

            itemLines.add(String.format("- %s x%d = %.2f",item.name,l.qty,lineTotal));
        }

        InvoiceSummary summary =
            pricingEngine.calculate( customerType, subtotal, lines.size());

        String printable =
            formatter.format(invId, itemLines, summary);
        System.out.print(printable);

        store.save(invId, printable);

        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}