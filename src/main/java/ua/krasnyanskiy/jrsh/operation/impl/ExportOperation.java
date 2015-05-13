package ua.krasnyanskiy.jrsh.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.FileUtil;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationGrammarFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;

import java.io.InputStream;

@SuppressWarnings("unchecked")
public class ExportOperation implements Operation<ExportOperationParameters> {

    private final static String SUCCESS_MSG = "Export status: SUCCESS";
    private final static String FAIL_MSG = "Export status: FAILED";

    private ExportOperationParameters parameters;
    private Grammar grammar;

    @SneakyThrows
    public ExportOperation() {
        this.grammar = OperationGrammarFactory.getGrammar(ExportOperationParameters.class/*getParametersType()*/);
    }

    @Override
    public EvaluationResult eval() {

        EvaluationResult res;

        try {
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


            state = task/*.parameters(parameters.getExportParameters())*/.create().getEntity();
            entity = session.exportService().task(state.getId()).fetch().getEntity();

            if (!parameters.getTo().isEmpty()) {
                if (parameters.getFileUri() != null) {
                    res = getEvaluationResult(parameters.getFileUri(), entity);
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
    public Grammar getGrammar() {
        return grammar;
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
