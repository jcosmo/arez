package arez;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.anodoc.TestOnly;

/**
 * This class is used to wait until a condition is true, then run effect and remove watch.
 *
 * <p>The condition function is run in a read-only, tracking transaction and will be re-evaluated
 * any time any of the observed elements are updated. The effect procedure is run in either a
 * read-only or read-write, non-tracking transaction.</p>
 */
final class Watcher
  extends Node
{
  /**
   * True if the effect should run in a read-write transaction.
   */
  private final boolean _mutation;
  /**
   * The condition to test.
   */
  @Nonnull
  private final ComputedValue<Boolean> _condition;
  /**
   * The effect/action to run when condition is true.
   */
  private final SafeProcedure _effect;

  /**
   * The observer responsible for watching the condition and running the effect reaction when condition triggered.
   */
  private final Observer _watcher;

  /**
   * Create the watcher.
   *
   * @param context   the Arez context.
   * @param name      the name (if any) used when naming the underlying Arez resources.
   * @param mutation  true if the effect can mutate state, false otherwise.
   * @param condition The function that determines when the effect is run.
   * @param effect    The procedure that is executed when the condition is true.
   */
  Watcher( @Nonnull final ArezContext context,
           @Nullable final String name,
           final boolean mutation,
           @Nonnull final SafeFunction<Boolean> condition,
           @Nonnull final SafeProcedure effect )
  {
    super( context, name );
    Objects.requireNonNull( condition );
    _mutation = mutation;
    _effect = Objects.requireNonNull( effect );
    _condition =
      getContext().createComputedValue( Arez.areNamesEnabled() ? getName() + ".condition" : null, condition );
    _watcher =
      getContext().autorun( Arez.areNamesEnabled() ? getName() + ".watcher" : null, true, this::checkCondition, false );

    getContext().triggerScheduler();
  }

  /**
   * Check the condition and when it returns true the run the effect and dispose the watcher.
   */
  private void checkCondition()
  {
    if ( _condition.get() )
    {
      getContext().safeAction( Arez.areNamesEnabled() ? getName() : null, _mutation, _effect );
      Disposable.dispose( _watcher );
      Disposable.dispose( _condition );
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose()
  {
    Disposable.dispose( _watcher );
    Disposable.dispose( _condition );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed()
  {
    return Disposable.isDisposed( _watcher );
  }

  @TestOnly
  boolean isMutation()
  {
    return _mutation;
  }

  @TestOnly
  SafeProcedure getEffect()
  {
    return _effect;
  }
}