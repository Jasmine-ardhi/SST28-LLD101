import java.nio.charset.StandardCharsets;



abstract class Exporter {

  
    public final ExportResult export(ExportRequest req) {

        if (req == null) {
            throw new IllegalArgumentException("ExportRequest cannot be null");
        }

        String safeTitle = req.title == null ? "" : req.title;
        String safeBody = req.body == null ? "" : req.body;

        ExportResult result = doExport(safeTitle, safeBody);

        if (result == null || result.bytes == null) {
            throw new IllegalStateException("Exporter returned invalid result");
        }

        return result;
    }

    
    protected abstract ExportResult doExport(String title, String body);
}



class ExportRequest {
    public final String title;
    public final String body;

    public ExportRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}

class ExportResult {
    public final String contentType;
    public final byte[] bytes;

    public ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
    }
}

/* PDF EXPORTER*/

class PdfExporter extends Exporter {

    @Override
    protected ExportResult doExport(String title, String body) {

        
        if (body.length() > 20) {
            throw new IllegalArgumentException(
                    "PDF cannot handle content > 20 chars"
            );
        }

        String fakePdf = "PDF(" + title + "):" + body;

        return new ExportResult(
                "application/pdf",
                fakePdf.getBytes(StandardCharsets.UTF_8)
        );
    }
}

/* CSV EXPORTER*/

class CsvExporter extends Exporter {

    @Override
    protected ExportResult doExport(String title, String body) {

        
        String safeBody = body.replace("\n", " ") .replace(",", " ");

        String csv = "title,body\n" + title + "," + safeBody + "\n";

        return new ExportResult("text/csv",csv.getBytes(StandardCharsets.UTF_8));
    }
}

/*JSON EXPORTER*/

class JsonExporter extends Exporter {

    @Override
    protected ExportResult doExport(String title, String body) {

        String json = "{\"title\":\""+ escape(title)+ "\",\"body\":\""+ escape(body)+ "\"}";

        return new ExportResult("application/json",json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}



class SampleData {
    public static String longBody() {
        return "Name,Score\nAyaan,82\nRiya,91\n";
    }
}



public class Demo05 {

    public static void main(String[] args) {

        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());

        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        System.out.println("PDF: " + safe(pdf, req));
        System.out.println("CSV: " + safe(csv, req));
        System.out.println("JSON: " + safe(json, req));
    }
fi
    private static String safe(Exporter e, ExportRequest r) {
        try {
            ExportResult out = e.export(r);
            return "OK bytes=" + out.bytes.length;
        } catch (RuntimeException ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}