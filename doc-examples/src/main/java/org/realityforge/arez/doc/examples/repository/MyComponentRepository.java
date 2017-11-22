package org.realityforge.arez.doc.examples.repository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.arez.annotations.Action;
import org.realityforge.arez.component.NoResultException;

public class MyComponentRepository
{
  @Nonnull
  public static MyComponentRepository newRepository()
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public MyComponent create( @Nonnull final String name )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  public boolean contains( @Nonnull final MyComponent entity )
  {
    ////DOC ELIDE START
    return true;
    ////DOC ELIDE END
  }

  @Action
  public void destroy( @Nonnull final MyComponent entity )
  {
    ////DOC ELIDE START
    ////DOC ELIDE END
  }

  @Nonnull
  public final List<MyComponent> findAll()
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public final List<MyComponent> findAll( @Nonnull final Comparator<MyComponent> sorter )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public final List<MyComponent> findAllByQuery( @Nonnull final Predicate<MyComponent> query )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public final List<MyComponent> findAllByQuery( @Nonnull final Predicate<MyComponent> query,
                                                 @Nonnull final Comparator<MyComponent> sorter )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nullable
  public final MyComponent findByQuery( @Nonnull final Predicate<MyComponent> query )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public final MyComponent getByQuery( @Nonnull final Predicate<MyComponent> query )
    throws NoResultException
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nullable
  public MyComponent findById( final int id )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public final MyComponent getById( final int id )
  {
    ////DOC ELIDE START
    return null;
    ////DOC ELIDE END
  }

  @Nonnull
  public final MyComponentRepository self()
  {
    return this;
  }
}
