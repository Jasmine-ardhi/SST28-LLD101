import java.util.concurrent.*;

class FixedWindowStrategy implements RateLimitingStrategy {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, Window> map = new ConcurrentHashMap<>();

    public FixedWindowStrategy(RateLimitConfig config) {
        this.config = config;
    }

    public boolean allow(String key) {
        long now = System.currentTimeMillis();

        map.putIfAbsent(key, new Window(0, now));
        Window window = map.get(key);

        synchronized (window) {
            if (now - window.startTime >= config.getWindowSizeMillis()) {
                window.startTime = now;
                window.count = 0;
            }

            if (window.count < config.getMaxRequests()) {
                window.count++;
                return true;
            }
            return false;
        }
    }

    static class Window {
        int count;
        long startTime;

        Window(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}