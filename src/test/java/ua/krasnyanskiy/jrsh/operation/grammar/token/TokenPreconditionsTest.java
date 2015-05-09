package ua.krasnyanskiy.jrsh.operation.grammar.token;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TokenPreconditionsTest {

    @Test
    public void should() {
        boolean isLogin = TokenPreconditions.isLoginTokens("login --username joe --password secret --server localhost");
        Assertions.assertThat(isLogin).isTrue();
    }
}