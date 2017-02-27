package view;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FXViewLoginPage extends GridPane {
  
  private Button b1, b2;
  private TextField t1; 
  private PasswordField p1;
          
    
    public FXViewLoginPage(){
      
        this.getColumnConstraints().add(new ColumnConstraints(200)); 
        this.getColumnConstraints().add(new ColumnConstraints(200));
        
        this.setAlignment(Pos.CENTER);
        //this.setHgap(10);
        this.setVgap(15);
          
        Label l1 = new Label("Email Address ");
        l1.setFont(new Font("Arial", 15)); 
        this.add(l1, 0, 1, 1, 1);
  
        t1 = new TextField();
        t1.setPrefSize(300,30); 
        this.add(t1, 0, 2, 2, 1);
   
        Label l2 = new Label("Password ");
        l2.setFont(new Font("Arial", 15));
        this.add(l2, 0, 3, 3, 1);
    
        p1 = new PasswordField(); 
        p1.setPrefSize(300,30);
        this.add(p1, 0, 4, 4, 1);
             
        b2 = new Button("Register");
        b2.setPrefSize(200,35);  
        this.add(b2, 0, 5);

        b1 = new Button("Logon");
        b1.setPrefSize(200,35);
        this.add(b1, 1, 5);      
        
  
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

  public PasswordField getPassword()
  {
      return p1;
  }
}
