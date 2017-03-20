package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FXViewCurrencyPairPane extends VBox {


  private Label p0, p1, p2, p3, p4;
  private Button b1, b1a, b2, b2a, b3, b3a, b4, b4a;
  private ToggleButton tb1, tb2, tb3;
  
  public FXViewCurrencyPairPane () {
    
    /**********************************************/
    
    
    VBox vb = new VBox();
    p0 = new Label("Quantity");
    p0.setFont(new Font("Arial", 20));
    vb.setAlignment(Pos.BASELINE_CENTER);
    vb.setSpacing(5);
    vb.setStyle("-fx-background-color :  #d9d9d9");

    
    tb1 = new ToggleButton("1000");
    tb1.setPrefSize(66,50); 
    tb1.setMinHeight(35);

    tb2 = new ToggleButton("2000");
    tb2.setPrefSize(67,50);
    tb2.setMinHeight(35);
    
    tb3 = new ToggleButton("5000");
    tb3.setPrefSize(67,50);
    tb3.setMinHeight(35);
       
    ToggleGroup group = new ToggleGroup();
    tb1.setToggleGroup(group);
    tb2.setToggleGroup(group);
    tb3.setToggleGroup(group);
    tb1.setSelected(true);
   
    HBox tb = new HBox();
    tb.getChildren().addAll(tb1,tb2,tb3);
   // vb.getChildren().addAll(p0,tb);

    /**********************************************/
    VBox main = new VBox();
    
    main.setAlignment(Pos.BASELINE_CENTER);
    main.setStyle("-fx-background-color :  #d9d9d9");
    
    Label l1 = new Label("EUR/USD");
    l1.setFont(new Font("Arial", 20));
    
    p1 = new Label("1.000");
    p1.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    b1 = new Button("Buy");
    b1.setPrefSize(100,50); 
    
    b1a = new Button("Sell");
    b1a.setPrefSize(100,50);
    
    HBox hbox1 = new HBox();
    hbox1.prefHeightProperty().bind(this.heightProperty().divide(4));
    hbox1.getChildren().addAll(b1,b1a);
    
    /**********************************************/
    
    Label l2 = new Label("USD/JPY");
    l2.setFont(new Font("Arial", 20));
    
    p2 = new Label("110.928");
    p2.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    b2 = new Button("Buy");
    b2.setPrefSize(100,50); 
    
    b2a = new Button("Sell");
    b2a.setPrefSize(100,50);
    
    HBox hbox2 = new HBox();
    hbox2.prefHeightProperty().bind(this.heightProperty().divide(4));
    hbox2.getChildren().addAll(b2,b2a);
    
    /**********************************************/
   
    Label l3 = new Label("GBP/USD");
    l3.setFont(new Font("Arial", 20));
    
    p3 = new Label("1.001");
    p3.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    b3 = new Button("Buy");
    b3.setPrefSize(100,50); 
    
    b3a = new Button("Sell");
    b3a.setPrefSize(100,50);
    
    HBox hbox3 = new HBox();
    hbox3.prefHeightProperty().bind(this.heightProperty().divide(4));
    hbox3.getChildren().addAll(b3,b3a);

    
    /**********************************************/
      
    Label l4 = new Label("USD/CHF");
    l4.setFont(new Font("Arial", 20));
    
    p4 = new Label("1.00998");
    p4.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    b4 = new Button("Buy");
    b4.setPrefSize(100,50); 
    
    b4a = new Button("Sell");
    b4a.setPrefSize(100,50);
    
    HBox hbox4 = new HBox();
    hbox4.prefHeightProperty().bind(this.heightProperty().divide(4));
    hbox4.getChildren().addAll(b4,b4a);
    

    
    /**********************************************/
    // add all uaed here
    main.setSpacing(15);
    main.getChildren().addAll(l1,p1,hbox1,l2,p2,hbox2,l3,p3,hbox3,l4,p4,hbox4);
    this.getChildren().addAll(vb,main,tb);

  }
  
  public Label getLabel1 (){
        return p1;
  }
  
  public void setLabel1Text (String text){
     p1.setText(text);
  }
  
  public Label getLabel2 (){
    return p2;
}
  public void setLabel2Text (String text){
    p2.setText(text);
 }
  
  public Label getLabel3(){
    return p3;
}
  public void setLabel3Text (String text){
    p3.setText(text);
 }
  
  public Label getLabel4(){
    return p4;
}
  public void setLabel4Text (String text){
    p4.setText(text);
 }
  
  public ToggleButton getQuantityToggle1()
  {
    return tb1;
  }
  
  public ToggleButton getQuantityToggle2()
  {
    return tb2;
  }
  
  public ToggleButton getQuantityToggle3()
  {
    return tb3;
  }
  
  public Button getEURUSDBuyButton()
  {
    return b1;
  }
  
  public Button getEURUSDSellButton()
  {
    return b1a;
  }
  
  public Button getUSDJPYBuyButton()
  {
    return b2;
  }
  
  public Button getUSDJPYSellButton()
  {
    return b2a;
  }

  public Button getGBPUSDBuyButton()
  {
    return b3;
  }
  
  public Button getGBPUSDSellButton()
  {
    return b3a;
  }

  public Button getUSDCHFBuyButton()
  {
    return b4;
  }
  
  public Button getUSDCHFSellButton()
  {
    return b4a;
  }
  
  
  public void setEURUSDBuyButtonHandler(EventHandler<ActionEvent> handler)
  {
     b1.setOnAction(handler);
  }
  
  public void setEURUSDSellButtonHandler(EventHandler<ActionEvent> handler)
  {
     b1a.setOnAction(handler);
  }
  
  public void setUSDJPYBuyButtonHandler(EventHandler<ActionEvent> handler)
  {
     b2.setOnAction(handler);
  }
  
  public void setUSDJPYSellButtonHandler(EventHandler<ActionEvent> handler)
  {
     b2a.setOnAction(handler);
  }

  public void setGBPUSDBuyButtonHandler(EventHandler<ActionEvent> handler)
  {
     b3.setOnAction(handler);
  }
  
  public void setGBPUSDSellButtonHandler(EventHandler<ActionEvent> handler)
  {
     b3a.setOnAction(handler);
  }

  public void setUSDCHFBuyButtonHandler(EventHandler<ActionEvent> handler)
  {
     b4.setOnAction(handler);
  }
  
  public void setUSDCHFSellButtonHandler(EventHandler<ActionEvent> handler)
  {
     b4a.setOnAction(handler);
  }



}
