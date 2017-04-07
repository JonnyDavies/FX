package view;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FXViewLoginPage extends GridPane {
  
  private Button b1, b2;
  private TextField t1; 
  private PasswordField p1;
  private Label l0;
          
    
    public FXViewLoginPage(){
      
      
        
        this.getColumnConstraints().add(new ColumnConstraints(200)); 
        this.getColumnConstraints().add(new ColumnConstraints(200));
        
        this.setAlignment(Pos.CENTER);
        //this.setHgap(10);
        this.setVgap(10);
        this.setStyle("-fx-background-color : #4d4d4d");
        
        l0 = new Label();        
        l0.setFont(Font.font("", FontWeight.BOLD, 15));
        l0.setTextFill(Color.WHITE);
        l0.setPrefSize(500,30); 
        l0.setStyle("-fx-background-color : green");
        l0.setAlignment(Pos.CENTER);
        l0.setVisible(false);
        this.add(l0, 0, 1, 2, 1);
          
        
        Label l1 = new Label("Email Address ");
        l1.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l1.setTextFill(Color.WHITE);
        this.add(l1, 0, 2, 2, 1);
  
        t1 = new TextField();
        t1.setPrefSize(300,30); 
        this.add(t1, 0, 3, 3, 1);
   
        Label l2 = new Label("Password ");
        l2.setFont(Font.font("", FontWeight.BOLD, 15));
        l2.setTextFill(Color.WHITE);
        this.add(l2, 0, 4, 4, 1);
    
        p1 = new PasswordField(); 
        p1.setPrefSize(300,30);
        this.add(p1, 0, 5, 5, 1);
             
        b2 = new Button("Register");
        b2.setFont(Font.font("", FontWeight.BOLD, 14));
        b2.setPrefSize(200,35);  
        b2.setPadding(new Insets(4));

        this.add(b2, 0, 6);

        b1 = new Button("Logon");
        b1.setFont(Font.font("", FontWeight.BOLD, 14));
        b1.setPrefSize(200,35);
        b1.setPadding(new Insets( 0, 0, 0, 1 ));
        this.add(b1, 1, 6);      

  }

  
  public void addRegisterHandler(EventHandler<ActionEvent> handler) {
       b2.setOnAction(handler);
  }
    
  public void addLoginHandler(EventHandler<ActionEvent> handler) {
       b1.setOnAction(handler);
  }
  
  public TextField getEmail()
  {
      return t1;
  }
  
  public Label getInfoLabel()
  {
      return l0;
  }

  public PasswordField getPassword()
  {
      return p1;
  }
}
