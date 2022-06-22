package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private static final int defaultInitialSize = 16;
    private static final double defaultLoadFactor = 0.75;
    private Collection<Node>[] buckets;
    private int size = 0;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(defaultInitialSize, defaultLoadFactor);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, defaultInitialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!


    @Override
    public void clear() {
        buckets = null;
        size = 0;
        loadFactor = defaultLoadFactor;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node getNode(K key) {
        if (size == 0) {
            return null;
        }
        // -1 % 4 --> -1; Math.floorMod(-1, 4) --> 3;
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
        } else {
            int index = Math.floorMod(key.hashCode(), buckets.length);
            buckets[index].add(createNode(key, value));
            size += 1;

            if (size / buckets.length > loadFactor) {
                resize(buckets.length * 2);
            }
        }
    }

    private void resize(int capacity) {
        Collection<Node>[] resized = createTable(capacity);
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int index = Math.floorMod(node.key.hashCode(), capacity);
                resized[index].add(node);
            }
        }
        buckets = resized;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> items : buckets) {
            for (Node node : items) {
                set.add(node.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        size -= 1;
        buckets[index].remove(node);
        return node.value;
    }

    @Override
    public V remove(K key, V value) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Node node = getNode(key);
        if (node == null || !node.value.equals(value)) {
            return null;
        }
        size -= 1;
        buckets[index].remove(node);
        return node.value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
