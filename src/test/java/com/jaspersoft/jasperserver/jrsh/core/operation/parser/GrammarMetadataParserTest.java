package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.script.OperationMetadataProvider.PrefixOperation;
import com.jaspersoft.jasperserver.jrsh.core.script.OperationMetadataProvider.T1Operation;
import com.jaspersoft.jasperserver.jrsh.core.script.OperationMetadataProvider.TestExportOperation;
import com.jaspersoft.jasperserver.jrsh.core.script.OperationMetadataProvider.TestLoginOperation;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class GrammarMetadataParserTest {

    @Test
    public void shouldReturnNotEmptyGrammar() throws Exception {
        Grammar grammar = GrammarMetadataParser.parseGrammar(new TestLoginOperation());
        Assertions.assertThat(grammar).isNotNull();
    }

    @Test
    public void shouldReturnGrammarWithProperRuleSize() throws Exception {
        Grammar grammar = GrammarMetadataParser.parseGrammar(new TestLoginOperation());
        Assertions.assertThat(grammar.getRules()).hasSize(31);
    }

    @Test
    public void shouldReturnProperGrammarSizeForTestExportOperation() throws Exception {
        Grammar grammar = GrammarMetadataParser.parseGrammar(new TestExportOperation());
        Assertions.assertThat(grammar.getRules()).hasSize(652);
    }

    @Test
    public void shouldReturnProperGrammarSizeForPrefixOperation() throws Exception {
        Grammar grammar = GrammarMetadataParser.parseGrammar(new PrefixOperation());
        Assertions.assertThat(grammar.getRules()).hasSize(6);
    }

    @Test
    public void shouldReturnGrammarForT1Operation() throws Exception {
        Grammar grammar = GrammarMetadataParser.parseGrammar(new T1Operation());
        Assertions.assertThat(grammar.getRules()).hasSize(1);
    }
}