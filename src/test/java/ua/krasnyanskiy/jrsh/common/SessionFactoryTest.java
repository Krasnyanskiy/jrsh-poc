package ua.krasnyanskiy.jrsh.common;

import org.junit.Ignore;
import org.junit.Test;

public class SessionFactoryTest {

    @Test(expected = IllegalStateException.class)
    @Ignore
    public void shouldName() {
        SessionFactory.getSharedSession();
    }
}