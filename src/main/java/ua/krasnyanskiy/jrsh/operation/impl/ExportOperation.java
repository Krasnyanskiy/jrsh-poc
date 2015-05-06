package ua.krasnyanskiy.jrsh.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import jline.console.ConsoleReader;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.common.FileUtil;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationSimpleGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.RepositoryPathToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode.FAILED;
import static ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode.SUCCESS;

@SuppressWarnings("unchecked")
public class ExportOperation implements Operation<ExportOperationParameters> {

    private final static String EXPORT_OK = "Export status: SUCCESS (file was created)";
    private final static String EXPORT_FAIL = "Export status: FAILED";
    //private final static String EXPORT_FAIL_MSG = "Export status: FAILED (%s)";

    private ConsoleReader console;
    private ExportOperationParameters parameters;
    private Grammar grammar;

    @Override
    public Callable<EvaluationResult> eval() {
        return new Callable<EvaluationResult>() {
            @Override
            public EvaluationResult call() throws Exception {
                String resultMessage = EXPORT_FAIL;

                Session session = SessionFactory.getSharedSession();
                ExportService exportService = session.exportService();
                ExportTaskAdapter task = exportService.newTask();

                // Add uris
                if ("repository".equals(parameters.getContext())) {
                    String path = parameters.getRepositoryPath();
                    if (path != null) {
                        task.uri(path);
                    }
                }

                // Add roles
                if ("role".equals(parameters.getContext())) {
                    task.roles(parameters.getRoles());
                }

                // Add users
                if ("user".equals(parameters.getContext())) {
                    task.users(parameters.getUsers());
                }

                StateDto state;
                InputStream entity;

                // maybe we should leave it to strategy (without try-catch block)?
                // because strategy is able to handle (print) any error.
                try {
                    state = task
                            .parameters(parameters.getExportParameters())
                            .create()
                            .getEntity();
                    entity = session.exportService()
                            .task(state.getId())
                            .fetch()
                            .getEntity();
                } catch (Exception err) {
                    // (i.e it could be wrong repository path)
                    //return new OperationResult(format(EXPORT_FAIL_MSG, err.getMessage()), FAILED);
                    return new EvaluationResult(EXPORT_FAIL, FAILED);
                }


                if (parameters.isTo()) {
                    if (parameters.getFilePath() != null) {
                        resultMessage = FileUtil.createFile(parameters.getFilePath(), entity)
                                ? EXPORT_OK : EXPORT_FAIL;
                    }
                } else
                    resultMessage = FileUtil.createFile("export.zip", entity)
                            ? EXPORT_OK : EXPORT_FAIL;

                return new EvaluationResult(resultMessage,
                        resultMessage.equals(EXPORT_OK) ? SUCCESS : FAILED);

            }
        };
    }

    @Override
    public Grammar getGrammar() { // FIXME: re-write
        if (grammar != null) {
            return grammar;
        }
//        else {
//            grammar = new ExportOperationGrammar();
//            return grammar;
//        }


        DefaultDirectedGraph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());

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
        Token v10 = new StringToken("with-user-roles", false);
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
        return "\t\u001B[1mExport\u001B[0m downloads resources from JRS and saves them to zip.\n\tUsage: \u001B[37mexport\u001B[0m repository /path/to/repository";
    }

    @Override
    public Class<ExportOperationParameters> getParametersType() {
        return ExportOperationParameters.class;
    }

    @Override
    public void setOperationParameters(ExportOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setConsole(ConsoleReader console) {
        if (this.console == null) {
            this.console = console;
        }
    }
}
