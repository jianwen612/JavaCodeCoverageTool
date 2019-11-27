package testing;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.printer.concretesyntaxmodel.CsmConditional;

public class StatementCoverageVisitor extends ModifierVisitor<Object> {
    // AST nodes don't know which file they come from, so we'll pass the information in
    private String filename;

    public StatementCoverageVisitor(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public Visitable visit(ExpressionStmt node, Object arg) {
        BlockStmt block = new BlockStmt();
        block.addStatement(node);
        block.addStatement(makeCoverageTrackingCall(filename, node.getBegin().get().line));

        return block;
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