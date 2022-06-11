package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * Creates an empty array deque
     */
    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    private int getFirstIndex() {
        return (nextFirst + 1) % items.length;
    }
    private int getLastIndex() {
        return (nextLast - 1 + items.length) % items.length;
    }

    /**
     * The amount of memory that your program uses at any given time must be proportional to the number of items.
     * For arrays of length 16 or more, your usage factor should always be at least 25%.
     * This means that before performing a remove operation
     * that will bring the number of elements in the array under 25% the length of the array,
     * you should resize the size of the array down.
     */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int first = getFirstIndex();
        int last = getLastIndex();
        if (first < last) {
            System.arraycopy(items, first, newItems, 0, size);
        } else {
            System.arraycopy(items, first, newItems, 0, items.length - first);
            System.arraycopy(items, 0, newItems, items.length - first, last + 1);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /**
     * check if the size of array is too small that needs to resize to double size
     */
    private void checkMul() {
        if (size == items.length) {
            resize(size * 2);
        }
    }

    /**
     * check if the size of array is too large with less than 25% usage that needs to resize
     */
    private void checkDiv() {
        int len = items.length;
        if (len >= 16 && size < len / 4) {
            resize(len / 4);
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     * You can assume that item is never null.
     */
    public void addFirst(T item) {
        checkMul();

        items[nextFirst] = item;
        size += 1;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    /**
     * Adds an item of type T to the back of the deque.
     * You can assume that item is never null.
     */
    public void addLast(T item) {
        checkMul();
        items[nextLast] = item;
        size += 1;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
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
        for (T i : items) {
            System.out.print(i + " ");
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        checkDiv();

        nextFirst = (nextFirst + 1) % items.length;
        T item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        checkDiv();

        nextLast = (nextLast - 1 + items.length) % items.length;
        T item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * Must not alter the deque!
     */
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int index;

        ArrayDequeIterator(){
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T item = get(index);
            index += 1;
            return item;
        }
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as goverened by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this.)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque ad = (ArrayDeque) o;
        if (ad.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (ad.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }
}
