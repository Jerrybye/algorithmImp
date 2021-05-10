package QuickUnion;

import java.util.ArrayList;
import java.util.List;

public class PCWeightedQuickUnion {

    private int[] id;
    private int[] sz;

    public PCWeightedQuickUnion(int N) {

        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public PCWeightedQuickUnion(int[] ids) {
        id = ids;
        sz = new int[ids.length];
        for (int i = 0; i < ids.length; i++) {
            //id[i] = i;
            sz[i] = 1;
        }
    }

    /*
     *
     *           0 1 2 3 4 5 6 7 8 9
     *           0 0 0 1 1 1 3 3 6 6
     *                             1 : id[9] = id[id[9]]
     *                                  id[9] = id[6] = 3
     *                                     p=3;
     *                             2.  id[3] = id[id[3]]
     *                                  id[3] = id[1] = 0
     *                                     p = 0;
     * */
    public int root(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public int pcRoot(int p) {
        List<Integer> known = new ArrayList<Integer>();
        while (p != id[p]) {
            known.add(p);
            p = id[p];
        }

        for (int knowned : known) {
            id[knowned] = p;
        }

        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        /*
         * move all what ever root of p to root of q;
         * */
        int i = root(p);
        int j = root(q);
        if (sz[i] >= sz[j]) {
            id[j] = i;
            sz[i] += sz[i] + sz[j];
        } else {
            id[i] = j;
            sz[j] += sz[j] + sz[i];
        }


    }

    public static void main(String args[]) {
        int[] test = new int[]{0, 0, 0, 1, 1, 1, 3, 3, 6, 6};
        PCWeightedQuickUnion pc = new PCWeightedQuickUnion(test);
        pc.pcRoot(9);
        //assert qf.connected(1, 5);

    }
}
