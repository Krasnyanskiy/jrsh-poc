package ua.krasnyanskiy.jrsh.common;

import org.junit.Ignore;
import org.junit.Test;

public class SessionOperationFactoryTest {

    @Test(expected = IllegalStateException.class)
    @Ignore
    public void shouldName() {
        SessionFactory.getSharedSession();
    }
}