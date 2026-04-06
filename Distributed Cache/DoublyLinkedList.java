class DoublyLinkedList<K, V> {
    Node<K, V> head, tail;

    public void addFirst(Node<K, V> node) {
        if (head == null) {
            head = tail = node;
            return;
        }
        node.next = head;
        head.prev = node;
        head = node;
    }

    public void moveToFront(Node<K, V> node) {
        if (node == head) return;

        if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        node.prev = null;
        node.next = head;
        head.prev = node;
        head = node;
    }

    public Node<K, V> removeLast() {
        Node<K, V> temp = tail;

        if (tail.prev != null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            head = tail = null;
        }
        return temp;
    }
}