import java.util.Iterator;

public class BST<K extends Comparable<K>, V> implements Iterable<K> {
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
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public V get(K key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    private Node get(Node node, K key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }
            Node successor = min(node.right);
            successor.right = deleteMin(node.right);
            successor.left = node.left;
            node = successor;
            size--;
        }
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Iterator<K> iterator() {
        return new KeyIterator(root);
    }

    public int size() {
        return size;
    }

    private class KeyIterator implements Iterator<K> {
        private CustomStack<Node> stack;

        public KeyIterator(Node root) {
            stack = new CustomStack<>();
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

        public K next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements");
            }
            Node current = stack.pop();
            pushLeft(current.right);
            return current.key;
        }
    }

    // Custom stack implementation
    private static class CustomStack<T> {
        private Object[] elements;
        private int size;
        private static final int DEFAULT_CAPACITY = 10;

        public CustomStack() {
            this.elements = new Object[DEFAULT_CAPACITY];
            this.size = 0;
        }

        public void push(T element) {
            if (size == elements.length) {
                resize(elements.length * 2);
            }
            elements[size++] = element;
        }

        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack is empty");
            }
            T element = (T) elements[--size];
            elements[size] = null;
            return element;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void resize(int capacity) {
            Object[] newElements = new Object[capacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }
}
