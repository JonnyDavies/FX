package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class FXViewMenuPane extends HBox {
  
  private Button b1, b2, b3;
  
  public FXViewMenuPane(){
    
    
    this.setStyle("-fx-background-color : grey");
    this.setPadding(new Insets(5, 10, 5, 10));
    this.setSpacing(5);
    this.setAlignment(Pos.CENTER_RIGHT);


    b1 = new Button("Logout");
    b1.setMinSize(100,30);
    b1.setMaxSize(100,30);
    b1.setPrefSize(100,30);
    
    b2 = new Button("New Order");
    b2.setMinSize(100,30);
    b2.setMaxSize(100,30);
    b2.setPrefSize(100,30);

    b3 = new Button("Open Chart");
    b3.setMinSize(100,30);
    b3.setMaxSize(100,30);
    b3.setPrefSize(100,30);
    
    this.getChildren().addAll(b3,b2,b1);
  }
  
  public void addLogOutHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }
  
  public void addOpenChartHandler(EventHandler<ActionEvent> handler) {
    b2.setOnAction(handler);
}


}
