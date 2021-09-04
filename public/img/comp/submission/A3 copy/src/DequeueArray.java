import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

class NoSuchElementE extends Exception {}

public abstract class DequeueArray<E> {
    protected Optional<E>[] elements;
    protected int capacity, front, back, size;
    //
    // data stored in locations:
    // front+1, front+2, ... back-2, back-1 (all mod capacity)
    //
    // common cases:
    // front points to an empty location
    // back points to an empty location
    // adding to front decreases 'front' by 1
    // adding to back increases 'back' by 1
    // removing does the opposite
    //
    // |-------------------------|
    // | 4 5 6 _ _ _ _ _ _ 1 2 3 |
    // |-------------------------|
    //         /\        /\      /\
    //        back      front  capacity
    //

    @SuppressWarnings("unchecked")
    DequeueArray(int initialCapacity) {
        elements = (Optional<E>[]) Array.newInstance(Optional.class, initialCapacity);
        Arrays.fill(elements, Optional.empty());
        capacity = initialCapacity;
        front = capacity - 1;
        back = 0;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        elements = (Optional<E>[]) Array.newInstance(Optional.class, 1);
        Arrays.fill(elements, Optional.empty());
        capacity = 1;
        front = 0;
        back = 0;
        size = 0;
    }

    public int size () { return size; }

    // --- real work begins --- //

    /**
     * Adds the given elem to the front of the dequeue
     * If there is no room, grow the dequeue first
     */
    public void addFirst(E elem) {
        if (capacity == size){
            grow();
        }
        elements[front] = Optional.of(elem);
        front = Math.floorMod(front - 1, capacity);
        size = size + 1;
        // TODO
    }

    /**
     * Adds the given elem to the back of the dequeue
     * If there is no room, grow the dequeue first
     */
    public void addLast(E elem) {
        if (capacity == size){
            grow();
        }
        elements[back] = Optional.of(elem);
        back =Math.floorMod(back +1, capacity);
        size = size + 1;

    } //Todo

    public E getFirst() throws NoSuchElementE {
        return elements[Math.floorMod(front+1,capacity)].orElseThrow(NoSuchElementE::new);
    }

    public E getLast() throws NoSuchElementE {
        return elements[Math.floorMod(back - 1,capacity)].orElseThrow(NoSuchElementE::new);
    }

    /**
     * Removes (and returns) the first element in the dequeue
     * If the dequeue is empty, throw an exception instead
     */
    public E removeFirst() throws NoSuchElementE {
        // get what is at front
        front = Math.floorMod(front + 1, capacity);
        //get the replacement
        Optional<E> newFirst = elements[front];
        //update elements at front index to
        elements[front] = Optional.empty();
        //update size by shrinking it
        size = size -1;
        //return element you got earlier
        return newFirst.orElseThrow(NoSuchElementE::new); // TODO
    }

    /**
     * Removes (and returns) the last element in the dequeue
     * If the dequeue is empty, throw an exception instead
     */
    public E removeLast() throws NoSuchElementE {
        back = Math.floorMod(back - 1, capacity);
        Optional<E> newback = elements[back];
        elements[back] = Optional.empty();
        size = size -1;
        return newback.orElseThrow(NoSuchElementE::new); // TODO
    }

    // the following methods are for debugging and testing

    Optional<E>[] getElements () { return elements; }

    int getCapacity () { return capacity; }

    int getFront () { return front; }

    int getBack () { return back; }

    abstract void grow ();
}

// ---------------------------------------------------------

class DequeueArrayDouble<E> extends DequeueArray<E> {

    DequeueArrayDouble (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grow the dequeue by doubling its size.
     * A new array is created, all the elements in the old array
     * are copied in the first half of the new array
     */
    @SuppressWarnings("unchecked")
    void grow() {
        // TODO
        //create an array that is some capacity larger
        //Optional<E>[] newElements= (Array.   )[capacity]
        Optional<E>[] newElements = (Optional<E>[])Array.newInstance(Optional.class, capacity*2);
        //fill array with elements Arrays.fill( ___,___)
        Arrays.fill(newElements, Optional.empty());
        //iterate through elements and put into new array
        for (int i = 0; i < this.capacity; i ++ ) {
            newElements[i] = elements[Math.floorMod(i + front + 1, capacity)];
        }
        //set variables
        elements = newElements;
        back = capacity;
        capacity = capacity *2;
        front = capacity - 1;
    }
}

// ---------------------------------------------------------

class DequeueArrayOneAndHalf<E> extends DequeueArray<E> {

    DequeueArrayOneAndHalf (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grow the dequeue by multiplying its size by 1.5 and rounding
     * A new array is created, all the elements in the old array
     * are copied in the first half of the new array
     */
    @SuppressWarnings("unchecked")
    void grow() {
        Optional<E>[] newElements = (Optional<E>[])Array.newInstance(Optional.class,(int) Math.round(capacity * 1.5));
        Arrays.fill(newElements, Optional.empty());
        for (int i = 0; i < this.capacity; i ++ ) {
            newElements[i] = elements[Math.floorMod(i + front + 1, capacity)];
        }
        elements = newElements;
        back = capacity;
        capacity = (int) Math.ceil(capacity *1.5);
        front = capacity - 1;

    }
}

// ---------------------------------------------------------

class DequeueArrayPlusOne<E> extends DequeueArray<E> {

    DequeueArrayPlusOne (int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Grow the dequeue by one
     * A new array is created, all the elements in the old array
     * are copied in the first part of the new array
     */
    @SuppressWarnings("unchecked")
    void grow() {
        Optional<E>[] newElements = (Optional<E>[])Array.newInstance(Optional.class, capacity + 1);
        Arrays.fill(newElements, Optional.empty());
        for (int i = 0; i < this.capacity; i ++ ) {
            newElements[i] = elements[Math.floorMod(i + front + 1, capacity)];
        }
        elements = newElements;
        back = capacity;
        capacity = capacity +1;
        front = capacity - 1;

    }
}
