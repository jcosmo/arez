package com.example.component_name;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.realityforge.arez.Arez;
import org.realityforge.arez.ArezContext;
import org.realityforge.arez.Disposable;
import org.realityforge.braincheck.Guards;

@Generated("org.realityforge.arez.processor.ArezProcessor")
public final class Arez_ComponentTypeNameAloneOnSingletonModel extends ComponentTypeNameAloneOnSingletonModel implements Disposable {
  private boolean $$arez$$_disposed;

  @Nonnull
  private final ArezContext $$arez$$_context;

  public Arez_ComponentTypeNameAloneOnSingletonModel() {
    super();
    this.$$arez$$_context = Arez.context();
  }

  @Nonnull
  public final String getTypeName() {
    return "ComponentTypeNameAloneOnSingletonModel";
  }

  @Override
  public boolean isDisposed() {
    return this.$$arez$$_disposed;
  }

  @Override
  public void dispose() {
    if ( !isDisposed() ) {
      this.$$arez$$_disposed = true;
    }
  }

  @Override
  public void doStuff(final long time, final float someOtherParameter) {
    Guards.invariant( () -> !this.$$arez$$_disposed, () -> "Method invoked on invalid component 'ComponentTypeNameAloneOnSingletonModel'" );
    try {
      this.$$arez$$_context.safeAction(Arez.areNamesEnabled() ? "ComponentTypeNameAloneOnSingletonModel.doStuff" : null, true, () -> super.doStuff(time,someOtherParameter), time, someOtherParameter );
    } catch( final RuntimeException $$arez$$_e ) {
      throw $$arez$$_e;
    } catch( final Exception $$arez$$_e ) {
      throw new IllegalStateException( $$arez$$_e );
    } catch( final Error $$arez$$_e ) {
      throw $$arez$$_e;
    } catch( final Throwable $$arez$$_e ) {
      throw new IllegalStateException( $$arez$$_e );
    }
  }

  @Override
  public final String toString() {
    if ( Arez.areNamesEnabled() ) {
      return "ArezComponent[ComponentTypeNameAloneOnSingletonModel]";
    } else {
      return super.toString();
    }
  }
}
