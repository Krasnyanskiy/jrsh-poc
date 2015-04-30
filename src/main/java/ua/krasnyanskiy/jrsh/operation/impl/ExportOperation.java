package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationSimpleGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.RepositoryPathToken;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public class ExportOperation implements Operation<Boolean, ExportOperationParameters> {

    private Grammar grammar;

    @Override
    public Callable<Boolean> perform(ExportOperationParameters parameters) {
        return null;
    }

    @Override
    public Grammar getGrammar() {

        if (grammar != null){
            return grammar;
        }

        Graph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());

        Grammar grammar = new OperationSimpleGrammar();
        Rule rule = new Rule();

        Token v1 = new StringToken("export", true);
        Token v2 = new StringToken("all", true);
        Token v3 = new StringToken("user", true);
        Token v4 = new StringToken("role", true);
        Token v5 = new StringToken("repository", true);

        Token v6 = new RepositoryPathToken("repositoryPath", true);
        Token v7 = new StringToken("to", false);
        Token v8 = new FileToken("filePath", true);

        Token v9 = new StringToken("with-repository-permissions", false);
        Token v10 = new StringToken("with-role-users", false);
        Token v11 = new StringToken("with-include-access-events", false);
        Token v12 = new StringToken("with-include-audit-events", false);
        Token v13 = new StringToken("with-include-monitoring-events", false);
        Token v14 = new StringToken("with-repository-permissions", false);

        // Vertexes
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);
        graph.addVertex(v9);
        graph.addVertex(v10);
        graph.addVertex(v11);
        graph.addVertex(v12);
        graph.addVertex(v13);
        graph.addVertex(v14);

        // Edges
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v1, v4);
        graph.addEdge(v1, v5);

        graph.addEdge(v5, v6);

        graph.addEdge(v6, v7);
        graph.addEdge(v6, v9);
        graph.addEdge(v6, v10);
        graph.addEdge(v6, v11);
        graph.addEdge(v6, v12);
        graph.addEdge(v6, v13);
        graph.addEdge(v6, v14);

        graph.addEdge(v7, v8);

        graph.addEdge(v8, v9);
        graph.addEdge(v8, v10);
        graph.addEdge(v8, v11);
        graph.addEdge(v8, v12);
        graph.addEdge(v8, v13);
        graph.addEdge(v8, v14);

        graph.addEdge(v9, v10);
        graph.addEdge(v9, v11);
        graph.addEdge(v9, v12);
        graph.addEdge(v9, v13);
        graph.addEdge(v9, v14);

        graph.addEdge(v10, v9);
        graph.addEdge(v10, v11);
        graph.addEdge(v10, v12);
        graph.addEdge(v10, v13);
        graph.addEdge(v10, v14);

        graph.addEdge(v11, v9);
        graph.addEdge(v11, v10);
        graph.addEdge(v11, v12);
        graph.addEdge(v11, v13);
        graph.addEdge(v11, v14);

        graph.addEdge(v12, v9);
        graph.addEdge(v12, v10);
        graph.addEdge(v12, v11);
        graph.addEdge(v12, v13);
        graph.addEdge(v12, v14);

        graph.addEdge(v13, v9);
        graph.addEdge(v13, v10);
        graph.addEdge(v13, v11);
        graph.addEdge(v13, v12);
        graph.addEdge(v13, v14);

        graph.addEdge(v14, v9);
        graph.addEdge(v14, v10);
        graph.addEdge(v14, v11);
        graph.addEdge(v14, v12);
        graph.addEdge(v14, v13);

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
            }
        }

        return this.grammar = grammar;
    }

    @Override
    public String getDescription() {
        return "This is Export";
    }

    @Override
    public Class<ExportOperationParameters> getParametersType() {
        return ExportOperationParameters.class;
    }

    @Override
    public void setConsole(ConsoleReader console) {
        // TODO
    }
}
