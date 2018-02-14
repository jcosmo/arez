package com.example.repository;

import arez.Arez;
import arez.ArezContext;
import arez.Component;
import arez.Disposable;
import arez.Observable;
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
final class Arez_PackageAccessRepositoryExampleRepository extends PackageAccessRepositoryExampleRepository implements Disposable, Identifiable<Long> {
  private static volatile long $$arez$$_nextId;

  private final long $$arez$$_id;

  private boolean $$arez$$_disposed;

  @Nullable
  private final ArezContext $$arez$$_context;

  private final Component $$arez$$_component;

  private final Observable<Boolean> $$arez$$_disposedObservable;

  @Nonnull
  private final Observable<Stream<PackageAccessRepositoryExample>> $$arez$$_entities;

  @Inject
  Arez_PackageAccessRepositoryExampleRepository() {
    super();
    this.$$arez$$_context = Arez.areZonesEnabled() ? Arez.context() : null;
    this.$$arez$$_id = $$arez$$_nextId++;
    this.$$arez$$_component = Arez.areNativeComponentsEnabled() ? getContext().createComponent( "PackageAccessRepositoryExampleRepository", $$arez$$_id(), getRepositoryName(), () -> super.preDispose(), null ) : null;
    this.$$arez$$_disposedObservable = getContext().createObservable( Arez.areNativeComponentsEnabled() ? this.$$arez$$_component : null, Arez.areNamesEnabled() ? getRepositoryName() + ".isDisposed" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> this.$$arez$$_disposed : null, null );
    this.$$arez$$_entities = getContext().createObservable( Arez.areNativeComponentsEnabled() ? this.$$arez$$_component : null, Arez.areNamesEnabled() ? getRepositoryName() + ".entities" : null, Arez.arePropertyIntrospectorsEnabled() ? () -> super.entities() : null, null );
    if ( Arez.areNativeComponentsEnabled() ) {
      this.$$arez$$_component.complete();
    }
  }

  @Override
  @Nonnull
  protected final ArezContext getContext() {
    return Arez.areZonesEnabled() ? this.$$arez$$_context : Arez.context();
  }

  final long $$arez$$_id() {
    return this.$$arez$$_id;
  }

  @Override
  @Nonnull
  public final Long getArezId() {
    return $$arez$$_id();
  }

  @Nonnull
  protected final String getRepositoryName() {
    return "PackageAccessRepositoryExampleRepository";
  }

  @Override
  public boolean isDisposed() {
    if ( getContext().isTransactionActive() && !this.$$arez$$_disposedObservable.isDisposed() )  {
      this.$$arez$$_disposedObservable.reportObserved();
      return this.$$arez$$_disposed;
    } else {
      return this.$$arez$$_disposed;
    }
  }

  @Override
  public void dispose() {
    if ( !isDisposed() ) {
      this.$$arez$$_disposed = true;
      if ( Arez.areNativeComponentsEnabled() ) {
        this.$$arez$$_component.dispose();
      } else {
        getContext().safeAction( Arez.areNamesEnabled() ? getRepositoryName() + ".dispose" : null, () -> { {
          super.preDispose();
          this.$$arez$$_disposedObservable.dispose();
          this.$$arez$$_entities.dispose();
        } } );
      }
    }
  }

  @Nonnull
  @Override
  public Stream<PackageAccessRepositoryExample> entities() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> !this.$$arez$$_disposed, () -> "Method invoked on invalid component '" + getRepositoryName() + "'" );
    }
    this.$$arez$$_entities.reportObserved();
    return super.entities();
  }

  @Nonnull
  @Override
  protected Observable getEntitiesObservable() {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> !this.$$arez$$_disposed, () -> "Method invoked on invalid component '" + getRepositoryName() + "'" );
    }
    return $$arez$$_entities;
  }

  @Override
  public void destroy(@Nonnull final PackageAccessRepositoryExample arg0) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> !this.$$arez$$_disposed, () -> "Method invoked on invalid component '" + getRepositoryName() + "'" );
    }
    try {
      getContext().safeAction(Arez.areNamesEnabled() ? getRepositoryName() + ".destroy" : null, true, () -> super.destroy(arg0), arg0 );
    } catch( final RuntimeException | Error $$arez$$_e ) {
      throw $$arez$$_e;
    } catch( final Throwable $$arez$$_e ) {
      throw new IllegalStateException( $$arez$$_e );
    }
  }

  @Nonnull
  @Override
  PackageAccessRepositoryExample create(@Nonnull final String packageName,
      @Nonnull final String name) {
    if ( Arez.shouldCheckApiInvariants() ) {
      Guards.apiInvariant( () -> !this.$$arez$$_disposed, () -> "Method invoked on invalid component '" + getRepositoryName() + "'" );
    }
    try {
      return getContext().safeAction(Arez.areNamesEnabled() ? getRepositoryName() + ".create_packageName_name" : null, true, () -> super.create(packageName,name), packageName, name );
    } catch( final RuntimeException | Error $$arez$$_e ) {
      throw $$arez$$_e;
    } catch( final Throwable $$arez$$_e ) {
      throw new IllegalStateException( $$arez$$_e );
    }
  }

  @Override
  public final int hashCode() {
    return Long.hashCode( $$arez$$_id() );
  }

  @Override
  public final boolean equals(final Object o) {
    if ( this == o ) {
      return true;
    } else if ( null == o || !(o instanceof Arez_PackageAccessRepositoryExampleRepository) ) {
      return false;
    } else {
      final Arez_PackageAccessRepositoryExampleRepository that = (Arez_PackageAccessRepositoryExampleRepository) o;;
      return $$arez$$_id() == that.$$arez$$_id();
    }
  }

  @Override
  public final String toString() {
    if ( Arez.areNamesEnabled() ) {
      return "ArezComponent[" + getRepositoryName() + "]";
    } else {
      return super.toString();
    }
  }
}