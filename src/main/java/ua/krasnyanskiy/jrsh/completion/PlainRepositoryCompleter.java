package ua.krasnyanskiy.jrsh.completion;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.common.RepositoryContentReceiver;

import java.util.List;
import java.util.Map;

import static ua.krasnyanskiy.jrsh.common.ResourcesUtil.extract;
import static ua.krasnyanskiy.jrsh.common.ResourcesUtil.filter;
import static ua.krasnyanskiy.jrsh.common.ResourcesUtil.normalize;
import static ua.krasnyanskiy.jrsh.common.ResourcesUtil.process;

/**
 * @author Alexander Krasnyanskiy
 */
public class PlainRepositoryCompleter implements Completer {

    private RepositoryContentReceiver receiver;

    public PlainRepositoryCompleter() {
        this.receiver = new RepositoryContentReceiver();
    }

    @Override
    public int complete(final String path, final int cursor, final List<CharSequence> candidates) {

        if (!receiver.isSessionAccessible()) {
            return path == null ? -1 : path.length();
        }

        RepositoryContentReceiver.Response resp;
        List<String> filtered;
        List<String> processedResources;
        Map<String, Boolean> resources;

        if (path == null) {
            candidates.add("/");
            return 0;
        }

        if (cursor < path.length()) {
            return path.length();
        }

        String lastToken = path;
        final int inputLength = lastToken.length();

        lastToken = normalize(lastToken);
        final String extracted = extract(lastToken);
        final int cutLen = extracted.length();

        resp = receiver.receive(lastToken);
        resources = resp.getLookups();
        processedResources = process(resources);
        filtered = filter(extracted, processedResources);

        if (resp.isSuccess() && !path.endsWith("/")) {
            candidates.clear();
            candidates.add("/");
            return path.length();
        }

        if (!filtered.isEmpty()) {
            String resourceName = filtered.get(0);
            if (filtered.size() == 1 && !resourceName.endsWith("/")
                    && extracted.endsWith(resourceName)) {
                candidates.clear();
                candidates.add(" ");
                return inputLength;
            }
            if (filtered.size() == 1) {
                candidates.addAll(filtered);
                return inputLength - cutLen;
            }
            candidates.addAll(filtered);
            return inputLength - cutLen;
        } else {
            if (!path.endsWith("/")) {
                candidates.clear();
            } else {
                candidates.addAll(processedResources);
            }
        }
        return inputLength;
    }
}
