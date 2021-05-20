/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private MinPQ<Board> pq;
    private int move = 0;
    private LinkedList<Board> cached;
    private Board solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        cached = new LinkedList<Board>();
        pq = new MinPQ<Board>(new Comparator<Board>() {
            public int compare(Board o1, Board o2) {
                int m1 = o1.manhattan() + o1.getMove();
                int m2 = o2.manhattan() + o2.getMove();
                return m1 - m2;
            }
        });

        pq.insert(initial);

        pq.insert(initial.twin());
        solution = pq.delMin();
        while (!solution.isGoal()) {
            move++;
            cached.add(solution);
            for (Board neighbor : solution.neighbors()) {
                neighbor.setMove(move);
                if (!neighbor.equals(solution) && !cached.contains(neighbor)) {
                    pq.insert(neighbor);
                }

            }

            solution = pq.delMin();

        }
        cached.add(solution);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !solution.isTwin();
    }


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.getMove();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return cached;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
