package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K , V> {

    private Node root;
    private int size = 0;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(root, key)!= null;
    }

    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getNode(node.left, key);
        } else if (cmp > 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }

    @Override
    public V get(K key) {

        Node node = getNode(root, key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Inserts the key-value pair of KEY and VALUE into this dictionary,
     *  replacing the previous value associated to KEY, if any.
     */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            this.size += 1;
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

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        LinkedList<Node> list = new LinkedList<>();
        list.addLast(root);
        while (!list.isEmpty()) {
            Node node = list.removeFirst();
            if (node == null) {
                continue;
            }
            list.addLast(node.left);
            list.addLast(node.right);
            set.add(node.key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V targetValue = get(key);
            root = remove(root, key);
            size -= 1;
            return targetValue;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // if remove node has two children, choose successor to this position
            Node originalNode = node;
            node = getMinChild(node.right);
            node.left = originalNode.left;
            node.right = remove(originalNode.right, node.key);
        }
        return node;
    }

    private Node getMinChild(Node node) {
        if (node.left == null) {
            return node;
        }
        return getMinChild(node.left);
    }


    /**
     * removes the entry for the specified key only if it is currently mapped to
     * the specified value.
     */
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            remove(root, key);
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {
        LinkedList<Node> list;

        public BSTMapIter() {
            list = new LinkedList<>();
            list.addLast(root);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         */
        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         */
        @Override
        public K next() {
            Node node = list.removeFirst();
            list.addLast(node.left);
            list.addLast(node.right);
            return node.key;
        }
    }

    /**
     * prints out BSTMap in order of increasing Key
     */
    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.println(node.key.toString() + " -> " + node.value.toString());
        printInOrder(node.right);
    }
}
