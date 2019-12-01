package testing;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class StatementCoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();
    private static final String classpath="D:\\Testing_Project2\\JavaCodeCoverageTool\\classfile\\";
//    modify to your path of class file
    private static final String fileName="coverage.txt";
    // modify if you want a different output name
    private static final String total_coverage_file_name = "total_coverage_file.txt";
    private static String report="";
    // Serializes coverage in some format; we'll revisit this.
    private static int total_statements = 0;
    static{
        File total_coverage_file = new File(classpath+total_coverage_file_name);
        try{
            BufferedReader in = new BufferedReader(new FileReader(total_coverage_file));
            String str;
            str=in.readLine();
            if(str!= null){
                total_statements=Integer.valueOf(str);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static void writeCoverageToFile() throws IOException {
        countAllCoveredLine();
        File out=new File(classpath+fileName); //store to classpath/filename
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
        report+="Totally " +result+" lines"+ " covered\n";
        report+="Coverage rate: "+(double)result/total_statements;
        return result;
    }
}