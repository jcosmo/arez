import org.realityforge.arez.annotations.Computed;
import org.realityforge.arez.annotations.Container;
import org.realityforge.arez.annotations.OnActivate;

@Container
public class OnActivatePrivateModel
{
  @Computed
  public int getMyValue()
  {
    return 0;
  }

  @OnActivate
  private void onMyValueActivate()
  {
  }
}