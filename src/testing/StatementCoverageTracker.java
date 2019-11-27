package testing;
import java.util.Map;
import java.util.HashMap;

public class StatementCoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();

    // Serializes coverage in some format; we'll revisit this.
    public static void writeCoverageToFile() { }

    public static void markExecuted(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, true);
    }
}