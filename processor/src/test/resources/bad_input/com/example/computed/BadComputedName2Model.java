package com.example.computed;

import arez.annotations.ArezComponent;
import arez.annotations.Computed;

@ArezComponent
public abstract class BadComputedName2Model
{
  @Computed( name = "public" )
  public int setField()
  {
    return 0;
  }
}
