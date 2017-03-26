package view;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FXViewRootPane extends BorderPane {
  
  private FXViewMenuPane mp;
  private FXViewChartPane chtp;
  private FXViewCurrencyPairPane cupp;
  private FXViewStatusBar sb;
  
  public FXViewRootPane(){
       

    mp = new FXViewMenuPane();
    chtp = new FXViewChartPane();
    cupp = new FXViewCurrencyPairPane();
    cupp.prefWidthProperty().bind(this.widthProperty().divide(7));
    
    sb = new FXViewStatusBar();

    this.setTop(mp);
    this.setLeft(cupp);    
    this.setCenter(chtp); 
    this.setBottom(sb);
    
    
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

  public FXViewStatusBar getStatusBarPane(){
    return sb;
  }
}
