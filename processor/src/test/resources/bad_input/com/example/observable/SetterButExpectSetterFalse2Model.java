package com.example.observable;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;

@ArezComponent
public abstract class SetterButExpectSetterFalse2Model
{
  private long _field;

  @Observable
  public void setField( final long field )
  {
    _field = field;
  }

  @Observable( expectSetter = false )
  public long getField()
  {
    return _field;
  }
}
