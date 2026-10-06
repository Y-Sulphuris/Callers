package bench;

import com.ydo4ki.callers.Callers;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class Bench {
    static {
        Callers.getCallerClass();
        System.out.println(System.getProperty("com.ydo4ki.callers.impl"));
    }
    @Benchmark
    public void get() {
        Callers.getCallerClass();
    }
}
