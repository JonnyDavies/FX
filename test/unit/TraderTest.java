package unit;
import junit.framework.*;
import static org.junit.Assert.*;
import model.Trader;
import org.junit.Test;



public class TraderTest {
  
  private Trader trader = new Trader();
  
  @Test
  public void gettingEmail()
  {
      trader.setEmail("jonathandavies27@gmail.com");      
      assertEquals("jonathandavies27@gmail.com", trader.getEmail());
  }
  
  @Test 
  public void gettingFirstName()
  {
      trader.setFirstName("Jonny");
      assertEquals("Jonny", trader.getFirstName());
  }
  
  @Test 
  public void gettingLastName()
  {
      trader.setLastName("Davies");
      assertEquals("Davies", trader.getLastName());
  }
  
  @Test 
  public void gettingPassword()
  {
      trader.setPassword("This will be hashed");
      assertEquals("This will be hashed", trader.getPassword());
  }
  
//  @Test 
//  public void gettingEquity()
//  {
//      trader.setEquity(1234.00);
//      assertTrue(1234.00, trader.getEquity());
//  }

  public void traderToString()
  {
    // System.out.println(trader.toString());
  }
}
