package testing;
import java.util.Map;
import java.util.HashMap;

public class StatementCoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();

    // Serializes coverage in some format; we'll revisit this.
    public static void writeCoverageToFile() { }

    public static boolean markExecuted(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, true);
        return false;
    }
    public static  int countAllCoveredLine(){
        int result=0;
        System.out.println("Coverage: ");

        for(String filename:coverage.keySet()){
            System.out.println("    In file "+filename+":");
            for(Integer line:coverage.get(filename).keySet()){
                System.out.println("        Line "+line+" is covered");
                result++;
            }
        }
        System.out.println("Totally " +result+" lines"+ " covered");
        return result;
    }
}