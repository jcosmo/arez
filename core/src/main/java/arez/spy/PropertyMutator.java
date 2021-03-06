package arez.spy;

/**
 * Changing the value of an Observable.
 *
 * @param <T> The type of the Observable value.
 */
@FunctionalInterface
public interface PropertyMutator<T>
{
  /**
   * Change the value of an Observable to specified value.
   *
   * @param value the value of an Observable.
   * @throws Throwable if unable to set value.
   */
  void set( T value )
    throws Throwable;
}
