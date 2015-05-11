package sandbox;

public class MissedMandatoryAnnotationException extends RuntimeException {
    public MissedMandatoryAnnotationException() {
        super("Класс не аннотирован обязательной аннотацией @Master");
    }
}

