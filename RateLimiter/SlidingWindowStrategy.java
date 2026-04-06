import java.util.*;
import java.util.concurrent.*;

class SlidingWindowStrategy implements RateLimitingStrategy {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, Deque<Long>> map = new ConcurrentHashMap<>();

    public SlidingWindowStrategy(RateLimitConfig config) {
        this.config = config;
    }

    public boolean allow(String key) {
        long now = System.currentTimeMillis();

        map.putIfAbsent(key, new LinkedList<>());
        Deque<Long> queue = map.get(key);

        synchronized (queue) {
            while (!queue.isEmpty() &&
                   now - queue.peekFirst() >= config.getWindowSizeMillis()) {
                queue.pollFirst();
            }

            if (queue.size() < config.getMaxRequests()) {
                queue.addLast(now);
                return true;
            }
            return false;
        }
    }
}