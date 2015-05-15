package refactoring.impl;

public class Lexer {

    public String[] getTokens(String line) {
        return line.split("\\s+");
    }
}
