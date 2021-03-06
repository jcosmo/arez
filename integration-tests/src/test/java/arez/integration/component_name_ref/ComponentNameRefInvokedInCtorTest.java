package arez.integration.component_name_ref;

import arez.annotations.ArezComponent;
import arez.annotations.ComponentNameRef;
import arez.integration.AbstractIntegrationTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ComponentNameRefInvokedInCtorTest
  extends AbstractIntegrationTest
{
  @ArezComponent( allowEmpty = true )
  static abstract class TestComponent
  {
    TestComponent()
    {
      getName();
    }

    @ComponentNameRef
    abstract String getName();
  }

  @Test
  public void scenario()
    throws Throwable
  {
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, ComponentNameRefInvokedInCtorTest_Arez_TestComponent::new );
    assertEquals( exception.getMessage(),
                  "Method named 'getName' invoked on uninitialized component of type 'TestComponent'" );
  }
}
