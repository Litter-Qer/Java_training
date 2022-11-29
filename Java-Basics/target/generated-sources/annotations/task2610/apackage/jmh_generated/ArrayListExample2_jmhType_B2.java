package task2610.apackage.jmh_generated;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
public class ArrayListExample2_jmhType_B2 extends ArrayListExample2_jmhType_B1 {
    public volatile int setupTrialMutex;
    public volatile int tearTrialMutex;
    public final static AtomicIntegerFieldUpdater<ArrayListExample2_jmhType_B2> setupTrialMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(ArrayListExample2_jmhType_B2.class, "setupTrialMutex");
    public final static AtomicIntegerFieldUpdater<ArrayListExample2_jmhType_B2> tearTrialMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(ArrayListExample2_jmhType_B2.class, "tearTrialMutex");

    public volatile int setupIterationMutex;
    public volatile int tearIterationMutex;
    public final static AtomicIntegerFieldUpdater<ArrayListExample2_jmhType_B2> setupIterationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(ArrayListExample2_jmhType_B2.class, "setupIterationMutex");
    public final static AtomicIntegerFieldUpdater<ArrayListExample2_jmhType_B2> tearIterationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(ArrayListExample2_jmhType_B2.class, "tearIterationMutex");

    public volatile int setupInvocationMutex;
    public volatile int tearInvocationMutex;
    public final static AtomicIntegerFieldUpdater<ArrayListExample2_jmhType_B2> setupInvocationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(ArrayListExample2_jmhType_B2.class, "setupInvocationMutex");
    public final static AtomicIntegerFieldUpdater<ArrayListExample2_jmhType_B2> tearInvocationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(ArrayListExample2_jmhType_B2.class, "tearInvocationMutex");

}
