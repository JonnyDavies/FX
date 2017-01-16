package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FXViewMenuPane extends HBox {
  
  public FXViewMenuPane (){
    
    
    this.setStyle("-fx-background-color : grey");
    this.setPadding(new Insets(5, 0, 5, 0));
    this.setAlignment(Pos.CENTER_RIGHT);     // Right-justify 

    Button m1 = new Button("Menu Test 1");
    m1.setMinSize(100,30);
    m1.setMaxSize(100,30);
    m1.setPrefSize(100,30);
    
    Button m2 = new Button("Menu Test 1");
    m2.setMinSize(100,30);
    m2.setMaxSize(100,30);
    m2.setPrefSize(100,30);
    
    this.getChildren().addAll(m1, m2);
    
  }

}
