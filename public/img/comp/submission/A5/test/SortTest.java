import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortTest {

    @Test
    void inc () {
        List<Integer> expected = Arrays.asList(1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929,
                16001, 36289, 64769, 146305, 260609, 587521, 1045505, 2354689, 4188161);
        for (int i=0; i<20; i++) assertEquals(expected.get(i), Sort.increment(i));
    }

    @Test
    void digit () {
        assertEquals(7, Sort.getDigit(1347,0));
        assertEquals(4, Sort.getDigit(1347,1));
        assertEquals(3, Sort.getDigit(1347,2));
        assertEquals(1, Sort.getDigit(1347,3));
    }

    @Test
    void sort () {
        List<Integer> ns = Arrays.asList(70,100,203,784,412,135,101);
        List<Integer> sorted = Arrays.asList(70, 100, 101, 135, 203, 412, 784);

        assertEquals(sorted, Sort.streamSort(ns));
        assertEquals(sorted, Sort.insertionSort(ns));
        assertEquals(sorted, Sort.mergeSort(ns));
        assertEquals(sorted, Sort.shellSort(ns));
        assertEquals(sorted, Sort.radixSort(ns,3));
    }

    Duration timeM (Function<List<Integer>,List<Integer>> f, List<Integer> ns) {
        Instant start = Instant.now();
        f.apply(ns);
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    @Test
    void timeSort () {
        Random r = new Random(500);
        int size = 10000;
        int bound = 1000;

        List<Integer> ns =
                r.ints(size, 0,bound).
                        boxed().
                        collect(Collectors.toList());

        long d;

        d = timeM(Sort::streamSort, ns).toMillis();
        System.out.printf("Java sort takes %d ms%n", d);

        d = timeM(Sort::insertionSort, ns).toMillis();
        System.out.printf("Insertion sort takes %d ms%n", d);

        d = timeM(Sort::mergeSort, ns).toMillis();
        System.out.printf("Merge sort takes %d ms%n", d);

        d = timeM(Sort::shellSort, ns).toMillis();
        System.out.printf("Shell sort takes %d ms%n", d);

        d = timeM(nums -> Sort.radixSort(nums, 3), ns).toMillis();
        System.out.printf("Radix sort takes %d ms%n", d);
    }
    @Test
    void mergesort () {
        List<Integer> ns = Arrays.asList(70,100,203,784,412,135,101,999);
        List<Integer> sorted = Arrays.asList(70, 100, 101, 135, 203, 412, 784,999);

        assertEquals(sorted, Sort.mergeSort(ns)); //check if even split at works

    }

    @Test
    void radixsort () {
        List<Integer> ns = Arrays.asList(111111,1111,11,1,111111,2,7,8);
        List<Integer> sorted = Arrays.asList(1,2,7,8,11,1111,111111,111111);

        assertEquals(sorted, Sort.radixSort(ns,6)); //check if even split at works

    }


}
