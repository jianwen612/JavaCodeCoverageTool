package testing;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import sun.jvm.hotspot.opto.Block;

import java.util.Optional;

public class BranchCoverageVisitor extends ModifierVisitor<Object> {
    // AST nodes don't know which file they come from, so we'll pass the information in
    private String filename;
    //    static private HashMap<BlockStmt,Integer> count=new HashMap<>();
    public BranchCoverageVisitor(String filename) {
        super();
        this.filename = filename;
    }


    @Override
    public Visitable visit(IfStmt node, Object arg) {
//        System.out.println(node.getThenStmt().toBlockStmt().toString());
        BlockStmt thenBlock=node.getThenStmt().toBlockStmt().get();

//        Node newnode=null;
        Node thenNode=makeCoverageTrackingCall(filename, node.getBegin().get().line,true);
        thenBlock.addStatement(0,(ExpressionStmt)thenNode);
        //finish then

        Optional<Statement> elseStmt=node.getElseStmt();
        if(elseStmt.isPresent()){

            if(elseStmt.get().toBlockStmt().isPresent()){
                BlockStmt elseBlock=elseStmt.get().toBlockStmt().get();
                Node elseNode=makeCoverageTrackingCall(filename, node.getBegin().get().line,false);
                elseBlock.addStatement(0,(ExpressionStmt)elseNode);
            }else{
                BlockStmt elseBlock=new BlockStmt();
                Node elseNode=makeCoverageTrackingCall(filename, node.getBegin().get().line,false);
                elseBlock.addStatement((ExpressionStmt)elseNode);
                elseBlock.addStatement(elseStmt.get());
                node.setElseStmt(elseBlock);
            }
        }else{
            BlockStmt elseBlock=new BlockStmt();
            Node elseNode=makeCoverageTrackingCall(filename, node.getBegin().get().line,false);
            elseBlock.addStatement(0,(ExpressionStmt)elseNode);
            node.setElseStmt(elseBlock);
        }
        return super.visit(node,arg);
    }

    @Override
    public Visitable visit(ForStmt node, Object arg) {
        BlockStmt result=new BlockStmt();

        BlockStmt thenBlock=node.getBody().toBlockStmt().get();
        BlockStmt newBlock=new BlockStmt();


        Node bodyMark=makeCoverageTrackingCall(filename, node.getBegin().get().line,true);
        Node afterMark=makeCoverageTrackingCall(filename, node.getBegin().get().line,false);

        newBlock.addStatement(0,(ExpressionStmt)bodyMark);
        newBlock.addStatement(1,(Statement) super.visit(thenBlock,arg));
        //finish then
        node.setBody(newBlock);
        result.addStatement(node);
        result.addStatement((ExpressionStmt)afterMark);

        return result;
    }

    @Override
    public Visitable visit(WhileStmt node, Object arg) {
        BlockStmt result=new BlockStmt();

        BlockStmt thenBlock=node.getBody().toBlockStmt().get();
        BlockStmt newBlock=new BlockStmt();


        Node bodyMark=makeCoverageTrackingCall(filename, node.getBegin().get().line,true);
        Node afterMark=makeCoverageTrackingCall(filename, node.getBegin().get().line,false);

        newBlock.addStatement(0,(ExpressionStmt)bodyMark);
        newBlock.addStatement(1,(Statement) super.visit(thenBlock,arg));
        //finish then
        node.setBody(newBlock);
        result.addStatement(node);
        result.addStatement((ExpressionStmt)afterMark);
        return result;
    }




    private Statement makeCoverageTrackingCall(String filename, int line, boolean flag) {
        NameExpr coverageTracker = new NameExpr("testing.BranchCoverageTracker");

        MethodCallExpr call = new MethodCallExpr(coverageTracker, "markExecuted");
        call.addArgument(new StringLiteralExpr(filename));
        call.addArgument(new IntegerLiteralExpr(String.valueOf(line)));
        call.addArgument(new BooleanLiteralExpr(flag));
        return new ExpressionStmt(call);
    }
}