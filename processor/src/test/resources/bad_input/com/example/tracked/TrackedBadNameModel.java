package com.example.tracked;

import org.realityforge.arez.annotations.ArezComponent;
import org.realityforge.arez.annotations.OnDepsUpdated;
import org.realityforge.arez.annotations.Track;

@ArezComponent
public class TrackedBadNameModel
{
  @Track(name = "-ace")
  static void render()
  {
  }

  @OnDepsUpdated
  public void onRenderDepsUpdated()
  {
  }
}
