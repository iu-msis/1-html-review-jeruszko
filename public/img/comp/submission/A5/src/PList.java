import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PEmptyE extends Exception {}

abstract class PList {
    abstract int getElem () throws PEmptyE;
    abstract PList getRest () throws PEmptyE;
    abstract int length ();

    /**
     * Splits the current list in two at the given
     * index
     */
    abstract Pair<PList,PList> splitAt (int index);

    /**
     * Keep dividing the list in two until you reach
     * a base case; then merge the sorted lists
     * resulting from the recursive calls
     */
    abstract PList mergeSort ();

    /**
     * The given list 'ns' is sorted; the current
     * list (this) is also sorted. Return a new
     * sorted list from these two lists.
     */
    abstract PList merge (PList ns);

    static List<Integer> toList (PList ns) {
        List<Integer> result = new ArrayList<>();
        while (true) {
            try {
                result.add(ns.getElem());
                ns = ns.getRest();
            } catch (PEmptyE e) {
                return result;
            }
        }
    }

    static PList fromList (List<Integer> ns) {
        PList result = new PEmpty();
        for (int i=0; i<ns.size(); i++) {
            result = new PNode(ns.get(i), result);
        }
        return result;
    }
}

class PEmpty extends PList {
    int getElem() throws PEmptyE {
        throw new PEmptyE();
    }

    PList getRest() throws PEmptyE {
        throw new PEmptyE();
    }

    int length() {
        return 0;
    }

    Pair<PList, PList> splitAt(int index) {
        return new Pair<>(this,this);
        //Pair<PList, PList> emptyList = new Pair<>(null,null);
        //return emptyList; // TODO
    }

    PList mergeSort() {
        return this;
        //PList emptyList = new PEmpty();
        //return emptyList; // TODO
    }

    PList merge(PList ns) {
        return ns;
        //PList emptyList = new PEmpty();
        //return emptyList; // TODO
    }
}


class PNode extends PList {
    private final int elem;
    private final PList rest;
    private final int len;

    PNode(int elem, PList rest) {
        this.elem = elem;
        this.rest = rest;
        this.len = rest.length() + 1;
    }

    int getElem() {
        return elem;
    }

    PList getRest() {
        return rest;
    }

    int length() {
        return len;
    }

    Pair<PList, PList> splitAt(int index) {
        if (index == 0 ){ //index 0
            return new Pair<>( new PEmpty(), this); //return new pair(PEmpty, this)
        }else{
            Pair<PList, PList> splice = rest.splitAt(index-1); //new pair that splits at index - 1
            return new Pair<>(new PNode(elem, splice.getFst()),splice.getSnd()); //return new pair (new Pnode(elem, first()), second())
        }
    }

    PList mergeSort() {
        if (this.length() == 1){ //len == 1
            return this; //return new pair(empty, this) <- Could not figure how to get this way... yet it works
        }else{
            Pair<PList, PList> newP = splitAt(this.len/2); //Pair<list, list> p = split(len/2)
            return  newP.getFst().mergeSort().merge(newP.getSnd().mergeSort());//return p.frist.mergesort

        }
        // TODO
    }

    PList merge(PList ns) {
        try{
            int ele = ns.getElem();
            if (elem < ele){ //compare the first value
                return new PNode(elem,rest.merge(ns));
            }else{
                return new PNode(ele, this.merge(ns.getRest()));
            }
        }catch (PEmptyE E){
            return this;
        }


        // TODO

    }
}

