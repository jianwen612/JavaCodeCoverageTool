import testing.StatementCoverageTracker;

public class TestCode {

    public static void main(String[] args) {
        testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 3);
        int a = 1;
        testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 4);
        int b = 2;
        if (testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 5) || a > 2) {
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 6);
            System.out.println("a>2");
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 7);
            a = 1;
        } else {
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 9);
            System.out.println("a<=2");
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 10);
            a = 1;
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 11);
            b = 1;
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 12);
            a = 3;
        }
        if (testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 15) || b >= 2) {
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 16);
            System.out.println("b>=2");
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 17);
            b = 1;
        } else if (testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 18) || b <= 0) {
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 19);
            b = 1;
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 20);
            b = 2;
        } else {
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 23);
            System.out.println("b<2");
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/document/TestCode.java", 24);
            b = 1;
        }
    }
}
