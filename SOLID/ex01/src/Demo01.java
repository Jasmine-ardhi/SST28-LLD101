import java.util.*;


public class Demo01 {

    public static void main(String[] args) {

        System.out.println("=== Student Onboarding ===");

        FakeDb db = new FakeDb();
        StudentRepository repo = new FakeDbRepository(db);

        OnboardingService service = new OnboardingService(repo);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        service.registerFromRawInput(raw);

        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}

/* WORKFLOW  */

class OnboardingService {

    private final StudentRepository repo;
    private final RegistrationParser parser;
    private final RegistrationValidator validator;
    private final OnboardingPrinter printer;

    public OnboardingService(StudentRepository repo) {
        this.repo = repo;
        this.parser = new RegistrationParser();
        this.validator = new RegistrationValidator();
        this.printer = new OnboardingPrinter();
    }

    public void registerFromRawInput(String raw) {

        printer.printInput(raw);

        Map<String,String> kv = parser.parse(raw);

        List<String> errors = validator.validate(kv);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repo.count());

        StudentRecord rec = new StudentRecord(id,kv.get("name"),kv.get("email"),kv.get("phone"), kv.get("program"));

        repo.save(rec);

        printer.printSuccess(id, repo.count(), rec);
    }
}

/* PARSER */

class RegistrationParser {

    public Map<String,String> parse(String raw) {

        Map<String,String> kv = new LinkedHashMap<>();

        String[] parts = raw.split(";");
        for (String p : parts) {
            String[] t = p.split("=", 2);
            if (t.length == 2) {
                kv.put(t[0].trim(), t[1].trim());
            }
        }

        return kv;
    }
}

/*  VALIDATOR */

class RegistrationValidator {

   
    public List<String> validate(Map<String,String> kv) {

        List<String> errors = new ArrayList<>();

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        if (name.isBlank())
            errors.add("name is required");

        if (email.isBlank() || !email.contains("@"))
            errors.add("email is invalid");

        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit))
            errors.add("phone is invalid");

        if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) 
            errors.add("program is invalid");

        return errors;
    }
}

/* PRINTER */

class OnboardingPrinter {

    public void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) {
            System.out.println("- " + e);
        }
    }

    public void printSuccess(String id, int total, StudentRecord rec) {
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + total);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}

/*REPOSITORY  */

interface StudentRepository {
    void save(StudentRecord r);
    int count();
}

class FakeDbRepository implements StudentRepository {

    private final FakeDb db;

    public FakeDbRepository(FakeDb db) {
        this.db = db;
    }

    public void save(StudentRecord r) {
        db.save(r);
    }

    public int count() {
        return db.count();
    }
}

/* FAKE DATABASE  */

class FakeDb {

    private final List<StudentRecord> rows = new ArrayList<>();

    public void save(StudentRecord r) {
        rows.add(r);
    }

    public int count() {
        return rows.size();
    }

    public List<StudentRecord> all() {
        return Collections.unmodifiableList(rows);
    }
}

/* ID GENERATOR */

class IdUtil {

    public static String nextStudentId(int currentCount) {
        int next = currentCount + 1;
        String num = String.format("%04d", next);
        return "SST-2026-" + num;
    }
}

/* MODEL*/

class StudentRecord {
    public final String id;
    public final String name;
    public final String email;
    public final String phone;
    public final String program;

    public StudentRecord(String id, String name, String email, String phone, String program) {
        this.id = id; this.name = name; this.email = email; this.phone = phone; this.program = program;
    }

    @Override
    public String toString() {
        return "StudentRecord{id='" + id + "', name='" + name + "', email='" + email + "', phone='" + phone + "', program='" + program + "'}";
    }
}

/* Text Table */

class TextTable {
    public static String render3(FakeDb db) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ID             | NAME | PROGRAM |\n");
        for (StudentRecord r : db.all()) {
            sb.append(String.format("| %-14s | %-4s | %-7s |\n", r.id, r.name, r.program));
        }
        return sb.toString();
    }
}