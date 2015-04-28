package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.Parameters;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;

import java.util.Collection;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;
import static ua.krasnyanskiy.jrsh.common.SessionFactory.createSharedSession;

public class LoginOperation implements Operation {

    private ConsoleReader console;

    @Override
    public Callable<Boolean> perform(@NonNull final Parameters parameters) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    createSharedSession(
                            parameters.<String>get("server"),
                            parameters.<String>get("username"),
                            parameters.<String>get("password"),
                            parameters.<String>get("organization"));
                    return true;
                } catch (Exception e) {
                    console.print("error: Connection cannot be established.");
                    console.flush();
                    return false;
                }
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        return new LoginOperationGrammar();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Parameters getParameters(String... args) {
        LoginToken tok = tokenize(args);
        Parameters params = new Parameters();
        params.put("server", tok.getUrl());
        params.put("username", tok.getUsername());
        params.put("password", tok.getPassword());
        params.put("organization", tok.getOrganization());
        return params;
    }

    protected static LoginToken tokenize(String... token) {
        String[] parts = token[0].split("[@]");
        String url = null;
        String username = null;
        String password = null;
        String organization = null;
        if (parts.length == 2) {
            url = parts[1].trim();
            parts = parts[0].split("[%]");
            if (parts.length == 2) {
                password = parts[1].trim();
                parts = parts[0].split("[|]");
                if (parts.length == 2) {
                    username = parts[0].trim();
                    organization = parts[1].trim();
                } else if (parts.length == 1) {
                    username = parts[0].trim();
                }
            } else if (parts.length == 1) {
                parts = parts[0].split("[|]");
                if (parts.length == 2) {
                    username = parts[0].trim();
                    organization = parts[1].trim();
                } else {
                    username = parts[0].trim();
                }
            }
        }

        checkArgument(url != null);
        checkArgument(password != null);
        checkArgument(username != null);

        return new LoginToken(fixUrl(url), username, password, organization);
    }

    protected static String fixUrl(String url) {
        return url.startsWith("http") ? url : "http://".concat(url);
    }

    @Override
    public void setConsole(ConsoleReader console) {
        this.console = console;
    }

    @Data
    @AllArgsConstructor
    protected static class LoginToken {
        String url;
        String username;
        String password;
        String organization;
    }

    protected class LoginOperationGrammar implements Grammar {
        @Override
        public Collection<Rule> getRules() {
            return emptyList();
        }

        @Override
        public void addRule(Rule rule) {

        }

        @Override
        public Completer getCompleter() {
            return null;
        }
    }
}
