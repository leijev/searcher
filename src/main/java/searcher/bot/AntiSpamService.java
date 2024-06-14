package searcher.bot;

import java.util.HashMap;
import java.util.Map;

public class AntiSpamService {
    private static AntiSpamService antiSpamService;
    private Map<Long, Long> cache;

    private AntiSpamService() {
        cache = new HashMap<>();
    }

    public static AntiSpamService getInstance() {
        if (antiSpamService == null) {
            antiSpamService = new AntiSpamService();
        }

        return antiSpamService;
    }

    public Long getLastRequest(Long id) {
        if (cache.get(id) == null) {
            cache.put(id, System.currentTimeMillis());
            return System.currentTimeMillis();
        }

        return cache.get(id);
    }

    public void requestMade(Long id, Long time) {
        cache.put(id, time);
    }
}
