//package com.jaspersoft.jasperserver.jrsh.core.operation.parser;
//
//import com.jaspersoft.jasperserver.jrsh.core.operation.impl.ExportOperation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoGrammarRulesFoundException;
//import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
//import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.WrongConnectionStringFormatException;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//public class LL1OperationParserTest {
//    public OperationParser parser = new LL1OperationParser();
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void shouldParseOperationAndReturnProperOperationInstance() {
//        /* Given */
//        String line = "export " + "repository " + "/public/Samples/blah " + "to " + "/Users/file.zip";
//        /* When */
//        ExportOperation retrieved = (ExportOperation) parser.parse(line);
//        /* Then */
//        Assert.assertNotNull(retrieved);
//        Assert.assertEquals("repository", retrieved.getContext());
//        Assert.assertEquals("/public/Samples/blah", retrieved.getRepositoryPath());
//        Assert.assertEquals("to", retrieved.getTo());
//        Assert.assertEquals("/Users/file.zip", retrieved.getFileUri());
//        Assert.assertEquals(null, retrieved.getWithUserRoles());
//        Assert.assertEquals(null, retrieved.getWithIncludeAuditEvents());
//        Assert.assertEquals(null, retrieved.getWithIncludeAccessEvents());
//        Assert.assertEquals(null, retrieved.getWithRepositoryPermissions());
//        Assert.assertEquals(null, retrieved.getWithIncludeMonitoringEvents());
//    }
//
//    @Test
//    public void shouldReturnExportOperationWithCorrectExportParameters() {
//        /* Given */
//        String line = "export " + "repository " + "/public/Samples/blah " + "with-include-monitoring-events " + "with-repository-permissions";
//        /* When */
//        ExportOperation retrieved = (ExportOperation) parser.parse(line);
//        /* Then */
//        Assert.assertNotNull(retrieved);
//        Assert.assertEquals("repository", retrieved.getContext());
//        Assert.assertEquals("/public/Samples/blah", retrieved.getRepositoryPath());
//        Assert.assertEquals("with-repository-permissions", retrieved.getWithRepositoryPermissions());
//        Assert.assertEquals("with-include-monitoring-events", retrieved.getWithIncludeMonitoringEvents());
//        Assert.assertEquals(null, retrieved.getWithUserRoles());
//        Assert.assertEquals(null, retrieved.getWithIncludeAccessEvents());
//        Assert.assertEquals(null, retrieved.getWithIncludeAuditEvents());
//    }
//
//    @Test
//    public void shouldParseLoginOperationAndReturnProperOperationInstanceWhenPassLoginToken() {
//        /* Given */
//        String line = "login " + "jasperadmin@localhost:8080/jasperserver-pro";
//        /* When */
//        thrown.expect(WrongConnectionStringFormatException.class);
//        thrown.expectMessage("Connection string doesn't match the format. Should be [username%password@url].");
//        parser.parse(line);
//    }
//
//    @Test public void shouldParseLoginOperation() {
//        /* Given */
//        String line = "login " + "--server " + "http://localhost:8080/jasperserver-pro " + "--username " + "superuser " + "--password " + "superuser";
//        /* When */
//        LoginOperation operation = (LoginOperation) parser.parse(line);
//        /* Then */
//        Assert.assertNotNull(operation);
//        Assert.assertEquals(null, operation.getConnectionString());
//        Assert.assertEquals("superuser", operation.getUsername());
//        Assert.assertEquals("superuser", operation.getPassword());
//        Assert.assertEquals("http://localhost:8080/jasperserver-pro", operation.getServer());
//    }
//
//    @Test public void shouldThrowAnExceptionIfMissedMandatoryParameter() {
//        /* Given */
//        String line = "export " + "repository " + "with-include-monitoring-events " + "with-repository-permissions";
//        /* When */
//        thrown.expect(NoGrammarRulesFoundException.class);
//        thrown.expectMessage("Cannot find a rule for given operation.");
//        /* Then */
//        parser.parse(line);
//    }
//
//    @Test public void shouldThrowAnExceptionWhenThereIsNoOperationWithGivenName() {
//        /* Given */
//        String line = "wrong " + "repository " + "with-include-monitoring-events " + "with-repository-permissions";
//        /* When */
//        thrown.expect(NoOperationFoundException.class);
//        thrown.expectMessage("Operation not found.");
//        /* Then */
//        parser.parse(line);
//    }
//}