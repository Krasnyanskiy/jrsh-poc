//package com.jaspersoft.jasperserver.jrsh.core.script;
//
//import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.Multimap;
//import com.jaspersoft.jasperserver.jrsh.core.NoGrammarRulesFoundException;
//import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Prefix;
//import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule.DefaultRule;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.graph.TokenEdge;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.graph.TokenEdgeFactory;
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
//import org.apache.commons.lang3.tuple.ImmutablePair;
//import org.apache.commons.lang3.tuple.Pair;
//import org.jgrapht.Graph;
//import org.jgrapht.GraphPath;
//import org.jgrapht.alg.KShortestPaths;
//import org.jgrapht.graph.DefaultDirectedGraph;
//import org.junit.Test;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.util.*;
//import java.util.Map.Entry;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ReflectionTest extends OperationMetadataProvider {
//
//    @Test
//    public void shouldReturnGrammarForTestLoginOperationWithCorrectRulesSize() throws Exception {
//        Grammar grammar = parseGrammar(new TestLoginOperation());
//        assertThat(grammar.getRules()).hasSize(31);
//    }
//
//    @Test
//    public void shouldReturn() throws Exception {
//        Grammar grammar = parseGrammar(new TestExportOperation());
//        assertThat(grammar.getRules()).hasSize(100500);
//    }
//
//    @Test
//    public void should() throws Exception {
//        Grammar grammar = parseGrammar(new PrefixOperation());
//        assertThat(grammar.getRules()).hasSize(100500);
//    }
//
//    protected Grammar parseGrammar(Operation operation) throws Exception {
//        //
//        Graph<Token, TokenEdge<Token>> graph = new DefaultDirectedGraph<>(new TokenEdgeFactory());
//        //
//        Multimap<String, Pair<String, Boolean>> tfg = ArrayListMultimap.create();   // Token Family Groups (example: the context has repo | all | user)
//        Multimap<String, Token> tokensByGroups = ArrayListMultimap.create();        // Mandatory Tokens (divide by group)
//        Map<String, Pair<Token, String[]>> dependencies = new HashMap<>();          // ...
//
//
//        // {2}
//        Grammar grammar = new SimpleGrammar();
//        Token rootToken = null; // determines a graph start node
//        Token tokenInstance;
//        String tokenName;
//        Set<Rule> rules;
//
//        Class<?> clazz = operation.getClass();
//        Master master = clazz.getDeclaredAnnotation(Master.class);
//
//        if (master != null) {
//            tokenInstance = createInstance(master.tokenClass(), master.name(), master.name(), true, true);
//            tokenName = master.name();
//            rootToken = tokenInstance; // set the root token
//
//            // add rule for single token operation (help, version, etc)
//            if (master.tail()) {
//                Rule rule = new DefaultRule();
//                rule.addToken(tokenInstance);
//            } else {
//                // add @master to dependencies
//                dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, new String[]{}));
//            }
//
//
//            // create a node of graph
//            graph.addVertex(tokenInstance);
//
//
//            Field[] fields = clazz.getDeclaredFields();
//
//
//            for (Field field : fields) {
//                Prefix prefix = field.getAnnotation(Prefix.class);
//                Parameter param = field.getAnnotation(Parameter.class);
//
//                if (prefix != null && param != null) {
//                    tokenName = prefix.value();
//                    tokenInstance = createInstance(prefix.tokenClass(), tokenName, tokenName, param.mandatory(), false);
//                    dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, param.dependsOn()));
//                    graph.addVertex(tokenInstance);
//
//                    String[] groups = param.ruleGroups();
//                    for (String group : groups) {
//                        tfg.put(group, new ImmutablePair<>(tokenName, param.mandatory()));
//                    }
//                }
//
//                // go through parameters and extract content
//                if (param != null) {
//                    Value[] values = param.values();
//                    for (Value val : values) {
//                        tokenName = val.tokenAlias();
//                        String tokenVal = val.tokenValue();
//                        tokenInstance = createInstance(val.tokenClass(), tokenName, tokenVal, param.mandatory(), val.tail());
//
//                        // handle prefix
//                        if (prefix != null) {
//                            dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, new String[]{prefix.value()}));
//                        } else {
//                            dependencies.put(tokenName, new ImmutablePair<>(tokenInstance, param.dependsOn()));
//                        }
//
//                        graph.addVertex(tokenInstance);
//                        String[] groups = param.ruleGroups();
//
//                        for (String group : groups) {
//                            tfg.put(group, new ImmutablePair<>(tokenName, param.mandatory()));
//                        }
//
//                        if (param.mandatory()) {
//                            String[] groupNames = param.ruleGroups();
//                            for (String name : groupNames) {
//                                tokensByGroups.put(name, tokenInstance);
//                            }
//                        }
//
////                        if ("field".equals(param.name())) {
////                            String name = field.getName();
////                            parameterTokens.put(name, tokenInstance);
////                        }
//
//                    }
//                }
//            }
//
//            for (String key : tfg.asMap().keySet()) {
//                tfg.get(key).add(new ImmutablePair<>(master.name(), true));
//            }
//        }
//
//        buildEdgesInGraph(graph, dependencies);
//
//        // JGraph doesn't build paths if there is only one token (version, help, etc)
//        if (!(graph.vertexSet().size() == 1 && graph.vertexSet().contains(rootToken))) {
//            rules = buildRules(graph, rootToken, tokensByGroups/*, parameterTokens*/);
//        } else {
//            // ignore if there is only one token node
//            rules = new HashSet<>();
//        }
//
//        if (!rules.isEmpty()) {
//            grammar.addRules(rules);
//        } else {
//            throw new NoGrammarRulesFoundException();
//        }
//
//        return grammar;
//    }
//
//    private Set<Rule> buildRules(Graph<Token, TokenEdge<Token>> graph, Token root, Multimap<String, Token> mtg) {
//        KShortestPaths<Token, TokenEdge<Token>> paths = new KShortestPaths<>(graph, root, 2500); // 2500 is a magic number
//        Set<Token> vertexes = graph.vertexSet();
//        Set<Rule> rules = new LinkedHashSet<>();
//
//        for (Token vertex : vertexes) {
//            if (!vertex.equals(root)) {
//                if (vertex.isTailOfRule()) {
//                    List<GraphPath<Token, TokenEdge<Token>>> ps = paths.getPaths(vertex);
//                    for (GraphPath<Token, TokenEdge<Token>> path : ps) {
//                        Rule rule = convertPathToRule(path);
//                        if (isValidRule(rule, mtg/*, parameterTokens*/)) {
//                            rules.add(rule);
//                        }
//                    }
//                }
//            }
//        }
//        return rules;
//    }
//
//    private boolean isValidRule(Rule rule, Multimap<String, Token> mandatoryTokenGroup) {
//        List<Token> ruleTokens = rule.getTokens();
//        for (Entry<String, Collection<Token>> entry : mandatoryTokenGroup.asMap().entrySet()) {
//            Collection<Token> mandatoryTokensList = entry.getValue();
//
//            boolean noElementsInCommon = Collections.disjoint(ruleTokens, mandatoryTokensList);
//            if (!noElementsInCommon) {
//
//                System.out.println();
//
//                //List<Token> allMandatoryTokensInGroup = groupTokens.stream().filter(Token::isMandatory).collect(Collectors.toList());
//                //return ruleTokens.containsAll(allMandatoryTokensInGroup);
//            }
//        }
//        return false;
//    }
//
//
//    private void buildEdgesInGraph(Graph<Token, TokenEdge<Token>> graph, Map<String, Pair<Token, String[]>> dependencies) {
//        for (Entry<String, Pair<Token, String[]>> entry : dependencies.entrySet()) {
//            Pair<Token, String[]> tokenPair = entry.getValue();
//            for (String dependencyName : tokenPair.getRight()) {
//                Pair<Token, String[]> dependency = dependencies.get(dependencyName);
//                graph.addEdge(dependency.getLeft(), tokenPair.getLeft());
//            }
//        }
//    }
//
//
//    public Rule convertPathToRule(GraphPath<Token, TokenEdge<Token>> path) {
//        List<TokenEdge<Token>> list = path.getEdgeList();
//        Rule rule = new DefaultRule();
//        Set<Token> set = new LinkedHashSet<>();
//        list.forEach(edge -> {
//            set.add(edge.getSource());
//            set.add(edge.getTarget());
//        });
//        set.forEach(rule::addToken);
//        return rule;
//    }
//
//
//    protected Token createInstance(Class<? extends Token> tokenType, String tokenName, String tokenValue, boolean mandatory, boolean tail) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        return tokenType.getConstructor(String.class, String.class, boolean.class, boolean.class).newInstance(tokenName, tokenValue, mandatory, tail);
//    }
//
//
//    public class SimpleGrammar implements Grammar {
//
//        private List<Rule> rules;
//
//        public SimpleGrammar(Rule... rules) {
//            Collections.addAll(this.rules, rules);
//        }
//
//        @Override
//        public List<Rule> getRules() {
//            return rules;
//        }
//
//        @Override
//        public void addRule(Rule rule) {
//            rules.add(rule);
//        }
//
//        @Override
//        public void addRules(Collection<Rule> rules) {
//            rules.addAll(rules);
//        }
//    }
//}
