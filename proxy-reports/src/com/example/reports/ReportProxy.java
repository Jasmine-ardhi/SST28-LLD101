package com.example.reports;

/**
 * TODO (student):
 * Implement Proxy responsibilities here:
 * - access check
 * - lazy loading
 * - caching of RealReport within the same proxy
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    // cached real object
    private RealReport realReport; 

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        // Starter placeholder: intentionally incorrect.
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED -> "+ user.getName() + " cannot open " + title);
            return;
        }

        // Students should remove direct real loading on every call.
        if (realReport == null) {
            realReport = new RealReport(reportId, title, classification);
        }
        
        realReport.display(user);
    }
}
