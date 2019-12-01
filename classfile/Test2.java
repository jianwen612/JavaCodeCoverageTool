import testing.StatementCoverageTracker;

public class Test2 {

    public static void main(String[] args) {
        testing.StatementCoverageTracker.markExecuted("Test2.java", 6);
        int a = 1;
        testing.StatementCoverageTracker.markExecuted("Test2.java", 7);
        int b = 2;
        if (testing.StatementCoverageTracker.markExecuted("Test2.java", 8) || a > 2) {
            testing.StatementCoverageTracker.markExecuted("Test2.java", 9);
            a = 1;
        } else {
            if (testing.StatementCoverageTracker.markExecuted("Test2.java", 11) || a > 1) {
                testing.StatementCoverageTracker.markExecuted("Test2.java", 12);
                a = 1;
                testing.StatementCoverageTracker.markExecuted("Test2.java", 13);
                b = 1;
                testing.StatementCoverageTracker.markExecuted("Test2.java", 14);
                a = 3;
            }
        }
        if (testing.StatementCoverageTracker.markExecuted("Test2.java", 17) || b >= 2) {
            testing.StatementCoverageTracker.markExecuted("Test2.java", 18);
            b = 1;
        } else if (testing.StatementCoverageTracker.markExecuted("Test2.java", 19) || b <= 0) {
            testing.StatementCoverageTracker.markExecuted("Test2.java", 20);
            b = 1;
            testing.StatementCoverageTracker.markExecuted("Test2.java", 21);
            b = 2;
            testing.StatementCoverageTracker.markExecuted("Test2.java", 22);
            b = 1;
        }
    }
}
