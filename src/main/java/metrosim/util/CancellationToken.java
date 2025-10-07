package metrosim.util;

import java.util.concurrent.atomic.AtomicBoolean;

public final class CancellationToken {
  private final AtomicBoolean cancelled = new AtomicBoolean(false);

  public boolean isCancelled() {
    return cancelled.get();
  }

  public void cancel() {
    cancelled.set(true);
  }
}
