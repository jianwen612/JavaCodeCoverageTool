package testing;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import java.io.*;
import java.util.Optional;

import java.util.Properties;

public class FileParser {
    static String classpath="";
    static String total_coverage_file_name = "total_coverage_file.txt";
    static String total_branch_file_name = "total_branch_file.txt";

    static {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("project.properties");
            properties.load(input);
            classpath = properties.getProperty("classpath");
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
    }

    public static String  getResult(String code,String fileName){
        Process p;

        String feedback = "";
        try
        {
            File out=new File(classpath+fileName); //store to classpath/filename
            if(!out.exists()) {
                out.createNewFile();
            } else {
                out.delete();
                out.createNewFile();
            }
            // write the new java file to classfile/
            Writer writer = new FileWriter(out);
            writer.write(code);
            writer.close();
            //execute java compiler

            String cmd="javac -cp "+classpath+" "
                    +out.getAbsolutePath();
            System.out.println("command: "+cmd);
            //read feedback from process
            p = Runtime.getRuntime().exec(cmd);

            InputStream fis=p.getInputStream();

            InputStreamReader isr=new InputStreamReader(fis);

            BufferedReader br=new BufferedReader(isr);
            String line=null;

            while((line=br.readLine())!=null)
            {
                feedback=feedback+line+"\n";
            }

            //execute java program
            cmd="java -cp "+classpath+" "+fileName.split("\\.")[0];
            System.out.println("command2: "+ cmd);
            p = Runtime.getRuntime().exec(cmd);

            fis=p.getInputStream();

            isr=new InputStreamReader(fis);

            br=new BufferedReader(isr);
            line=null;

            while((line=br.readLine())!=null)
            {
                feedback=feedback+line+"\n";
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return feedback;
    }
    public static void main(String[] args) throws ParseException, IOException {
        FileParser fp=new FileParser();
        String filepath=args[0];
        JavaParser jp=new JavaParser();
        File file=new File(filepath);
        Optional<CompilationUnit> result = jp.parse(file).getResult();
        CompilationUnit unit=null;
        if(result.isPresent()){
            unit=result.get();
        }
        int count_ifstmt = unit.findAll(IfStmt.class).size();
        int count_exprstmt = unit.findAll(ExpressionStmt.class).size();
        int count_forstmt = unit.findAll(ForStmt.class).size();
        int count_whilestmt = unit.findAll(WhileStmt.class).size();
        int total_count = count_ifstmt+count_exprstmt+count_forstmt+count_whilestmt;
        int total_branch = (count_forstmt+count_whilestmt+count_ifstmt)*2;

        //write total statements count to file
        File total_coverage_file =new File(classpath+total_coverage_file_name);
        if(!total_coverage_file.exists()) {
            total_coverage_file.createNewFile();
        } else {
            total_coverage_file.delete();
            total_coverage_file.createNewFile();
        }
        Writer writer = new FileWriter(total_coverage_file);
        writer.write(String.valueOf(total_count));
        writer.close();
        //write total branches count to file
        File total_branch_file=new File(classpath+total_branch_file_name);
        if(!total_branch_file.exists()){
            total_branch_file.createNewFile();
        }else{
            total_branch_file.delete();
            total_branch_file.createNewFile();
        }
        Writer writer2 = new FileWriter(total_branch_file);
        writer2.write(String.valueOf(total_branch));
        writer2.close();


        unit.accept(new StatementCoverageVisitor(file.getName()), null);
        unit.addImport("testing.StatementCoverageTracker");
        unit.addImport("testing.BranchCoverageTracker");

        String feedback=getResult(unit.toString(),file.getName());

        //start branch runtime
        Optional<CompilationUnit> result2 = jp.parse(file).getResult();
        CompilationUnit unit2=null;
        if(result2.isPresent()){
            unit2=result2.get();
        }
        unit2.accept(new BranchCoverageVisitor(file.getName()),null);
        unit2.addImport("testing.StatementCoverageTracker");

        feedback=getResult(unit2.toString(),file.getName());
        System.out.println("runtime output:\n"+feedback);

    }
}
