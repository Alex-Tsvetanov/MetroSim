package metrosim.domain;

public final class SimClock {
    private long seconds = 0;
    public long now(){ return seconds; }
    public void advance(long s){ seconds += s; }
}
