package arez;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import org.realityforge.anodoc.TestOnly;
import org.realityforge.braincheck.BrainCheckConfig;
import static org.realityforge.braincheck.Guards.*;

/**
 * The scheduler is responsible for scheduling observer reactions.
 *
 * <p>When state has changed or potentially changed, observers are en-queued onto {@link #_pendingObservers}.
 * Observers are processed in rounds. Each round involves processing the number of observers that are
 * pending at the start of the round. Processing observers can schedule more observers and thus another
 * round is scheduled if more observers are scheduled during the round.</p>
 *
 * If {@link #_maxReactionRounds} is not <tt>0</tt> and the number of rounds exceeds the value of
 * {@link #_maxReactionRounds} then it is assumed that we have a runaway observer that does not
 * terminate or stabilize and a failure is triggered.
 */
final class ReactionScheduler
{
  static final int DEFAULT_MAX_REACTION_ROUNDS = 100;
  @Nonnull
  private final ArezContext _context;
  /**
   * Observers that have been scheduled but are not yet running.
   */
  @Nonnull
  private final CircularBuffer<Observer> _pendingObservers = new CircularBuffer<>( 100 );
  /**
   * The current reaction round.
   */
  @Nonnegative
  private int _currentReactionRound;
  /**
   * The number of reactions left in the current round.
   */
  @Nonnegative
  private int _remainingReactionsInCurrentRound;
  /**
   * The maximum number of iterations that can be triggered in sequence without triggering an error. Set this
   * to 0 to disable check, otherwise trigger
   */
  @Nonnegative
  private int _maxReactionRounds = DEFAULT_MAX_REACTION_ROUNDS;

  ReactionScheduler( @Nonnull final ArezContext context )
  {
    _context = Objects.requireNonNull( context );
  }

  /**
   * Return the maximum number of rounds before runaway reaction is detected.
   *
   * @return the maximum number of rounds.
   */
  @Nonnegative
  int getMaxReactionRounds()
  {
    return _maxReactionRounds;
  }

  /**
   * Set the maximum number of rounds before a runaway reaction is detected.
   *
   * @param maxReactionRounds the maximum number of rounds.
   */
  void setMaxReactionRounds( @Nonnegative final int maxReactionRounds )
  {
    if ( Arez.shouldCheckInvariants() )
    {
      invariant( () -> maxReactionRounds >= 0,
                 () -> "Arez-0098: Attempting to set maxReactionRounds to negative " +
                       "value " + maxReactionRounds + "." );
    }
    _maxReactionRounds = maxReactionRounds;
  }

  /**
   * Return true if we are currently running reactions.
   *
   * @return true if we are currently running reactions.
   */
  boolean isRunningReactions()
  {
    return 0 != _currentReactionRound;
  }

  /**
   * Add the specified observer to the list of pending observers.
   * The observer must have a reaction and must not already be in
   * the list of pending observers.
   *
   * @param observer the observer.
   */
  void scheduleReaction( @Nonnull final Observer observer )
  {
    if ( Arez.shouldCheckInvariants() )
    {
      invariant( () -> !_pendingObservers.contains( observer ),
                 () -> "Arez-0099: Attempting to schedule observer named '" + observer.getName() +
                       "' when observer is already pending." );
    }
    observer.setScheduledFlag();
    if ( observer.isHighPriority() )
    {
      _pendingObservers.addFirst( Objects.requireNonNull( observer ) );
    }
    else
    {
      _pendingObservers.add( Objects.requireNonNull( observer ) );
    }
  }

  /**
   * Return true if reactions are currently running, false otherwise.
   *
   * @return true if reactions are currently running, false otherwise.
   */
  boolean isReactionsRunning()
  {
    return 0 != _currentReactionRound;
  }

  /**
   * If the schedule is not already running pending observers then run pending observers until
   * complete or runaway reaction detected.
   *
   * @return the number of reactions executed
   */
  int runPendingObservers()
  {
    // Each reaction creates a top level transaction that attempts to run call
    // this method when it completes. Rather than allow this if it is detected
    // that we are running reactions already then just abort and assume the top
    // most invocation of runPendingObservers will handle scheduling
    if ( !isReactionsRunning() )
    {
      int observersScheduled = 0;
      while ( runObserver() )
      {
        observersScheduled++;
      }
      return observersScheduled;
    }
    else
    {
      return 0;
    }
  }

  /**
   * Execute the next pending observer if any.
   * <ul>
   * <li>
   * If there is any reactions left in this round then run the next reaction and consume a token.
   * </li>
   * <li>
   * If there are more rounds left in budget and more pending observers then start a new round,
   * allocating a number of tokens equal to the number of pending reactions, run the next reaction
   * and consume a token.
   * </li>
   * <li>
   * Otherwise runaway reactions detected, so act appropriately. (In development this means
   * purging pending observers and failing an invariant check)
   * </li>
   * </ul>
   *
   * @return true if an observer was ran, false otherwise.
   */
  boolean runObserver()
  {
    /*
     * All reactions expect to run as top level transactions so
     * there should be no transaction active.
     */
    if ( Arez.shouldCheckInvariants() )
    {
      invariant( () -> !getContext().isTransactionActive(),
                 () -> "Arez-0100: Invoked runObserver when transaction named '" +
                       getContext().getTransaction().getName() + "' is active." );
    }
    final int pendingObserverCount = _pendingObservers.size();
    // If we have reached the last observer in this round then
    // determine if we need any more rounds and if we do ensure
    if ( 0 == _remainingReactionsInCurrentRound )
    {
      if ( 0 == pendingObserverCount )
      {
        _currentReactionRound = 0;
        return false;
      }
      else if ( _currentReactionRound + 1 > _maxReactionRounds )
      {
        _currentReactionRound = 0;
        onRunawayReactionsDetected();
        return false;
      }
      else
      {
        _currentReactionRound = _currentReactionRound + 1;
        _remainingReactionsInCurrentRound = pendingObserverCount;
      }
    }
    /*
     * If we get to here there are still observers that need processing and we have not
     * exceeded our round budget. So we pop an observer off the list and process it.
     *
     * NOTE: The selection of the first observer ensures that the same observer is not
     * scheduled multiple times within a single round. This means that when runaway reaction
     * detection code is active, the list of pending observers contains those observers
     * that have likely lead to the runaway reaction. This of course assumes that the observers
     * were scheduled by appending to the buffer.
     */
    _remainingReactionsInCurrentRound--;
    final Observer observer = _pendingObservers.pop();
    assert null != observer;
    observer.clearScheduledFlag();
    observer.invokeReaction();
    return true;
  }

  /**
   * Called when runaway reactions detected.
   * Depending on configuration will optionally purge the pending
   * observers and optionally fail an invariant check.
   */
  void onRunawayReactionsDetected()
  {
    final List<String> observerNames =
      Arez.shouldCheckInvariants() && BrainCheckConfig.verboseErrorMessages() ?
      _pendingObservers.stream().map( Node::getName ).collect( Collectors.toList() ) :
      null;

    if ( ArezConfig.purgeReactionsWhenRunawayDetected() )
    {
      _pendingObservers.clear();
    }

    if ( Arez.shouldCheckInvariants() )
    {
      fail( () -> "Arez-0101: Runaway reaction(s) detected. Observers still running after " + _maxReactionRounds +
                  " rounds. Current observers include: " + observerNames );
    }
  }

  @Nonnull
  ArezContext getContext()
  {
    return _context;
  }

  @TestOnly
  @Nonnull
  CircularBuffer<Observer> getPendingObservers()
  {
    return _pendingObservers;
  }

  @TestOnly
  int getCurrentReactionRound()
  {
    return _currentReactionRound;
  }

  @TestOnly
  int getRemainingReactionsInCurrentRound()
  {
    return _remainingReactionsInCurrentRound;
  }

  @TestOnly
  void setCurrentReactionRound( final int currentReactionRound )
  {
    _currentReactionRound = currentReactionRound;
  }
}
