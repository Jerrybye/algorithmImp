package BinarySearchTree;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class BST<Key extends Comparable<Key>, Value> {

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int level;
        private int count;

        public Node(Key key, Value val, int count, int level) {
            this.key = key;
            this.val = val;
            this.count = count;
            this.level = level;

        }
    }

    private Node root;

    public void put(Key key, Value val) {
        root = put(root, key, val, 1);
    }

    private Node put(Node x, Key key, Value val, int level) {
        if (x == null) {
            return new Node(key, val, 1, level);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            if (x.left == null) {
                x.left = put(null, key, val, x.level++);
            } else {
                x.left = put(x.left, key, val, x.level);
            }

        } else if (cmp > 0) {
            if (x.right == null) {
                x.right = put(null, key, val, x.level++);
            } else {
                x.right = put(x.right, key, val, x.level);
            }

        } else {
            x.val = val;
        }

        x.count = 1 + size(x.left) + size(x.right);


        return x;
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {

            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                x = x.right;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                return x.val;
            }


        }
        return null;
    }

    public int size() {
        return size(root);
    }

    public int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.count;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    /*
     * floor key means the largest key in the tree that is less than the given key
     * */
    private Node floor(Node x, Key key) {

        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);

        if (cmp == 0) {
            return x;
        }

        if (cmp < 0) {
            return floor(x.left, key);
        }

        Node t = floor(x.right, key);
        if (t == null) {
            return x;
        } else {
            return t;
        }
    }

    /*
     * Ceil means the min key in the tree that is larger than the given key
     * */

    public Key ceil(Key key) {
        Node t = ceil(root, key);
        if (t != null) {
            return t.key;
        }
        return null;
    }

    public Node ceil(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp == 0) {
            return x;
        }//base case
        if (cmp > 0) {
            return ceil(x.right, key);
        }

        Node t = ceil(x.left, key);
        return t == null ? x : t;


    }

    //Hibbard Deletetion
    public void delete(Key key) {

        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;

        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    public Key min() {
        return min(root).key;
    }

    public int getLevel(Key key) {
        Node x = root;
        while (x != null) {

            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                x = x.right;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                return x.level;
            }


        }
        return -1;
    }

    public boolean contains(Key key) {

        return contains(root, key);
    }

    private boolean contains(Node x, Key key) {
        if (x == null) {
            return false;
        }
        int cmp = key.compareTo(x.key);

        if (cmp > 0) {
            return contains(x.right, key);
        } else if (cmp < 0) {
            return contains(x.left, key);
        }

        return true;
    }


    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return min(x.right);
    }

    public Iterator<Key> iterator() {
        return null;
    }

    public static void main(String[] args) {
        BST<Integer, String> b = new BST<Integer, String>();

        b.put(5, "a");
        b.put(1, "b");
        b.put(3, "b");
        b.put(2, "b");
        b.put(4, "b");
        b.put(0, "b");


        System.out.println(b.size());
        b.delete(3);
        System.out.println(b.ceil(5));


    }
}
