package testing;

import java.io.*;
import java.util.*;

public class BranchCoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, TreeSet<Boolean>>> coverage =
            new TreeMap<>();
//    private static final String classpath="/Users/jianwendong/softwaretesting/JavaCodeCoverageTool/classfile/";
//    modify to your path of class file
    private static String classpath="";
    private static final String total_branch_file_name="total_branch_file.txt";
    private static final String branch_report_name="branch_report.txt";
    // modify if you want a different output name
//    private static final String total_coverage_file_name = "total_branch_file.txt";
    private static String report="";
    // Serializes coverage in some format; we'll revisit this.
    private static int total_branch = 0;
    static {
        Properties properties = new Properties();
        InputStream input = null;
        String classpath_str = "";
        try {
            input = new FileInputStream("project.properties");
            properties.load(input);
            classpath_str = properties.getProperty("classpath");
            classpath = classpath_str;
        } catch (IOException io) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File total_coverage_file = new File(classpath_str+total_branch_file_name);
        try{
            BufferedReader in = new BufferedReader(new FileReader(total_coverage_file));
            String str;
            str=in.readLine();
            if(str!= null){
                total_branch=Integer.valueOf(str);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static void writeCoverageToFile() throws IOException {
        countAllCoveredLine();
        File out=new File(classpath+branch_report_name); //store to classpath/filename
        if(!out.exists()) {
            out.createNewFile();
        } else {
            out.delete();
            out.createNewFile();
        }

        Writer writer = new FileWriter(out);
        writer.write(report);
        writer.close();

    }

    public static boolean markExecuted(String filename, int line, boolean flag) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new TreeMap<>());
        }
        if(!coverage.get(filename).containsKey(line)){
            coverage.get(filename).put(line,new TreeSet<>());
        }
        coverage.get(filename).get(line).add(flag);

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
                if(total_branch==0){
                    report=report+"        No Branch Expression in this file\n";
                    return 0;
                }
                report=report+"        Branch at Line "+line+" is covered by\n";
                for(Boolean flag:coverage.get(filename).get(line)){
                    report=report+"            "+(flag?"True Branch":"False Branch");
                    result++;
                }
                report=report+"\n";

            }
            report=report+"\n";
        }
        report+="Totally " +result+" branches "+ " covered ";
        report+="out of " + total_branch+" branches \n";
        report+="Coverage rate: "+(double)result/total_branch;
        return result;
    }
}