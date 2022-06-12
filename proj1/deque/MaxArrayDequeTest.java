package deque;

import org.junit.Test;
import java.util.Comparator;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    public static class StrComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b)
        {
            if(a.compareTo(b)>0)
                return 1;
            else if(a.compareTo(b)<0)
                return -1;
            else
                return 0;
        }
    }

    private static class IntComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer i1, Integer i2) {
            return i1 - i2;
        }
    }

    @Test
    public void SimpleMaxArrayTest() {

        StrComparator strComparator = new StrComparator();

        MaxArrayDeque<String> mad1 = new MaxArrayDeque<String>(strComparator);

        mad1.addFirst("front");
        mad1.addLast("middle");
        mad1.addLast("back");

        System.out.println(mad1.max());
        assertTrue("", mad1.max().equals("middle"));
    }


    @Test
    public void IntMaxArrayTest() {

        IntComparator intComparator = new IntComparator();

        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<Integer>(intComparator);

        mad1.addFirst(1);
        mad1.addLast(-5);
        mad1.addLast(3);

        System.out.println(mad1.max());
        assertTrue("", mad1.max().equals(3));
    }
}
