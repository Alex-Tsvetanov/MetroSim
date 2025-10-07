package metrosim.metrics;

public interface MetricsRegistry {
  void counter(String name, long delta);

  void recordTime(String name, long nanos);

  long getCounter(String name);

  long getTimeSum(String name);
}
