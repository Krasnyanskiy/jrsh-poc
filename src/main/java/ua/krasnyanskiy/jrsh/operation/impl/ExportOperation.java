package ua.krasnyanskiy.jrsh.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.common.FileUtil;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.SimpleOperationGrammar;
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

@SuppressWarnings("unchecked")
public class ExportOperation implements Operation<ExportOperationParameters> {

    private final static String SUCCESS_MSG = "Export status: SUCCESS";
    private final static String FAIL_MSG = "Export status: FAILED";

    private ExportOperationParameters parameters;
    private Grammar grammar;

    public ExportOperation() {
        setGrammar();
    }

    @Override
    public EvaluationResult eval() {

        EvaluationResult res;

        try {

            // никаких исключений наружу!!!

            /* fixme возможна ошибка в Script режиме */
            Session session = SessionFactory.getSharedSession();


            ExportService exportService = session.exportService();
            ExportTaskAdapter task = exportService.newTask();


            if ("repository".equals(parameters.getContext())) {
                String path = parameters.getRepositoryPath();
                if (path != null) {
                    task.uri(path);
                }
            }

            StateDto state;
            InputStream entity;

            /* fixme возможна ошибка выполнения (connection) */
            state = task.parameters(parameters.getExportParameters()).create().getEntity();
            entity = session.exportService().task(state.getId()).fetch().getEntity();

            if (parameters.isTo()) {
                if (parameters.getFilePath() != null) {
                    res = getEvaluationResult(parameters.getFilePath(), entity);
                } else {
                    res = createUnsuccessfulResult();
                }
            } else {
                res = getEvaluationResult("export.zip", entity);
            }
        } catch (Exception err) {
            res = createUnsuccessfulResult();
        }

        return res;
    }

    protected EvaluationResult createUnsuccessfulResult() {
        return new EvaluationResult(FAIL_MSG, ResultCode.FAILED, this);
    }

    protected EvaluationResult getEvaluationResult(String file, InputStream entity) {
        EvaluationResult res;
        if (FileUtil.createFile(file, entity)) {
            res = new EvaluationResult(SUCCESS_MSG, ResultCode.SUCCESS, this);
        } else {
            res = createUnsuccessfulResult();
        }
        return res;
    }

    @Override
    public Grammar getGrammar() { // FIXME: re-write
        return grammar;
    }

    protected void setGrammar() {
        DefaultDirectedGraph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());
        Grammar grammar = new SimpleOperationGrammar();
        Rule rule = new Rule();

        Token v1 = new StringToken("export", "export", true, false);
        //Token v2 = new StringToken("all", "all", true, true);
        //Token v3 = new StringToken("user", "user", true, false);
        //Token v4 = new StringToken("role", "role", true, false);
        Token v5 = new StringToken("context", "repository", true, false);

        Token v6 = new RepositoryPathToken("repositoryPath", true, true);
        Token v7 = new StringToken("to", "to", false, false);
        Token v8 = new FileToken("filePath", true, true);

        Token v9 = new StringToken("withRepositoryPermissions", "with-repository-permissions", false, true);
        Token v10 = new StringToken("withUserRoles", "with-user-roles", false, true);
        Token v11 = new StringToken("withIncludeAccessEvents", "with-include-access-events", false, true);
        Token v12 = new StringToken("withIncludeAuditEvents", "with-include-audit-events", false, true);
        Token v13 = new StringToken("withIncludeMonitoringEvents", "with-include-monitoring-events", false, true);

        // Vertexes
        graph.addVertex(v1);
        //graph.addVertex(v2);
        //graph.addVertex(v3);
        //graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);
        graph.addVertex(v9);
        graph.addVertex(v10);
        graph.addVertex(v11);
        graph.addVertex(v12);
        graph.addVertex(v13);
        //graph.addVertex(v14);

        // Edges
        //graph.addEdge(v1, v2);
        //graph.addEdge(v1, v3);
        //graph.addEdge(v1, v4);
        graph.addEdge(v1, v5);

        graph.addEdge(v5, v6);

        graph.addEdge(v6, v7);
        graph.addEdge(v6, v9);
        graph.addEdge(v6, v10);
        graph.addEdge(v6, v11);
        graph.addEdge(v6, v12);
        graph.addEdge(v6, v13);
        //graph.addEdge(v6, v14);

        graph.addEdge(v7, v8);

        graph.addEdge(v8, v9);
        graph.addEdge(v8, v10);
        graph.addEdge(v8, v11);
        graph.addEdge(v8, v12);
        graph.addEdge(v8, v13);
        //graph.addEdge(v8, v14);

        graph.addEdge(v9, v10);
        graph.addEdge(v9, v11);
        graph.addEdge(v9, v12);
        graph.addEdge(v9, v13);
        //graph.addEdge(v9, v14);

        graph.addEdge(v10, v9);
        graph.addEdge(v10, v11);
        graph.addEdge(v10, v12);
        graph.addEdge(v10, v13);
        //graph.addEdge(v10, v14);

        graph.addEdge(v11, v9);
        graph.addEdge(v11, v10);
        graph.addEdge(v11, v12);
        graph.addEdge(v11, v13);
        //graph.addEdge(v11, v14);

        graph.addEdge(v12, v9);
        graph.addEdge(v12, v10);
        graph.addEdge(v12, v11);
        graph.addEdge(v12, v13);
        //graph.addEdge(v12, v14);

        graph.addEdge(v13, v9);
        graph.addEdge(v13, v10);
        graph.addEdge(v13, v11);
        graph.addEdge(v13, v12);
        //graph.addEdge(v13, v14);

        //graph.addEdge(v14, v9);
        //graph.addEdge(v14, v10);
        //graph.addEdge(v14, v11);
        //graph.addEdge(v14, v12);
        //graph.addEdge(v14, v13);

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

        this.grammar = grammar;
    }

    @Override
    public String getDescription() {
        return "\t\u001B[1mExport\u001B[0m downloads resources from JRS and saves them to zip." +
                "\n\tUsage: \u001B[37mexport\u001B[0m repository /path/to/repository";
    }

    @Override
    public Class<ExportOperationParameters> getParametersType() {
        return ExportOperationParameters.class;
    }

    @Override
    public void setOperationParameters(ExportOperationParameters parameters) {
        this.parameters = parameters;
    }

}
