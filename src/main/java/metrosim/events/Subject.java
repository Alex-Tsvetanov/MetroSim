package metrosim.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Subject<T> {
  private final List<Observer<T>> observers = new CopyOnWriteArrayList<>();

  public void subscribe(Observer<T> o) {
    observers.add(o);
  }

  public void unsubscribe(Observer<T> o) {
    observers.remove(o);
  }

  public void publish(T v) {
    for (var o : observers) o.onNext(v);
  }
}
