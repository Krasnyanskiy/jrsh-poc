package ua.krasnyanskiy.jrsh.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.common.FileUtil;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public class ExportOperation implements Operation<ExportOperationParameters> {

    private final static String EXPORT_OK = "Export status: SUCCESS";
    private final static String EXPORT_FAIL = "Export status: FAILED";

    private ConsoleReader console;
    private ExportOperationParameters parameters;
    private Grammar grammar;

    @Override
    public Callable<String> execute() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                //synchronized (console) {

                String result = EXPORT_FAIL;
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

                StateDto state = task
                        .parameters(parameters.getExportParameters())
                        .create()
                        .getEntity();

                InputStream entity = session.exportService()
                        .task(state.getId())
                        .fetch()
                        .getEntity();

                if (parameters.getTo()) {
                    if (parameters.getFilePath() != null) {
                        result = FileUtil.createFile(parameters.getFilePath(), entity) ? EXPORT_OK : EXPORT_FAIL;
                    }
                } else
                    result = FileUtil.createFile("export.zip", entity) ? EXPORT_OK : EXPORT_FAIL;

                return result;
                //}
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        // TODO: implement me!
        if (grammar != null) {
            return grammar;
        } else {
            grammar = new ExportOperationGrammar();
            return grammar;
        }
    }

    @Override
    public String getDescription() {
        return "This is export, bitch!";
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


    protected static class ExportOperationGrammar implements Grammar {

        @Override
        public Collection<Rule> getRules() {
            return Collections.emptyList();
        }

        @Override
        public void addRule(Rule rule) {
            // NOP
        }

        @Override
        public Completer getCompleter() {
            return null; // NOP
        }
    }
}
