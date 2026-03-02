import java.util.*;

public class Demo06 {

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

        email.send(n);
        sms.send(n);
        wa.send(n);

        System.out.println("AUDIT entries=" + audit.size());
    }
}

/* ================= BASE CLASS ================= */

abstract class NotificationSender {

    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    // Final → workflow cannot be changed
    public final void send(Notification n) {

        if (n == null)
            throw new IllegalArgumentException("Notification cannot be null");

        if (n.body == null)
            throw new IllegalArgumentException("Body is required");

        // Delegate to subclass
        doSend(n);
    }

    protected abstract void doSend(Notification n);
}

/* ================= EMAIL ================= */

class EmailSender extends NotificationSender {

    public EmailSender(AuditLog audit) {
        super(audit);
    }

    @Override
    protected void doSend(Notification n) {

        if (n.email == null) {
            System.out.println("EMAIL ERROR: email missing");
            audit.add("email failed");
            return;
        }

        System.out.println("EMAIL -> to=" + n.email
                + " subject=" + n.subject
                + " body=" + n.body);

        audit.add("email sent");
    }
}

/* ================= SMS ================= */

class SmsSender extends NotificationSender {

    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    protected void doSend(Notification n) {

        if (n.phone == null) {
            System.out.println("SMS ERROR: phone missing");
            audit.add("sms failed");
            return;
        }

        System.out.println("SMS -> to=" + n.phone
                + " body=" + n.body);

        audit.add("sms sent");
    }
}

/* ================= WHATSAPP ================= */

class WhatsAppSender extends NotificationSender {

    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    protected void doSend(Notification n) {

        if (n.phone == null || !n.phone.startsWith("+")) {
            // No exception → no tightened precondition
            System.out.println("WA ERROR: phone must start with + and country code");
            audit.add("WA failed");
            return;
        }

        System.out.println("WA -> to=" + n.phone
                + " body=" + n.body);

        audit.add("wa sent");
    }
}

/* ================= NOTIFICATION ================= */

class Notification {

    public final String subject;
    public final String body;
    public final String email;
    public final String phone;

    public Notification(String subject, String body,
                        String email, String phone) {
        this.subject = subject;
        this.body = body;
        this.email = email;
        this.phone = phone;
    }
}

/* ================= AUDIT ================= */

class AuditLog {

    private final List<String> entries = new ArrayList<>();

    public void add(String e) {
        entries.add(e);
    }

    public int size() {
        return entries.size();
    }
}