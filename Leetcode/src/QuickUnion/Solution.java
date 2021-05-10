package QuickUnion;

public class Solution {

    public int findCircleNum(int[][] isConnected) {

        return 0;
    }

    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] bgrid = new boolean[n][m];
        WeightedQuickUnionUF wuf = new WeightedQuickUnionUF(grid.length * grid[0].length);

        return 0;
    }

    public void open(boolean[][] bgrid, int p, int q, WeightedQuickUnionUF wuf) {
        bgrid[p][q] = true;

        //left
        if (p > 1 && bgrid[p - 1][q] == true) {
            wuf.connected()
        }
    }
}
