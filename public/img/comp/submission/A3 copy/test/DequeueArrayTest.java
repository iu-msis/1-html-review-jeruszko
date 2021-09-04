import org.junit.jupiter.api.Test;

import javax.lang.model.element.Element;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DequeueArrayTest {

    @Test
    public void dequeDoubleNoResize() throws NoSuchElementE {
       DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        assertEquals(0, d.size());
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeLast());
        assertEquals(2, d.removeLast());
        assertEquals(0, d.size());
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        assertEquals(5, d.removeFirst());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(4, d.removeFirst());

        // My tests
        //Normal Array
        DequeueArray<Integer> dNew = new DequeueArrayDouble<>(5);
        assertEquals(0, dNew.size());
        dNew.addFirst(5);
        dNew.addFirst(6);
        dNew.addFirst(3);
        dNew.addFirst(2);
        dNew.addFirst(1);
        assertEquals(5,dNew.removeLast());//check removeslast
        assertEquals(4,dNew.size); //check size
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(3,dNew.size);//check size update
        assertEquals(6, dNew.getLast()); //Checks if GetLast works
        dNew.addLast(1);
        dNew.removeFirst();
        dNew.removeFirst();
        dNew.removeFirst();
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(0,dNew.size); //check size


        //Empty Array
        DequeueArray<Integer> dNull = new DequeueArrayDouble<>(5);
        assertThrows(NoSuchElementE.class, () -> { dNull.removeFirst(); //Checks removeFirst on Empty Array
        });
        assertThrows(NoSuchElementE.class, () -> { dNull.removeLast(); //Checks removeLast on Empty Array
        });
        assertThrows(NoSuchElementE.class, () -> { dNull.getFirst(); //Checks getFirst on Empty Array
        });
        assertThrows(NoSuchElementE.class, () -> { dNull.getLast(); //Checks getLast on Empty Array
        });

        //String Array No resize
        DequeueArray<String> dSt = new DequeueArrayDouble<>(5);
        assertEquals(0, d.size());
        dSt.addFirst("1");
        dSt.addFirst("2");
        dSt.addFirst("3");
        assertEquals("1", dSt.getLast()); //check getLats works with string
        assertEquals("3", dSt.removeFirst()); //check
        assertEquals("1", dSt.removeLast());
        assertEquals("2", dSt.removeLast());
        assertEquals(0, dSt.size());
        dSt.addLast("1");
        dSt.addLast("2");
        dSt.addFirst("3");
        dSt.addLast("4");
        dSt.addFirst("5");
        assertEquals("5", dSt.removeFirst());
        assertEquals("3", dSt.removeFirst());
        assertEquals("1", dSt.removeFirst());
        assertEquals("2", dSt.removeFirst());
        assertEquals("4", dSt.removeFirst());


    }

    @Test
    public void dequeDoubleResize() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayDouble<>(5);
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        d.addFirst(6);
        assertEquals(10, d.getCapacity());
        assertEquals(6, d.removeFirst());
        assertEquals(5, d.removeFirst());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(4, d.removeFirst());
        // My tests
        //Normal Array with grow
        DequeueArray<Integer> dNew = new DequeueArrayDouble<>(5);
        assertEquals(0, dNew.size());
        dNew.addFirst(5);
        dNew.addFirst(6);
        dNew.addFirst(3);
        dNew.addFirst(2);
        dNew.addFirst(1);
        assertEquals(5,dNew.removeLast());//check removeslast
        assertEquals(4,dNew.size); //check size
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(3,dNew.size);//check size update
        dNew.addLast(1);
        dNew.removeFirst();
        dNew.removeFirst();
        dNew.removeFirst();
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(0,dNew.size);

        // Empty Array
        DequeueArray<Integer> dZer = new DequeueArrayDouble<>(0); //Initial Cap at 0
        assertEquals(dZer.capacity, 0);
        assertEquals(0, dNew.size());
        //dZer.addFirst(1);         Not sure how to test for these values
        //System.out.println(dZer.getFirst());
        //assertEquals(dZer.capacity, 1);
    }
    @Test//By me
    public void DequeueArrayOneAndHalf() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayOneAndHalf<>(5);
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        d.addFirst(6);
        assertEquals(8, d.getCapacity()); //check for capacity 1.5times
        assertEquals(6, d.removeFirst());
        assertEquals(5, d.removeFirst());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(4, d.removeFirst());
        // My tests
        //Normal Array with grow
        DequeueArray<Integer> dNew = new DequeueArrayDouble<>(5);
        assertEquals(0, dNew.size());
        dNew.addFirst(5);
        dNew.addFirst(6);
        dNew.addFirst(3);
        dNew.addFirst(2);
        dNew.addFirst(1);
        assertEquals(5,dNew.removeLast());//check removeslast
        assertEquals(4,dNew.size); //check size
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(3,dNew.size);//check size update
        dNew.addLast(1);
        dNew.removeFirst();
        dNew.removeFirst();
        dNew.removeFirst();
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(0,dNew.size);
    }
    @Test//By me
    public void DequeueArrayPlusOne() throws NoSuchElementE {
        DequeueArray<Integer> d = new DequeueArrayPlusOne<>(5);
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);
        d.addFirst(5);
        d.addFirst(6);
        assertEquals(6, d.getCapacity()); //check for capacity + 1
        assertEquals(6, d.removeFirst());
        assertEquals(5, d.removeFirst());
        assertEquals(3, d.removeFirst());
        assertEquals(1, d.removeFirst());
        assertEquals(2, d.removeFirst());
        assertEquals(4, d.removeFirst());
        // My tests
        //Normal Array with grow
        DequeueArray<Integer> dNew = new DequeueArrayDouble<>(5);
        assertEquals(0, dNew.size());
        dNew.addFirst(5);
        dNew.addFirst(6);
        dNew.addFirst(3);
        dNew.addFirst(2);
        dNew.addFirst(1);
        assertEquals(5,dNew.removeLast());//check removeslast
        assertEquals(4,dNew.size); //check size
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(3,dNew.size);//check size update
        dNew.addLast(1);
        dNew.removeFirst();
        dNew.removeFirst();
        dNew.removeFirst();
        assertEquals(1,dNew.removeFirst());//removefirst
        assertEquals(0,dNew.size);
    }


    Duration timeDequeue (Random r, int size, int bound, DequeueArray<Integer> dq) throws NoSuchElementE {
        Instant start = Instant.now();

        for (int i=0; i<1000; i++) {
            dq.addFirst(r.nextInt(bound));
        }

        for (int i=0; i<size; i++) {
            int e = r.nextInt(bound);
            switch (r.nextInt(4)) {
                case 0 -> dq.addFirst(e);
                case 1 -> dq.addLast(e);
                case 2 -> dq.removeFirst();
                case 3 -> dq.removeLast();
                default -> throw new Error("Impossible");
            }
        }

        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    @Test
    void timeDeQueues () throws NoSuchElementE {
        Random r = new Random(500);
        int size = 10000;
        int bound = 1000;

        Duration d1 = timeDequeue(r, size, bound, new DequeueArrayDouble<>(100));
        System.out.printf("Running %d operations (size *2); time in ms = %d%n",
                size, d1.toMillis());

        Duration d2 = timeDequeue(r, size, bound, new DequeueArrayOneAndHalf<>(100));
        System.out.printf("Running %d operations (size *1.5); time in ms = %d%n",
                size, d2.toMillis());

        Duration d3 = timeDequeue(r, size, bound, new DequeueArrayPlusOne<>(100));
        System.out.printf("Running %d operations (size +1); time in ms = %d%n",
                size, d3.toMillis());

        // My time tests
        int sd1 = d1.toSecondsPart();
        String sd2 = d2.toString();
        String sd3 = d3.toString();
        //assertEquals(Integer.parseInt(sd2) < Integer.parseInt(sd1) < d3.toMillis(), true); //was not sure how to make this work
    }


}

