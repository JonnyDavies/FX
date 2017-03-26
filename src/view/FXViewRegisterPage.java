package view;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FXViewRegisterPage extends GridPane {
  
private Button b1, b2;
private TextField t1, t2, t3;
private PasswordField p1, p2;
private Label l1a, l2a, l3a, l4a, l5a; 
  

    public FXViewRegisterPage(){
      
        this.getColumnConstraints().add(new ColumnConstraints(200)); 
        this.getColumnConstraints().add(new ColumnConstraints(200));
        
        this.setAlignment(Pos.CENTER);
        //this.setHgap(10);
        this.setVgap(15);
        this.setStyle("-fx-background-color : #4d4d4d");

          
      
        
        Label l1 = new Label("First Name");
        l1.setFont(Font.font("", FontWeight.BOLD, 15));
        l1.setTextFill(Color.WHITE);

        l1a = new Label("- length between 3 & 15");
        l1a.setVisible(false);
        l1a.setTextFill(Color.web("red"));
        l1a.setPadding(new Insets(0, 0, 0, 5));
        
        this.add(l1, 0, 1);
        this.add(l1a, 1, 1);

                
        t1 = new TextField();    
        t1.setPrefSize(300,30); 
        
        this.add(t1, 0, 2, 2, 1);

        
  
        Label l2 = new Label("Last Name");
        l2.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l2.setTextFill(Color.WHITE);

        l2a = new Label("- length between 3 & 15");
        l2a.setVisible(false);
        l2a.setTextFill(Color.web("red"));
        l2a.setPadding(new Insets(0, 0, 0, 5));
        
        this.add(l2, 0, 3);
        this.add(l2a, 1, 3);
        
        
        t2 = new TextField();    
        t2.setPrefSize(300,30); 

        this.add(t2, 0, 4, 4, 1);
        
        Label l3 = new Label("Email Address");
        l3.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l3.setTextFill(Color.WHITE);

        
        l3a = new Label("- incorrect format");
        l3a.setVisible(false);
        l3a.setTextFill(Color.web("red"));
        l3a.setPadding(new Insets(0, 0, 0, 5));
        
        this.add(l3, 0, 5);
        this.add(l3a, 1, 5);
        
        t3 = new TextField();    
        t3.setPrefSize(300,30); 
        
        this.add(t3, 0, 6, 6, 1);

        Label l4 = new Label("Password ");
        l4.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l4.setTextFill(Color.WHITE);

        
        l4a = new Label("- incorrect format");
        l4a.setVisible(false);
        l4a.setTextFill(Color.web("red"));
        l4a.setPadding(new Insets(0, 0, 0, 5));
        
        this.add(l4, 0, 7);
        this.add(l4a, 1, 7);
        
        p1 = new PasswordField(); 
        p1.setPrefSize(300,30);

        this.add(p1, 0, 8, 8, 1);

        
        Label l5 = new Label("Re-enter Password");
        l5.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l5.setTextFill(Color.WHITE);

        
        l5a = new Label("- incorrect format");
        l5a.setVisible(false);
        l5a.setTextFill(Color.web("red"));
        l5a.setPadding(new Insets(0, 0, 0, 5));
        
        this.add(l5, 0, 9);
        this.add(l5a, 1, 9);
        
        p2 = new PasswordField();
        p2.setPrefSize(300,30); 

        this.add(p2, 0, 10, 10, 1);

               
        b2 = new Button("Register");
        b2.setFont(Font.font("", FontWeight.BOLD, 14));
        b2.setPrefSize(200,35);  
        b2.setPadding( new Insets( 0, 0, 0, 1));

        
        b1 = new Button("Back");
        b1.setFont(Font.font("", FontWeight.BOLD, 14));
        b1.setPrefSize(200,35);
        b1.setPadding( new Insets( 0, 1, 0, 0));

              
        this.add(b2, 1, 11);
        this.add(b1, 0, 11);
        

   }

  
  
  public void addRegisterInfoHandler(EventHandler<ActionEvent> handler) {
    b2.setOnAction(handler);
  }
 
  public void addBackHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }
  
  public TextField getFirstName(){
    return t1;
  }
  
  public TextField getLastName(){
    return t2;
  }
    
  public TextField getEmail(){
    return t3;
  }
     
  public PasswordField getPassword(){
    return p1;
  }
  
  public PasswordField getPasswordConfirmed(){
    return p2;
  }
  
  public Label getErrorFirstName(){
    return l1a;
  }
  
  public Label getErrorLastName(){
    return l2a;
  }
  
  public Label getErrorEmail(){
    return l3a;
  }
     
  public Label getErrorPassword(){
    return l4a;
  }
  
  public Label getErrorPasswordConfirmed(){
    return l5a;
  }

}
