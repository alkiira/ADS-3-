import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return;
        }

        Node current = root;
        while (true) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new Node(key, value);
                    size++;
                    return;
                }
                current = current.left;
            } else if (cmp > 0) {
                if (current.right == null) {
                    current.right = new Node(key, value);
                    size++;
                    return;
                }
                current = current.right;
            } else {
                current.value = value; // Update value if key already exists
                return;
            }
        }
    }

    public V get(K key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public void delete(K key) {
        Node parent = null;
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                break;
            }
            parent = current;
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current == null) {
            return; // Key not found
        }
        size--;
        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else if (current == parent.left) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.right == null) {
            if (parent == null) {
                root = current.left;
            } else if (current == parent.left) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else {
            Node successor = getSuccessor(current);
            if (parent == null) {
                root = successor;
            } else if (current == parent.left) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
        }
    }

    private Node getSuccessor(Node node) {
        Node parent = node;
        Node successor = node;
        Node current = node.right;
        while (current != null) {
            parent = successor;
            successor = current;
            current = current.left;
        }
        if (successor != node.right) {
            parent.left = successor.right;
            successor.right = node.right;
        }
        return successor;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator();
    }

    public int size() {
        return size;
    }

    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private class InOrderIterator implements Iterator<Entry<K, V>> {
        private Stack<Node> stack;

        public InOrderIterator() {
            stack = new Stack<>();
            pushLeft(root);
        }

        private void pushLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            Node current = stack.pop();
            pushLeft(current.right);
            return new Entry<>(current.key, current.value);
        }
    }
}
