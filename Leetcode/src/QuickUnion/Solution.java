import java.util.ArrayList;
import java.util.List;

class Solution {
    int[] ids;
    int[] sz;
    boolean[][] bgrid;
    int islands = 0;

    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        ids = new int[n * m];
        sz = new int[n * m];
        bgrid = new boolean[n][m];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = i;
            sz[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (Character.compare(grid[i][j], '1') == 0) {
                    islands++;
                    open(i, j, n, m);
                }
            }
        }


        return islands;

    }

    public void open(int p, int q, int n, int m) {
        bgrid[p][q] = true;

        //left
        if (q >= 1 && bgrid[p][q - 1]) {
            connect(metricToArray(p, q, m), metricToArray(p, q - 1, m));
        }

        //rightint island
//        if (q < m - 1 && bgrid[p][q + 1]) {
//            connect(ids, metricToArray(p, q, m), metricToArray(p, q + 1, m), sz);
//        }

        //up
        if (p >= 1 && bgrid[p - 1][q]) {
            connect(metricToArray(p, q, m), metricToArray(p - 1, q, m));
        }

        //down
//        if (p < n - 1 && bgrid[p + 1][q]) {
//            connect(ids, metricToArray(p, q, m), metricToArray(p + 1, q, m), sz);
//        }
    }

    public int metricToArray(int i, int j, int n) {

        return i * n + j;
    }

    public void connect(int p, int q) {

        int i = root(p);
        int j = root(q);

        if (i != j) {
            if (sz[i] >= sz[j]) {
                ids[j] = i;
                sz[i] = sz[j] + sz[i];
                sz[j] = 1;
            } else {
                ids[i] = j;
                sz[j] = sz[i] + sz[j];
                sz[i] = 1;
            }

            islands--;
        }

    }

    public int root(int p) {
        List<Integer> known = new ArrayList<Integer>();
        while (ids[p] != p) {
            //ids[p] = ids[ids[p]];
            known.add(p);
            p = ids[p];
        }

        for (int knowned : known) {
            ids[knowned] = p;
        }
        return p;
    }
    
}
