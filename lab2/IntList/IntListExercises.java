package IntList;

public class IntListExercises {

    /**
     * Part A: (Buggy) mutative method that adds a constant C to each
     * element of an IntList
     * FIXED!
     * @param lst IntList from Lecture
     */
    public static void addConstant(IntList lst, int c) {
        IntList head = lst;
        /* Originally Wrong Code
        while (head.rest != null) {
            head.first += c;
            head = head.rest;
        }*/
        int size = head.size();
        for (int i=0; i< size; i++) {
            head.first += c;
            head = head.rest;
        }
    }

    /**
     * Part B: Buggy method that sets node.first to zero if
     * the max value in the list starting at node has the same
     * first and last digit, for every node in L
     * FIXED!
     * @param L IntList from Lecture
     */
    public static void setToZeroIfMaxFEL(IntList L) {
        IntList p = L;
        while (p != null) {
            /* refactor my code to make it more debugging friendly
            if (firstDigitEqualsLastDigit(max(p))) {
                p.first = 0;
            }*/
            int currentMax = max(p);
            boolean firstEqualsLast = firstDigitEqualsLastDigit(currentMax);
            if (firstEqualsLast) {
                p.first = 0;
            }
            p = p.rest;
        }
    }

    /** Returns the max value in the IntList starting at L. */
    public static int max(IntList L) {
        int max = L.first;
        IntList p = L.rest;
        while (p != null) {
            if (p.first > max) {
                max = p.first;
            }
            p = p.rest;
        }
        return max;
    }

    /** Returns true if the last digit of x is equal to
     *  the first digit of x.
     */
    public static boolean firstDigitEqualsLastDigit(int x) {
        int lastDigit = x % 10;
        /* if x=10, should also divide 10, otherwise, firstDigit of 10 will wrongly be 0 instead of 1 */
        while (x >= 10) {
            x = x / 10;
        }
        int firstDigit = x % 10;
        return firstDigit == lastDigit;
    }

    /**
     * Part C: (Buggy) mutative method that squares each prime
     * element of the IntList.
     * FIXED
     * @param lst IntList from Lecture
     * @return True if there was an update to the list
     */
    public static boolean squarePrimes(IntList lst) {
        // Base Case: we have reached the end of the list
        if (lst == null) {
            return false;
        }

        boolean currElemIsPrime = Primes.isPrime(lst.first);

        if (currElemIsPrime) {
            lst.first *= lst.first;
        }

        /* This will just square the first prime number instead all. So it needs to change return order:
         * if there is one currElemIsPrime=TRUE, return ture; but iteratively square prime number because
         * it needs to do the squarePrimes(lst.rest) first until the null, then do calculate "or" to return.
         * return currElemIsPrime || squarePrimes(lst.rest);
        */
        return squarePrimes(lst.rest) || currElemIsPrime;
    }
}
