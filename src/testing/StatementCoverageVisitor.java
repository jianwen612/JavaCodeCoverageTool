package testing;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.printer.concretesyntaxmodel.CsmConditional;

import java.util.HashMap;

public class StatementCoverageVisitor extends ModifierVisitor<Object> {
    // AST nodes don't know which file they come from, so we'll pass the information in
    private String filename;
//    static private HashMap<BlockStmt,Integer> count=new HashMap<>();
    public StatementCoverageVisitor(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public Visitable visit(ExpressionStmt node, Object arg) {
        if(node.getParentNode().isPresent()){
            BlockStmt parent = (BlockStmt) node.getParentNode().get();
//            if(!count.containsKey(parent)){
//                count.put(parent,0);
//            }else{
//                count.replace(parent,count.get(parent)+1);
//            }
//            System.out.println(count.get(parent));
//            int curIndex=parent.getChildNodes().indexOf(node);
            int curIndex=node.getBegin().get().line-parent.getChildNodes().get(0).getBegin().get().line;
            Node newnode=makeCoverageTrackingCall(filename, node.getBegin().get().line);
            newnode.setParentNode(parent);
            parent.addStatement(curIndex*2,(ExpressionStmt)newnode);
        }

//        BlockStmt block = new BlockStmt();
//        block.addStatement(node);
//        block.addStatement(makeCoverageTrackingCall(filename, node.getBegin().get().line));

        return super.visit(node,arg);
    }
    @Override
    public Visitable visit(IfStmt node, Object arg) {
//        BlockStmt block = new BlockStmt();
//        block.addStatement(node);
//        node.setThenStmt(visit(node.getThenStmt()))

//        block.addStatement(makeCoverageTrackingCall(filename, node.getBegin().get().line));
//        Expression e=node.getCondition();
        BinaryExpr e= new BinaryExpr();
        e.setRight(node.getCondition());
        e.setOperator(BinaryExpr.Operator.OR);

        e.setLeft(((ExpressionStmt)makeCoverageTrackingCall(filename, node.getBegin().get().line)).getExpression());//need expression
        node.setCondition(e);
        return super.visit(node,arg);
    }

    @Override
    public Visitable visit(ForStmt node, Object arg) {
        BinaryExpr e= new BinaryExpr();
        e.setRight(node.getCompare().get());
        e.setOperator(BinaryExpr.Operator.OR);

        e.setLeft(((ExpressionStmt)makeCoverageTrackingCall(filename, node.getBegin().get().line)).getExpression());//need expression
        node.setCompare(e);
        return super.visit(node,arg);
    }

    @Override
    public Visitable visit(WhileStmt node, Object arg) {
        BinaryExpr e= new BinaryExpr();
        e.setRight(node.getCondition());
        e.setOperator(BinaryExpr.Operator.OR);

        e.setLeft(((ExpressionStmt)makeCoverageTrackingCall(filename, node.getBegin().get().line)).getExpression());//need expression
        node.setCondition(e);
        return super.visit(node,arg);
    }




    private Statement makeCoverageTrackingCall(String filename, int line) {
        NameExpr coverageTracker = new NameExpr("testing.StatementCoverageTracker");

        MethodCallExpr call = new MethodCallExpr(coverageTracker, "markExecuted");
        call.addArgument(new StringLiteralExpr(filename));
        call.addArgument(new IntegerLiteralExpr(String.valueOf(line)));
        return new ExpressionStmt(call);
    }
}