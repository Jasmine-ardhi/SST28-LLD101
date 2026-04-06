interface RateLimitingStrategy {
    boolean allow(String key);
}