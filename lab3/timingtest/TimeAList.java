package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 * The first column N gives the size of the data structure (how many elements it contains).
 * The second column time (s) gives the time required to complete all operations.
 * The third column # ops gives the number of calls to addLast made during the timing experiment.
 * The fourth column microsec/op gives the number of microseconds it took on average to complete each call to addLast.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> N = new AList<>();
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        Stopwatch sw = new Stopwatch();
        int tick = 0;
        int ops = 0;
        /* change to a large size with multiply resize strategy to see the fast speed
        for (int i = 0; i < 128000; i += 1) {
         */
        for (int i = 0; i < 1024000; i += 1) {
            N.addLast(i);
            ops += 1;
            if (N.size() == Math.pow(2, tick) * 1000) {
                Ns.addLast(N.size());
                times.addLast(sw.elapsedTime());
                opCounts.addLast(ops);
                tick += 1;
            }
        }
        printTimingTable(Ns, times, opCounts);
    }
}
