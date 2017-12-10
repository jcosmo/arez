package org.realityforge.arez.extras.spy;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.arez.spy.ComponentInfo;
import org.realityforge.arez.spy.ComputedValueInfo;
import org.realityforge.arez.spy.ObservableInfo;
import org.realityforge.arez.spy.ObserverInfo;
import org.realityforge.guiceyloops.shared.ValueUtil;

final class NullObserverInfo
  implements ObserverInfo
{
  @Override
  public boolean isRunning()
  {
    return false;
  }

  @Override
  public boolean isScheduled()
  {
    return false;
  }

  @Override
  public boolean isComputedValue()
  {
    return false;
  }

  @Override
  public boolean isReadOnly()
  {
    return false;
  }

  @Override
  public ComputedValueInfo asComputedValue()
  {
    return null;
  }

  @Nonnull
  @Override
  public List<ObservableInfo> getDependencies()
  {
    return Collections.emptyList();
  }

  @Nullable
  @Override
  public ComponentInfo getComponent()
  {
    return null;
  }

  @Nonnull
  @Override
  public String getName()
  {
    return ValueUtil.randomString();
  }

  @Override
  public boolean isDisposed()
  {
    return false;
  }
}
