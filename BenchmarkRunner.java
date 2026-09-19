import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 100000, 1000000};
        String[] types = {"random", "sorted", "duplicates"};
        String[] algorithms = {"MergeSort", "QuickSort", "QuickSelect"};

        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.append("algorithm,input,n,time_ms,comparisons,max_depth\n");

            for (String algo : algorithms) {
                for (String type : types) {
                    for (int n : sizes) {
                        if (n == 1000000 && type.equals("sorted") && algo.equals("QuickSort")) {
                            continue;
                        }

                        long[] times = new long[5];
                        long comps = 0;
                        int depth = 0;

                        for (int run = 0; run < 5; run++) {
                            int[] original = generateArray(n, type);
                            Metrics metrics = new Metrics();
                            int[] copy = original.clone();

                            long start = System.nanoTime();
                            if (algo.equals("MergeSort")) {
                                SortingAlgorithms.mergeSort(copy, metrics);
                            } else if (algo.equals("QuickSort")) {
                                SortingAlgorithms.quickSort(copy, metrics);
                            } else if (algo.equals("QuickSelect")) {
                                int k = n / 2;
                                SortingAlgorithms.quickSelect(copy, k, metrics);
                            }
                            long end = System.nanoTime();

                            times[run] = (end - start) / 1_000_000;
                            comps = metrics.getComparisons();
                            depth = metrics.getMaxDepth();
                        }

                        Arrays.sort(times);
                        long medianTime = times[2];

                        writer.append(String.format("%s,%s,%d,%d,%d,%d\n", algo, type, n, medianTime, comps, depth));
                        System.out.printf("Finished: %s | %s | n=%d\n", algo, type, n);
                    }
                }
            }
            System.out.println("Benchmark completed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateArray(int n, String type) {
        int[] a = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            if (type.equals("random")) {
                a[i] = rand.nextInt();
            } else if (type.equals("sorted")) {
                a[i] = i;
            } else if (type.equals("duplicates")) {
                a[i] = rand.nextInt(10);
            }
        }
        return a;
    }
}


