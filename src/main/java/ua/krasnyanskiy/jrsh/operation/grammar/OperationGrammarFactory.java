package ua.krasnyanskiy.jrsh.operation.grammar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import ua.krasnyanskiy.jrsh.exception.MissedMandatoryAnnotationException;
import ua.krasnyanskiy.jrsh.exception.NoGrammarFoundException;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenEdge;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenEdgeFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Prefix;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OperationGrammarFactory {

    public static Grammar getGrammar(Class<? extends AbstractOperationParameters> paramClass) throws Exception {
        Token masterToken;
        SimpleOperationGrammar grammar = new SimpleOperationGrammar();
        Graph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());
        Map<String, Pair<Token, String[]>> dependencies = new HashMap<>();
        Multimap<String, Token> mandatoryTokenByGroup = ArrayListMultimap.create();

        Field[] fields = paramClass.getDeclaredFields();
        Master master = paramClass.getAnnotation(Master.class);

        // check if operation name is available in the metadata
        if (master == null) {
            throw new MissedMandatoryAnnotationException();
        }

        String tokenName = master.name();
        Token tokenInstance;

        if (master.terminal()) {
            tokenInstance = master.token().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenName, true, true);
            Rule rule = new Rule(tokenInstance);
            grammar.addRule(rule);
        }

        tokenInstance = master.token().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenName, true, false);
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
                    tokenName = val.tokenName();
                    String tokenValue = val.tokenValue();
                    tokenInstance = val.tokenClass().getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenValue, fp.mandatory(), val.terminal());

                    if (fpr != null) {
                        String prefixTokenName = fpr.value();
                        dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, new String[]{prefixTokenName}));
                    } else {
                        dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, fp.dependsOn()));
                    }
                    graph.addVertex(tokenInstance);

                    if (fp.mandatory()) {
                        mandatoryTokenByGroup.put(fp.mandatoryGroup(), tokenInstance);
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
            grammar.addRules(rules);
        }

        if (grammar.getRules().isEmpty()) {
            throw new NoGrammarFoundException();
        }

        return grammar;
    }

    protected static Set<Rule> setRules(Graph<Token, TokenEdge<Token>> graph, Token masterToken, Multimap<String, Token> mandatoryGroups, Map<String, Pair<Token, String[]>> dependencies) {
        KShortestPaths<Token, TokenEdge<Token>> paths = new KShortestPaths<>(graph, masterToken, 2500);
        Set<Token> vertexes = graph.vertexSet();
        Set<Rule> rules = new LinkedHashSet<>();

        for (Token v : vertexes) {
            if (!v.equals(masterToken)) {
                if (v.isTerminal()) {
                    List<GraphPath<Token, TokenEdge<Token>>> allPaths = paths.getPaths(v);
                    for (GraphPath<Token, TokenEdge<Token>> path : allPaths) {
                        Rule rule = convertPathToRule(path);
                        if (isValidRule(rule.getTokens(), dependencies, mandatoryGroups)) {
                            rules.add(rule);
                        }
                    }
                }
            }
        }
        return rules;
    }

    protected static boolean isValidRule(List<Token> ruleTokens, Map<String, Pair<Token, String[]>> dependencies, Multimap<String, Token> mandatoryGroups) {
        int counter = 0;
        for (Token token : ruleTokens) {
            for (String key : mandatoryGroups.keys()) {
                Collection<Token> group = mandatoryGroups.get(key);
                if (group.contains(token)) {
                    counter++;
                }
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
//        Pair<Token, String[]> pair = dependencies.get(tName);
//        String[] dNames = pair.getRight();
//        if (dNames.length != 0) {
//            for (String dName : dNames) {
//                rec(dName, dependencies);
//            }
//        }
//    }

    protected static void setDependencies(Graph<Token, TokenEdge<Token>> graph,
                                          Map<String, Pair<Token, String[]>> dependencies) {
        for (Entry<String, Pair<Token, String[]>> entry : dependencies.entrySet()) {
            Pair<Token, String[]> tuple = entry.getValue();
            for (String dependencyName : tuple.getRight()) {
                Pair<Token, String[]> __dep = dependencies.get(dependencyName);
                graph.addEdge(__dep.getLeft(), tuple.getLeft());
            }
        }
    }

    protected static Rule convertPathToRule(GraphPath<Token, TokenEdge<Token>> path) {
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
}
