package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationResult;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationSimpleGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringValueToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import java.util.concurrent.Callable;

import static java.lang.String.format;
import static ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode.FAILED;
import static ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode.SUCCESS;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private final static String LOGIN_OK = "You've successfully logged in as (\u001B[1m%s\u001B[0m)";
    private final static String LOGIN_FAIL = "Login failed (\u001B[1m%s\u001B[0m)";

    private LoginOperationParameters parameters;
    private Grammar grammar;
    //private ConsoleReader console;

    @Override
    public Callable<OperationResult> execute() {

        return new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws Exception {
                try {
                    SessionFactory.createSharedSession(
                            parameters.getServer(),
                            parameters.getUsername(),
                            parameters.getPassword(),
                            parameters.getOrganization());
                    return new OperationResult(format(LOGIN_OK, parameters.getUsername()), SUCCESS);
                } catch (Exception e) {
                    return new OperationResult(format(LOGIN_FAIL, e.getMessage()), FAILED);
                }
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        if (grammar != null) {
            return grammar;
        } else {
             // TODO: build grammar and save it
            DefaultDirectedGraph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());
            Grammar grammar = new OperationSimpleGrammar();

            // login
            Token login = new StringToken("login", true);

            //  server
            Token url = new StringToken("--server", true);
            Token urlValue = new StringValueToken("server-value", true);

            //  name
            Token username = new StringToken("--username", true);
            Token usernameValue = new StringValueToken("username-value", true);

            // pass
            Token password = new StringToken("--password", true);
            Token passwordValue = new StringValueToken("password-value", true);

            // org
            Token organization = new StringToken("--organization", false);
            Token organizationValue = new StringValueToken("organization-value", true);

            grammar.addRule(new Rule(login, url, urlValue, username, usernameValue, password, passwordValue));
            grammar.addRule(new Rule(login, url, urlValue, password, passwordValue, username, usernameValue));

            grammar.addRule(new Rule(login, username, usernameValue, url, urlValue, password, passwordValue));
            grammar.addRule(new Rule(login, username, usernameValue, password, passwordValue, url, urlValue));

            grammar.addRule(new Rule(login, password, passwordValue, username, usernameValue, url, urlValue));
            grammar.addRule(new Rule(login, password, passwordValue, url, urlValue, username, usernameValue));

            // Vertexes
//            graph.addVertex(v1);
//            graph.addVertex(v2);
//            graph.addVertex(v3);
//            graph.addVertex(v4);
//            graph.addVertex(v5);
//            graph.addVertex(v6);
//            graph.addVertex(v7);
//            graph.addVertex(v8);
//            graph.addVertex(v9);

            // Edges
//            graph.addEdge(v1, v2);
//            graph.addEdge(v1, v4);
//            graph.addEdge(v1, v6);
//            graph.addEdge(v1, v8);

//            graph.addEdge(v2, v3);
//            graph.addEdge(v4, v5);
//            graph.addEdge(v6, v7);
//            graph.addEdge(v8, v9);

//            graph.addEdge(v3, v4);
//            graph.addEdge(v3, v6);
//            graph.addEdge(v3, v8);

//            graph.addEdge(v5, v2);
//            graph.addEdge(v5, v6);
//            graph.addEdge(v5, v8);

//            graph.addEdge(v7, v2);
//            graph.addEdge(v7, v4);
//            graph.addEdge(v7, v8);

//            graph.addEdge(v9, v2);
//            graph.addEdge(v9, v4);
//            graph.addEdge(v9, v6);

            //KShortestPaths<Token, TokenEdge<Token>> paths = new KShortestPaths<>(graph, v1, 1000);
            //Set<Token> vertexes = graph.vertexSet();

            // TODO: let's find another way to configure parameters

            /*
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
            */

            this.grammar = grammar;
            return this.grammar;
        }
    }

    @Override
    public String getDescription() {
        return "\t\u001B[1mLogin\u001B[0m makes a JRS REST client session which is used for interaction wih server.\n\tUsage: \u001B[37mlogin\u001B[0m --server <url> --username <name> --password <pass>";
    }

    @Override
    public Class<LoginOperationParameters> getParametersType() {
        return LoginOperationParameters.class;
    }

    @Override
    public void setOperationParameters(LoginOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setConsole(ConsoleReader console) {
        // ignored
    }
}
