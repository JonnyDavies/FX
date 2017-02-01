package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FXViewRootPane extends BorderPane {
  
  private FXViewMenuPane mp;
  private FXViewChartPane chtp;
  private FXViewCurrencyPairPane cupp;
  private FXViewOrderPane op;
  
  
  public FXViewRootPane(){
   
    VBox center = new VBox();
    
    mp = new FXViewMenuPane();
    chtp = new FXViewChartPane();
    cupp = new FXViewCurrencyPairPane();
    this.setTop(mp);
    this.setLeft(cupp);    
    this.setCenter(chtp);    
  }
  
  public FXViewMenuPane getMenuPane(){
    return mp;
  }
  
  public FXViewChartPane getChartPane(){
    return chtp;
  }
  
  public FXViewCurrencyPairPane getCurrencyPairPane(){
    return cupp;
  }

}
