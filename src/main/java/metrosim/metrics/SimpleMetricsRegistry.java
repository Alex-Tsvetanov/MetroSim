package metrosim.metrics;
import java.util.concurrent.ConcurrentHashMap; import java.util.concurrent.atomic.AtomicLong;
public final class SimpleMetricsRegistry implements MetricsRegistry {
    private final ConcurrentHashMap<String, AtomicLong> counters=new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicLong> timeSums=new ConcurrentHashMap<>();
    public void counter(String name, long d){ counters.computeIfAbsent(name,k->new AtomicLong()).addAndGet(d); }
    public void recordTime(String name, long ns){ timeSums.computeIfAbsent(name,k->new AtomicLong()).addAndGet(ns); }
    public long getCounter(String name){ var v=counters.get(name); return v==null?0:v.get(); }
    public long getTimeSum(String name){ var v=timeSums.get(name); return v==null?0:v.get(); }
}
