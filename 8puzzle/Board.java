/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[] ids;
    private int dimention;
    private int[][] board;
    private int move;
    private boolean isTwin;
    private List<Board> neighbors = new ArrayList<Board>();

    private int blankRCoordinate;
    private int blankCCoordinate;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = deepCopy2DArray(tiles);
        dimention = tiles.length;
        ids = new int[dimention * dimention + 1];
        ids[0] = -99;

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                if (tiles[i][j] == 0) {
                    blankCCoordinate = i;
                    blankRCoordinate = j;
                }
                ids[i * dimention + j + 1] = tiles[i][j];
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimention).append("\n");
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dimention;
    }

    // number of tiles out of place
    public int hamming() {
        int hcounter = 0;
        if (ids == null) return -1;
        int n = 1;
        while (n < dimention * dimention + 1) {
            if (this.ids[n] != n && ids[n] != 0) {
                hcounter++;
            }
            n++;
        }

        return hcounter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        int n = 0;

        /*
         * rindex*d +cindex = b
         * cindex = b -
         * */
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                if (board[i][j] != i * dimention + j + 1 && board[i][j] != 0) {
                    int rindex = board[i][j] / dimention;
                    int cindex = board[i][j] - rindex * dimention - 1;

                    distance += Math.abs(rindex - i) + Math.abs(cindex - j);

                }
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int n = 1;

        while (n < dimention * dimention + 1) {
            if (ids[n] != n && ids[n] != 0) {
                return false;
            }
            n++;
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (!(y instanceof Board)) return false;
        Board o = (Board) y;
        if (o.dimention == this.dimention) {
            return Arrays.equals(o.ids, this.ids);
        }

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        boolean isTwin = this.isTwin;
        //left
        if (blankRCoordinate >= 1 && blankRCoordinate < dimention) {
            int[][] aux = deepCopy2DArray(board);
            swap(blankCCoordinate, blankRCoordinate, blankCCoordinate, blankRCoordinate - 1, aux);
            Board lNeighbor = new Board(aux);
            lNeighbor.setTwin(isTwin);
            neighbors.add(lNeighbor);
        }
        //right
        if (blankRCoordinate < dimention - 1) {
            int[][] aux = deepCopy2DArray(board);
            swap(blankCCoordinate, blankRCoordinate, blankCCoordinate, blankRCoordinate + 1, aux);
            Board lNeighbor = new Board(aux);
            lNeighbor.setTwin(isTwin);
            neighbors.add(lNeighbor);
        }
        //up
        if (blankCCoordinate >= 1 && blankCCoordinate < dimention) {
            int[][] aux = deepCopy2DArray(board);
            swap(blankCCoordinate, blankRCoordinate, blankCCoordinate - 1, blankRCoordinate, aux);
            Board lNeighbor = new Board(aux);
            lNeighbor.setTwin(isTwin);
            neighbors.add(lNeighbor);
        }

        //down
        if (blankCCoordinate < dimention - 1) {
            int[][] aux = deepCopy2DArray(board);
            swap(blankCCoordinate, blankRCoordinate, blankCCoordinate + 1, blankRCoordinate, aux);
            Board lNeighbor = new Board(aux);
            lNeighbor.setTwin(isTwin);
            neighbors.add(lNeighbor);
        }
        return neighbors;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getMove() {
        return this.move;
    }

    public void setTwin(boolean isTwin) {
        this.isTwin = isTwin;
    }

    public boolean isTwin() {
        return this.isTwin;
    }

    private void swap(int i, int j, int k, int h, int[][] target) {
        int temp = target[i][j];
        target[i][j] = target[k][h];
        target[k][h] = temp;
    }

    private int[][] deepCopy2DArray(int[][] origin) {
        int[][] aux = new int[origin.length][origin[0].length];

        for (int i = 0; i < origin.length; i++) {
            int[] sub = Arrays.copyOf(origin[i], origin[0].length);
            aux[i] = sub;
        }
        return aux;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] aux = deepCopy2DArray(board);
        Board b = null;
        // 随便找两个相邻的位置就可以了，只要不越界，只要不是 0，就可以交换
        for (int i = 0; i < dimention * dimention - 1; i++) {
            int x = i / dimention;
            int y = i % dimention;
            int xx = (i + 1) / dimention;
            int yy = (i + 1) % dimention;
            if (aux[x][y] != 0 && aux[xx][yy] != 0) {
                swap(x, y, xx, yy, aux);
                b = new Board(aux);
                break;
            }
        }
        b.setTwin(true);
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] tiles = new int[][] {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };

        Board b = new Board(tiles);
        Board aa = b.twin();
        for (Board bb : b.neighbors()) {
            System.out.println(bb.toString());
        }

    }

}
