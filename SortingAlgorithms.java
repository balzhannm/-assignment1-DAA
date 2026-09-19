import java.util.Random;

public class SortingAlgorithms {
    private static final int INSERTION_SORT_CUTOFF = 15;
    private static final Random RANDOM = new Random();

    public static void mergeSort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        int[] buffer = new int[a.length];
        mergeSortHelper(a, 0, a.length - 1, buffer, metrics);
    }

    private static void mergeSortHelper(int[] a, int low, int high, int[] buffer, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (high - low <= INSERTION_SORT_CUTOFF) {
                insertionSort(a, low, high, metrics);
                return;
            }

            int mid = low + (high - low) / 2;
            mergeSortHelper(a, low, mid, buffer, metrics);
            mergeSortHelper(a, mid + 1, high, buffer, metrics);
            merge(a, low, mid, high, buffer, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static void merge(int[] a, int low, int mid, int high, int[] buffer, Metrics metrics) {
        for (int i = low; i <= high; i++) {
            buffer[i] = a[i];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > high) {
                a[k] = buffer[i++];
            } else {
                metrics.incrementComparisons();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int temp = a[i];
            int j = i - 1;
            while (j >= low) {
                metrics.incrementComparisons();
                if (a[j] > temp) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = temp;
        }
    }

    public static void quickSort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        quickSortHelper(a, 0, a.length - 1, metrics);
    }

    private static void quickSortHelper(int[] a, int low, int high, Metrics metrics) {
        while (low < high) {
            if (high - low <= INSERTION_SORT_CUTOFF) {
                metrics.enterRecursion();
                try {
                    insertionSort(a, low, high, metrics);
                } finally {
                    metrics.exitRecursion();
                }
                break;
            }

            metrics.enterRecursion();
            try {
                int randomIndex = low + RANDOM.nextInt(high - low + 1);
                swap(a, low, randomIndex);

                int[] eqRange = partition3Way(a, low, high, metrics);
                int lt = eqRange[0];
                int gt = eqRange[1];

                if (lt - low < high - gt) {
                    quickSortHelper(a, low, lt - 1, metrics);
                    low = gt + 1;
                } else {
                    quickSortHelper(a, gt + 1, high, metrics);
                    high = lt - 1;
                }
            } finally {
                metrics.exitRecursion();
            }
        }
    }

    private static int[] partition3Way(int[] a, int low, int high, Metrics metrics) {
        int lt = low, gt = high;
        int v = a[low];
        int i = low + 1;

        while (i <= gt) {
            metrics.incrementComparisons();
            if (a[i] < v) {
                swap(a, lt++, i++);
            } else {
                metrics.incrementComparisons();
                if (a[i] > v) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{lt, gt};
    }

    public static int quickSelect(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid array or k out of bounds");
        }
        return selectHelper(a, 0, a.length - 1, k, metrics);
    }

    private static int selectHelper(int[] a, int low, int high, int k, Metrics metrics) {
        if (low == high) return a[low];

        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(a, low, randomIndex);

        int[] eqRange = partition3Way(a, low, high, metrics);
        int lt = eqRange[0];
        int gt = eqRange[1];

        if (k >= lt && k <= gt) {
            return a[k];
        } else if (k < lt) {
            return selectHelper(a, low, lt - 1, k, metrics);
        } else {
            return selectHelper(a, gt + 1, high, k, metrics);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
