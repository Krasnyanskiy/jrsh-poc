package com.jaspersoft.jasperserver.jrsh.core.script;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Prefix;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.FileNameToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.RepositoryPathToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;

public class OperationMetadataProvider {

    @Data
    @Master(name = "login", description = "This is a test operation.")
    public static class TestLoginOperation implements Operation {

        @Prefix("--server")
        @Parameter(
                mandatory = true,
                ruleGroups = {"SIMPLE_LOGIN_TOKENS_GROUP"},
                dependsOn = {"login", "U", "P", "O"},
                values = @Value(tokenAlias = "S", tail = true))
        private String server;

        @Prefix("--username")
        @Parameter(
                mandatory = true,
                ruleGroups = "SIMPLE_LOGIN_TOKENS_GROUP",
                dependsOn = {"login", "S", "O", "P"},
                values = @Value(tokenAlias = "U", tail = true))
        private String username;

        @Prefix("--password")
        @Parameter(
                mandatory = true,
                ruleGroups = "SIMPLE_LOGIN_TOKENS_GROUP",
                dependsOn = {"login", "S", "U", "O"},
                values = @Value(tokenAlias = "P", tail = true))
        private String password;

        @Prefix("--organization")
        @Parameter(
                mandatory = false,
                ruleGroups = "SIMPLE_LOGIN_TOKENS_GROUP",
                dependsOn = {"login", "S", "U", "P"},
                values = @Value(tokenAlias = "O", tail = true))
        private String organization;

        @Parameter(
                mandatory = true,
                ruleGroups = "COMPLEX_LOGIN_TOKENS_GROUP",
                dependsOn = "login",
                values = @Value(tokenAlias = "CS", tail = true))
        private String connectionString;

        @Override
        public OperationResult eval() {
            return new OperationResult("Test", ResultCode.SUCCESS, this, null);
        }
    }


    @Data
    @Master(name = "export", description = "none")
    public static class TestExportOperation implements Operation {

        @Parameter(mandatory = true, dependsOn = "export", values = {
                @Value(tokenAlias = "RE", tokenClass = StringToken.class, tokenValue = "repository"),
                @Value(tokenAlias = "RO", tokenClass = StringToken.class, tokenValue = "role"),
                @Value(tokenAlias = "U", tokenClass = StringToken.class, tokenValue = "user"),
                @Value(tokenAlias = "A", tokenClass = StringToken.class, tokenValue = "all")
        })
        private String context;

        @Parameter(mandatory = true, dependsOn = "RE", values =
        @Value(tokenAlias = "RP", tokenClass = RepositoryPathToken.class, tail = true))
        private String repositoryPath;

        @Parameter(dependsOn = "RP", values =
        @Value(tokenAlias = "->", tokenClass = StringToken.class, tokenValue = "to"))
        private String to;

        @Parameter(dependsOn = "->", values =
        @Value(tokenAlias = "F", tokenClass = FileNameToken.class, tail = true))
        private String fileUri;

        @Parameter(dependsOn = {"F", "RP", "IUR", "IME", "RPP", "IAE"}, values =
        @Value(tokenAlias = "UR", tokenClass = StringToken.class, tokenValue = "with-user-roles", tail = true))
        private String withUserRoles;

        @Parameter(dependsOn = {"F", "RP", "UR", "IME", "RPP", "IAE"}, values =
        @Value(tokenAlias = "IUR", tokenClass = StringToken.class, tokenValue = "with-include-audit-events", tail = true))
        private String withIncludeAuditEvents;

        @Parameter(dependsOn = {"F", "RP", "UR", "IUR", "RPP", "IAE"}, values =
        @Value(tokenAlias = "IME", tokenClass = StringToken.class, tokenValue = "with-include-monitoring-events", tail = true))
        private String withIncludeMonitoringEvents;

        @Parameter(dependsOn = {"F", "RP", "UR", "IUR", "IME", "IAE"}, values =
        @Value(tokenAlias = "RPP", tokenClass = StringToken.class, tokenValue = "with-repository-permissions", tail = true))
        private String withRepositoryPermissions;

        @Parameter(dependsOn = {"F", "RP", "UR", "IUR", "RPP", "IME"}, values =
        @Value(tokenAlias = "IAE", tokenClass = StringToken.class, tokenValue = "with-include-access-events", tail = true))
        private String withIncludeAccessEvents;

        @Override
        public OperationResult eval() {
            return new OperationResult("Test", ResultCode.SUCCESS, this, null);
        }
    }


    /*

    // Grammar for PrefixOperation //

    dummy -o X1
    dummy -o X2
    dummy -o X3

    dummy -> Abc
    dummy -o X2 -> Abc
    dummy -o X3 -> Abc

    dummy -> Abc -o X1
    dummy -> Abc -o X2
    dummy -> Abc -o X3

     */

    @Data
    @Master(name = "dummy")
    public static class PrefixOperation implements Operation {

        @Prefix("-o")
        @Parameter(dependsOn = {"dummy", "Abc"}, values = {
                @Value(tokenAlias = "X1", tokenClass = StringToken.class, tokenValue = "first", tail = true),
                @Value(tokenAlias = "X2", tokenClass = StringToken.class, tokenValue = "second", tail = true),
                @Value(tokenAlias = "X3", tokenClass = StringToken.class, tokenValue = "third", tail = true)
        })
        private String order;

        @Prefix("->")
        @Parameter(dependsOn = {"X3", "dummy", "X2"}, values = @Value(tokenAlias = "Abc", tail = true))
        private String abc;

        @Override
        public OperationResult eval() {
            return new OperationResult("Test", ResultCode.SUCCESS, this, null);
        }
    }

    @Master(name = "t1", tail = true)
    public static class T1Operation implements Operation {

        @Override
        public OperationResult eval() {
            return null;
        }
    }

}
