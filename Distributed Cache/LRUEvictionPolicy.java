import java.util.*;
class LRUEvictionPolicy<K, V> implements EvictionPolicy<K, V> {

    private final int capacity;
    private Map<K, Node<K, V>> map;
    private DoublyLinkedList<K, V> dll;

    public LRUEvictionPolicy(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;

        Node<K, V> node = map.get(key);
        dll.moveToFront(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            dll.moveToFront(node);
        } else {
            if (map.size() == capacity) {
                Node<K, V> lru = dll.removeLast();
                map.remove(lru.key);
            }
            Node<K, V> newNode = new Node<>(key, value);
            dll.addFirst(newNode);
            map.put(key, newNode);
        }
    }
}