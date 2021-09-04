
import java.util.function.BiFunction;
import java.util.function.Function;



class EmptyListE extends Exception {}

//-------------------------------------------------------------

/**
 * This is persistent implementation.
 * After a list is created, it is never updated
 *
 * See the test cases for examples
 */
abstract class List<E> {
    static List<Integer> countdown (int n) {
        if (n == 0)
            return new NodeL<>(0, new EmptyL<>());
        else
            return new NodeL<>(n, countdown(n-1));
    }
    /**
     * Getter Methods
     * */
    abstract E getFirst() throws EmptyListE;

    abstract List<E> getRest() throws EmptyListE;

    /**
     * Computes the length of the list
     */
    abstract int length();

    /**
     * Checks if the given elem occurs in the list
     * (Use .equals() to check for equality)
     */
    abstract boolean inList(E elem);

    /**
     * Inserts newElem after every occurrence of elem
     */
    abstract List<E> insertAfter (E elem, E newElem); // add throw?

    /**
     * Removes the first occurrence of elem (if
     * there is one)
     */
    abstract List<E> removeFirst (E elem);

    /**
     * Returns the 0-based index of elem in the list
     * If the element is not in the list, throw
     * an exception
     */
    abstract int indexOf(E elem) throws EmptyListE;

    /**
     * Returns a new list that only contains the
     * elements satisfying the given predicate
     */
    abstract List<E> filter (Function<E,Boolean> pred);

    /**
     * Returns a new list in which every element
     * is transformed using the given function
     */
    abstract <F> List<F> map (Function<E,F> f);

    /**
     * Starting with base as the current result,
     * repeatedly applies the bifunction f to the
     * current result and one element from the list
     */
    abstract <F> F reduce(F base, BiFunction<E,F,F> f);

    /**
     * Appends the given list at the end of the
     * current list
     */
    abstract List<E> append (List<E> other);


    /**
     * Use to accumulate the reverse of the given list
     */
    abstract List<E> reverseHelper (List<E> result);

    /**
     * Returns a big list containing all the sublists of
     * the current list
     */
    abstract List<List<E>> powerSet ();

    List<E> reverse () {
        return reverseHelper(new EmptyL<>());
    }
}

//-------------------------------------------------------------

class EmptyL<E> extends List<E> {

    E getFirst() throws EmptyListE {
      throw new EmptyListE();
    }

    List<E> getRest() throws EmptyListE {
      throw new EmptyListE();
    }

     int length() {
        return 0;
    }


    boolean inList(E elem) {
        return false;
    }

    List<E> insertAfter(E elem, E newElem) {
        return this;
    }

    List<E> removeFirst(E elem) {
        return new EmptyL<>();
    }

    int indexOf(E elem) throws EmptyListE{
        throw new EmptyListE();
    }

    List<E> filter(Function<E, Boolean> pred) {
        return new EmptyL<>();
    }

    <F> List<F> map(Function<E, F> f) {
        return new EmptyL<>();
    }

    <F> F reduce(F base, BiFunction<E, F, F> f) {
        return base;
    }

    List<E> append(List<E> other) {
        return other;
    }

    List<E> reverseHelper(List<E> result) {
        return result;
    }

    List<List<E>> powerSet() {
        return new NodeL<>(new EmptyL<>(), new EmptyL<>());
    }

    public boolean equals (Object o) {
        return o instanceof EmptyL;
    }
}

//-------------------------------------------------------------

class NodeL<E> extends List<E> {
    private final E first;
    private final List<E> rest;

    NodeL(E first, List<E> rest) {
        this.first = first;
        this.rest = rest;
    }

    E getFirst() {
        return this.first;
    }

    List<E> getRest() {
        return this.rest;
    }

    int length() {
        return rest.length() + 1;
    }

    boolean inList(E elem) {
        if (first.equals(elem)) {
            return true;
        } else {
            return rest.inList(elem);
        }

    }

    List<E> insertAfter(E elem, E newElem) {
        List<E> newL = rest.insertAfter(elem, newElem);
        if (first.equals(elem)) {
            return new NodeL<>(first, new NodeL<>(newElem, newL));
        } else {
            return new NodeL<>(first,newL);
        }
    }

    List<E> removeFirst(E elem) {
        if (first.equals(elem)) {
            return this.rest;
        }else{
           return new NodeL<>(first, rest.removeFirst(elem));
        }
    }

    int indexOf(E elem) throws EmptyListE {
        if (first.equals(elem)) {
            return 0;
        } else {
            return rest.indexOf(elem) + 1;
        }
    }

    List<E> filter(Function<E, Boolean> pred) {
        if (pred.apply(first) == false) {
            return rest.filter(pred);
        } else {
            return new NodeL<E>(first, rest.filter(pred));
        }
    }

    <F> List<F> map(Function<E, F> f) {
        return new NodeL(f.apply(this.first), rest.map(f));
    }

    <F> F reduce(F base, BiFunction<E, F, F> f) {
        return f.apply(first, rest.reduce(base, f));

    }

    List<E> append(List<E> other) {

        return new NodeL<E>(this.first, rest.append(other));

    }


    List<E> reverseHelper(List<E> result) {

         return rest.reverseHelper(new NodeL<>(first, result));

    }


    List<List<E>> powerSet() {
        List<List<E>> lst = rest.powerSet();
        return lst.append(lst.map(n -> new NodeL<>(this.first, n)));
    }

    public boolean equals (Object o) {
        if (o instanceof NodeL) {
            NodeL<E> other = (NodeL<E>) o;
            return first.equals(other.first) && rest.equals(other.rest);
        }
        else return false;
    }
}

//-------------------------------------------------------------
//-------------------------------------------------------------

