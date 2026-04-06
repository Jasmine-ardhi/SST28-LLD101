import java.util.*;
class Database<K, V> {
    private Map<K, V> db = new HashMap<>();

    public V get(K key) {
        System.out.println("Fetching from DB...");
        return db.get(key);
    }

    public void put(K key, V value) {
        db.put(key, value);
    }
}