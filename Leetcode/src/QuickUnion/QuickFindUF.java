package QuickUnion;

public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int N) {
        id = new int[N];

        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public static void main(String args[]) {
        QuickFindUF qf = new QuickFindUF(10);

        qf.union(1, 3);
        qf.union(3, 4);

        int x = 1;

        assert qf.connected(1, 5);

    }
}
