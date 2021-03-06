package com.example.computed_value_ref;

import arez.ComputedValue;
import arez.annotations.ArezComponent;
import arez.annotations.Computed;
import arez.annotations.ComputedValueRef;
import javax.annotation.Nonnull;

@ArezComponent
public abstract class BadNameModel
{
  @Computed
  public long getTime()
  {
    return 0;
  }

  @Nonnull
  @ComputedValueRef( name = "-ace" )
  abstract ComputedValue getTimeComputedValue();
}
