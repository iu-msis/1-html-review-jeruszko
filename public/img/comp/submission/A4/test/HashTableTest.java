import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {

    @Test
    public void simpleLinear() {
        HashTable<Integer, String> ht = new HashLinearProbing<>();
        ht.insert(0, "lamb");
        ht.insert(1, "cat");
        ht.insert(2, "dog");
        ht.insert(3, "horse");
        ht.insert(4, "cow");
        ht.insert(5, "chicken");
        ht.insert(6, "monkey");

        assertEquals(7, ht.getSize());
        assertTrue(ht.getDeleted().isEmpty());

        ArrayList<Optional<Map.Entry<Integer, String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0, "lamb")), slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(1, "cat")), slots.get(1));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(2, "dog")), slots.get(2));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(3, "horse")), slots.get(3));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(4, "cow")), slots.get(4));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(5, "chicken")), slots.get(5));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(6, "monkey")), slots.get(6));
    }

    @Test
    public void simpleQuad() throws NotFoundE {
        HashTable<Integer, String> ht = new HashQuadProbing<>();
        ht.insert(0, "lamb");
        System.out.println(ht.getCapacity());
        ht.insert(1, "cat");
        ht.insert(2, "dog");
        System.out.println(ht.getCapacity());
        ht.insert(3, "horse");
        ht.insert(4, "cow");
        ht.insert(5, "chicken");
        ht.insert(6, "monkey");
        assertEquals(7, ht.getSize());

        System.out.println(ht);
        assertTrue(ht.getDeleted().isEmpty());

        ArrayList<Optional<Map.Entry<Integer, String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0, "lamb")), slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(1, "cat")), slots.get(1));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(2, "dog")), slots.get(2));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(3, "horse")), slots.get(3));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(4, "cow")), slots.get(4));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(5, "chicken")), slots.get(5));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(6, "monkey")), slots.get(6));
    }

    @Test
    public void clashQuadez() { //checking when key is double
        HashTable<Integer, String> ht = new HashQuadProbing<>();
        ht.insert(0, "lamb");
        ht.insert(17, "cat");
        System.out.println(ht.getSize());
        /*ht.insert(34,"dog");
         */
    }


    @Test
    public void clashQuad() {
        HashTable<Integer, String> ht = new HashQuadProbing<>();
        ht.insert(0, "lamb");
        ht.insert(17, "cat");
        ht.insert(34, "dog");
        ht.insert(51, "horse");
        ht.insert(68, "cow");
        ht.insert(8, "fox");
        ht.insert(85, "tiger");
        System.out.println(ht.getCapacity());

        assertEquals(7, ht.getSize());
        assertTrue(ht.getDeleted().isEmpty());

        ArrayList<Optional<Map.Entry<Integer, String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0, "lamb")), slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(17, "cat")), slots.get(1));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(34, "dog")), slots.get(4));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(51, "horse")), slots.get(9));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(8, "fox")), slots.get(8));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(85, "tiger")), slots.get(2));
    }

    @Test
    public void clashLinear() throws NotFoundE {
        HashTable<Integer, String> ht = new HashLinearProbing<>();

        assertThrows(NotFoundE.class, () -> ht.search(1000));

        ht.insert(0, "lamb");
        ht.insert(7, "cat");
        ht.insert(14, "dog");
        ht.insert(21, "horse");
        ht.insert(35, "chicken");
        ht.insert(42, "monkey");
        ht.insert(24, "cow");

        assertEquals(7, ht.getSize());

        assertEquals("lamb", ht.search(0));
        assertEquals("cat", ht.search(7));
        assertEquals("dog", ht.search(14));
        assertEquals("horse", ht.search(21));
        assertEquals("chicken", ht.search(35));
        assertEquals("monkey", ht.search(42));
        assertEquals("cow", ht.search(24));

        assertThrows(NotFoundE.class, () -> ht.search(1000));

        ht.insert(49, "lion");

        ArrayList<Optional<Map.Entry<Integer, String>>> slots = ht.getSlots();
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(0, "lamb")), slots.get(0));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(35, "chicken")), slots.get(1));
        assertTrue(slots.get(2).isEmpty());
        assertTrue(slots.get(3).isEmpty());
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(21, "horse")), slots.get(4));
        assertTrue(slots.get(5).isEmpty());
        assertTrue(slots.get(6).isEmpty());
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(7, "cat")), slots.get(7));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(42, "monkey")), slots.get(8));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(24, "cow")), slots.get(9));
        assertTrue(slots.get(10).isEmpty());
        assertTrue(slots.get(11).isEmpty());
        assertTrue(slots.get(12).isEmpty());
        assertTrue(slots.get(13).isEmpty());
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(14, "dog")), slots.get(14));
        assertEquals(Optional.of(new AbstractMap.SimpleImmutableEntry<>(49, "lion")), slots.get(15));
        assertTrue(slots.get(16).isEmpty());
    }


    @Test
    public void deletes() throws NotFoundE {
        HashTable<Integer, String> ht = new HashLinearProbing<>();

        ht.insert(1, "cat");
        ht.insert(18, "dog");//del
        ht.insert(35, "horse");
        assertEquals("horse", ht.search(35));//CHECK TO MAKE SURE ITS THERE
        ht.insert(52, "cow");
        ht.insert(69, "chicken");
        ht.insert(86, "lion");
        ht.insert(103, "tiger");
        ht.insert(120, "cheetah");

        assertThrows(NotFoundE.class, () -> ht.delete(1000));
        ht.delete(18);
        ht.delete(69);
        assertEquals(ht.getSize(), 6);

        assertThrows(NotFoundE.class, () -> ht.search(18));
        assertThrows(NotFoundE.class, () -> ht.search(69));
        assertTrue(ht.getDeleted().contains(2));
        assertTrue(ht.getDeleted().contains(5));
        assertTrue(ht.getSlots().get(5).isEmpty());
        assertTrue(ht.getSlots().get(2).isEmpty());

        assertEquals("cat", ht.search(1));
        System.out.println(ht.getSlots().get(3).get().getValue());
        System.out.println(ht.getSlots().get(3).get().getKey());//mine - its there in ht
        //System.out.println(ht.getSize());//mine
        assertEquals("horse", ht.search(35)); //doesnt show up? why?

        assertEquals("cow", ht.search(52));

        assertEquals("lion", ht.search(86));
        assertEquals("tiger", ht.search(103));
        assertEquals("cheetah", ht.search(120));

        ht.insert(18, "fox");
        assertEquals(ht.getSlots().get(2).orElseThrow(NotFoundE::new).getValue(), "fox");
    }
    // TODO
    // your own test cases here
    @Test
    public void ezhashtest() throws NotFoundE {
        HashTable<Integer, String> ht = new HashQuadProbing<>();
        ht.insert(0, "lamb");
        //System.out.println(ht.getCapacity());
        ht.insert(1, "cat");
        ht.insert(2, "dog");
        //System.out.println(ht.getCapacity());
        ht.insert(3, "horse");
        ht.insert(4, "cow");
        ht.insert(5, "chicken");
        ht.insert(6, "monkey");
        ht.insert(7, "monke");
        ht.insert(8, "monk");

        //ht.insert(9, "mony");
        //System.out.println(ht.getSize());
        //System.out.println(ht.getCapacity());
        assertEquals(9, ht.getSize());
        assertEquals(37, ht.getCapacity()); //YAY IT HASHED!!!!!
    }

    @Test
    public void ezsearch() throws NotFoundE {
        HashTable<Integer, String> ht = new HashQuadProbing<>();
        ht.insert(0, "lamb");
        ht.insert(1, "cat");
        ht.insert(2, "dog");
        ht.insert(3, "horse");
        ht.insert(4, "cow");
        ht.insert(5, "chicken");
        ht.insert(6, "monkey");
        assertEquals("cat", ht.search(1));
        assertEquals("cow", ht.search(4));
    }



    @Test
    public void simpledeletes() throws NotFoundE {
        HashTable<Integer, String> ht = new HashLinearProbing<>();

        ht.insert(1, "cat");
        ht.insert(2, "dog");
        ht.insert(3, "horse");
        ht.insert(4, "cow");
        ht.insert(5, "chicken");
        ht.insert(6, "lion");
        ht.insert(7, "tiger");
        ht.insert(8, "cheetah");

        assertThrows(NotFoundE.class, () -> ht.delete(1000));
        ht.delete(2);
        System.out.println(ht.getSlots().get(2));
        ht.delete(3);
        assertEquals(ht.getSize(), 6);
    }
    @Test
    public void Challenge() throws NotFoundE {
        HashTable<Integer, String> ht = new HashLinearProbing<>();

        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.insert(1, "challenge");
        ht.delete(1);
        ht.insert(1, "challenge");
        ht.delete(1);
        ht.insert(1, "challenge");
        System.out.println(this.hashCode());//Tough challenge

    }
    @Test
    public void simpledeleteslab() throws NotFoundE {
        HashTable<Integer, String> ht = new HashLinearProbing<>();

        ht.insert(1, "cat");
        ht.insert(11, "dog");
        ht.insert(21, "horse");
        ht.insert(31, "cow");
        ht.insert(41, "chicken");
    }

    @Test
    public <HashFunction> void linear () {
       /* HashTable<Integer,String> ht = new HashLinearProbing<>();
        ht.insert(1, "_");
        ht.insert(12, "_");
        ht.insert(23, "_");
        ht.insert(34, "_");
        ht.insert(45, "_");
        ht.insert(3, "_");
        System.out.println(2^(4));*
        */
        int j = 0;
        for (int i =0; i<9;i++){
            if (i==Math.log(2)) j = j+ i;
            else j = j +1;
        }
        System.out.println(Math.sqrt(8));
    }

}