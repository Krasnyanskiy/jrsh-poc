package ua.krasnyanskiy.jrsh.operation.grammar.token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class TokenPreconditions {

    public static boolean isLoginToken(String token) {
        Pattern pattern = compile("(\\w+[|])?\\w+[%]\\w+[@]\\S+");
        Matcher matcher = pattern.matcher(token);
        return matcher.matches();
    }

    public static boolean isScriptNameToken(String token) {
        Pattern scriptPattern = compile("(.)+(.jrs)$");
        Matcher scriptMatcher = scriptPattern.matcher(token);
        return scriptMatcher.matches();
    }

}