// public class Main {
//     public static void main(String[] args) {
//         System.out.println("=== Evaluation Pipeline ===");
//         Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
//         new EvaluationPipeline().evaluate(sub);
//     }
// }
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");

        Submission sub = new Submission(
                "23BCS1007",
                "public class A{}",
                "A.java");

        Rubric rubric = new Rubric();

        PlagiarismService plagiarism = new PlagiarismChecker();
        GradingService grading = new CodeGrader(rubric);
        ReportService report = new ReportWriter();

        EvaluationPipeline pipeline =
                new EvaluationPipeline(plagiarism, grading, report);

        pipeline.evaluate(sub);
    }
}