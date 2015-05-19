package com.jaspersoft.jasperserver.jrsh.core.completion;

import jline.console.completer.Completer;

import java.util.List;

public class RepositoryStaticCompleter implements Completer {

//    public static List<String> resources;

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
//        if (buffer == null) {
//            buffer = "";
//        }
//        String translated = buffer;
//
//        if (resources == null) {
//            resources = new RepositoryContentReceiver().receiveAsList();
//
//        }
//
//        return matchFiles(translated, resources, candidates);
//    }
//
//    protected int matchFiles(String translated, List<String> resources, List<CharSequence> candidates) {
//        if (resources == null) {
//            return -1;
//        }
//        resources.stream().filter(resource -> resource.startsWith(translated)).forEach(resource -> {
//            String name = resource + " ";
//            candidates.add(name);
//        });
//
//        String common = commonSubstring(translated, resources);
//        String diff = diff(translated, common);
//        if (!diff.isEmpty()) {
//            candidates.clear();
//            candidates.add(diff);
//            return translated.length();
//        }
//
//        Set<String> cuts = new HashSet<>();
//
//        resources.stream().filter(r -> r.startsWith(common)).forEach(r -> {
//            String s = r.substring(common.length());
//            int idx = s.indexOf("/");
//            String cut = idx < 0 ? s : s.substring(0, idx);
//            cuts.add(cut + (idx < 0 ? "" : "/"));
//        });
//
//        if (!cuts.isEmpty()) {
//            candidates.clear();
//            if (cuts.contains("/")) {
//                candidates.add("/");
//            } else {
//                candidates.addAll(cuts);
//            }
//        }
//
//        return translated.length();
//    }
//
//    private String commonSubstring(String src, Collection<String> col) {
//        Set<String> set = col.stream()
//                .filter(el -> el.startsWith(src))
//                .collect(Collectors.toCollection(() -> new TreeSet<>()));
//
//        String common = "",
//               small = "",
//               temp = "";
//
//        String[] arr = set.toArray(new String[set.size()]);
//        for (String el : arr) {
//            if (small.length() < el.length()) small = el;
//        }
//
//        char[] smallStrChars = small.toCharArray();
//        for (char c : smallStrChars) {
//            temp += c;
//            for (String el : set.toArray(new String[set.size()])) {
//                if (!el.contains(temp)) {
//                    temp = "";
//                    break;
//                }
//            }
//            if (!temp.equals("") && temp.length() > common.length()) {
//                common = temp;
//            }
//        }
//        return common;
        return 0;
    }

//    private String diff(String input, String common) {
//        return common.isEmpty() ? "" : common.substring(input.length());
//    }



//    class RepositoryContentReceiver {
//        public List<String> receiveAsList() {
//            List<String> list = new ArrayList<>();
//            List<ClientResourceLookup> lookups = new Connector().connect()
//                    .resourcesService()
//                    .resources()
//                    .parameter(ResourceSearchParameter.FOLDER_URI, "/")
//                    .parameter(ResourceSearchParameter.LIMIT, "2500")
//                    .search()
//                    .getEntity()
//                    .getResourceLookups();
//            for (ClientResourceLookup lookup : lookups) {
//                list.add(lookup.getUri());
//            }
//            return list;
//        }
//    }

//    public static class Connector {
//        public Session connect() {
//            return SessionFactory.getSharedSession();
//        }
//    }
}