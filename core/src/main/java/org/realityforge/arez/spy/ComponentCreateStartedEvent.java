package org.realityforge.arez.spy;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.realityforge.arez.Component;

/**
 * Notification when a Component is starting to be created.
 */
public final class ComponentCreateStartedEvent
  implements SerializableEvent
{
  public static final String TYPE_NAME = EventUtil.getName( ComponentCreateStartedEvent.class );

  @Nonnull
  private final Component _component;

  public ComponentCreateStartedEvent( @Nonnull final Component component )
  {
    _component = Objects.requireNonNull( component );
  }

  @Nonnull
  public Component getComponent()
  {
    return _component;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toMap( @Nonnull final Map<String, Object> map )
  {
    map.put( "type", TYPE_NAME );
    map.put( "component", getComponent().getName() );
  }
}