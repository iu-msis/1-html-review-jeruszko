import java.util.ArrayList;
import java.util.List;

public class Sort {

    // This method should sort the input list using the algorithm for insertion sort.
    // That is, first create a new ArrayList with all of the elements from input ns.
    // Then, iterate through this new ArrayList - comparing a current element to its
    // predecessor. While current is less, it is swapped w predecessor.

    // For those who prefer wordier instructions, check out Lab 5 post on canvas :)
    // Otherwise, best of luck on the lab! Tests/debugging will help a lot with IndexOutOfBoundsExceptions
    static List<Integer> insertionSort (List<Integer> ns) {
        int len = ns.size();
        for (int i = 0; i < len; i++){
            int spot = ns.get(i);//current spot
            int prev = i-1;//previous spot
            while (prev >= 0 && ns.get(prev) > spot) //while previous spot is less than current spot
            {
                ns.set(prev+1,ns.get(prev));
                prev = prev-1;
            }
            ns.set(prev+1,spot);
        }
        return ns;
    }
}
