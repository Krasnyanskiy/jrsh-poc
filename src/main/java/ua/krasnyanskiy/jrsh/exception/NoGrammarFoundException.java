package ua.krasnyanskiy.jrsh.exception;

public class NoGrammarFoundException extends RuntimeException {
    public NoGrammarFoundException() {
        super("Не могу найти подходящую грамматику для операции");
    }
}
