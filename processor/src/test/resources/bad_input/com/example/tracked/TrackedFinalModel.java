package com.example.tracked;

import arez.annotations.ArezComponent;
import arez.annotations.OnDepsChanged;
import arez.annotations.Track;

@ArezComponent
public class TrackedFinalModel
{
  @Track
  public final void render()
  {
  }

  @OnDepsChanged
  public void onRenderDepsChanged()
  {
  }
}
