package com.example.repository;

import arez.Arez;
import arez.ArezContext;
import arez.Component;
import arez.Disposable;
import arez.Observable;
import arez.component.ComponentObservable;
import arez.component.ComponentState;
import arez.component.Identifiable;
import java.util.stream.Stream;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.realityforge.braincheck.Guards;

@Generated("arez.processor.ArezProcessor")
@Singleton
final class Arez_DaggerDisabledRepositoryRepository extends DaggerDisabledRepositoryRepository implements Disposable, Identifiable<Long>, ComponentObservable {
  private static volatile long $$arezi$$_nextId;

  private final long $$arezi$$_id;

  private byte $$arezi$$_state;

  @Nullable
  private final ArezContext $$arezi$$_context;

  private final Component $$arezi$$_component;

  private final Observable<Boolean> $$arezi$$_disposedObservable;

  @Nonnull
  private final Observable<Stream<DaggerDisabledRepository>> $$arez$$_entities;

  @Inject
  Arez_DaggerDisabledRepositoryRepository() {
    super();
    this.$$arezi$$_context = Arez.areZonesEnabled() ? Arez.context() : null;
    this.$$arezi$$_id = Arez.areNativeComponentsEnabled() ? $$arezi$$_nextId++ : 0L;
    this.$$arezi$$_state = ComponentState.COMPONENT_INITIALIZED;
    this.$$arezi$$_component = Arez.areNativeComponentsEnabled() ? getContext().createComponent( "DaggerDisabledRepositoryRepository", $$arezi$$_id(), Arez.areNamesEnabled() ? getContainerName() : null, () -> super.preDispose() ) : null;
    this.$$arezi$$_disposedObservable = getContext().createObservable( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? getContainerName() + ".isDisposed" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> this.$$arezi$$_state >= 0 : null );
    this.$$arez$$_entities = getContext().createObservable( Arez.areNativeComponentsEnabled() ? this.$$arezi$$_component : null, Arez.areNamesEnabled() ? getContainerName() + ".entities" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> super.entities() : null, null );
    this.$$arezi$$_state = ComponentState.COMPONENT_CONSTRUCTED;
    if ( Arez.areNativeComponentsEnabled() ) {
      this.$$arezi$$_component.complete();
    }
    this.$$arezi$$_state = ComponentState.COMPONENT_READY;
  }

  @Override
  @Nonnull
  protected final ArezContext getContext() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named 'getContext' invoked on uninitialized component of type 'DaggerDisabledRepositoryRepository'" );
    }
    return Arez.areZonesEnabled() ? this.$$arezi$$_context : Arez.context();
  }

  @Nonnull
  protected final Component component() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named 'component' invoked on uninitialized component of type 'DaggerDisabledRepositoryRepository'" );
    }
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenConstructed( this.$$arezi$$_state ), () -> "Method named 'component' invoked on un-constructed component named '" + getContainerName() + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenCompleted( this.$$arezi$$_state ), () -> "Method named 'component' invoked on incomplete component named '" + getContainerName() + "'" );
    }
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'component' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + getContainerName() + "'" );
    }
    if ( Arez.shouldCheckInvariants() ) {
      Guards.invariant( () -> Arez.areNativeComponentsEnabled(), () -> "Invoked @ComponentRef method 'component' but Arez.areNativeComponentsEnabled() returned false." );
    }
    return this.$$arezi$$_component;
  }

  final long $$arezi$$_id() {
    if ( Arez.shouldCheckInvariants() && !Arez.areNativeComponentsEnabled() ) {
      Guards.fail( () -> "Method invoked to access id when id not expected." );
    }
    return this.$$arezi$$_id;
  }

  @Override
  @Nonnull
  public final Long getArezId() {
    return $$arezi$$_id();
  }

  @Nonnull
  protected final String getContainerName() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.hasBeenInitialized( this.$$arezi$$_state ), () -> "Method named 'getContainerName' invoked on uninitialized component of type 'DaggerDisabledRepositoryRepository'" );
    }
    return "DaggerDisabledRepositoryRepository";
  }

  private boolean $$arezi$$_observe() {
    final boolean isDisposed = isDisposed();
    if ( !isDisposed )  {
      this.$$arezi$$_disposedObservable.reportObserved();
    }
    return !isDisposed;
  }

  @Override
  public boolean observe() {
    return $$arezi$$_observe();
  }

  @Override
  public boolean isDisposed() {
    return ComponentState.isDisposingOrDisposed( this.$$arezi$$_state );
  }

  @Override
  public void dispose() {
    if ( !ComponentState.isDisposingOrDisposed( this.$$arezi$$_state ) ) {
      this.$$arezi$$_state = ComponentState.COMPONENT_DISPOSING;
      if ( Arez.areNativeComponentsEnabled() ) {
        this.$$arezi$$_component.dispose();
      } else {
        getContext().dispose( Arez.areNamesEnabled() ? getContainerName() : null, () -> { {
          super.preDispose();
          this.$$arezi$$_disposedObservable.dispose();
          this.$$arez$$_entities.dispose();
        } } );
      }
      this.$$arezi$$_state = ComponentState.COMPONENT_DISPOSED;
    }
  }

  @Nonnull
  @Override
  public Stream<DaggerDisabledRepository> entities() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'entities' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + getContainerName() + "'" );
    }
    this.$$arez$$_entities.reportObserved();
    return super.entities();
  }

  @Nonnull
  @Override
  protected Observable getEntitiesObservable() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'getEntitiesObservable' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + getContainerName() + "'" );
    }
    return $$arez$$_entities;
  }

  @Override
  public void destroy(@Nonnull final DaggerDisabledRepository arg0) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'destroy' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + getContainerName() + "'" );
    }
    try {
      getContext().safeAction(Arez.areNamesEnabled() ? getContainerName() + ".destroy" : null, true, () -> super.destroy(arg0), arg0 );
    } catch( final RuntimeException | Error $$arez_exception$$ ) {
      throw $$arez_exception$$;
    } catch( final Throwable $$arez_exception$$ ) {
      throw new IllegalStateException( $$arez_exception$$ );
    }
  }

  @Nonnull
  @Override
  public DaggerDisabledRepository create(@Nonnull final String name) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> ComponentState.isActive( this.$$arezi$$_state ), () -> "Method named 'create' invoked on " + ComponentState.describe( this.$$arezi$$_state ) + " component named '" + getContainerName() + "'" );
    }
    try {
      return getContext().safeAction(Arez.areNamesEnabled() ? getContainerName() + ".create_name" : null, true, () -> super.create(name), name );
    } catch( final RuntimeException | Error $$arez_exception$$ ) {
      throw $$arez_exception$$;
    } catch( final Throwable $$arez_exception$$ ) {
      throw new IllegalStateException( $$arez_exception$$ );
    }
  }

  @Override
  public final int hashCode() {
    if ( Arez.areNativeComponentsEnabled() ) {
      return Long.hashCode( $$arezi$$_id() );
    } else {
      return super.hashCode();
    }
  }

  @Override
  public final boolean equals(final Object o) {
    if ( Arez.areNativeComponentsEnabled() ) {
      if ( this == o ) {
        return true;
      } else if ( null == o || !(o instanceof Arez_DaggerDisabledRepositoryRepository) ) {
        return false;
      } else {
        final Arez_DaggerDisabledRepositoryRepository that = (Arez_DaggerDisabledRepositoryRepository) o;;
        return $$arezi$$_id() == that.$$arezi$$_id();
      }
    } else {
      return super.equals( o );
    }
  }

  @Override
  public final String toString() {
    if ( Arez.areNamesEnabled() ) {
      return "ArezComponent[" + getContainerName() + "]";
    } else {
      return super.toString();
    }
  }
}
