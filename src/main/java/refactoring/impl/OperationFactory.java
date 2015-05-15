package refactoring.impl;

import refactoring.Operation;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

public class OperationFactory {


    public static final String CLASS_ERROR = "Can't find operation with given class (%s)";
    public static final String NAME_ERROR = "Can't find operation with given name (%s)";


    private static Set<Operation> operations = new HashSet<>();


    public static Operation getOperationByName(String oName) {
        for (Operation operation : operations) {
            Master meta = operation.getClass().getAnnotation(Master.class);
            if (meta != null) {
                if (meta.name().equals(oName)) {
                    return operation;
                }
            }
        }
        throw new RuntimeException(format(NAME_ERROR, oName));
    }


    public static Operation getOperationByClass(Class<? extends Operation> paramClass) {
        for (Operation operation : operations) {
            if (operation.getClass() == paramClass) {
                return operation;
            }
        }
        throw new RuntimeException(format(CLASS_ERROR, paramClass));
    }


    public static Collection<Operation> getOperations() {
        return operations;
    }
}
