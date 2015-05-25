package com.jaspersoft.jasperserver.jrsh.core.script;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategy;

import java.util.List;

public interface Script {

    List<String> getSource();

    Class<? extends EvaluationStrategy> getEvaluationStrategyType();

}
