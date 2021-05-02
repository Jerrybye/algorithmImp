/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rs = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);


        while (!StdIn.isEmpty()) {
            rs.enqueue(StdIn.readString());
        }

        while (k > 0) {
            System.out.println(rs.dequeue());
            k--;
        }

    }
}
