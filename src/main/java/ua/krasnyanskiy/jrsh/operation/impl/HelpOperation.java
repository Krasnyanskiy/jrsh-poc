package ua.krasnyanskiy.jrsh.operation.impl;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.SimpleOperationGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.HelpOperationParameters;

import java.util.List;
import java.util.Set;

public class HelpOperation implements Operation<HelpOperationParameters> {

    private HelpOperationParameters parameters;
    private Grammar grammar;

    public HelpOperation() {
        setGrammar();
    }

//    public HelpOperation(HelpOperationParameters parameters) {
//        this.parameters = parameters;
//        setGrammar();
//    }

    @Override
    public EvaluationResult eval() {
        StringBuilder builder = new StringBuilder();
        String context = parameters.getContext();

        if (context != null) {
            Operation op = OperationFactory.getOperation(context);
            builder.append(op.getDescription()).append("\n");
        } else {
            builder.append("\nUsage (Tool):   \u001B[37mjrsh\u001B[0m username%password@url <operation> <parameters>\n");
            builder.append("Usage (Shell):  \u001B[37mjrsh\u001B[0m username%password@url\n");
            builder.append("Usage (Script): \u001B[37mjrsh\u001B[0m script.jrs\n");
            builder.append("\nAvailable operations: \n");
            List<Operation<? extends AbstractOperationParameters>> operations = OperationFactory.getOperations();
            for (Operation op : operations) {
                builder.append(op.getDescription()).append("\n");
            }
        }
        return new EvaluationResult(builder.toString(), ResultCode.SUCCESS, this);
    }

    @Override
    public Grammar getGrammar() {
        return grammar;
    }

    protected void setGrammar() {
        DefaultDirectedGraph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());

        Grammar grammar = new SimpleOperationGrammar();
        Rule rule = new Rule();

        Token v1 = new StringToken("help", "help", true, true);
        Token v2 = new StringToken("context", "export", false, true);
        Token v3 = new StringToken("context", "login", false, true);

        // Vertexes
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        // Edges
        // graph.addEdge(v1, v1);
        grammar.addRule(new Rule(v1)); // <help>
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);

        KShortestPaths<Token, TokenEdge<Token>> paths = new KShortestPaths<>(graph, v1, 1000);
        Set<Token> vertexes = graph.vertexSet();

        for (Token endPoint : vertexes) {
            if (!endPoint.equals(v1)) {

                Set<TokenEdge<Token>> edgesOfEndPoint = graph.edgesOf(endPoint);
                boolean hasMandatoryNeighbours = false;

                for (TokenEdge<Token> edge : edgesOfEndPoint) {
                    if (edge.getSource().equals(endPoint)) {
                        if (edge.getTarget().isMandatory()) {
                            hasMandatoryNeighbours = true;
                        }
                    }
                }

                if (hasMandatoryNeighbours) continue;

                List<GraphPath<Token, TokenEdge<Token>>> list = paths.getPaths(endPoint);
                for (GraphPath<Token, TokenEdge<Token>> path : list) {
                    List<TokenEdge<Token>> edges = path.getEdgeList();
                    boolean isSourceTokenNeeded = true;
                    for (TokenEdge<Token> edge : edges) {
                        if (isSourceTokenNeeded) {
                            Token src = edge.getSource();
                            rule.addToken(src);
                            isSourceTokenNeeded = false;
                        }
                        Token target = edge.getTarget();
                        rule.addToken(target);
                    }
                    grammar.addRule(rule);
                    rule = new Rule();
                }
            } else {

                // help -> help

                rule.addToken(v1);
                grammar.addRule(rule);
                rule = new Rule();
            }
        }

        this.grammar = grammar;
    }

    @Override
    public String getDescription() {
        return "\t\u001B[1mHelp\u001B[0m shows information about JRSH and its operations." +
                "\n\tUsage: \u001B[37mhelp\u001B[0m <operation>";
    }

    @Override
    public Class<HelpOperationParameters> getParametersType() {
        return HelpOperationParameters.class;
    }

    @Override
    public void setOperationParameters(HelpOperationParameters parameters) {
        this.parameters = parameters;
    }
}
