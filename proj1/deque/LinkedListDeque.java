package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T> {
    /**
     * choose circular sentinel topology for doubly linked list that
     * the last node's next points to the first node.
     * instead of two sentinel topology with only item and next.
     */
    private class Node {
        public Node prev;
        public T item;
        public Node next;

        private Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    /**
     * Creates an empty linked list deque
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null , null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * You can assume that item is never null.
     */
    public void addFirst(T item) {
        // add the new one to the first position
        sentinel.next = new Node(sentinel, item, sentinel.next);
        // set the previous first sentinel's prev to the new one instead of the last
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * You can assume that item is never null.
     */
    public void addLast(T item) {
        // add the new one to the last position
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        // set the previous last one (now the second last one)'s next to the new one instead of the first
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        for (Node p = sentinel.next; p.item != null; p = p.next) {
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */public T removeFirst() {
         if (isEmpty()) { return null; }
         T item = sentinel.next.item;
         sentinel.next = sentinel.next.next;
         sentinel.next.prev = sentinel;
         size -= 1;
         return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) { return null; }
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * Must not alter the deque!
     */
    public T get(int index) {
        int nodeIndex = 0;
        if (index < 0 || index >= size) { return null; }
        for (Node p = sentinel.next; p.item != null; p = p.next) {
            if (nodeIndex == index) { return p.item; }
            else { nodeIndex += 1; }
        }
        return null;
    }

    /**
     * Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        Node p = sentinel.next;
        if (index < 0 || index >= size) { return null; }
        return getRecursiveHelper(index, p);
    }

    /**
     * helper method for getRecursive method with recursion return.
     */
    private T getRecursiveHelper(int index, Node p) {
        if (index == 0) { return p.item; }
        return getRecursiveHelper(index - 1, p.next);
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node ptr;
        private LinkedListDequeIterator() {
            ptr = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return ptr == sentinel;
        }

        @Override
        public T next() {
            T item = ptr.item;
            ptr = ptr.next;
            return item;
        }
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as goverened by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this.
     * The java instanceof operator is used to test
     * whether the object is an instance of the specified type (class or subclass or interface))
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque other = (LinkedListDeque) o;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!other.get(i).equals(this.get(i))) {
                return false;
            }
        }
        return true;
    }
}
