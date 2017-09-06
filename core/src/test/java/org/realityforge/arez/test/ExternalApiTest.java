package org.realityforge.arez.test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nonnull;
import org.realityforge.arez.AbstractArezTest;
import org.realityforge.arez.ArezContext;
import org.realityforge.arez.ComputedValue;
import org.realityforge.arez.Observable;
import org.realityforge.arez.Observer;
import org.realityforge.arez.ObserverErrorHandler;
import org.realityforge.arez.Procedure;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * This class tests all the public API of Arez and identifies all
 * the elements that should be visible outside package.
 */
public class ExternalApiTest
  extends AbstractArezTest
{
  @Test
  public void createComputedValue()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final String name = ValueUtil.randomString();
    final ComputedValue<String> computedValue = context.createComputedValue( name, () -> "", Objects::equals );

    context.procedure( ValueUtil.randomString(), false, null, () -> {
      assertEquals( computedValue.getName(), name );
      assertEquals( computedValue.get(), "" );

      computedValue.dispose();

      assertThrows( computedValue::get );
    } );
  }

  @Test
  public void createReactionObserver()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final AtomicInteger callCount = new AtomicInteger();

    final String name = ValueUtil.randomString();
    final Observer observer = context.autorun( name, false, callCount::incrementAndGet, true );

    assertEquals( observer.getName(), name );
    assertEquals( observer.isActive(), true );
    assertEquals( callCount.get(), 1 );

    observer.dispose();

    assertEquals( observer.isActive(), false );
  }

  @Test
  public void observerErrorHandler()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final AtomicInteger callCount = new AtomicInteger();

    final ObserverErrorHandler handler = ( observer, error, throwable ) -> callCount.incrementAndGet();
    context.addObserverErrorHandler( handler );

    final Procedure reaction = () -> {
      throw new RuntimeException();
    };
    // This will run immediately and generate an exception
    context.autorun( ValueUtil.randomString(), false, reaction, true );

    assertEquals( callCount.get(), 1 );

    context.removeObserverErrorHandler( handler );

    // This will run immediately and generate an exception
    context.autorun( ValueUtil.randomString(), false, reaction, true );

    assertEquals( callCount.get(), 1 );
  }

  @Test
  public void interactionWithSingleObservable()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final Observable observable = context.createObservable( ValueUtil.randomString() );

    final AtomicInteger reactionCount = new AtomicInteger();

    final Observer observer =
      context.autorun( ValueUtil.randomString(),
                       false,
                       () -> {
                         observable.reportObserved();
                         reactionCount.incrementAndGet();
                       },
                       true );

    assertEquals( reactionCount.get(), 1 );
    assertEquals( observer.isActive(), true );

    // Run an "action"
    context.procedure( ValueUtil.randomString(), true, null, observable::reportChanged );

    assertEquals( reactionCount.get(), 2 );
    assertEquals( observer.isActive(), true );
  }

  @Test
  public void interactionWithMultipleObservable()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final Observable observable1 = context.createObservable( ValueUtil.randomString() );
    final Observable observable2 = context.createObservable( ValueUtil.randomString() );
    final Observable observable3 = context.createObservable( ValueUtil.randomString() );
    final Observable observable4 = context.createObservable( ValueUtil.randomString() );

    final AtomicInteger reactionCount = new AtomicInteger();

    final Observer observer =
      context.autorun( ValueUtil.randomString(),
                       false,
                       () -> {
                         observable1.reportObserved();
                         observable2.reportObserved();
                         observable3.reportObserved();
                         reactionCount.incrementAndGet();
                       },
                       true );

    assertEquals( reactionCount.get(), 1 );
    assertEquals( observer.isActive(), true );

    // Run an "action"
    context.procedure( ValueUtil.randomString(), true, null, observable1::reportChanged );

    assertEquals( reactionCount.get(), 2 );
    assertEquals( observer.isActive(), true );

    // Update observer1+observer2 in transaction
    context.procedure( ValueUtil.randomString(),
                       true,
                       null,
                       () -> {
                         observable1.reportChanged();
                         observable2.reportChanged();
                       } );

    assertEquals( reactionCount.get(), 3 );
    assertEquals( observer.isActive(), true );

    context.procedure( ValueUtil.randomString(),
                       true,
                       null,
                       () -> {
                         observable3.reportChanged();
                         observable4.reportChanged();
                       } );

    assertEquals( reactionCount.get(), 4 );
    assertEquals( observer.isActive(), true );

    // observable4 should not cause a reaction as not observed
    context.procedure( ValueUtil.randomString(),
                       true,
                       null,
                       observable4::reportChanged );

    assertEquals( reactionCount.get(), 4 );
    assertEquals( observer.isActive(), true );
  }

  @Test
  public void function()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final Observable observable = context.createObservable( ValueUtil.randomString() );

    assertNotInTransaction( observable );

    final String expectedValue = ValueUtil.randomString();

    final String v0 =
      context.function( ValueUtil.randomString(), false, null, () -> {
        assertInTransaction( observable );
        return expectedValue;
      } );

    assertNotInTransaction( observable );

    assertEquals( v0, expectedValue );
  }

  @Test
  public void proceduresCanBeNested()
    throws Exception
  {
    final ArezContext context = new ArezContext();
    final Observable observable = context.createObservable( ValueUtil.randomString() );

    assertNotInTransaction( observable );

    context.procedure( ValueUtil.randomString(), false, null, () -> {
      assertInTransaction( observable );

      //First nested exception
      context.procedure( ValueUtil.randomString(), false, null, () -> {
        assertInTransaction( observable );

        //Second nested exception
        context.procedure( ValueUtil.randomString(), false, null, () -> assertInTransaction( observable ) );

        assertInTransaction( observable );
      } );

      assertInTransaction( observable );
    } );

    assertNotInTransaction( observable );
  }

  @Test
  public void nestedFunctions()
    throws Exception
  {
    final ArezContext context = new ArezContext();

    final Observable observable = context.createObservable( ValueUtil.randomString() );

    assertNotInTransaction( observable );

    final String expectedValue = ValueUtil.randomString();

    final String v0 =
      context.function( ValueUtil.randomString(), false, null, () -> {
        assertInTransaction( observable );

        //First nested exception
        final String v1 =
          context.function( ValueUtil.randomString(), false, null, () -> {
            assertInTransaction( observable );

            //Second nested exception
            final String v2 =
              context.function( ValueUtil.randomString(), false, null, () -> {
                assertInTransaction( observable );
                return expectedValue;
              } );

            assertInTransaction( observable );

            return v2;
          } );

        assertInTransaction( observable );
        return v1;
      } );

    assertNotInTransaction( observable );

    assertEquals( v0, expectedValue );
  }

  /**
   * Test we are in a transaction by trying to observe an observable.
   */
  private void assertInTransaction( @Nonnull final Observable observable )
  {
    observable.reportObserved();
  }

  /**
   * Test we are not in a transaction by trying to observe an observable.
   */
  private void assertNotInTransaction( @Nonnull final Observable observable )
  {
    assertThrows( observable::reportObserved );
  }
}
