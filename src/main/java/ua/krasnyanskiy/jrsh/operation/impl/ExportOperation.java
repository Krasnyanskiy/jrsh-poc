package ua.krasnyanskiy.jrsh.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import lombok.NonNull;
import lombok.Setter;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.Parameters;
import ua.krasnyanskiy.jrsh.operation.grammar.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.RepositoryPathToken;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.TokenEdgeFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.INCLUDE_ACCESS_EVENTS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.INCLUDE_AUDIT_EVENTS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.INCLUDE_MONITORING_EVENTS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.REPOSITORY_PERMISSIONS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.ROLE_USERS;
import static ua.krasnyanskiy.jrsh.common.FileUtil.createFile;

@SuppressWarnings("unchecked")
public class ExportOperation implements Operation {

    private ConsoleReader console;
    private Grammar grammar;

    @Override
    public Callable<Boolean> perform(@NonNull final Parameters parameters) {

        final Session session = SessionFactory.getSharedSession();
        String repository = parameters.get("repository");
        final String repositoryPath = parameters.get("repositoryPath");
        final String to = parameters.get("to");
        final String filePath = parameters.get("filePath");
        final List<String> arguments = new ArrayList<>();

        String a1 = parameters.get("with-repository-permissions");
        String a2 = parameters.get("with-role-users");
        String a3 = parameters.get("with-include-access-events");
        String a4 = parameters.get("with-include-audit-events");
        String a5 = parameters.get("with-include-monitoring-events");
        String a6 = parameters.get("with-repository-permissions");

        if (a1 != null) arguments.add(a1);
        if (a2 != null) arguments.add(a2);
        if (a3 != null) arguments.add(a3);
        if (a4 != null) arguments.add(a4);
        if (a5 != null) arguments.add(a5);
        if (a6 != null) arguments.add(a6);

        ExportService exportService = session.exportService();
        final ExportTaskAdapter task = exportService.newTask();

        if (repository != null) {
            if (repositoryPath != null) {
                task.uri(repositoryPath);
            }
        }

        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                StateDto state = task
                        .uri(repositoryPath)
                        .parameters(convert(arguments))
                        .create()
                        .getEntity();

                InputStream entity = session.exportService()
                        .task(state.getId())
                        .fetch()
                        .getEntity();

                if (to != null) {
                    return (filePath != null) && createFile(filePath, entity);
                } else {
                    return createFile("export.zip", entity);
                }
            }
        };
    }

    protected List<ExportParameter> convert(List<String> arguments) {
        List<ExportParameter> params = new ArrayList<>();
        for (String par : arguments) {
            switch (par) {
                case "with-repository-permissions":
                    params.add(REPOSITORY_PERMISSIONS);
                    break;
                case "with-role-users":
                    params.add(ROLE_USERS);
                    break;
                case "with-include-access-events":
                    params.add(INCLUDE_ACCESS_EVENTS);
                    break;
                case "with-include-audit-events":
                    params.add(INCLUDE_AUDIT_EVENTS);
                    break;
                case "with-include-monitoring-events":
                    params.add(INCLUDE_MONITORING_EVENTS);
                    break;
            }
        }
        return params;
    }

    @Override
    public Grammar getGrammar() {

        if (grammar != null){
            return grammar;
        }

        DefaultDirectedGraph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());

        Grammar grammar = new ExportOperationGrammar();
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
    public Parameters getParameters(String... args) {
        Parameters params = new Parameters();
        Grammar grammar = getGrammar();
        Collection<Rule> rules = grammar.getRules();

        for (Rule rule : rules) {
            List<Token> tokens = rule.getTokens();
            boolean isMatchingRule = true;

            if (args.length == tokens.size()) {
                for (int i = 0; i < tokens.size(); i++) {
                    if (!tokens.get(i).match(args[i])) {
                        isMatchingRule = false;
                    }
                }

                if (isMatchingRule) {
                    for (int i = 0; i < tokens.size(); i++) {
                        Token token = tokens.get(i);
                        params.put(token.getName(), args[i]);
                    }
                }
            }

        }

        if (params.isEmpty()) {
            throw new RuntimeException("There is no matched rule");
        }

        return params;
    }

    @Override
    public String getDescription() {
        return "This is Export";
    }

    @Override
    public void setConsole(ConsoleReader console) {
        this.console = console;
    }

    protected static class ExportOperationGrammar implements Grammar {
        @Setter
        private Collection<Rule> rules = new ArrayList<>();
        @Override
        public Collection<Rule> getRules() {
            return rules;
        }
        @Override
        public void addRule(Rule rule) {
            rules.add(rule);
        }
        @Override
        public Completer getCompleter() {
            AggregateCompleter operation = new AggregateCompleter();
            ArgumentCompleter arg = new ArgumentCompleter();
            for (Rule rule : rules) {
                List<Token> tokens = rule.getTokens();
                for (Token token : tokens) {
                    Completer fromToken = token.getCompleter();
                    arg.getCompleters().add(fromToken);
                }
                arg.getCompleters().add(new NullCompleter());
                operation.getCompleters().add(arg);
                arg = new ArgumentCompleter();
            }
            return operation;
        }
    }

}
