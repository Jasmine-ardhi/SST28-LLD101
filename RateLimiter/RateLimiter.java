class RateLimiter {

    private RateLimitingStrategy strategy;

    public RateLimiter(RateLimitingStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean allowRequest(String key) {
        return strategy.allow(key);
    }

    
    public void setStrategy(RateLimitingStrategy strategy) {
        this.strategy = strategy;
    }
}