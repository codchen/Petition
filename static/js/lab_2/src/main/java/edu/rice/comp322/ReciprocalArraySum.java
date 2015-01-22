package edu.rice.comp322;

import edu.rice.hj.api.HjProcedure;
import edu.rice.hj.api.HjSuspendingCallable;
import edu.rice.hj.api.SuspendableException;
import edu.rice.hj.runtime.util.Pair;

import java.util.Random;

import static edu.rice.hj.Module0.doWork;
import static edu.rice.hj.Module0.finish;
import static edu.rice.hj.Module1.async;
import static edu.rice.hj.Module1.launchHabaneroApp;

// import statements for HJlib constructs

/**
 * edu.rice.comp322.ReciprocalArraySum --- Computing the sum of reciprocals of array elements with 2-way parallelism
 * <p/>
 * The goal of this example program is to create an array of n random int's, and compute the sum of their reciprocals in
 * two ways: 1) Sequentially in method seqArraySum() 2) In parallel using two tasks in method parArraySum() The
 * profitability of the parallelism depends on the size of the array and the overhead of async creation.
 * <p/>
 * Your assignment is to use two-way parallelism in method parArraySum() to obtain a smaller execution time than
 * seqArraySum().  Note that execution times and the impact of parallelism will vary depending on the machine that you
 * run this program on, and other applications that may be executing on the machine.
 *
 * @author Vivek Sarkar (vsarkar@rice.edu)
 */
public class ReciprocalArraySum {
    /**
     * Constant <code>ERROR_MSG="Incorrect argument for array size"</code>
     */
    public static final String ERROR_MSG = "Incorrect argument for array size (should be > 0), assuming n = 25,000,000";
    /**
     * Constant <code>DEFAULT_N=100_000_000</code>
     */
    public static final int DEFAULT_N = 1_000_000;

    /**
     * <p>main.</p>
     *
     * @param argv an array of {@link String} objects.
     */
    public static void main(final String[] argv) {
        // Initialization
        int n = parseLengthArgument(argv);
        final double[] X = initializeArray(n);

        System.out.println("ReciprocalArraySum.main: starting...");
        for (int numRun = 0; numRun < 5; numRun++) {
            System.out.printf("  Run %d\n", numRun);
            launchHabaneroApp(() -> {
                timeExecution(() -> seqArraySum(X), (p) -> printResults("seqArraySum", p.right, p.left));
                timeExecution(() -> parArraySum_2asyncs(X), (p) -> printResults("parArraySum", p.right, p.left));
            });
        }
        System.out.println("ReciprocalArraySum.main: ended.");

    }

    protected static int parseLengthArgument(final String[] argv) {
        int n;
        if (argv.length != 0) {
            try {
                n = Integer.parseInt(argv[0]);
                if (n <= 0) {
                    // Bad value of n
                    System.out.println(ERROR_MSG);
                    n = DEFAULT_N;
                }
            } catch (Throwable e) {
                System.out.println(ERROR_MSG);
                n = DEFAULT_N;
            }
        } else { // argv.length == 0
            n = DEFAULT_N;
        }
        return n;
    }

    protected static double[] initializeArray(final int n) {
        final double[] X = new double[n];
        final Random myRand = new Random(n);

        for (int i = 0; i < n; i++) {
            X[i] = myRand.nextInt(n);
            if (X[i] == 0.0) {
                i--;
            }
        }
        return X;
    }

    protected static Pair<Double, Long> timeExecution(
            final HjSuspendingCallable<Double> body,
            final HjProcedure<Pair<Double, Long>> callback) throws SuspendableException {

        final long startTime = System.nanoTime();

        final Double result = body.call();

        final long endTime = System.nanoTime();
        final long execTimeInNanos = endTime - startTime;

        final Pair<Double, Long> resultPair = Pair.factory(result, execTimeInNanos);
        if (callback != null) {
            callback.apply(resultPair);
        }

        return resultPair;
    }

    protected static void printResults(final String name, final long timeInNanos, final double sum) {
        System.out.printf("    %s completed in %8.3f milliseconds, with sum = %10.6f \n", name, timeInNanos / 1e6, sum);
    }

    /**
     * Sequentially compute the sum of the reciprocals of the array elements
     */
    protected static double seqArraySum(final double[] X) throws SuspendableException {

        // Use this array to safely write to memory from the async
        double[] sum = {0};

        // Wrap this for loop in an async so its abstract metrics can be counted
        finish(() -> {
            async(() -> {
                // Compute the sum of the reciprocals of the array elements
                for (int i = 0; i < X.length; i++) {
                    sum[0] += 1 / X[i];
                    // Call doWork() here to keep track abstractly of how much work is being done
                    doWork(1);
                }
            });
        });

        // Return the contents of the sum array
        return sum[0];
    }

    /**
     * Compute the sum of the reciprocals of the array elements in parallel using two asyncs
     */
    protected static double parArraySum_2asyncs(final double[] X) throws SuspendableException {

        // Use this array to safely write to memory from both asyncs
        double[] sum = {0, 0};

        finish(() -> {
            // TODO Write a parallel version of the seqArraySum code above using two asyncs
            // HINT You have already written code almost exactly like this in Lab 1 last week!
            // Remember to add the calls to doWork() as seen above to keep track of abstract metrics.
        });

        // Return the grand total sum
        return sum[0] + sum[1];

    }

    /**
     * Compute the sum of the reciprocals of the array elements in parallel using four asyncs
     */
    protected static double parArraySum_4asyncs(final double[] X) throws SuspendableException {

        // TODO Create a version of parArraySum_2asyncs that uses 4 asyncs.
        // How do you want to split up the work among the 4 tasks? Equally? Is this the best way?

        // Return the grand total sum
        return 0;

    }

    /**
     * Compute the sum of the reciprocals of the array elements in parallel using eight asyncs
     */
    protected static double parArraySum_8asyncs(final double[] X) throws SuspendableException {

        // TODO Create a version of parArraySum_2asyncs that uses 8 asyncs.
        // Do you really want to have to manually create 8 asyncs manually?
        // Is there a better way you could write this function?
        // Remember that copying and pasting code is generally discouraged.

        // Return the grand total sum
        return 0;

    }

}
