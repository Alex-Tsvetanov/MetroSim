package metrosim.events;

public interface Observer<T> {
  void onNext(T value);
}
