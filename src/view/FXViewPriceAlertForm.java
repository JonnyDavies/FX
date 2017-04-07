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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FXViewPriceAlertForm extends VBox {
  
  private Button b1, b2;
  private ComboBox<String> cb1;
  private TextField t1;
  
  public FXViewPriceAlertForm()
  {
    
    GridPane grid = new GridPane();
    grid.getColumnConstraints().add(new ColumnConstraints(120)); 
    grid.getColumnConstraints().add(new ColumnConstraints(120));
    
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    
    ObservableList<String> options = 
        FXCollections.observableArrayList(
            "EUR/USD",
            "USD/JPY",
            "GBP/USD",
            "USD/CHF"
        );

    cb1 = new ComboBox<String>(options);
    cb1.setPrefSize(120,30);  

    grid.add(cb1, 1, 1);
    
    Label cp = new Label("Currency Pair:");
    cp.setFont(Font.font(13));
    cp.setTextFill(Color.WHITE);

    grid.add(cp, 0, 1);

    t1 = new TextField();
    t1.setPrefSize(120,30);  

    grid.add(t1, 1, 2);
    
    Label p = new Label("Price:");
    p.setTextFill(Color.WHITE);
    cp.setFont(Font.font(13));
    p.setAlignment(Pos.BASELINE_RIGHT);
    grid.add(p, 0, 2);
  
    b1 = new Button("Cancel");
    b1.setPrefSize(130,35);  
    
    b2 = new Button("Confirm");
    b2.setPrefSize(130,35);  

    HBox buttons = new HBox();
    buttons.getChildren().addAll(b1,b2);
    buttons.setAlignment(Pos.CENTER);
    
    Text scenetitle = new Text("Price Alert");
    scenetitle.setFont(new Font("Arial", 24));
    scenetitle.setFill(Color.WHITE);

    
    this.getChildren().addAll(scenetitle, grid, buttons);
    this.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color : #4d4d4d");
    this.setSpacing(10);
  }
  
  public void cancelButtonHandler(EventHandler<ActionEvent> handler)
  {
          b1.setOnAction(handler);
  }
  
              
  public void okButtonHandler(EventHandler<ActionEvent> handler)
  {
          b2.setOnAction(handler);
  }
  
 
  public ComboBox<String> returnCurrencyPair()
  {
          return cb1;
  }
  
  public TextField returnPrice()
  {
          return t1;
  }

}
