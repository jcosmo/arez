package com.example.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.arez.Disposable;
import org.realityforge.arez.Observable;
import org.realityforge.arez.annotations.Action;
import org.realityforge.arez.annotations.ArezComponent;
import org.realityforge.arez.annotations.ObservableRef;
import org.realityforge.arez.annotations.PreDispose;
import org.realityforge.arez.component.NoResultException;
import org.realityforge.braincheck.Guards;

@Generated("org.realityforge.arez.processor.ArezProcessor")
@ArezComponent(
    singleton = true
)
public class NestedModel$BasicActionModelRepository implements NestedModel$BasicActionModelBaseRepositoryExtension {
  private static final boolean $$arez$$_IMMUTABLE_RESULTS = "true".equals( System.getProperty( "arez.repositories_return_immutables", String.valueOf( System.getProperty( "arez.environment", "production" ).equals( "development" ) ) ) );
  ;

  private final HashMap<Long, NestedModel.BasicActionModel> $$arez$$_entities = new HashMap<>();
  ;

  private final Collection<NestedModel.BasicActionModel> $$arez$$_entityList = Collections.unmodifiableCollection( $$arez$$_entities.values() );
  ;

  NestedModel$BasicActionModelRepository() {
  }

  @Nonnull
  public static NestedModel$BasicActionModelRepository newRepository() {
    return new Arez_NestedModel$BasicActionModelRepository();
  }

  @Action(
      name = "create"
  )
  @Nonnull
  public NestedModel.BasicActionModel create() {
    final NestedModel$Arez_BasicActionModel entity = new NestedModel$Arez_BasicActionModel();
    $$arez$$_entities.put( entity.$$arez$$_id(), entity );
    getEntitiesObservable().reportChanged();
    return entity;
  }

  @PreDispose
  final void preDispose() {
    $$arez$$_entityList.forEach( e -> Disposable.dispose( e ) );
    $$arez$$_entities.clear();
    getEntitiesObservable().reportChanged();
  }

  public boolean contains(@Nonnull final NestedModel.BasicActionModel entity) {
    getEntitiesObservable().reportObserved();
    return entity instanceof NestedModel$Arez_BasicActionModel && $$arez$$_entities.containsKey( ((NestedModel$Arez_BasicActionModel) entity).$$arez$$_id() );
  }

  @Action
  public void destroy(@Nonnull final NestedModel.BasicActionModel entity) {
    assert null != entity;
    if ( entity instanceof NestedModel$Arez_BasicActionModel && null != $$arez$$_entities.remove( ((NestedModel$Arez_BasicActionModel) entity).$$arez$$_id() ) ) {
      Disposable.dispose( entity );
      getEntitiesObservable().reportChanged();
    } else {
      Guards.fail( () -> "Called destroy() passing an entity that was not in the repository. Entity: " + entity );
    }
  }

  @ObservableRef
  Observable getEntitiesObservable() {
    throw new IllegalStateException();
  }

  /**
   * Return the raw collection of entities in the repository.
   * This collection should not be exposed to the user but may be used be repository extensions when
   * they define custom queries. NOTE: use of this method marks the list as observed.
   */
  @org.realityforge.arez.annotations.Observable(
      expectSetter = false
  )
  @Nonnull
  protected Collection<NestedModel.BasicActionModel> entities() {
    return $$arez$$_entityList;
  }

  /**
   * If config option enabled, wrap the specified list in an immutable list and return it.
   * This method should be called by repository extensions when returning list results when not using {@link toList(List)}.
   */
  @Nonnull
  protected final List<NestedModel.BasicActionModel> wrap(@Nonnull final List<NestedModel.BasicActionModel> list) {
    return $$arez$$_IMMUTABLE_RESULTS ? Collections.unmodifiableList( list ) : list;
  }

  /**
   * Convert specified stream to a list, wrapping as an immutable list if required.
   * This method should be called by repository extensions when returning list results.
   */
  @Nonnull
  protected final List<NestedModel.BasicActionModel> toList(@Nonnull final Stream<NestedModel.BasicActionModel> stream) {
    return wrap( stream.collect( Collectors.toList() ) );
  }

  @Nonnull
  public final List<NestedModel.BasicActionModel> findAll() {
    return toList( entities().stream() );
  }

  @Nonnull
  public final List<NestedModel.BasicActionModel> findAll(@Nonnull final Comparator<NestedModel.BasicActionModel> sorter) {
    return toList( entities().stream().sorted( sorter ) );
  }

  @Nonnull
  public final List<NestedModel.BasicActionModel> findAllByQuery(@Nonnull final Predicate<NestedModel.BasicActionModel> query) {
    return toList( entities().stream().filter( query ) );
  }

  @Nonnull
  public final List<NestedModel.BasicActionModel> findAllByQuery(@Nonnull final Predicate<NestedModel.BasicActionModel> query, @Nonnull final Comparator<NestedModel.BasicActionModel> sorter) {
    return toList( entities().stream().filter( query ).sorted( sorter ) );
  }

  @Nullable
  public final NestedModel.BasicActionModel findByQuery(@Nonnull final Predicate<NestedModel.BasicActionModel> query) {
    return entities().stream().filter( query ).findFirst().orElse( null );
  }

  @Nonnull
  public final NestedModel.BasicActionModel getByQuery(@Nonnull final Predicate<NestedModel.BasicActionModel> query) {
    final NestedModel.BasicActionModel entity = findByQuery( query );
    if ( null == entity ) {
      throw new NoResultException();
    }
    return entity;
  }

  @Override
  @Nonnull
  public final NestedModel$BasicActionModelRepository self() {
    return this;
  }
}
