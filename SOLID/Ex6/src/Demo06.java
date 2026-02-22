import java.util.*;

/* =========================================================
   Ex6 — LSP: Notification Sender (Single File Version)
   Substitutability ensured.
   ========================================================= */

public class Demo06{

    public static void main(String[] args) {

        System.out.println("=== Notification Demo ===");

        AuditLog audit = new AuditLog();

        Notification n = new Notification(
                "Welcome",
                "Hello and welcome to SST!",
                "riya@sst.edu",
                "9876543210"
        );

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        // Polymorphic calls (LSP satisfied)
        email.send(n);
        sms.send(n);
        wa.send(n);  // No exception thrown anymore

        System.out.println("AUDIT entries=" + audit.size());
    }
}

/* ========================= BASE CONTRACT ========================= */

/*
   Clear contract:
   - send() attempts delivery.
   - Must not tighten preconditions.
   - Must not throw unexpected runtime exceptions.
   - Handles invalid cases gracefully.
*/
abstract class NotificationSender {

    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    public abstract void send(Notification n);
}

/* ========================= EMAIL ========================= */

class EmailSender extends NotificationSender {

    public EmailSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Notification n) {
        // No silent truncation (semantics preserved)
        System.out.println("EMAIL -> to=" + n.email +
                " subject=" + n.subject +
                " body=" + n.body);
        audit.add("email sent");
    }
}

/* ========================= SMS ========================= */

class SmsSender extends NotificationSender {

    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Notification n) {
        // Subject intentionally ignored (but not breaking contract)
        System.out.println("SMS -> to=" + n.phone +
                " body=" + n.body);
        audit.add("sms sent");
    }
}

/* ========================= WHATSAPP ========================= */

class WhatsAppSender extends NotificationSender {

    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Notification n) {

        // Instead of throwing exception (tightening precondition),
        // handle invalid input gracefully to preserve substitutability.
        if (n.phone == null || !n.phone.startsWith("+")) {
            System.out.println("WA ERROR: phone must start with + and country code");
            audit.add("WA failed");
            return;
        }

        System.out.println("WA -> to=" + n.phone +
                " body=" + n.body);
        audit.add("wa sent");
    }
}

/* ========================= NOTIFICATION ========================= */

class Notification {

    public final String subject;
    public final String body;
    public final String email;
    public final String phone;

    public Notification(String subject, String body, String email, String phone) {
        this.subject = subject;
        this.body = body;
        this.email = email;
        this.phone = phone;
    }
}

/* ========================= AUDIT ========================= */

class AuditLog {

    private final List<String> entries = new ArrayList<>();

    public void add(String e) {
        entries.add(e);
    }

    public int size() {
        return entries.size();
    }
}