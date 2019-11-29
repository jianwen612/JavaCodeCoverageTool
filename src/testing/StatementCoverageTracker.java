package testing;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.HashMap;

public class StatementCoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();
    private static final String classpath="/Users/jianwendong/softwaretesting/JavaCodeCoverageTool/classfile/";
    private static final String fileName="coverage.txt";
    private static String report="";
    // Serializes coverage in some format; we'll revisit this.
    private static void writeCoverageToFile() throws IOException {
        countAllCoveredLine();
        File out=new File(classpath+fileName); //store to classpath/filename
//        System.out.println(out.getAbsolutePath());
        if(!out.exists()) {
            out.createNewFile();
        } else {
            out.delete();
            out.createNewFile();
        }

//            if(!out.getParentFile().exists()){
//                out.getParentFile().mkdirs();
//            }

        //2：准备输出流
        Writer writer = new FileWriter(out);
        writer.write(report);
        writer.close();

    }

    public static boolean markExecuted(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, true);
        try{
            writeCoverageToFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }
    public static  int countAllCoveredLine(){
        int result=0;
        report="Coverage: \n";

        for(String filename:coverage.keySet()){
            report=report+"    In file "+filename+":\n";
            for(Integer line:coverage.get(filename).keySet()){
                report=report+"        Line "+line+" is covered\n";
                result++;
            }
        }
        report+="Totally " +result+" lines"+ " covered";
        return result;
    }
}