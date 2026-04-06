import java.util.*;
class DistributedCache<K, V> {

    private List<CacheNode<K, V>> nodes;
    private DistributionStrategy<K> strategy;
    private Database<K, V> db;

    public DistributedCache(int numNodes, int capacityPerNode) {
        nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new CacheNode<>(capacityPerNode));
        }
        this.strategy = new ModuloHashStrategy<>();
        this.db = new Database<>();
    }

    public V get(K key) {
        int index = strategy.getNodeIndex(key, nodes.size());
        CacheNode<K, V> node = nodes.get(index);

        V value = node.get(key);

        if (value == null) {
            value = db.get(key);
            if (value != null) {
                node.put(key, value); // cache fill
            }
        }
        return value;
    }

    public void put(K key, V value) {
        int index = strategy.getNodeIndex(key, nodes.size());
        CacheNode<K, V> node = nodes.get(index);

        node.put(key, value);
        db.put(key, value); // assumption: write-through
    }
}