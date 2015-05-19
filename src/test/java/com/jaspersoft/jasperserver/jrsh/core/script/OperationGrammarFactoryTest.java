package com.jaspersoft.jasperserver.jrsh.core.script;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Prefix;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule.DefaultRule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.graph.TokenEdge;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.graph.TokenEdgeFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.Field;
import java.util.*;

public class OperationGrammarFactoryTest {



    public static Grammar getGrammar(Operation operation) throws Exception {
        Token masterToken;
        //Grammar grammar = new SimpleOperationGrammar();
        Graph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());
        Map<String, Pair<Token, String[]>> dependencies = new HashMap<>();
        Multimap<String, Token> mandatoryTokenByGroup = ArrayListMultimap.create();

        Field[] fields = operation.getClass().getDeclaredFields();
        Master master = operation.getClass().getAnnotation(Master.class);

        // check if operation name is available in the metadata
        /*
        if (master == null) {
            throw new MissedMandatoryAnnotationException();
        }
        */
        String tokenName = master.name();
        Token tokenInstance;

        if (master.tail()) {
            tokenInstance = master.tokenClass().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenName, true, true);
            //Rule rule = new SimpleRule(tokenInstance);
            //grammar.addRule(rule);
        }

        tokenInstance = master.tokenClass().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenName, true, false);
        masterToken = tokenInstance;
        dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, new String[]{}));
        graph.addVertex(tokenInstance);

        for (Field field : fields) {
            Prefix fpr = field.getAnnotation(Prefix.class);
            Parameter fp = field.getAnnotation(Parameter.class);

            if (fpr != null && fp != null) {
                tokenName = fpr.value();
                tokenInstance = fpr.tokenClass().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenName, fp.mandatory(), false);
                dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, fp.dependsOn()));
                graph.addVertex(tokenInstance);
            }

            if (fp != null) {
                Value[] vals = fp.values();
                for (Value val : vals) {
                    tokenName = val.tokenAlias();
                    String tokenValue = val.tokenValue();
                    //tokenInstance = val.tokenClass().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenValue, fp.mandatory(), val.terminal());

                    if (fpr != null) {
                        String prefixTokenName = fpr.value();
                        dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, new String[]{prefixTokenName}));
                    } else {
                        dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, fp.dependsOn()));
                    }
                    graph.addVertex(tokenInstance);

                    if (fp.mandatory()) {
                        //mandatoryTokenByGroup.put(fp.mandatoryGroup(), tokenInstance);
                    }
                }
            }
        }

        Set<Rule> rules;
        setDependencies(graph, dependencies);

        if (graph.vertexSet().size() == 1 && graph.vertexSet().contains(masterToken)) {
            rules = new HashSet<>();
        } else {
            rules = setRules(graph, masterToken, mandatoryTokenByGroup, dependencies);
        }

        if (!rules.isEmpty()) {
            //grammar.addRules(rules);
        }

        //if (grammar.getRules().isEmpty()) {
            //throw new NoGrammarFoundException();
        //}

        return /*grammar*/null;
    }

    protected static Set<Rule> setRules(Graph<Token, TokenEdge<Token>> graph, Token masterToken, Multimap<String, Token> mandatoryGroups, Map<String, Pair<Token, String[]>> dependencies) {
        KShortestPaths<Token, TokenEdge<Token>> paths = new KShortestPaths<>(graph, masterToken, 2500);
        Set<Token> vertexes = graph.vertexSet();
        Set<Rule> rules = new LinkedHashSet<>();

//        vertexes.stream().filter(v -> !v.equals(masterToken)).filter(Token::isTailOfRule).forEach(v -> {
//            List<GraphPath<Token, TokenEdge<Token>>> allPaths = paths.getPaths(v);
//            for (GraphPath<Token, TokenEdge<Token>> path : allPaths) {
//                Rule rule = convertPathToRule(path);
//                if (isValidRule(rule.getTokens(), dependencies, mandatoryGroups)) {
//                    rules.add(rule);
//                }
//            }
//        });
        return rules;
    }

    protected static boolean isValidRule(List<Token> ruleTokens, Map<String, Pair<Token, String[]>> dependencies, Multimap<String, Token> mandatoryGroups) {
        int counter = 0;
        for (Token token : ruleTokens) {
            for (String key : mandatoryGroups.keys()) {
                //Collection<Token> group = mandatoryGroups.getOperationByName(key);
                //if (group.contains(token)) {
                //    counter++;
                //}
            }
        }
        //if (counter == 0) {
        // check if dependencies contains one or more mandatory group
        //for (Token rToken : ruleTokens) {
        //String name = rToken.getName();
        // TODO
        //}
        //}

        return counter == mandatoryGroups.keys().size();
    }

//    static void rec(String tName, Map<String, Pair<Token, String[]>> dependencies) {
//        Pair<Token, String[]> pair = dependencies.getOperationByName(tName);
//        String[] dNames = pair.getRight();
//        if (dNames.length != 0) {
//            for (String dName : dNames) {
//                rec(dName, dependencies);
//            }
//        }
//    }

    protected static void setDependencies(Graph<Token, TokenEdge<Token>> graph, Map<String, Pair<Token, String[]>> dependencies) {
        for (Map.Entry<String, Pair<Token, String[]>> entry : dependencies.entrySet()) {
            Pair<Token, String[]> tuple = entry.getValue();
            for (String dependencyName : tuple.getRight()) {
                //Pair<Token, String[]> __dep = dependencies.getOperationByName(dependencyName);
                //graph.addEdge(__dep.getLeft(), tuple.getLeft());
            }
        }
    }

    protected static Rule convertPathToRule(GraphPath<Token, TokenEdge<Token>> path) {
        List<TokenEdge<Token>> list = path.getEdgeList();
        Rule rule = new DefaultRule();
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
}
