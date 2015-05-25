package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.FilterBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OperationFactory {

    private static Map<String, Class<? extends Operation>> AVAILABLE_OPERATIONS = new HashMap<>();

    static {
        Set<Class<? extends Operation>> types = getOperationTypes();
        for (Class<? extends Operation> operationType : types) {
            Master annotation = operationType.getAnnotation(Master.class);
            if (annotation != null) {
                String operationName = annotation.name();
                AVAILABLE_OPERATIONS.put(operationName, operationType);
            }
        }
    }

    public static Operation getOperationByName(String operationName) {
        Class<? extends Operation> operationType = AVAILABLE_OPERATIONS.get(operationName);
        if (operationType == null) {
            throw new NoOperationFoundException();
        }
        return create(operationType);
    }

    protected static Operation create(Class<? extends Operation> operationType) {
        Operation instance = null;
        try {
            instance = operationType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Set<Operation> getAvailableOperations() {
        Set<Operation> set = new HashSet<>();
        for (Class<? extends Operation> type : AVAILABLE_OPERATIONS.values()) {
            set.add(create(type));
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    protected static Set<Class<? extends Operation>> getOperationTypes() {
        Set<Class<? extends Operation>> operationTypes = new HashSet<>();

        Map<String, Object> config = getConfig();
        List<String> packages = (List<String>) config.get("packages-to-scan");
        List<String> classes = (List<String>) config.get("classes");

        // default package
        FilterBuilder filter = new FilterBuilder()
                .includePackage("com.jaspersoft.jasperserver.jrsh");


        if (packages != null) {
            for (String aPackage : packages) {
                aPackage = StringUtils.chomp(aPackage, ".*");
                filter.includePackage(aPackage);
            }
        }

        if (classes != null) {
            for (String aClass : classes) {
                try {
                    Class clz = Class.forName(aClass);
                    if (!Modifier.isAbstract(clz.getModifiers())
                            && Operation.class.isAssignableFrom(clz)) {
                        operationTypes.add(clz);
                    }
                } catch (ClassNotFoundException ignored) {
                    // NOP
                }
            }
        }

        Reflections ref = new Reflections(new SubTypesScanner(), filter);
        for (Class<? extends Operation> subType : ref.getSubTypesOf(Operation.class)) {
            if (!Modifier.isAbstract(subType.getModifiers())) {
                operationTypes.add(subType);
            }
        }
        return operationTypes;
    }

    @SuppressWarnings("unchecked")
    protected static Map<String, Object> getConfig() {
        Yaml yaml = new Yaml();
        InputStream stream = OperationFactory.class.getClassLoader()
                .getResourceAsStream("config.yml");
        return (Map<String, Object>) yaml.load(stream);
    }

}
