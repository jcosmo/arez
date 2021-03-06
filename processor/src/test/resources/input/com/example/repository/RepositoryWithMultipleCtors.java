package com.example.repository;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.annotations.Repository;
import javax.annotation.Nonnull;

@Repository
@ArezComponent
public abstract class RepositoryWithMultipleCtors
{
  @Nonnull
  private String _name;

  RepositoryWithMultipleCtors( @Nonnull final String packageName, @Nonnull final String name )
  {
    _name = name;
  }

  RepositoryWithMultipleCtors( @Nonnull final String name )
  {
    _name = name;
  }

  RepositoryWithMultipleCtors()
  {
    _name = "";
  }

  @Observable
  @Nonnull
  public String getName()
  {
    return _name;
  }

  public void setName( @Nonnull final String name )
  {
    _name = name;
  }
}
