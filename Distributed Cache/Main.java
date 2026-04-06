public class Main {
    public static void main(String[] args) {

     
        DistributedCache<String, String> cache =
                new DistributedCache<>(3, 2); 

      
        cache.put("A", "Apple");
        cache.put("B", "Ball");
        cache.put("C", "Cat");

     
        System.out.println(cache.get("A"));

        
        
        System.out.println(cache.get("D"));
        

        
        cache.put("D", "Dog");
        cache.put("E", "Elephant");

        
        

        cache.get("B");
        cache.put("F", "Fish");

      
        
        System.out.println(cache.get("C")); 
        System.out.println(cache.get("B")); 
    }
}