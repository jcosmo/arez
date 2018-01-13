package arez.doc.examples.step4;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Autorun;
import arez.annotations.Observable;

@ArezComponent
public class TrainTicket
{
  private int _remainingRides;

  public static TrainTicket create( int remainingRides )
  {
    return new Arez_TrainTicket( remainingRides );
  }

  TrainTicket( int remainingRides )
  {
    _remainingRides = remainingRides;
  }

  @Observable
  public int getRemainingRides()
  {
    return _remainingRides;
  }

  public void setRemainingRides( int remainingRides )
  {
    _remainingRides = remainingRides;
  }

  @Action
  public void rideTrain()
  {
    setRemainingRides( getRemainingRides() - 1 );
  }

  @Autorun
  public void notifyUserWhenTicketExpires()
  {
    if ( 0 == getRemainingRides() )
    {
      NotifyTool.notifyUserTicketExpired( this );
    }
  }
}