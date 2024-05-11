public class MyHashTable<K, V> {
    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        this(11);
    }
    public MyHashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Invalid initial capacity");
        }
        this.M = initialCapacity;
        this.chainArray = new HashNode[M];
        this.size = 0;
    }
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        if (chainArray[index] == null) {
            chainArray[index] = newNode;
        } else {
            HashNode<K, V> current = chainArray[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    public int getM() {
        return M;
    }
    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        HashNode<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    chainArray[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
    public boolean contains(V value) {
        for (HashNode<K, V> node : chainArray) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (value == null) {
                    if (current.value == null) {
                        return true;
                    }
                } else {
                    if (value.equals(current.value)) {
                        return true;
                    }
                }
                current = current.next;
            }
        }
        return false;
    }
    public K getKey(V value) {
        for (HashNode<K, V> node : chainArray) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (value == null) {
                    if (current.value == null) {
                        return current.key;
                    }
                } else {
                    if (value.equals(current.value)) {
                        return current.key;
                    }
                }
                current = current.next;
            }
        }
        return null;
    }
    private static class HashNode<K, V> {
        private final K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " : " + value + "}";
        }
    }
}