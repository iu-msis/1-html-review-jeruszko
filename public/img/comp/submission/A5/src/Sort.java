import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All sorting algorithms must return a NEW sorted list. Do not
 * modify the incoming list
 */
public class Sort {
    static List<Integer> streamSort(List<Integer> ns) {
        return ns.stream().sorted().collect(Collectors.toList());
    }

    static List<Integer> insertionSort(List<Integer> ns) {
        List<Integer> newL = new ArrayList<>(ns);
        int len = newL.size();
        for (int i = 1; i < len; i++) {
            int spot = newL.get(i);//current spot
            int prev = i;//previous spot This wrong
            while (prev > 0 && spot < newL.get(prev - 1)) //while previous spot is less than current spot
            {
                newL.set(prev, newL.get(prev - 1));//
                prev--;
            }
            newL.set(prev, spot);
        }
        return newL; // TODO (from lab)
    }

    static List<Integer> mergeSort(List<Integer> ns) {
        // real work done in PList
        return PList.toList(PList.fromList(ns).mergeSort());
    }

    static int increment(int n) {
        // From https://oeis.org/search?q=shell+sort
        // a(n) = 9*2^n - 9*2^(n/2) + 1 if n is even;
        // a(n) = 8*2^n - 6*2^((n+1)/2) + 1 if n is odd.
        if (n % 2 == 0)
            return (int) (9 * Math.pow(2, n) - 9 * Math.pow(2, n / 2) + 1);
        else
            return (int) (8 * Math.pow(2, n) - 6 * Math.pow(2, (n + 1) / 2) + 1);
    }

    /**
     * Steps:
     * 1. create an array incSequence that calls increment above until the
     * gap is more than half of the size of the array
     * 2. Start from the largest gap and iterate down the list of gaps
     * 3. For each gap, do an insertion sort for the elements separated
     * by the given gap
     */
    static List<Integer> shellSort(List<Integer> ns) { //call increment???
        List<Integer> incSequence = new ArrayList<>();
        List<Integer> newL = new ArrayList<>(ns);
        int len = ns.size();
        //find gaps
        for (int gap = 0; increment(gap) < len/2; gap++){
            incSequence.add(increment(gap));
        }

        for (int incLen = incSequence.size() - 1;incLen > -1; incLen--){
            int incEle = incSequence.get(incLen);

            for (int i = 0; i <incEle; i++){
                for (int ii = i; ii < newL.size(); ii+=incEle){
                    int curEle = newL.get(ii);
                    int prev;
                    for (prev  = ii-incEle ; (prev >= 0) && (newL.get(prev) > curEle); prev -= incEle){
                        newL.set(prev + incEle, newL.get(prev));
                    }
                    newL.set(prev + incEle, curEle);
                }
            }
        }
        return newL;
         // TODO
    }

    static int getDigit(int n, int d) {
        if (d == 0) return n % 10;
        else return getDigit(n / 10, d - 1);
    }

    /**
     * Steps:
     * 1. Create 10 buckets represented as ArrayLists, one for each digit
     * 2. For each digit d (d=0 to len-1) with 0 the least significant digit,
     * do the following
     * 3. Take a number from the input list, look at digit 'd' in that number,
     * and add it to the bucket 'd'
     * 4. When you finish processing the list, copy the contents of the
     * buckets into a temporary list
     * 5. Clear the buckets and repeat for the next 'd'
     */ //Think like a human  do
    static List<Integer> radixSort(List<Integer> ns, int len) { //len is max
        final int rad = 10;
        List<Integer>[] bucket = new ArrayList[rad];

        List<Integer> combine = new ArrayList<>(ns); //return
        //Make the buckets
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<>();
        }
        //add to buckets
        for (int j = 0; j < len; j++) {
            for (Integer i : combine) {
                bucket[getDigit(i, j)].add(i);
            }
            //add to array
            int pos = 0;
            for (int radix = 0; radix < rad; radix++) {
                for (Integer i : bucket[radix]) {
                    combine.set(pos++, i);
                }
                bucket[radix].clear();
            }
        }
        return combine;
    }
}
/*

Left this.... I know this is a much more complicated way... but if you have any suggestions on what i did wrong please leave a comment:)
My buckets did not sort, marked with **** on where bucket sorting happening.


        for (int l = 0; l < len; l++) { //for each digit
            for (int i = 0; i < ns.size(); i++)//for each elem
                for (int go = 0; go < rad; go++) {//10 for each rad
                    if (getDigit(ns.get(i), l) == go) {
                        //if (bucket[go].contains(ns.get(i))) {
                            //break;
                        //} else {
                        ////////
                        System.out.println("before:"+bucket[go]);
                        if (bucket[go].isEmpty()){
                            bucket[go].add(ns.get(i));
                        }else{
                   *******************************************************
                            for (int j = 0; j <= bucket[go].size();j++){ ********8
                                System.out.println("bucket elem:"+bucket[go].get(j));
                                System.out.println("new elem:"+ns.get(i));
                                if (bucket[go].get(j) > ns.get(i)){ //to move behind if bucket elem > list elem 0.70 > 100
                                    System.out.println("before sort:"+bucket[go]);
                                    int temp = bucket[go].get(j);
                                    int change = ns.get(i);
                                    bucket[go].add(ns.get(i));
                                    bucket[go].set(j, change);
                                    bucket[go].set(j+1, temp);
                                    System.out.println("after sort:"+bucket[go]);
                *******************************************************

                                }else{
                                    bucket[go].add(ns.get(i));
                                    break;
                                }
                            }
                        }
                        System.out.println("after:"+bucket[go]);
                        combine.clear();
                    }
                }
            for (int rade = 0; rade < rad; rade++) {//10
                if (bucket.length == 0) {
                    break;
                } else {
                    //System.out.println(("bucketlen =" + bucket[rade].size()));
                    //System.out.println(combine);
                    combine.addAll(bucket[rade]);
                    bucket[rade].clear();
                }
            }
        }
        return combine;
    }
}
*/