public class Main {
    public static void main(String[] args) {

        RateLimitConfig config =
            new RateLimitConfig(5, 60_000); // 5 req/min

       
        RateLimitingStrategy strategy =
            new FixedWindowStrategy(config);

        RateLimiter rateLimiter = new RateLimiter(strategy);

        String user = "T1";

        for (int i = 1; i <= 7; i++) {
            boolean allowed = rateLimiter.allowRequest(user);

            if (allowed) {
                System.out.println("Request " + i + " allowed → call external API");
            } else {
                System.out.println("Request " + i + " blocked ");
            }
        }

        
        
        rateLimiter.setStrategy(new SlidingWindowStrategy(config));

        System.out.println("\nSwitched to Sliding Window\n");

        for (int i = 1; i <= 7; i++) {
            System.out.println(rateLimiter.allowRequest(user));
        }
    }
}