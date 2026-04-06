class CacheNode<K, V> {
    private EvictionPolicy<K, V> evictionPolicy;

    public CacheNode(int capacity) {
        this.evictionPolicy = new LRUEvictionPolicy<>(capacity);
    }

    public V get(K key) {
        return evictionPolicy.get(key);
    }

    public void put(K key, V value) {
        evictionPolicy.put(key, value);
    }
}