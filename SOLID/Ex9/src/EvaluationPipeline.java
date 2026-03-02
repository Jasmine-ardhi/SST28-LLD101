// public class EvaluationPipeline {
//     // DIP violation: high-level module constructs concretes directly
//     public void evaluate(Submission sub) {
//         Rubric rubric = new Rubric();
//         PlagiarismChecker pc = new PlagiarismChecker();
//         CodeGrader grader = new CodeGrader();
//         ReportWriter writer = new ReportWriter();

//         int plag = pc.check(sub);
//         System.out.println("PlagiarismScore=" + plag);

//         int code = grader.grade(sub, rubric);
//         System.out.println("CodeScore=" + code);

//         String reportName = writer.write(sub, plag, code);
//         System.out.println("Report written: " + reportName);

//         int total = plag + code;
//         String result = (total >= 90) ? "PASS" : "FAIL";
//         System.out.println("FINAL: " + result + " (total=" + total + ")");
//     }
// }
public class EvaluationPipeline {

    private final PlagiarismService plagiarismService;
    private final GradingService gradingService;
    private final ReportService reportService;

    public EvaluationPipeline(
            PlagiarismService plagiarismService,
            GradingService gradingService,
            ReportService reportService) {

        this.plagiarismService = plagiarismService;
        this.gradingService = gradingService;
        this.reportService = reportService;
    }

    public void evaluate(Submission sub) {

        int plag = plagiarismService.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = gradingService.grade(sub);
        System.out.println("CodeScore=" + code);

        String reportName = reportService.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}