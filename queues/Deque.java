/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head, tail;
    private int size = 0;

    private class Node {
        Item item;
        Node last;
        Node next;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        Node oldHead = head;
        head = new Node();
        head.item = item;
        if (oldHead == null) {
            tail = head;
        }
        else {
            head.next = oldHead;
            oldHead.last = head;
        }

        size++;

    }

    // add the item to the back
    public void addLast(Item item) {
        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        if (isEmpty()) {
            head = tail;
        }
        else {
            oldTail.next = tail;
            tail.last = oldTail;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = head.item;
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
        }

        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();

        }
        Item item = tail.item;

        if (size == 1) {
            tail = null;
            head = null;
        }
        else {
            tail = tail.last;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new deckIterator();
    }

    private class deckIterator implements Iterator<Item> {
        private Node cur = head;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (hasNext()) {
                Item item = cur.item;
                cur = cur.next;
                return item;
            }
            else {
                throw new NoSuchElementException();
            }

        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        deque.addFirst(3);
        deque.removeLast();
    }

}
