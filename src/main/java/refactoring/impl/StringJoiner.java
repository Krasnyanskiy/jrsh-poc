package refactoring.impl;

import org.apache.commons.lang3.StringUtils;

public class StringJoiner {

    public static String join(String[] input) {
        return StringUtils.join(input, " ");
    }

    public static String join(String[] input, String prefix) {
        return prefix.concat(StringUtils.join(input, " "));
    }

}
