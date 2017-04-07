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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXViewNewOrderForm extends VBox {
  
  private Button b1, b2, tp, sl;
  private Spinner<Double> sptp, spsl; 
  private ComboBox<String> cb1, cb2;
  private Label l0;
  
  public FXViewNewOrderForm ()
  {
    
    GridPane grid = new GridPane();
    grid.getColumnConstraints().add(new ColumnConstraints(120)); 
    grid.getColumnConstraints().add(new ColumnConstraints(120));
    
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
      
    l0 = new Label("Select Currency Pair and Quantity");        
    l0.setFont(Font.font("", FontWeight.BOLD, 15));
    l0.setTextFill(Color.WHITE);
    l0.setPrefSize(260,30); 
    l0.setStyle("-fx-background-color : red");
    l0.setAlignment(Pos.CENTER);
    l0.setVisible(false);
    grid.add(l0, 0, 1, 2, 1);
        
    ObservableList<String> options = 
        FXCollections.observableArrayList(
            "EUR/USD",
            "USD/JPY",
            "GBP/USD",
            "USD/CHF"
        );

    cb1 = new ComboBox<String>(options);
    cb1.setPrefSize(120,30);  

    grid.add(cb1, 1, 2);
    Label cp = new Label("Currency Pair:");
    cp.setTextFill(Color.WHITE);
    cp.setFont(Font.font(13));

    grid.add(cp, 0, 2);
    
    ObservableList<String> qty = 
        FXCollections.observableArrayList (
            "2000",
            "4000",
            "6000",
            "8000",
            "10000"
        );

    cb2 = new ComboBox<String>(qty);
    cb2.setPrefSize(120,30);  

    grid.add(cb2, 1, 3);
    Label q = new Label("Quantity:");
    q.setTextFill(Color.WHITE);
    q.setFont(Font.font(13));


    
    grid.add(q, 0, 3);

    tp = new Button("Take Profit");
    tp.setPrefSize(120,30); 
    grid.add(tp, 0, 4);
    
    sl = new Button("Stop Loss");
    sl.setPrefSize(120,30); 
    grid.add(sl, 1, 4);
    
    sptp = new Spinner<>(0.8, 3.0, 1.2, 0.010);
    sptp.setPrefSize(120,30);  
    sptp.setDisable(true);
    grid.add(sptp, 0, 5);

    spsl = new Spinner<>(0.5, 3.0, 1.0, 0.010);
    spsl.setPrefSize(120,30);  
    spsl.setDisable(true);
    grid.add(spsl, 1, 5);
    
    b1 = new Button("Sell");
    b1.setPrefSize(130,35);  
    
    b2 = new Button("Buy");
    b2.setPrefSize(130,35);  

    HBox buttons = new HBox();
    buttons.getChildren().addAll(b1,b2);
    buttons.setAlignment(Pos.CENTER);
    
    Text scenetitle = new Text("New Order");
    scenetitle.setFont(new Font("Arial", 26));
    scenetitle.setFill(Color.WHITE);

    
    this.getChildren().addAll(scenetitle, grid, buttons);
    this.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color : #4d4d4d");
    this.setSpacing(10);
  }
  
  public void sellNewOrderButtonHandler(EventHandler<ActionEvent> handler)
  {
          b1.setOnAction(handler);
  }
                
  public void buyNewOrderButtonHandler(EventHandler<ActionEvent> handler)
  {
          b2.setOnAction(handler);
  }
  
  public void takeProfitButtonHandler(EventHandler<ActionEvent> handler)
  {
          tp.setOnAction(handler);
  }
               
  public void stopLossButtonHandler(EventHandler<ActionEvent> handler)
  {
          sl.setOnAction(handler);
  }
  
  public Spinner<Double> returnTakeProfit()
  {
          return this.sptp;
  }

  public Spinner<Double> returnStopLoss()
  {
          return this.spsl;
  }
  
  public void setDisableStopLoss(Boolean bool)
  {
           this.spsl.setDisable(bool);
  }
  
  public void setDisableTakeProfit(Boolean bool)
  {
           this.sptp.setDisable(bool);
  }
  
  public ComboBox<String> returnCurrencyPair()
  {
          return cb1;
  }

  public ComboBox<String> returnQuantity()
  {
          return cb2;
  }

  public Label returnErrorLabel()
  {
          return l0;
  }

}
