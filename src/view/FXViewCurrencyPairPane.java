package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FXViewCurrencyPairPane extends VBox {


  private Label p1;
  private Label p2;
  private Label p3;
  private Label p4;
  
  public FXViewCurrencyPairPane () {
    
    this.setSpacing(1);

//    Text title = new Text("Currency Pair");
//    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    this.setAlignment(Pos.BASELINE_CENTER);
    this.setStyle("-fx-background-color :  #d9d9d9");
//    this.getChildren().add(title);

    /**********************************************/
    
    Label l1 = new Label("EUR/USD");
    l1.setFont(new Font("Arial", 20));
    
    p1 = new Label("1.06079");
    p1.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    Button b1 = new Button("Buy");
    b1.setMinSize(100,50);
    b1.setMaxSize(100,50);
    b1.setPrefSize(100,50); 
    
    Button b1a = new Button("Sell");
    b1a.setMinSize(100,50);
    b1a.setMaxSize(100,50);
    b1a.setPrefSize(100,50);
    
    HBox hbox1 = new HBox();
    hbox1.getChildren().addAll(b1,b1a);
    
    /**********************************************/
    
    Label l2 = new Label("USD/JPY");
    l2.setFont(new Font("Arial", 20));
    
    p2 = new Label("110.928");
    p2.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    Button b2 = new Button("Buy");
    b2.setMinSize(100,50);
    b2.setMaxSize(100,50);
    b2.setPrefSize(100,50); 
    
    Button b2a = new Button("Sell");
    b2a.setMinSize(100,50);
    b2a.setMaxSize(100,50);
    b2a.setPrefSize(100,50);
    
    HBox hbox2 = new HBox();
    hbox2.getChildren().addAll(b2,b2a);
    
    /**********************************************/
    
    
    Label l3 = new Label("GBP/USD");
    l3.setFont(new Font("Arial", 20));
    
    p3 = new Label("1.21415");
    p3.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    Button b3 = new Button("Buy");
    b3.setMinSize(100,50);
    b3.setMaxSize(100,50);
    b3.setPrefSize(100,50); 
    
    Button b3a = new Button("Sell");
    b3a.setMinSize(100,50);
    b3a.setMaxSize(100,50);
    b3a.setPrefSize(100,50);
    
    HBox hbox3 = new HBox();
    hbox3.getChildren().addAll(b3,b3a);
    
    /**********************************************/
    
      
    Label l4 = new Label("USD/CHF");
    l4.setFont(new Font("Arial", 20));
    
    p4 = new Label("1.00998");
    p4.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    
    Button b4 = new Button("Buy");
    b4.setMinSize(100,50);
    b4.setMaxSize(100,50);
    b4.setPrefSize(100,50); 
    
    Button b4a = new Button("Sell");
    b4a.setMinSize(100,50);
    b4a.setMaxSize(100,50);
    b4a.setPrefSize(100,50);
    
    HBox hbox4 = new HBox();
    hbox4.getChildren().addAll(b4,b4a);
    
    /**********************************************/
    

    // add all uaed here
    this.setSpacing(8);
    this.getChildren().add(l1);
    this.getChildren().add(p1);
    this.getChildren().add(hbox1);
    this.getChildren().add(l2);
    this.getChildren().add(p2); 
    this.getChildren().add(hbox2);
    this.getChildren().add(l3);
    this.getChildren().add(p3);  
    this.getChildren().add(hbox3);
    this.getChildren().add(l4);
    this.getChildren().add(p4); 
    this.getChildren().add(hbox4);

  }
  
  public Label getLabel1 (){
        return p1;
  }
  
  public Label getLabel2 (){
    return p2;
}
  
  public Label getLabel3(){
    return p3;
}
  
  public Label getLabel4(){
    return p4;
}

}
