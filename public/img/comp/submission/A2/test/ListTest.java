import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    @Test
    void lists() throws EmptyListE {
        List<Integer> ints =
                new NodeL<>(5,
                        new NodeL<>(4,
                                new NodeL<>(3,
                                        new NodeL<>(2,
                                                new NodeL<>(1,
                                                        new NodeL<>(0,
                                                                new EmptyL<>()))))));
        assertEquals(ints, List.countdown(5));

        List<Integer> ints100 =
                new NodeL<>(5,
                        new NodeL<>(4,
                                new NodeL<>(3,
                                        new NodeL<>(100,
                                                new NodeL<>(2,
                                                        new NodeL<>(1,
                                                                new NodeL<>(0,
                                                                        new EmptyL<>())))))));

        assertEquals(ints100, ints.insertAfter(3, 100));

        assertEquals(ints, ints100.removeFirst(100));

        assertEquals(3, ints.indexOf(2));

        List<Integer> intsEvens =
                new NodeL<>(4,
                        new NodeL<>(2,
                                new NodeL<>(0,
                                        new EmptyL<>())));

        List<Integer> intsOdds =
                new NodeL<>(5,
                        new NodeL<>(3,
                                new NodeL<>(1,
                                        new EmptyL<>())));

        assertEquals(intsEvens, ints.filter(n -> n%2==0));
        assertEquals(intsOdds, ints.filter(n -> n%2==1));

        List<Integer> intsSquared =
                new NodeL<>(25,
                        new NodeL<>(16,
                                new NodeL<>(9,
                                        new NodeL<>(4,
                                                new NodeL<>(1,
                                                        new NodeL<>(0,
                                                                new EmptyL<>()))))));

        assertEquals(intsSquared, ints.map(n -> n*n));

        assertEquals(15, ints.reduce(0, Integer::sum));

        List<Integer> intsEvensOdds =
                new NodeL<>(5,
                        new NodeL<>(3,
                                new NodeL<>(1,
                                        new NodeL<>(4,
                                                new NodeL<>(2,
                                                        new NodeL<>(0,
                                                                new EmptyL<>()))))));

        assertEquals(intsEvensOdds, intsOdds.append(intsEvens));

        List<Integer> intsRev =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(2,
                                        new NodeL<>(3,
                                                new NodeL<>(4,
                                                        new NodeL<>(5,
                                                                new EmptyL<>()))))));

        assertEquals(intsRev, ints.reverse());

        List<List<Integer>> evensPS = intsEvens.powerSet();

        assertEquals(8, evensPS.length());
        assertTrue(evensPS.inList(new EmptyL<>()));
        assertTrue(evensPS.inList(new NodeL<>(0, new EmptyL<>())));
        assertTrue(evensPS.inList(new NodeL<>(2, new EmptyL<>())));
        assertTrue(evensPS.inList(new NodeL<>(4, new EmptyL<>())));
        assertTrue(evensPS.inList(new NodeL<>(4, new NodeL<>(2, new EmptyL<>()))));
        assertTrue(evensPS.inList(new NodeL<>(4, new NodeL<>(0, new EmptyL<>()))));
        assertTrue(evensPS.inList(new NodeL<>(2, new NodeL<>(0, new EmptyL<>()))));
        assertTrue(evensPS.inList(new NodeL<>(4, new NodeL<>(2, new NodeL<>(0, new EmptyL<>())))));

        // - - -
        // MY TESTS
        // - - -
        //Len
        List<Integer> intslen =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(2,
                                        new NodeL<>(3,
                                                new NodeL<>(4,
                                                        new NodeL<>(5,
                                                                new EmptyL<>()))))));
        List<Integer> intslen1 = new NodeL<>(0,new EmptyL<>());
        List<Integer> intslen0 = new EmptyL<>();

        assertEquals(intslen.length(), 6); //testing length of List with values
        assertEquals(intslen1.length(), 1); //testing length of list with single value
        assertEquals(intslen0.length(), 0); //testing length of list with no value

        //inList
        assertTrue(intslen.inList(4)); //item in list with values
        assertFalse(intslen.inList(6));//item not in list with values
        assertFalse(intslen0.inList(0));//item in empty list
        assertTrue(intslen1.inList(0)); //item in single list true

        //insert after
        List<Integer> intsaf =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(2,
                                        new NodeL<>(3,
                                                new NodeL<>(5,
                                                        new NodeL<>(4,
                                                                new NodeL<>(5,
                                                                    new EmptyL<>())))))));
        assertEquals(intslen.insertAfter(3,5), intsaf); //insert after in list
        assertEquals(intslen1.insertAfter(0,5), new NodeL<>(0, new NodeL<>(5, new EmptyL<>())));// ADDS TO SINGLE
        assertEquals(intslen0.insertAfter(4, 6), new EmptyL<>() ); // insert after empty list

        // REMOVE FIRST
        List<Integer> intsRem =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(3,
                                        new NodeL<>(5,
                                                new NodeL<>(4,
                                                        new NodeL<>(5,
                                                                new EmptyL<>()))))));
        List<Integer> intsRem2 =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(3,
                                        new NodeL<>(4,
                                                new NodeL<>(5,
                                                        new EmptyL<>())))));
        assertEquals(intsaf.removeFirst(2), intsRem);
        assertEquals(intsRem.removeFirst(5), intsRem2);
        assertEquals(intslen1.removeFirst(6), intslen1); // not in list
        assertEquals(intslen1.removeFirst(0), new EmptyL<>()); // removes value in single
        assertEquals(intslen0.removeFirst(4), new EmptyL<>()); // removes on empty list

        //INDEX OF
        //Exception exception = assertThrows(List.class, () -> {intslen1.indexOf(6); });  //I could not figure out what goes in the assert throws. tried different objects

        String expectedMessage = "Not an index in list";
        //String actualMessage = exception.getMessage();

        assertEquals(intsRem2.indexOf(1), 1); // checkin index of
        assertEquals(intslen1.indexOf(0), 0); // single obj list
        //assertEquals(intslen1.indexOf(6), exception); //??? //not in list I was stuck on this and could not figure it out
       //assertEquals(intslen0.indexOf(0),  new EmptyListE()); //??

        //Filter
        List<Integer> intsfil =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new NodeL<>(3,
                                        new NodeL<>(4,
                                                        new EmptyL<>()))));
        assertEquals(  intsRem.filter(n -> n  == 5), new NodeL<>(5,new NodeL<>(5, new EmptyL<>()))); // checking filter method with 2 values
        assertEquals(  intsRem.filter(n -> n  == 7),  new EmptyL<>()); //no values
        assertEquals(intslen1.filter(n -> n == 0), intslen1); // return same list of single object matching filter
        assertEquals(intslen1.filter(n -> n == 1), new EmptyL<>()); //testing filter on list with single obj
        assertEquals(intslen0.filter(n -> n == 1), new EmptyL<>());//testing filter on empty list

        //MAP
        List<Integer> intMapSing = new NodeL<>(2, new EmptyL<>());
        assertEquals(intslen1.map(n -> n +2 ), intMapSing); //map for list with 1 obj
        assertEquals(intslen0.map(n -> n +2 ), intslen0); //map for empty list

        //Reduce
        assertEquals(intslen0.reduce(0, (x,y)-> x+y), 0); //reduce from an empty list
        assertEquals(intslen1.reduce(0, (x,y)-> x+y), 0); //reduce from a list with 1 obj
        assertEquals(intslen1.reduce(1, (x,y)-> x+y), 1);// reduce not at base

        //Append
        List<Integer> intapp =
                new NodeL<>(0,
                        new NodeL<>(1,
                                new EmptyL<>()));
        List<Integer> intappp =
                new NodeL<>(3,
                        new NodeL<>(4,
                                new EmptyL<>()));
        List<Integer> intnewSing =
                new NodeL<>(0, new EmptyL<>());
        assertEquals(intapp.append(intappp), intsfil); //append 2 lists
        assertEquals(intslen0.append(intnewSing), intslen1); //append to empty list

        //ReverseHelper
        List<Integer> intsrev =
                new NodeL<>(4,
                        new NodeL<>(3,
                                new NodeL<>(1,
                                        new NodeL<>(0,
                                                new EmptyL<>())))); //call Reverse!!!

        assertEquals(intsrev.reverse(), intsfil);  // reverse list
       assertEquals(intslen1.reverse(), intslen1); //reverse of single object list
        assertEquals(intslen0.reverse(), intslen0); //reverse helper on empty list

        //PowerSet
        List<Integer> Ps =new NodeL<>(5, new EmptyL<>());
        assertEquals(intslen1.powerSet().length(), 2);//powerset of single obj list returns 2 [obj, empty]
        assertEquals(intslen0.powerSet(), new NodeL<>(new EmptyL<>(), new EmptyL<>())); // checking ps of empty list


       /* ---
        i really tried to nail every test case I could think of. I am sure I am missing some,
        just confused on how grading will go...
        */
    }
}