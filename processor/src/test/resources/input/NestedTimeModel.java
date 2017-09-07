import org.realityforge.arez.annotations.Action;
import org.realityforge.arez.annotations.Container;
import org.realityforge.arez.annotations.Observable;

public class NestedTimeModel
{
  @Container
  public static class TimeModel
  {
    private long _time;

    protected TimeModel( final long time )
    {
      _time = time;
    }

    @Observable
    public long getTime()
    {
      return _time;
    }

    @Observable
    public void setTime( final long time )
    {
      _time = time;
    }

    @Action
    public void updateTime()
    {
      setTime( System.currentTimeMillis() );
    }
  }
}
