package QuickUnion;

public class WeightedQuickUnionUF {

    private int[] id;
    private int[] sz;
    private int size = 0;

    public WeightedQuickUnionUF(int N) {

        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }

    }

    public int root(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    public int components() {
        for (int size : sz) {
            if (size == 0) {
                continue;
            }

            this.size++;
        }

        return this.size;
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
        QuickUnion.QuickFindUF qf = new QuickUnion.QuickFindUF(10);

        qf.union(1, 3);


    }
}
