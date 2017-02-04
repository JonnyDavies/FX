package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXViewNewOrderForm extends VBox {
  
  private Button b1, b2;
  private Spinner<Double> sptp, spsl; 
  private ComboBox<String> cb1, cb2;
  
  public FXViewNewOrderForm ()
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
    grid.add(cp, 0, 1);
    
    ObservableList<String> qty = 
        FXCollections.observableArrayList(
            "2,000",
            "4,000",
            "10,000",
            "20,000",
            "40,000",
            "100,000"
        );

    cb2 = new ComboBox<String>(qty);
    cb2.setPrefSize(120,30);  

    grid.add(cb2, 1, 2);
    Label q = new Label("Quantity:");
    
    grid.add(q, 0, 2);

    ToggleGroup tg = new ToggleGroup();
    RadioButton tp = new RadioButton("Take Profit");
    tp.setToggleGroup(tg);
    grid.add(tp, 0, 3);

    RadioButton sl = new RadioButton("Stop Loss");
    sl.setToggleGroup(tg);
    grid.add(sl, 1, 3);
    
    sptp = new Spinner<>(0.0, 10.0, 0.0, 0.01);
    sptp.setPrefSize(120,30);  
    grid.add(sptp, 0, 4);

    spsl = new Spinner<>(0.0, 10.0, 0.0, 0.01);
    spsl.setPrefSize(120,30);  
    grid.add(spsl, 1, 4);
    
    b1 = new Button("Sell");
    b1.setPrefSize(130,35);  
    
    b2 = new Button("Buy");
    b2.setPrefSize(130,35);  

    HBox buttons = new HBox();
    buttons.getChildren().addAll(b1,b2);
    buttons.setAlignment(Pos.CENTER);
    
    Text scenetitle = new Text("New Order");
    scenetitle.setFont(new Font("Arial", 24));
    
    this.getChildren().addAll(scenetitle, grid, buttons);
    this.setAlignment(Pos.CENTER);
    this.setSpacing(10);
  }
  
  public void sellButtonHandler(EventHandler<ActionEvent> handler)
  {
          b1.setOnAction(handler);
  }
  
              
  public void buyButtonHandler(EventHandler<ActionEvent> handler)
  {
          b2.setOnAction(handler);
  }
  
  public Spinner<Double> returnTakeProfit()
  {
          return sptp;
  }

  public Spinner<Double> returnStopLoss()
  {
          return spsl;
  }
  
  public ComboBox<String> returnCurrencyPair()
  {
          return cb1;
  }

  public ComboBox<String> returnQuantity()
  {
          return cb2;
  }


}
