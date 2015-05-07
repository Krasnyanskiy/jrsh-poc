package ua.krasnyanskiy.jrsh.operation.grammar;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.edge.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Prefix;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RuleBuilder {
    private Set<Token> mandatoryTokens = new HashSet<>();

    public Set<Rule> buildRules(OperationParameters param) throws Exception {

        Graph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());
        Map<String, Pair<Token, Token>> tokens = new HashMap<>(); // (prefix, param)
        Class<? extends OperationParameters> clazz = param.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Token root = null;

        //
        // Vertex building
        //
        for (Field field : fields) {
            Parameter paramMeta = field.getAnnotation(Parameter.class);
            Prefix prefixMeta = field.getAnnotation(Prefix.class);
            Master master = field.getAnnotation(Master.class);
            if (paramMeta != null) {
                StringToken tknPrefix = null;
                if (prefixMeta != null) {
                    tknPrefix = new StringToken(prefixMeta.value(), paramMeta.mandatory());
                    graph.addVertex(tknPrefix);
                }
                for (String val : paramMeta.value()) {
                    boolean isEndPoint = paramMeta.terminal();
                    Token tknValue = paramMeta.token()
                            .getConstructor(String.class, boolean.class, boolean.class)
                            .newInstance(val, paramMeta.mandatory(), isEndPoint);
                    if (master != null) {
                        root = tknValue;
                    }
                    if (paramMeta.mandatory()) {
                        mandatoryTokens.add(tknValue);
                    }
                    graph.addVertex(tknValue);
                    tokens.put(val, new ImmutablePair<Token, Token>(tknValue, tknPrefix));
                }
            }
        }

        //
        // Dependencies resolving (Setup edges)
        //
        for (Field field : fields) {
            Parameter paramMeta = field.getAnnotation(Parameter.class);
            if (paramMeta != null) {
                for (String val : paramMeta.value()) {
                    Pair<Token, Token> target = tokens.get(val);
                    Token targetTkn = target.getLeft();
                    Token prefixTargetTkn = target.getRight();
                    if (targetTkn != null && prefixTargetTkn != null) {
                        graph.addEdge(prefixTargetTkn, targetTkn); // Tuple: (--username, username)
                    }
                    String[] dependencies = paramMeta.dependsOn();
                    for (String dependency : dependencies) {
                        Pair<Token, Token> pair = tokens.get(dependency);
                        Token value = pair.getLeft();
                        if (prefixTargetTkn != null) {
                            graph.addEdge(value, prefixTargetTkn);
                        } else {
                            graph.addEdge(value, targetTkn);
                        }
                    }
                }
            }
        }

        //
        // Rules building
        //
        if (root == null) {
            throw new RuntimeException("Oops!");
        }

        KShortestPaths<Token, TokenEdge<Token>> paths = new KShortestPaths<>(graph, root, 1000);
        Set<Token> vertexes = graph.vertexSet();

        Set<Rule> rules = new LinkedHashSet<>();

        for (Token endPoint : vertexes) {
            if (!endPoint.equals(root)) {
                if (endPoint.isEndPoint()) {
                    List<GraphPath<Token, TokenEdge<Token>>> paths_ = paths.getPaths(endPoint);

                    for (GraphPath<Token, TokenEdge<Token>> path : paths_) {
                        if (isValid(path)) {
                            Rule rule = pathToRule(path);
                            rules.add(rule);
                        }
                    }
                }
            }
        }
        return rules;
    }

    private Rule pathToRule(GraphPath<Token, TokenEdge<Token>> path) {
        List<TokenEdge<Token>> list = path.getEdgeList();
        Rule rule = new Rule();
        Set<Token> set = new LinkedHashSet<>();
        for (TokenEdge<Token> edge : list) {
            set.add(edge.getSource());
            set.add(edge.getTarget());
        }
        for (Token token : set) {
            rule.addToken(token);
        }
        return rule;
    }

    private boolean isValid(GraphPath<Token, TokenEdge<Token>> path) {
        Set<Token> copy = new HashSet<>(mandatoryTokens);
        List<TokenEdge<Token>> edges = path.getEdgeList();
        for (TokenEdge<Token> edge : edges) {
            Token t2 = edge.getTarget();
            Token t1 = edge.getSource();
            if (copy.contains(t2)){
                copy.remove(t2);
            }
            if (copy.contains(t1)){
                copy.remove(t1);
            }
        }
        return copy.isEmpty();
    }
}
