public class ElementSort {

    public static void selectionSort(Comparable[] a) {

        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            exch(a, min, i);

        }
    }

    public static void insertionSort(Comparable[] a) {
        int N = a.length;

        for (int i = 0; i < N; i++) {

            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                } else break;
            }
        }
    }

    public static void shellSort(Comparable[] a) {
        int N = a.length;
        int h = 1;

        while (h < N / 3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h; j--) {
                    if (less(a[j], a[j - h])) {
                        exch(a, j, j - h);
                    }
                }
            }
            h = h / 3;

        }
    }

    private static boolean less(Comparable v, Comparable w) {

        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;


    }

    public static void main(String[] args) {
        Integer[] a = {1, 3, 2, 4, 1};
        Heap.sort(a);


        for (int aa : a) {
            System.out.println(aa);
        }

        //Arrays.sort(a);

    }
}
