
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class SortingAlgorithmsTest {

    @Test
    void testMergeSortCorrectness() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int[] a = rand.ints(500, -1000, 1000).toArray();
            int[] expected = a.clone();
            Arrays.sort(expected);

            Metrics metrics = new Metrics();
            SortingAlgorithms.mergeSort(a, metrics);
            assertArrayEquals(expected, a);
        }
    }

    @Test
    void testQuickSortCorrectnessAndDepth() {
        int n = 100000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;

        Metrics metrics = new Metrics();
        SortingAlgorithms.quickSort(a, metrics);

        int maxAllowedDepth = (int) (2 * Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth);
    }

    @Test
    void testQuickSelect() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int[] a = rand.ints(100, -500, 500).toArray();
            int[] expected = a.clone();
            Arrays.sort(expected);

            int k = rand.nextInt(a.length);
            Metrics metrics = new Metrics();
            int result = SortingAlgorithms.quickSelect(a.clone(), k, metrics);
            assertEquals(expected[k], result);
        }
    }

    @Test
    void testEdgeCases() {
        Metrics m = new Metrics();

        int[] empty = {};
        assertDoesNotThrow(() -> SortingAlgorithms.mergeSort(empty, m));
        assertDoesNotThrow(() -> SortingAlgorithms.quickSort(empty, m));
        assertThrows(IllegalArgumentException.class, () -> SortingAlgorithms.quickSelect(empty, 0, m));

        int[] single = {42};
        SortingAlgorithms.mergeSort(single, m);
        assertArrayEquals(new int[]{42}, single);

        int[] equals = {5, 5, 5, 5, 5};
        SortingAlgorithms.quickSort(equals, m);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, equals);
    }
}
