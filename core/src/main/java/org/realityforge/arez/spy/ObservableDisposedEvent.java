package org.realityforge.arez.spy;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.realityforge.arez.Observable;

/**
 * Notification when Observable is disposed.
 */
public final class ObservableDisposedEvent
{
  @Nonnull
  private final Observable _observable;

  public ObservableDisposedEvent( @Nonnull final Observable observable )
  {
    _observable = Objects.requireNonNull( observable );
  }

  @Nonnull
  public Observable getObservable()
  {
    return _observable;
  }
}