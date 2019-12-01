package testing;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import java.io.*;
import java.util.Optional;

public class FileParser {
    static String classpath="D:\\Testing_Project2\\JavaCodeCoverageTool\\classfile\\";
    static String total_coverage_file_name = "total_coverage_file.txt";
    public static String  getResult(String code,String fileName){
        Process p;
        //test.bat中的命令是ipconfig/all
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

//            if(!out.getParentFile().exists()){
//                out.getParentFile().mkdirs();
//            }

            //2：准备输出流
            Writer writer = new FileWriter(out);
            writer.write(code);
            writer.close();

            //执行命令
//            String cmd="ls";
            String cmd="javac -cp "+classpath+" "
                    +out.getAbsolutePath();
            System.out.println("command: "+cmd);
            p = Runtime.getRuntime().exec(cmd);
            //取得命令结果的输出流
            InputStream fis=p.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr=new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br=new BufferedReader(isr);
            String line=null;
            //直到读完为止


            while((line=br.readLine())!=null)
            {
                feedback=feedback+line+"\n";
            }
//            System.out.println(feedback);


            cmd="java -cp "+classpath+" "+fileName.split("\\.")[0];
            System.out.println("command2: "+ cmd);
            p = Runtime.getRuntime().exec(cmd);
            //取得命令结果的输出流
            fis=p.getInputStream();
            //用一个读输出流类去读
            isr=new InputStreamReader(fis);
            //用缓冲器读行
            br=new BufferedReader(isr);
            line=null;
            //直到读完为止


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
//        String filepath = fp.getClass().getClassLoader().getResource("./TestCode.class").getPath();
        String filepath=args[0];

//        System.out.println(filepath);
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
        File total_coverage_file =new File(classpath+total_coverage_file_name); //stor to classpath/filename

        if(!total_coverage_file.exists()) {
            total_coverage_file.createNewFile();
        } else {
            total_coverage_file.delete();
            total_coverage_file.createNewFile();
        }
        //2：准备输出流
        Writer writer = new FileWriter(total_coverage_file);
        writer.write(String.valueOf(total_count));
        writer.close();

        unit.accept(new StatementCoverageVisitor(file.getName()), null);
        unit.addImport("testing.StatementCoverageTracker");

        String feedback=getResult(unit.toString(),file.getName());
        System.out.println(unit.toString());
        System.out.println("runtime output:\n"+feedback);
    }
}
