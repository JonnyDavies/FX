package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FXViewPriceAlertConfirmation extends VBox {
  
  private Button b1, b2;
  private ComboBox<String> cb1;
  private TextField t1;
  
  public FXViewPriceAlertConfirmation()
  {
    
    GridPane grid = new GridPane();
    grid.getColumnConstraints().add(new ColumnConstraints(120)); 
    grid.getColumnConstraints().add(new ColumnConstraints(120));
    
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    

    
    Label cp = new Label("Currency Pair:");
    grid.add(cp, 0, 1);

  
    b1 = new Button("Cancel");
    b1.setPrefSize(130,35);  
    
    b2 = new Button("Ok");
    b2.setPrefSize(130,35);  

    HBox buttons = new HBox();
    buttons.getChildren().addAll(b1,b2);
    buttons.setAlignment(Pos.CENTER);
    
    Text scenetitle = new Text("Price Alert");
    scenetitle.setFont(new Font("Arial", 24));
    
    this.getChildren().addAll(scenetitle, grid, buttons);
    this.setAlignment(Pos.CENTER);
    this.setSpacing(10);
  }
  
  public void cancelButtonHandler(EventHandler<ActionEvent> handler)
  {
          b1.setOnAction(handler);
  }
  
              
  public void okConfirmButtonHandler(EventHandler<ActionEvent> handler)
  {
          b2.setOnAction(handler);
  }
 

}
