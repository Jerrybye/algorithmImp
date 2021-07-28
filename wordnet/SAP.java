/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/*
 * immutable data type
 * */
public class SAP {
    private final Digraph di;
    private Ancestor cm;

    public SAP(Digraph G) {
        di = G;
    }

    private final class Ancestor implements Comparable<Ancestor> {
        private final int id;
        private final int length;

        private Ancestor(int id, int length) {
            this.id = id;
            this.length = length;
        }

        public int compareTo(Ancestor o) {
            return Integer.compare(this.length, o.length);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= di.V() || w >= di.V())
            throw new java.lang.IllegalArgumentException();


        List<Integer> vl = new ArrayList<>();
        vl.add(v);

        List<Integer> wl = new ArrayList<>();
        wl.add(w);
        return length(vl,
                      wl);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        length(v, w);

        return cm == null ? -1 : cm.id;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int size = di.V();
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }

        /*BFS*/
        MinPQ<Ancestor> ancestors = new MinPQ<>();

        for (int nodev : v) {

            for (int nodew : w) {
                if (nodev < 0 || nodew < 0 || nodev >= di.V() || nodew >= di.V())
                    throw new java.lang.IllegalArgumentException();

                boolean[] markv = new boolean[size];
                boolean[] markw = new boolean[size];

                int[] lengv = new int[size];
                int[] lengw = new int[size];

                for (int i = 0; i < size; i++) {
                    lengv[i] = -1;
                    lengw[i] = -1;
                }

                lengv[nodev] = 0;
                lengw[nodew] = 0;

                //search from v
                Queue<Integer> bfs = new Queue<>();
                bfs.enqueue(nodev);

                while (!bfs.isEmpty()) {
                    int cur = bfs.dequeue();
                    markv[cur] = true;
                    for (int next : di.adj(cur)) {
                        if (!markv[next]) {
                            bfs.enqueue(next);
                            lengv[next] = lengv[cur] + 1;
                        }
                        // if (lengv[next] == -1 || lengv[next] > lengv[cur] + 1) { // 更新距离
                        //     lengv[next] = lengv[cur] + 1;
                        // }
                    }


                }

                bfs.enqueue(nodew);

                while (!bfs.isEmpty()) {
                    int cur = bfs.dequeue();
                    markw[cur] = true;
                    if (markv[cur]) {
                        Ancestor ca = new Ancestor(cur, lengv[cur] + lengw[cur]);
                        ancestors.insert(ca);
                    }
                    for (int next : di.adj(cur)) {
                        if (!markw[next]) {
                            bfs.enqueue(next);
                            lengw[next] = lengw[cur] + 1;
                        }

                        // if (lengw[next] == -1 || lengw[next] > lengw[cur] + 1) { // 更新距离
                        //     lengw[next] = lengw[cur] + 1;
                        // }
                    }
                }
            }
        }


        if (!ancestors.isEmpty()) {
            cm = ancestors.min();
            return cm.length;
        }
        else {
            cm = null;
            return -1;
        }

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        length(v, w);

        return cm == null ? -1 : cm.id;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
