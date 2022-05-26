package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> expected = new AListNoResizing<>();
        BuggyAList<Integer> result = new BuggyAList<>();

        expected.addLast(4);
        result.addLast(4);
        expected.addLast(5);
        result.addLast(5);
        expected.addLast(6);
        result.addLast(6);

        assertEquals(expected.removeLast(), result.removeLast());
        assertEquals(expected.removeLast(), result.removeLast());
        assertEquals(expected.removeLast(), result.removeLast());
    }

    @Test
    public void RandomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            /* returns a random integer in the range [0, 2), i.e. exclusive of the right argument */
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = correct.size();
                int size2 = broken.size();
                //System.out.println("size1: " + size1 + " size2: " + size2);
                assertEquals(size1, size2);
            } else if (operationNumber == 2 && correct.size() != 0) {
                // getLast
                int last1 = correct.getLast();
                int last2 = broken.getLast();
                //System.out.println("getLast1: " + last1 + " getLast2: " + last2);
                assertEquals(last1, last2);
            } else if (operationNumber == 3 && correct.size() != 0) {
                // removeLast
                int rev1 = correct.removeLast();
                int rev2 = broken.removeLast();
                //System.out.println("removeLast1(" + rev1 + ") removeLast2(" + rev2 + ")");
                assertEquals(rev1, rev2);
            }
        }
    }
}
