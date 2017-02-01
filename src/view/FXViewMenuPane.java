package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FXViewMenuPane extends HBox {
  
  private Button b1;
  
  public FXViewMenuPane(){
    
    
    this.setStyle("-fx-background-color : grey");
    this.setPadding(new Insets(5, 0, 5, 0));
    this.setAlignment(Pos.CENTER_RIGHT);     // Right-justify 

    b1 = new Button("Logout");
    b1.setMinSize(100,30);
    b1.setMaxSize(100,30);
    b1.setPrefSize(100,30);
    
    this.getChildren().addAll(b1);
  }
  
  public void addLogOutHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }


}
