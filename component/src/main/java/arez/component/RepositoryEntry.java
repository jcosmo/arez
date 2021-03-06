package arez.component;

import arez.Disposable;
import arez.Observer;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Entity to contain reference to entity and a monitor.
 * The monitor is Disposable representing a "when" observer that triggers removal of
 * entity from repository
 */
final class RepositoryEntry<E>
  implements Disposable
{
  /**
   * The underlying entity.
   */
  @Nonnull
  private final E _entity;
  /**
   * The monitor observer that will remove entity from repository if it is independent disposed.
   */
  @Nullable
  private Observer _monitor;

  /**
   * Create entry for entity.
   *
   * @param entity the entity placed into repository.
   */
  RepositoryEntry( @Nonnull final E entity )
  {
    _entity = Objects.requireNonNull( entity );
  }

  /**
   * Return the entity the entry represents.
   *
   * @return the entity the entry represents.
   */
  @Nonnull
  E getEntity()
  {
    return _entity;
  }

  /**
   * Set the monitor that will remove entity from repository when entity is disposed.
   *
   * @param monitor the monitor that will remove entity from repository when entity is disposed.
   */
  void setMonitor( @Nonnull final Observer monitor )
  {
    _monitor = Objects.requireNonNull( monitor );
  }

  /**
   * {@inheritDoc}
   */

  @Override
  public void dispose()
  {
    if ( null != _monitor )
    {
      _monitor.dispose();
      _monitor = null;
    }
    Disposable.dispose( _entity );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed()
  {
    return Disposable.isDisposed( _entity );
  }
}
