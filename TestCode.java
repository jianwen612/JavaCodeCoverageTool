import testing.StatementCoverageTracker;

public class TestCode {

    public static void main(String[] args) {
        {
            int a = 1;
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 3);
        }
        {
            int b = 2;
            testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 4);
        }
        if (testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 5) || a > 2) {
            {
                System.out.println("a>2");
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 6);
            }
            {
                a = 1;
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 7);
            }
        } else {
            {
                System.out.println("a<=2");
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 9);
            }
            {
                a = 1;
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 10);
            }
        }
        if (testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 12) || b >= 2) {
            {
                System.out.println("b>=2");
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 13);
            }
            {
                b = 1;
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 14);
            }
        } else if (testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 15) || b <= 0) {
            {
                b = 1;
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 16);
            }
            {
                b = 1;
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 17);
            }
        } else {
            {
                System.out.println("b<2");
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 20);
            }
            {
                b = 1;
                testing.StatementCoverageTracker.markExecuted("/Users/jianwendong/Document/TestCode.java", 21);
            }
        }
    }
}
