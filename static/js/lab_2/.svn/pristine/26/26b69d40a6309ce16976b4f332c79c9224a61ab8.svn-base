package edu.rice.comp322;

import edu.rice.hj.api.HjMetrics;
import edu.rice.hj.runtime.config.HjSystemProperty;
import edu.rice.hj.runtime.util.Pair;
import junit.framework.TestCase;

import static edu.rice.comp322.ReciprocalArraySum.*;
import static edu.rice.hj.Module0.abstractMetrics;
import static edu.rice.hj.Module0.launchHabaneroApp;

/**
 * Unit test for simple App.
 */
public class Lab2Test extends TestCase {

    public void testReciprocalParallelism2Asyncs() {
        System.out.println("\nLab2Test.testReciprocalParallelism2Asyncs() starts...");

        @SuppressWarnings("unchecked")
        final Pair<Double, Long>[] results = new Pair[]{null, null};
        final double[] X = initializeArray(ReciprocalArraySum.DEFAULT_N);
        HjSystemProperty.abstractMetrics.set(true);

        // sequential
        launchHabaneroApp(() -> {
            results[0] = timeExecution(() -> seqArraySum(X), null);
            System.out.println("\n* running sequential version (ie, 1 async) *");
            printStats(abstractMetrics(), results, false);
        });

        // two asyncs
        launchHabaneroApp(() -> {
            results[1] = timeExecution(() -> parArraySum_2asyncs(X), null);
            System.out.println("\n* running parallel version with 2 asyncs *");
            printStats(abstractMetrics(), results, true);
        });

        System.out.println("\nLab2Test.testReciprocalParallelism2Asyncs() ends.");
    }

    public void testReciprocalParallelism4Asyncs() {
        System.out.println("\nLab2Test.testReciprocalParallelism4Asyncs() starts...");

        @SuppressWarnings("unchecked")
        final Pair<Double, Long>[] results = new Pair[]{null, null};
        final double[] X = initializeArray(ReciprocalArraySum.DEFAULT_N);
        HjSystemProperty.abstractMetrics.set(true);

        // sequential
        launchHabaneroApp(() -> {
            results[0] = timeExecution(() -> seqArraySum(X), null);
            System.out.println("\n* running sequential version (ie, 1 async) *");
            printStats(abstractMetrics(), results, false);
        });

        // four asyncs
        launchHabaneroApp(() -> {
            results[1] = timeExecution(() -> parArraySum_4asyncs(X), null);
            System.out.println("\n* running parallel version with 4 asyncs *");
            printStats(abstractMetrics(), results, true);
        });

        System.out.println("\nLab2Test.testReciprocalParallelism4Asyncs() ends.");
    }

    public void testReciprocalParallelism8Asyncs() {
        System.out.println("\nLab2Test.testReciprocalParallelism8Asyncs() starts...");

        @SuppressWarnings("unchecked")
        final Pair<Double, Long>[] results = new Pair[]{null, null};
        final double[] X = initializeArray(ReciprocalArraySum.DEFAULT_N);
        HjSystemProperty.abstractMetrics.set(true);

        // sequential
        launchHabaneroApp(() -> {
            results[0] = timeExecution(() -> seqArraySum(X), null);
            System.out.println("\n* running sequential version (ie, 1 async) *");
            printStats(abstractMetrics(), results, false);
        });

        // eight asyncs
        launchHabaneroApp(() -> {
            results[1] = timeExecution(() -> parArraySum_8asyncs(X), null);
            System.out.println("\n* running parallel version with 8 asyncs *");
            printStats(abstractMetrics(), results, true);
        });

        System.out.println("\nLab2Test.testReciprocalParallelism8Asyncs() ends.");
    }

    private void printStats(final HjMetrics actualMetrics, final Pair<Double, Long>[] results, final boolean isParallelResult) {

        // print abstract metrics
        System.out.println("\nabstract metrics:");
        System.out.println("\ttotal work = number of calls to doWork() = " + actualMetrics.totalWork());
        System.out.println("\tcritical path length through asyncs = " + actualMetrics.criticalPathLength());
        System.out.println("\tideal parallelism = total work / CPL = " + actualMetrics.idealParallelism());

        if (isParallelResult) {

            // ensure the parallel code is faster than the sequential code
            assertTrue("Not achieving any ideal parallelism", actualMetrics.idealParallelism() > 1.1);

            // print actual metrics
            System.out.println("\nactual speedup metrics:");
            final Pair<Double, Long> seqRes = results[0];
            final Pair<Double, Long> parRes = results[1];
            printResults("seqArraySum", seqRes.right, seqRes.left);
            printResults("parArraySum", parRes.right, parRes.left);
            final double speedup = (1.0 * seqRes.right) / parRes.right;
            System.out.println("    actual speedup = " + speedup);

            // check for correctness
            final String seqResult = String.format("%8.5f", seqRes.left);
            final String parResult = String.format("%8.5f", parRes.left);
            assertTrue("Expected = " + seqResult + ", actual = " + parResult, parResult.compareTo(seqResult) == 0);

        }

    }

}
