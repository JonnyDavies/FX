package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class FXViewSettingsForm extends VBox {
  
  private Button b1, b2;

  private PasswordField t1, t2;
  
  private Label l0, ep, l1, l2, ecp, l3, l4, l5;
  
  public FXViewSettingsForm()
  {
    
    GridPane grid = new GridPane();
    grid.getColumnConstraints().add(new ColumnConstraints(100)); 
    grid.getColumnConstraints().add(new ColumnConstraints(200));

    
    grid.setAlignment(Pos.CENTER_RIGHT);
    grid.setVgap(15);
    
    
    Label tn = new Label(" First Name: ");
    tn.setFont(Font.font("", FontWeight.BOLD, 14));
    tn.setTextFill(Color.WHITE);
    tn.setAlignment(Pos.CENTER_RIGHT);
    grid.add(tn, 0, 1);

    
    l1 = new Label("");
    l1.setFont(Font.font(14));
    l1.setTextFill(Color.WHITE);  
    grid.add(l1, 1, 1);
        
    
    Label te = new Label(" Last Name: ");
    te.setFont(Font.font("", FontWeight.BOLD, 14));
    te.setTextFill(Color.WHITE);
    te.setAlignment(Pos.BASELINE_RIGHT);
    grid.add(te, 0, 2);

    l2 = new Label("");
    l2.setFont(Font.font(14));
    l2.setTextFill(Color.WHITE);
    grid.add(l2, 1, 2);
    
    Label em = new Label(" Email: ");
    em.setFont(Font.font("", FontWeight.BOLD, 14));
    em.setTextFill(Color.WHITE);
    grid.add(em, 0, 3);

    l3 = new Label("jonathandavies27@gmail.com");
    l3.setFont(Font.font(14));
    l3.setTextFill(Color.WHITE);
    grid.add(l3, 1, 3);
    
    Label ot = new Label(" Order Total: ");
    ot.setFont(Font.font("", FontWeight.BOLD, 14));
    ot.setTextFill(Color.WHITE);
    grid.add(ot, 0, 4);

    l4 = new Label("");
    l4.setFont(Font.font(14));
    l4.setTextFill(Color.WHITE);
    grid.add(l4, 1, 4);
    
    Label eq = new Label(" Equity: ");
    eq.setFont(Font.font("", FontWeight.BOLD, 14));
    eq.setTextFill(Color.WHITE);
    grid.add(eq, 0, 5);

    l5 = new Label("");
    l5.setFont(Font.font(14));
    l5.setTextFill(Color.WHITE);
    grid.add(l5, 1, 5);
    
    
    b1 = new Button("Back");
    b1.setFont(Font.font("", FontWeight.BOLD, 13));
    b1.setPrefSize(200,35);  
    b1.setAlignment(Pos.CENTER);
       
    Text scenetitle = new Text("Account Information");
    scenetitle.setFont(new Font("Arial", 30));
    scenetitle.setFill(Color.WHITE);
  
    this.getChildren().addAll(scenetitle, grid, b1);
    this.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color : #4d4d4d");
    this.setSpacing(15);
  }
  
  public void cancelSettingsButtonHandler(EventHandler<ActionEvent> handler)
  {
          b1.setOnAction(handler);
  }
  
              
  public void changePasswordButtonHandler(EventHandler<ActionEvent> handler)
  {
          b2.setOnAction(handler);
  }
  
  public Label getFirstNameLabel ()
  {
          return l1;
  }
  
  public Label getLastNameLabel ()
  {
          return l2;
  }
  
  public Label getEmailLabel ()
  {
          return l3;
  }
  
  public Label getOrdersLabel ()
  {
          return l4;
  }
  
  public Label getEquityLabel ()
  {
          return l5;
  }
}
