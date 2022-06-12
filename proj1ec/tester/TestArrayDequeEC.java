package tester;
import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        String str = "";

        for (int i = 0; i < 200; i++) {
            int opt = StdRandom.uniform(0,4);
            if (opt == 0) {
                //addFirst
                int num = StdRandom.uniform(0, 100);
                student.addFirst(num);
                solution.addFirst(num);
                str += "addFirst(" + num + ")\n";
            } else if (opt == 1) {
                // addLast
                int num = StdRandom.uniform(0, 100);
                student.addLast(num);
                solution.addLast(num);
                str += "addLast(" + num + ")\n";
            }

            if (student.isEmpty()) {
                continue;
            }

            if (opt == 2) {
                // removeFirst
                str += "removeFirst()\n";
                assertEquals(str, solution.removeFirst(), student.removeFirst());
            } else if (opt == 3) {
                // removeLast
                str += "removeLast()\n";
                assertEquals(str, solution.removeLast(), student.removeLast());
            }
        }
    }
}
