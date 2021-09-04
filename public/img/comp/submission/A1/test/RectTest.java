import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectTest {

    /**
     * This is a very minimal version of the method you need to write.
     * Your test cases should attempt to cover all patterns of
     * intersecting rectangles.
     *
     * We will run your testing method against several implementations
     * of "intersect" that might be incorrect in subtle ways. We expect
     * your test cases to pass when the implementation of "intersect"
     * is correct and to fail when the implementation of "intersect"
     * has errors. 
     */
    @Test
     void intersect() { //can we build out more rect?
        Rect r1 = new Rect(0, 0, 10, 10);
        Rect r2 = new Rect(20, 20, 30, 30);
        Rect r3 = new Rect(5, 5, 15, 15);
        Rect r4 = new Rect(10, 10, 15, 15);
        Rect r5 = new Rect(0,0,0,0);
        Rect r6 = new Rect(1,1,1,1);
        Rect r7 = new Rect(-1,-1,1,1);
       Rect r8 = new Rect(5,5,15,15);
       Rect r9 = new Rect(0,10,10,20);
       Rect r10 = new Rect(-10,0,0,10);
       Rect r11 = new Rect(0,0,2,2);
       Rect r12 = new Rect(-5, -5, -1, -1);
       Rect r13 = new Rect(-5, -5, 0, 0);
       Rect r14 = new Rect(-4, -4, 1, 1);
       Rect r15 = new Rect(0, 0, 10, 10);
       Rect r16 = new Rect(-1, -1, 11, 11);
       Rect r17 = new Rect(-5, 0, 5, 10);
       Rect r18 = new Rect(10, 10, 0, 0);
       Rect r19 = new Rect(5, -5, 15, 5);

        assertFalse(r1.intersect(r2));//NA
        assertTrue(r1.intersect(r3));
        assertTrue(r1.intersect(r4));
        assertTrue(r1.intersect(r5));
        assertTrue(r1.intersect(r6));//not sure how to get around this
        assertTrue(r1.intersect(r7));
       assertFalse(r1.intersect(r12));
        assertFalse(r2.intersect(r1));
        assertFalse(r2.intersect(r3));
       assertTrue(r3.intersect(r4));
        assertFalse(r6.intersect(r5));
       assertTrue(r6.intersect(r7));
        assertTrue(r5.intersect(r7));
        assertTrue(r7.intersect(r5));
        assertFalse(r5.intersect(r6));
       assertTrue(r1.intersect(r8));
       assertTrue(r1.intersect(r10));
       assertTrue(r7.intersect(r11));
       assertFalse(r1.intersect(r12));
       assertTrue(r13.intersect(r1));
       assertTrue(r14.intersect(r1));
       assertTrue(r15.intersect(r1));//same overlap
       assertTrue(r16.intersect(r1));
       assertTrue(r1.intersect(r16));
       assertFalse(r6.intersect(r2));
       assertTrue(r1.intersect(r17));
       assertTrue(r1.intersect(r9));
       assertTrue(r1.intersect(r18));
       assertTrue(r1.intersect(r19));




    }
}