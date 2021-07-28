package DFS;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    /*
     * Leet code 547 number of Provice
     * */

//    public int findCircleNum(int[][] isConnected) {
//
//        boolean[] marked = new boolean[isConnected.length];
//        int count = 0;
//        for (int i = 0; i < isConnected.length; i++) {
//            if (!marked[i]) {
//                dfs(isConnected, marked, i);
//                count++;
//            }
//        }
//        return count;
//    }

//    public void dfs(int[][] isConnected, boolean[] marked, int i) {
//        for (int j = 0; j < isConnected.length; j++) {
//            if (isConnected[i][j] == 1 && !marked[j]) {
//                marked[j] = true;
//                dfs(isConnected, marked, j);
//
//            }
//        }
//
//    }

    /*
     * Leetcode 323
     * */
    public int countComponents(int n, int[][] edges) {

        boolean[] marked = new boolean[n - 1];
        int count = 0;
        Graph g = new Graph(n);

        for (int[] edge : edges) {
            g.addEdge(edge[0], edge[1]);
        }

        for (int i = 0; i < n - 1; i++) {
            if (!marked[i]) {
                dfs(g, marked, i);
            }
        }
        return count;
    }

    public void dfs(Graph g, boolean[] marked, int w) {
        marked[w] = true;
        for (int v : g.adj(w)) {
            if (!marked[v]) {
                dfs(g, marked, v);

            }
        }
    }

    public static class Graph {
        int V;
        int E;
        List<Integer>[] adj;

        public Graph(int V) {
            this.V = V;
            adj = (List<Integer>[]) new List[V];

            for (int i = 0; i < V - 1; i++) {
                adj[i] = new ArrayList<Integer>();
            }
        }

        public void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    /*
    * leetcode 490 Maze
    * m == maze.length
        n == maze[i].length
        1 <= m, n <= 100
        maze[i][j] is 0 or 1.
        start.length == 2
        destination.length == 2
        0 <= startrow, destinationrow <= m
        0 <= startcol, destinationcol <= n
        Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
        The maze contains at least 2 empty spaces.
    * */
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] marked = new boolean[maze.length][maze[0].length];
        dfs(maze, marked, start);
        return marked[destination[0]][destination[1]];
    }

    enum direction {
        U, L, R, D
    }

    public void dfs(int[][] maze, boolean[][] marked, int[] start) {
        int c = start[0];
        int r = start[1];

        if (!marked[c][r]) {
            marked[c][r] = true;
        } else {
            return;
        }

        // go up
        while (c > 0 && maze[c][r] == 0) {
            if (maze[c - 1][r] != 1) {
                c--;
            } else {
                break;
            }
        }

        if (!marked[c][r]) {
            dfs(maze, marked, new int[]{c, r});
        }
        c = start[0];
        r = start[1];

        //go left

        while (r > 0 && maze[c][r] == 0) {

            if (maze[c][r - 1] != 1) {
                r--;
            } else {
                break;
            }
        }

        if (!marked[c][r]) {
            dfs(maze, marked, new int[]{c, r});
        }
        c = start[0];
        r = start[1];

        //go right
        while (r < maze[0].length - 1 && maze[c][r] == 0) {
            if (maze[c][r + 1] != 1) {
                r++;
            } else {
                break;
            }


        }
        if (!marked[c][r]) {
            dfs(maze, marked, new int[]{c, r});
        }
        c = start[0];
        r = start[1];


        //go down
        while (c < maze.length - 1 && maze[c][r] == 0) {
            if (maze[c + 1][r] != 1) {
                c++;
            } else {
                break;
            }
        }

        if (!marked[c][r]) {
            dfs(maze, marked, new int[]{c, r});

        }
        c = start[0];
        r = start[1];

    }

    /*
     * LeetCode 200
     * */
    public int numIslands(char[][] grid) {
        boolean[][] marked = new boolean[grid.length][grid[0].length];
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!marked[i][j] && grid[i][j] == '1') {
                    islands++;
                    dfsl200(grid, marked, new int[]{i, j});
                }
            }
        }
        return islands;
    }

    public void dfsl200(char[][] grid, boolean[][] marked, int[] pos) {
        marked[pos[0]][pos[1]] = true;
        for (int[] adj : adj(grid, pos)) {
            if (!marked[adj[0]][adj[1]]) {
                dfsl200(grid, marked, adj);
            }

        }
    }

    public Iterable<int[]> adj(char[][] grid, int[] pos) {
        List<int[]> adj = new ArrayList<>();
        int i = pos[0];
        int j = pos[1];
        //up
        if (i >= 1 && grid[i - 1][j] == '1') {
            adj.add(new int[]{i - 1, j});
        }
        //down
        if (i + 1 < grid.length && grid[i + 1][j] == '1') {
            adj.add(new int[]{i + 1, j});
        }
        //left
        if (j >= 1 && grid[i][j - 1] == '1') {
            adj.add(new int[]{i, j - 1});
        }
        if (j + 1 < grid[0].length && grid[i][j + 1] == '1') {
            adj.add(new int[]{i, j + 1});
        }

        return adj;
    }
    
    public static void main(String[] args) {
//        char[][] maze = new char[][]{
//                {'1', '1', '1', '1', '0'},
//                {'1', '1', '0', '1', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '0', '0', '0'}
//        };
//
//
//        Solution s = new Solution();
//        s.numIslands(maze);

        String s = "Hello";
        fun(s);


        System.out.println(s);
    }

    public static void fun(String temp) {
        temp = "World";
    }


}
