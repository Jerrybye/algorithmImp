/* *****************************************************************************
 *  Name:Haoran Deng
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node head, tail;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node last;
    }


    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }


    // add the item
    public void enqueue(Item item) {
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

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int ur = StdRandom.uniform(0, size);

        Node cur = head;

        if (ur == size - 1 && size > 1) {
            Item item = tail.item;
            tail = tail.last;
            //tail.next = null;

            size--;
            return item;
        }
        else if (ur == 0) {
            Item item = head.item;
            head = head.next;

            if (size == 1) {
                head = null;
                tail = null;
            }

            size--;
            return item;
        }
        else {
            while (ur > 0) {
                cur = cur.next;

                ur--;
            }

            if (cur.next != null) {
                cur.last.next = cur.next;
                cur.next.last = cur.last;
            }

            size--;
            return cur.item;
        }


    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int ur = StdRandom.uniform(0, size);

        Node cur = head;
        while (ur != 0) {
            cur = cur.next;

            ur--;
        }

        return cur.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new randomizedQueueIterator();
    }

    private class randomizedQueueIterator implements Iterator<Item> {
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
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.size();
        queue.enqueue(502);
        queue.dequeue();
        queue.enqueue(722);
        queue.size();
        queue.dequeue();
    }

}
