package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.SimpleOperationGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.HelpOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode.SUCCESS;

public class HelpOperation implements Operation<HelpOperationParameters> {

    private HelpOperationParameters parameters;
    private Grammar grammar;

    public HelpOperation() {
    }

    public HelpOperation(HelpOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public Callable<EvaluationResult> eval() {
        return new Callable<EvaluationResult>() {
            @Override
            public EvaluationResult call() throws Exception {
                StringBuilder builder = new StringBuilder();
                String context = parameters.getContext();
                if (context != null) {
                    Operation op = OperationFactory.getOperation(context);
                    builder.append(op.getDescription()).append("\n");
                } else {
                    // fixme -> move to cfg file
                    builder.append("\nUsage (Tool):   \u001B[37mjrsh\u001B[0m username%password@url <operation> <parameters>\n");
                    builder.append("Usage (Shell):  \u001B[37mjrsh\u001B[0m username%password@url\n");
                    builder.append("Usage (Script): \u001B[37mjrsh\u001B[0m script.jrs\n");
                    builder.append("\nAvailable operations: \n");

                    List<Operation<? extends OperationParameters>> operations =
                            OperationFactory.getOperations();
                    for (Operation op : operations) {
                        builder.append(op.getDescription()).append("\n");
                    }
                }
                return new EvaluationResult(builder.toString(), SUCCESS);
            }
        };
    }

    @Override
    public Grammar getGrammar() {

        if (grammar != null) {
            return grammar;
        }

        DefaultDirectedGraph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());

        Grammar grammar = new SimpleOperationGrammar();
        Rule rule = new Rule();

        Token v1 = new StringToken("help", true);
        Token v2 = new StringToken("export", false);
        Token v3 = new StringToken("login", false);

        // Vertexes
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        // Edges
        // graph.addEdge(v1, v1);
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

        return this.grammar = grammar; // fixme
    }

    @Override
    public String getDescription() {
        return "\t\u001B[1mHelp\u001B[0m shows information about JRSH and its operations.\n\tUsage: \u001B[37mhelp\u001B[0m <operation>";
    }

    @Override
    public Class<HelpOperationParameters> getParametersType() {
        return HelpOperationParameters.class;
    }

    @Override
    public void setOperationParameters(HelpOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void parseParameters(String line) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setConsole(ConsoleReader console) {
        // ignored
    }
}
